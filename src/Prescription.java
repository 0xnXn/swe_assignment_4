import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Prescription {
    private float sphere;
    private float axis;
    private float cylinder;
    private Date examinationDate;
    private int prescID;
    private String firstName;
    private String lastName;
    private String address;

    private String optometrist;
    private String[] remarkTypes = {"Client", "Optometrist"};
    private ArrayList<String> postRemarks = new ArrayList<>();

    // Attributes for remark
    private String remarkText;
    private String remarkType;


    public boolean addPrescription() throws IllegalArgumentException {
        List<String> errorList = new ArrayList<>();

        // Validate First Name and Last Name
        if (this.firstName == null || this.firstName.trim().isEmpty()) {
            errorList.add("Please enter the first name.");
        } else if (this.firstName.length() < 4 || this.firstName.length() > 15) {
            errorList.add("First name must be between 4 and 15 characters long.");
        }

        if (this.lastName == null || this.lastName.trim().isEmpty()) {
            errorList.add("Please enter the last name.");
        } else if (this.lastName.length() < 4 || this.lastName.length() > 15) {
            errorList.add("Last name must be between 4 and 15 characters long.");
        }

        // Validate Address
        if (this.address == null || this.address.trim().isEmpty()) {
            errorList.add("Address cannot be empty.");
        } else if (this.address.length() < 20) {
            errorList.add("Address should be at least 20 characters long.");
        }

        // Validate Sphere, Cylinder, Axis
        if (this.sphere < -20.00f || this.sphere > 20.00f) {
            errorList.add("Sphere value must be between -20.00 and 20.00.");
        }
        if (this.cylinder < -4.00f || this.cylinder > 4.00f) {
            errorList.add("Cylinder value must be between -4.00 and 4.00.");
        }
        if (this.axis < 0.0f || this.axis > 180.0f) {
            errorList.add("Axis value must be between 0 and 180.");
        }

        // Validate Examination Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        dateFormat.setLenient(false); // Strict date parsing
        String dateStr;
        if (this.examinationDate != null) {
            try {
                dateStr = dateFormat.format(this.examinationDate);
            } catch (Exception e) {
                errorList.add("There was an issue formatting the examination date.");
                dateStr = "N/A";
            }
        } else {
            errorList.add("Please provide a valid examination date in the format dd/MM/yy.");
            dateStr = "N/A";
        }

        // Validate Optometrist Name
        if (this.optometrist == null || this.optometrist.trim().isEmpty()) {
            errorList.add("Optometrist name cannot be empty.");
        } else if (this.optometrist.length() < 8 || this.optometrist.length() > 25) {
            errorList.add("Optometrist name must be between 8 and 25 characters long.");
        }

        // If there are validation errors, print them and throw an exception
        if (!errorList.isEmpty()) {
            System.out.println("Please address the following issues before proceeding:");
            for (String error : errorList) {
                System.out.println("- " + error);
            }
            throw new IllegalArgumentException(String.join("\n", errorList));
        }

        // If all conditions are valid, write the data to the file
        try (FileWriter fw = new FileWriter("presc.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            // Write prescription details to the file
            out.println("Prescription ID: " + this.prescID);
            out.println("First Name: " + this.firstName);
            out.println("Last Name: " + this.lastName);
            out.println("Address: " + this.address);
            out.println("Sphere: " + this.sphere);
            out.println("Cylinder: " + this.cylinder);
            out.println("Axis: " + this.axis);
            out.println("Examination Date: " + dateStr);
            out.println("Optometrist: " + this.optometrist + "\n");

            System.out.println("Prescription successfully added!\n");
        } catch (IOException e) {
            System.err.println("An error occurred while saving the prescription: " + e.getMessage());
            return false;
        }

        return true; // If all went well
    }

    public boolean addRemark() throws IllegalArgumentException {
        List<String> errorList = new ArrayList<>();

        // Check if remarks are less than 2
        if (postRemarks.size() >= 2) {
            errorList.add("You can only add up to 2 remarks.");
        }

        // Validate Remark Type
        if (remarkType == null || remarkType.trim().isEmpty()) {
            errorList.add("Please specify the type of remark (Client or Optometrist).");
        } else {
            String remarkTypeLower = remarkType.toLowerCase();
            if (!remarkTypeLower.equals("client") && !remarkTypeLower.equals("optometrist")) {
                errorList.add("Remark type must be either 'Client' or 'Optometrist'.");
            }
        }

        // Validate Remark Text
        if (remarkText == null || remarkText.trim().isEmpty()) {
            errorList.add("Remark text cannot be empty.");
        } else {
            String[] words = remarkText.trim().split("\\s+");
            int wordCount = words.length;
            if (wordCount < 6 || wordCount > 20) {
                errorList.add("Remark must be between 6 and 20 words.");
            }
            // Check if the first character of the first word is uppercase
            if (wordCount > 0 && !Character.isUpperCase(words[0].charAt(0))) {
                errorList.add("The first word of the remark should start with a capital letter.");
            }
        }

        // If there are validation errors, print them and throw an exception
        if (!errorList.isEmpty()) {
            System.out.println("Please correct the following issues with your remark:");
            for (String error : errorList) {
                System.out.println("- " + error);
            }
            throw new IllegalArgumentException(String.join("\n", errorList));
        }

        // All validations passed, add the remark
        postRemarks.add(remarkText);

        // Write the remark to remark.txt
        try (FileWriter fw = new FileWriter("remark.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            out.println("Prescription ID: " + prescID);
            out.println("Remark Type: " + capitalize(remarkType));
            out.println("Remark: " + remarkText + "\n");

            System.out.println("Remark successfully added!\n");
        } catch (IOException e) {
            System.err.println("An error occurred while saving the remark: " + e.getMessage());
            return false;
        }

        return true;
    }

    // Helper method to capitalize the first letter
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    // Setters for testing purposes
    public void setPrescID(int prescID) {
        this.prescID = prescID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSphere(float sphere) {
        this.sphere = sphere;
    }

    public void setAxis(float axis) {
        this.axis = axis;
    }

    public void setCylinder(float cylinder) {
        this.cylinder = cylinder;
    }

    public void setExaminationDate(Date examinationDate) {
        this.examinationDate = examinationDate;
    }

    public void setOptometrist(String optometrist) {
        this.optometrist = optometrist;
    }

    public void setRemarkText(String remarkText) {
        this.remarkText = remarkText;
    }

    public void setRemarkType(String remarkType) {
        this.remarkType = remarkType;
    }


     // Main method for user interaction.

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Prescription prescription = new Prescription();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        dateFormat.setLenient(false); // Strict date parsing

        try {
            // Collect Prescription ID
            System.out.print("Please enter the Prescription ID (integer): ");
            String prescIDInput = scanner.nextLine();
            int prescID;
            try {
                prescID = Integer.parseInt(prescIDInput);
                prescription.setPrescID(prescID);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Prescription ID must be an integer.");
                return;
            }

            // Collect First Name
            System.out.print("Enter First Name (4-15 characters): ");
            String firstName = scanner.nextLine();
            prescription.setFirstName(firstName);

            // Collect Last Name
            System.out.print("Enter Last Name (4-15 characters): ");
            String lastName = scanner.nextLine();
            prescription.setLastName(lastName);

            // Collect Address
            System.out.print("Enter Address (at least 20 characters): ");
            String address = scanner.nextLine();
            prescription.setAddress(address);

            // Collect Sphere
            System.out.print("Enter Sphere value (-20.00 to 20.00): ");
            String sphereInput = scanner.nextLine();
            float sphere;
            try {
                sphere = Float.parseFloat(sphereInput);
                prescription.setSphere(sphere);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Sphere value must be a number (float).");
                return;
            }

            // Collect Cylinder
            System.out.print("Enter Cylinder value (-4.00 to 4.00): ");
            String cylinderInput = scanner.nextLine();
            float cylinder;
            try {
                cylinder = Float.parseFloat(cylinderInput);
                prescription.setCylinder(cylinder);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Cylinder value must be a number (float).");
                return;
            }

            // Collect Axis
            System.out.print("Enter Axis value (0 to 180): ");
            String axisInput = scanner.nextLine();
            float axis;
            try {
                axis = Float.parseFloat(axisInput);
                prescription.setAxis(axis);
            } catch (NumberFormatException e) {
                System.err.println("Invalid input. Axis value must be a number (float).");
                return;
            }

            // Collect Examination Date
            System.out.print("Enter Examination Date (dd/MM/yy): ");
            String dateInput = scanner.nextLine();
            Date examinationDate = null;
            try {
                examinationDate = dateFormat.parse(dateInput);
                prescription.setExaminationDate(examinationDate);
            } catch (ParseException e) {
                System.err.println("Invalid date format. Please enter the date in dd/MM/yy format.");
                // Proceeding with null examinationDate, which will trigger validation
                prescription.setExaminationDate(null);
            }

            // Collect Optometrist Name
            System.out.print("Enter Optometrist Name (8-25 characters): ");
            String optometrist = scanner.nextLine();
            prescription.setOptometrist(optometrist);

            // Add Prescription
            try {
                boolean prescriptionAdded = prescription.addPrescription();

                if (prescriptionAdded) {
                    // Allow adding remarks
                    while (true) {
                        System.out.print("Would you like to add a remark? (yes/no): ");
                        String addRemarkChoice = scanner.nextLine().trim().toLowerCase();
                        if (!addRemarkChoice.equals("yes")) {
                            break;
                        }

                        // Collect Remark Type
                        System.out.print("Specify Remark Type (Client/Optometrist): ");
                        String remarkType = scanner.nextLine();
                        prescription.setRemarkType(remarkType);

                        // Collect Remark Text
                        System.out.print("Enter Remark Text (6-20 words, starts with a capital letter): ");
                        String remarkText = scanner.nextLine();
                        prescription.setRemarkText(remarkText);

                        // Add Remark
                        try {
                            prescription.addRemark();
                        } catch (IllegalArgumentException iae) {
                            System.out.println("Unable to add remark due to the following issues:");
                            String[] errors = iae.getMessage().split("\n");
                            for (String error : errors) {
                                System.out.println("- " + error);
                            }
                        }

                        if (prescription.postRemarks.size() >= 2) {
                            System.out.println("You have reached the maximum of 2 remarks.");
                            break;
                        }
                    }
                }
            } catch (IllegalArgumentException iae) {
                System.out.println("Could not add prescription because of these issues:");
                String[] errors = iae.getMessage().split("\n");
                for (String error : errors) {
                    System.out.println("- " + error);
                }
            }

        } finally {
            scanner.close();
        }
    }
}
