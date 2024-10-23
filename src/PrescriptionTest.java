import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class PrescriptionTest {

    private Prescription prescription;

    @BeforeEach
    public void setUp() {
        prescription = new Prescription();
        prescription.setPrescID(1);
    }


    @Test
    public void test1_AddPrescription_ValidData() {
        // Test Data 1
        prescription.setFirstName("nixon");
        prescription.setLastName("paulson");
        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");
        prescription.setSphere(-4.00f);
        prescription.setCylinder(-2.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("doctor Raghav");

        // Assert that addPrescription() does not throw an exception and returns true
        assertDoesNotThrow(() -> {
            boolean result1 = prescription.addPrescription();
            assertTrue(result1, "Valid prescription should return true");
        });

        // Reset for Test Data 2
        prescription = new Prescription();
        prescription.setPrescID(2);

        // Test Data 2
        prescription.setFirstName("kishor");
        prescription.setLastName("Rathod ");
        prescription.setSphere(2.50f);
        prescription.setCylinder(1.50f);
        prescription.setAxis(45.0f);
        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("Doctor amit patel");

        // Assert that addPrescription() does not throw an exception and returns true
        assertDoesNotThrow(() -> {
            boolean result2 = prescription.addPrescription();
            assertTrue(result2, "Valid prescription should return true");
        });
    }


    @Test
    public void test2_AddPrescription_InvalidFirstNameLength() {
        // Test Data 1: First Name too short
        prescription.setFirstName("nix"); // 3 characters
        prescription.setLastName("paulson");
        prescription.setAddress("911,address,inAstate,InAcountry,Incontinent");
        prescription.setSphere(-5.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("doctor Raghav");

        // Expect IllegalArgumentException
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addPrescription();
        });
        assertTrue(exception1.getMessage().contains("First name must be between 4 and 15 characters long."),
                "Exception message should contain first name length error");

        // Reset for Test Data 2
        prescription = new Prescription();
        prescription.setPrescID(2);
        prescription.setFirstName("Alexanderthegreat"); // 17 characters
        prescription.setLastName("Paulson");

        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");


        prescription.setSphere(2.50f);
        prescription.setCylinder(1.50f);
        prescription.setAxis(45.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("doctor Raghav");

        // Expect IllegalArgumentException
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addPrescription();
        });
        assertTrue(exception2.getMessage().contains("First name must be between 4 and 15 characters long."),
                "Exception message should contain first name length error");
    }


    @Test
    public void test3_AddPrescription_InvalidAddressLength() {
        // Test Data 1: Address too short
        prescription.setFirstName("Nixon");
        prescription.setLastName("Paulson");
        prescription.setAddress("Short Address");
        prescription.setSphere(-5.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("doctor Raghav");


        // Expect IllegalArgumentException
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addPrescription();
        });
        assertTrue(exception1.getMessage().contains("Address should be at least 20 characters long."),
                "Exception message should contain address length error");

        // Reset for Test Data 2
        prescription = new Prescription();
        prescription.setPrescID(2);
        prescription.setFirstName("Nixon");

        prescription.setLastName("Paulson");

        prescription.setAddress("");
        prescription.setSphere(-5.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("doctor Raghav");


        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addPrescription();
        });
        assertTrue(exception2.getMessage().contains("Address cannot be empty."),
                "Exception message should contain address required error");
    }

    /**
     * Test Case 4: Sphere Value Out of Range
     * Tests sphere values that are too low and too high.
     */
    @Test
    public void test4_AddPrescription_SphereValueOutOfRange() {
        // Test Data 1: Sphere less than -20.00
        prescription.setFirstName("Nixon");

        prescription.setLastName("Paulson");

        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");


        prescription.setSphere(-25.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("doctor Raghav");


        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addPrescription();
        });
        assertTrue(exception1.getMessage().contains("Sphere value must be between -20.00 and 20.00."),
                "Exception message should contain sphere value out of range error");

        // Reset for Test Data 2
        prescription = new Prescription();
        prescription.setPrescID(2);
        prescription.setFirstName("Nixon");

        prescription.setLastName("Paulson");

        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");


        prescription.setSphere(25.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("doctor Raghav");


        // Expect IllegalArgumentException
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addPrescription();
        });
        assertTrue(exception2.getMessage().contains("Sphere value must be between -20.00 and 20.00."),
                "Exception message should contain sphere value out of range error");
    }


    @Test
    public void test5_AddPrescription_InvalidExaminationDate() {
        // Test Data 1: Null examination date
        prescription.setFirstName("Nixon");

        prescription.setLastName("Paulson");

        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");


        prescription.setSphere(-5.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(null);
        prescription.setOptometrist("doctor Raghav");


        // Expect IllegalArgumentException
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addPrescription();
        });
        assertTrue(exception1.getMessage().contains("Please provide a valid examination date in the format dd/MM/yy."),
                "Exception message should contain examination date error");

        // Test Data 2: Invalid date (e.g., 31/02/24)
        prescription = new Prescription();
        prescription.setPrescID(2);
        prescription.setFirstName("Nixon");

        prescription.setLastName("Paulson");

        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");


        prescription.setSphere(-5.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        dateFormat.setLenient(false);
        try {
            Date invalidDate = dateFormat.parse("31/02/24");
            prescription.setExaminationDate(invalidDate);
        } catch (ParseException e) {
            // Setting to null as parsing failed
            prescription.setExaminationDate(null);
        }

        // Expect IllegalArgumentException
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addPrescription();
        });
        assertTrue(exception2.getMessage().contains("Please provide a valid examination date in the format dd/MM/yy."),
                "Exception message should contain examination date error");
    }


    @Test
    public void test6_AddPrescription_InvalidOptometristNameLength() {
        // Test Data 1: Optometrist name too short
        prescription.setFirstName("Nixon");

        prescription.setLastName("Paulson");

        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");


        prescription.setSphere(-5.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("Dr. R"); // Less than 8 characters

        // Expect IllegalArgumentException
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addPrescription();
        });
        assertTrue(exception1.getMessage().contains("Optometrist name must be between 8 and 25 characters long."),
                "Exception message should contain optometrist name length error");

        // Reset for Test Data 2
        prescription = new Prescription();
        prescription.setPrescID(2);
        prescription.setFirstName("Nixon");

        prescription.setLastName("Paulson");

        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");


        prescription.setSphere(-5.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("Dr. RAGHAVvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv"); // Exceeds 25 characters

        // Expect IllegalArgumentException
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addPrescription();
        });
        assertTrue(exception2.getMessage().contains("Optometrist name must be between 8 and 25 characters long."),
                "Exception message should contain optometrist name length error");
    }

    //remark test cases
    @Test
    public void test7__AddRemark_ValidData() {
        // Prepare a valid prescription first
        prescription.setFirstName("Nixon");

        prescription.setLastName("Paulson");

        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");


        prescription.setSphere(-5.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("doctor Raghav");


        // Add prescription without expecting exceptions
        assertDoesNotThrow(() -> {
            boolean prescriptionAdded = prescription.addPrescription();
            assertTrue(prescriptionAdded, "Valid prescription should return true");
        });

        // Test Data 1: Valid Remark
        prescription.setRemarkType("client");
        prescription.setRemarkText("This is a valid remark from the client.");

        // Assert that addRemark() does not throw an exception and returns true
        assertDoesNotThrow(() -> {
            boolean result1 = prescription.addRemark();
            assertTrue(result1, "Valid remark should return true");
        });

        // Test Data 2: Another Valid Remark
        prescription.setRemarkType("Optometrist");
        prescription.setRemarkText("Additional details provided by the optometrist.");

        // Assert that addRemark() does not throw an exception and returns true
        assertDoesNotThrow(() -> {
            boolean result2 = prescription.addRemark();
            assertTrue(result2, "Valid remark should return true");
        });
    }

    @Test
    public void test8_AddRemark_ExceedingRemarkLimit() {
        // Prepare a valid prescription first
        prescription.setFirstName("Nixon");

        prescription.setLastName("Paulson");

        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");


        prescription.setSphere(-5.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("doctor Raghav");


        // Add prescription without expecting exceptions
        assertDoesNotThrow(() -> {
            boolean prescriptionAdded = prescription.addPrescription();
            assertTrue(prescriptionAdded, "Valid prescription should return true");
        });

        // Add first remark
        prescription.setRemarkType("client");
        prescription.setRemarkText("First valid remark from the client.");
        assertDoesNotThrow(() -> {
            boolean result1 = prescription.addRemark();
            assertTrue(result1, "First valid remark should return true");
        });

        // Add second remark
        prescription.setRemarkType("optometrist");
        prescription.setRemarkText("Second valid remark from the optometrist.");
        assertDoesNotThrow(() -> {
            boolean result2 = prescription.addRemark();
            assertTrue(result2, "Second valid remark should return true");
        });

        // Test Data 1: Attempt to add a third remark
        prescription.setRemarkType("client");
        prescription.setRemarkText("Third remark should fail.");
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addRemark();
        });
        assertTrue(exception1.getMessage().contains("You can only add up to 2 remarks."),
                "Exception message should indicate that only 2 remarks are allowed");

        // Test Data 2: Another attempt to add a third remark
        prescription.setRemarkType("optometrist");
        prescription.setRemarkText("Another third remark should also fail.");
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addRemark();
        });
        assertTrue(exception2.getMessage().contains("You can only add up to 2 remarks."),
                "Exception message should indicate that only 2 remarks are allowed");
    }


    @Test
    public void test9_AddRemark_InvalidRemarkType() {
        // Prepare a valid prescription first
        prescription.setFirstName("Nixon");

        prescription.setLastName("Paulson");

        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");


        prescription.setSphere(-5.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("doctor Raghav");


        // Add prescription without expecting exceptions
        assertDoesNotThrow(() -> {
            boolean prescriptionAdded = prescription.addPrescription();
            assertTrue(prescriptionAdded, "Valid prescription should return true");
        });

        // Test Data 1: Invalid remark type
        prescription.setRemarkType("patient");
        prescription.setRemarkText("Valid remark text.");
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addRemark();
        });
        assertTrue(exception1.getMessage().contains("Remark type must be either 'Client' or 'Optometrist'."),
                "Exception message should contain invalid remark type error");

        // Test Data 2: Null remark type
        prescription.setRemarkType(null);
        prescription.setRemarkText("Valid remark text.");
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addRemark();
        });
        assertTrue(exception2.getMessage().contains("Please specify the type of remark (Client or Optometrist)."),
                "Exception message should contain missing remark type error");
    }


    @Test
    public void test10_AddRemark_InvalidRemarkTextLength() {
        // Prepare a valid prescription first
        prescription.setFirstName("Nixon");

        prescription.setLastName("Paulson");

        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");


        prescription.setSphere(-5.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("doctor Raghav");


        // Add prescription without expecting exceptions
        assertDoesNotThrow(() -> {
            boolean prescriptionAdded = prescription.addPrescription();
            assertTrue(prescriptionAdded, "Valid prescription should return true");
        });

        // Test Data 1: Remark text too short
        prescription.setRemarkType("client");
        prescription.setRemarkText("Too short.");
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addRemark();
        });
        assertTrue(exception1.getMessage().contains("Remark must be between 6 and 20 words."),
                "Exception message should contain remark text length error");

        // Test Data 2: Remark text too long
        String longRemark = "This remark is excessively long and contains more than twenty words, which exceeds the allowed limit for remarks in the system. It should therefore cause the method to throw an exception.";
        prescription.setRemarkText(longRemark);
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addRemark();
        });
        assertTrue(exception2.getMessage().contains("Remark must be between 6 and 20 words."),
                "Exception message should contain remark text length error");
    }


    @Test
    public void test11_AddRemark_FirstCharacterNotUppercase() {
        // Prepare a valid prescription first
        prescription.setFirstName("Nixon");

        prescription.setLastName("Paulson");

        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");


        prescription.setSphere(-5.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("doctor Raghav");


        // Add prescription without expecting exceptions
        assertDoesNotThrow(() -> {
            boolean prescriptionAdded = prescription.addPrescription();
            assertTrue(prescriptionAdded, "Valid prescription should return true");
        });

        // Test Data 1: Starts with lowercase letter
        prescription.setRemarkType("client");
        prescription.setRemarkText("this remark starts with a lowercase letter.");
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addRemark();
        });
        assertTrue(exception1.getMessage().contains("The first word of the remark should start with a capital letter."),
                "Exception message should contain first character uppercase error");

        // Test Data 2: Starts with space
        prescription.setRemarkText(" another remark that starts with a space.");
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addRemark();
        });
        assertTrue(exception2.getMessage().contains("The first word of the remark should start with a capital letter."),
                "Exception message should contain first character uppercase error");
    }


    @Test
    public void test12_AddRemark_NullRemarkText() {
        // Prepare a valid prescription first
        prescription.setFirstName("Nixon");

        prescription.setLastName("Paulson");

        prescription.setAddress("apptNo.,apptName,CityName,StateName,CoutnryName,PIN");


        prescription.setSphere(-5.00f);
        prescription.setCylinder(-1.00f);
        prescription.setAxis(90.0f);
        prescription.setExaminationDate(new Date());
        prescription.setOptometrist("doctor Raghav");


        // Add prescription without expecting exceptions
        assertDoesNotThrow(() -> {
            boolean prescriptionAdded = prescription.addPrescription();
            assertTrue(prescriptionAdded, "Valid prescription should return true");
        });

        // Test Data 1: Null remark text
        prescription.setRemarkType("client");
        prescription.setRemarkText(null);
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addRemark();
        });
        assertTrue(exception1.getMessage().contains("Remark text cannot be empty."),
                "Exception message should contain missing remark text error");

        // Test Data 2: Empty remark text
        prescription.setRemarkText("");
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            prescription.addRemark();
        });
        assertTrue(exception2.getMessage().contains("Remark text cannot be empty."),
                "Exception message should contain missing remark text error");
    }
}
