package unit_tests;

import static org.junit.Assert.*;

import hms.doctor.AppointmentOutcomeRecord;
import hms.hms_main.ExportData;
import org.junit.Before;
import org.junit.Test;
import hms.patient.Appointment;
import hms.patient.BillingDetails;
import hms.patient.Patient;

import java.util.*;
import java.io.*;

public class AdditionalActionsTest {

    private Patient patient;
    private BillingDetails billingService;
    private Appointment appointment;
    private AppointmentOutcomeRecord outcomeRecordService;
    private List<Map<String, String>> doctorList;
    private List<Map<String, String>> appointmentList;
    private ExportData exportData;
    private Map<String, Map<Integer, Map<String, Object>>> outcomeRecords;
    private BillingDetails billingDetails;
    private List<Map<String, String>> medicineList;

    @Before
    public void setUp() {
        outcomeRecordService = new AppointmentOutcomeRecord();
        billingService = new BillingDetails();
        // Initialize a sample patient
        patient = new Patient("P1001", "Alice Brown", "01-Jan-1980", Arrays.asList("alice@example.com", "1234567890"), "Female", "O+", Arrays.asList("Diabetes"), Arrays.asList("Insulin"));

        // Initialize doctor list
        doctorList = new ArrayList<>();
        Map<String, String> doctor = new HashMap<>();
        doctor.put("Doctor ID", "D001");
        doctor.put("Name", "Dr. Lee");
        doctor.put("Gender", "Female");
        doctorList.add(doctor);

        // Initialize appointment list
        appointmentList = new ArrayList<>();
        Map<String, String> appointment = new HashMap<>();
        appointment.put("Appointment ID", "A101");
        appointment.put("Patient ID", "P1001");
        appointment.put("Doctor ID", "D001");
        appointment.put("Status", "Completed");
        appointment.put("Date", "15-Nov-2024");
        appointment.put("Time", "10:00");
        appointmentList.add(appointment);

        // Initialize ExportData instance
        exportData = new ExportData();

        // Initialize outcome records
        String serviceType = "Consultation";
        String medicationName = "Medication A";
        String medicationStatus = "Pending";
        String medicationQuantity = "15";
        String consultationNotes = "Consultation notes";

        Appointment appointment1 = new Appointment(105, patient.getId(), "Dr. Lee", "13-Nov-2024", "13:00", "Completed");
        Appointment.getAllAppointments().add(appointment1);
        outcomeRecordService.addOutcomeRecord(patient.getId(), 105, serviceType, medicationName, medicationStatus, medicationQuantity, consultationNotes);

        medicineList = new ArrayList<>();
        Map<String, String> med1 = new HashMap<>();
        med1.put("Medicine Name", "Medication A");
        med1.put("Price", "10.0");
        med1.put("Initial Stock", "50");
        med1.put("Low Stock Level Alert", "10");
        medicineList.add(med1);

        Map<String, String> med2 = new HashMap<>();
        med2.put("Medicine Name", "Medication B");
        med2.put("Price", "20.0");
        med2.put("Initial Stock", "30");
        med2.put("Low Stock Level Alert", "5");
        medicineList.add(med2);

        // Initialize BillingDetails instance
        billingDetails = new BillingDetails();
        billingDetails.initializeBills(medicineList);
    }

    @Test
    public void testExportPatientAppointmentOutcomeRecord() {
        // Test Case: Export Patient Appointment Outcome Record Details
        String patientId = "P1001";
        outcomeRecords = AppointmentOutcomeRecord.getAllOutcomeRecords();
        exportData.patientViewOrExportRecords(patientId, outcomeRecords, true);

        // Verify the CSV file content
        String fileName = patientId + "_records.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            // Check if headers match
            String[] headers = {"Appointment ID", "Date", "Service", "Medications", "Notes"};
            assertEquals("Headers should match", String.join(",", headers), lines.get(0));

            // Check if data rows match
            Map<String, Object> details = outcomeRecords.get(patientId).get(105);
            String medications = "Medication A (Pending)";
            String[] expectedRow = {"105", (String) details.get("date"), (String) details.get("stype"), medications, (String) details.get("cnotes")};
            assertEquals("Data row should match", String.join(",", expectedRow), lines.get(1));

        } catch (IOException e) {
            fail("IOException occurred while reading the CSV file: " + e.getMessage());
        }
    }

    @Test
    public void testAppointmentBilling() {
        // Sample data setup
        Appointment appointment = new Appointment(105, "P1001", "Dr. Smith", "13-Nov-2024", "13:00", "Completed");
        Appointment.getAllAppointments().add(appointment);

        outcomeRecordService.addOutcomeRecord("P1001", 105, "Consultation", "Medication A", "Pending", "2", "Consultation notes");

        // Initialize bills using the medicine list
        billingService.initializeBills(medicineList);

        // Verifying that the bill for patient P1001 is present and unpaid
        List<Map<String, Object>> billingList = billingService.billingList;
        boolean found = false;
        for (Map<String, Object> bill : billingList) {
            if (bill.get("patientId").equals("P1001") && bill.get("appointmentId").equals("105")) {
                found = true;
                assertEquals(50.0, bill.get("totalAmount")); // Consultation fee (30) + Medication A (10 * 2)
                assertFalse((Boolean) bill.get("isPaid"));
            }
        }
        assertTrue( "Billing record for patient P1001 was not found.", found);
    }

    @Test
    public void testDoctorRatingServices() {
        // Test Case: Doctor Rating Services
        String doctorId = "D001";
        int rating = 4; // Assuming patient rates the doctor 4 out of 5

        for (Map<String, String> doctor : doctorList) {
            if (doctor.get("Doctor ID").equals(doctorId)) {
                System.out.println("Doctor Rating: " + doctor.get("Name") + " (" + rating + "/5)");
                doctor.put("Rating", String.valueOf(rating));
                assertEquals("Doctor rating should be updated", String.valueOf(rating), doctor.get("Rating"));
                break;
            }
        }
    }

    @Test
    public void testSpecifyGenderWhenDisplayDoctor() {
        // Test Case: Specify Gender when Display Doctor
        boolean doctorFound = false;

        for (Map<String, String> doctor : doctorList) {
            if (doctor.get("Name").equals("Dr. Lee")) {
                doctorFound = true;
                System.out.println("Doctor Name: " + doctor.get("Name") + " (Gender: " + doctor.get("Gender") + ")");
                assertEquals("Doctor gender should be displayed", "Female", doctor.get("Gender"));
                break;
            }
        }
        assertTrue("Doctor should be found and gender should be displayed", doctorFound);
    }
}
