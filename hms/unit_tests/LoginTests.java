package unit_tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import patient.Patient;

import java.util.*;

public class LoginTests {

    private Patient patient;
    private List<Map<String, String>> patientList;

    @Before
    public void setUp() {
        // Initialize a sample patient
        List<String> contactInfo = Arrays.asList("12345678", "patient@example.com");
        List<String> pastDiagnoses = Arrays.asList("Hypertension");
        List<String> pastTreatments = Arrays.asList("Medication A");

        patient = new Patient("P123", "John Doe", "01-Jan-1990", contactInfo, "Male", "O+", pastDiagnoses, pastTreatments);
        patientList = new ArrayList<>();
        Map<String, String> patientData = new HashMap<>();
        patientData.put("Patient ID", patient.getId());
        patientData.put("Password", "password"); // Default password
        patientList.add(patientData);
    }

    @Test
    public void testFirstTimeLoginAndPasswordChange() {
        // Test Case 25: First-Time Login and Password Change
        assertTrue("Patient should be able to login with default password", patient.login("password"));

        // Change password
        String newPassword = "newPassword123";
        patient.changePassword(newPassword, patientList);

        // Verify that the password has been changed
        assertTrue("Patient should be able to login with new password", patient.login(newPassword));
        assertFalse("Patient should not be able to login with old password", patient.login("password"));
    }

    @Test
    public void testLoginWithIncorrectCredentials() {
        // Test Case 26: Login with Incorrect Credentials
        assertFalse("Patient should not be able to login with incorrect password", patient.login("wrongPassword"));
    }
}
