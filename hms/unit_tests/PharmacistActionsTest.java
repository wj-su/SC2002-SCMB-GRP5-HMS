package unit_tests;

import static org.junit.Assert.*;

import doctor.AppointmentOutcomeRecord;
import org.junit.Before;
import org.junit.Test;
import patient.Appointment;
import patient.Patient;
import pharmacist.Pharmacist;
import pharmacist.Prescription;

import java.util.*;

public class PharmacistActionsTest {

    private Pharmacist pharmacist;
    private Patient patient;
    private Appointment appointmentService;
    private AppointmentOutcomeRecord outcomeRecordService;
    private Map<String, Map<Integer, Map<String, Object>>> outcomeRecords;
    private List<Map<String, String>> patientList;
    private List<Map<String, String>> medicationInventory;
    private List<Map<String, String>> replenishmentRequests;
    private Prescription prescription;

    @Before
    public void setUp() {
        // Initialize a sample pharmacist
        pharmacist = new Pharmacist("PH789", "Jane Doe", "35", "Female");

        // Initialize a sample patient
        List<String> contactInfo = Arrays.asList("98765432", "patient2@example.com");
        List<String> pastDiagnoses = Arrays.asList("Asthma");
        List<String> pastTreatments = Arrays.asList("Inhaler");
        patient = new Patient("P234", "Alice Brown", "02-Feb-1985", contactInfo, "Female", "A+", pastDiagnoses, pastTreatments);

        appointmentService = new Appointment();
        outcomeRecordService = new AppointmentOutcomeRecord();
        outcomeRecords = new HashMap<>();

        // Initialize patient list
        patientList = new ArrayList<>();
        Map<String, String> patientData = new HashMap<>();
        patientData.put("Patient ID", patient.getId());
        patientData.put("Name", patient.getName());
        patientData.put("Date of Birth", patient.getDOB());
        patientData.put("Gender", patient.getGender());
        patientData.put("Blood Type", patient.getBlood());
        patientData.put("Contact Information", String.join(" | ", contactInfo));
        patientData.put("Past Diagnoses", String.join(" | ", pastDiagnoses));
        patientData.put("Past Treatment", String.join(" | ", pastTreatments));
        patientList.add(patientData);

        // Add an initial appointment outcome record
        Appointment appointment = new Appointment(201, patient.getId(), "Dr. Lee", "15-Nov-2024", "10:00", "Completed");
        Appointment.getAllAppointments().add(appointment);
        outcomeRecordService.addOutcomeRecord(patient.getId(), 201, "Consultation", "Medication B", "Pending", "10", "Take twice daily");
        outcomeRecords = AppointmentOutcomeRecord.getAllOutcomeRecords();

        // Initialize a prescription
        prescription = new Prescription("RX101", "201", "Medication B", "10mg");
        pharmacist.addPrescription(prescription);

        // Initialize medication inventory
        medicationInventory = new ArrayList<>();
        Map<String, String> medication = new HashMap<>();
        medication.put("Medicine Name", "Medication B");
        medication.put("Initial Stock", "4");
        medication.put("Low Stock Level Alert", "5");
        medicationInventory.add(medication);

        // Initialize replenishment requests
        replenishmentRequests = new ArrayList<>();
    }

    @Test
    public void testViewAppointmentOutcomeRecord() {
        // Test Case 16: View Appointment Outcome Record
        assertTrue("Outcome records should contain patient ID", outcomeRecords.containsKey(patient.getId()));
        assertTrue("Patient records should contain appointment ID", outcomeRecords.get(patient.getId()).containsKey(201));

        Map<String, Object> outcomeDetails = outcomeRecords.get(patient.getId()).get(201);
        assertEquals("Service type should match", "Consultation", outcomeDetails.get("stype"));
        assertEquals("Consultation notes should match", "Take twice daily", outcomeDetails.get("cnotes"));

        List<Map<String, String>> medications = (List<Map<String, String>>) outcomeDetails.get("pmeds");
        assertNotNull("Medications should not be null", medications);
        assertEquals("Medication name should match", "Medication B", medications.get(0).get("name"));
        assertEquals("Medication status should match", "Pending", medications.get(0).get("status"));
        assertEquals("Medication quantity should match", "10", medications.get(0).get("quantity"));
    }

    @Test
    public void testUpdatePrescriptionStatus() {
        // Test Case 17: Update Prescription Status
        String patientId = patient.getId();
        int appointmentId = 201;
        String updatedStatus = "Dispensed";

        // Update the prescription status
        pharmacist.updatePrescriptionStatus(patientId, String.valueOf(appointmentId), "Medication B", updatedStatus, outcomeRecords);
        prescription.updateStatus(updatedStatus);

        // Verify that the prescription status is updated
        Map<String, Object> outcomeDetails = outcomeRecords.get(patientId).get(appointmentId);
        List<Map<String, String>> medications = (List<Map<String, String>>) outcomeDetails.get("pmeds");
        assertNotNull("Medications should not be null", medications);
        assertEquals("Medication status should be updated", updatedStatus, medications.get(0).get("status"));
        assertEquals("Prescription status should be updated", updatedStatus, prescription.getStatus());
    }

    @Test
    public void testViewMedicationInventory() {
        // Test Case 18: View Medication Inventory
        assertFalse("Medication inventory should not be empty", medicationInventory.isEmpty());

        Map<String, String> medication = medicationInventory.get(0);
        assertEquals("Medication name should match", "Medication B", medication.get("Medicine Name"));
        assertEquals("Stock level should match", "4", medication.get("Initial Stock"));
    }

    @Test
    public void testSubmitReplenishmentRequest() {
        // Test Case 19: Submit Replenishment Request
        // Submit replenishment request
        pharmacist.submitReplenishmentRequest(medicationInventory, replenishmentRequests);

        // Verify that the replenishment request is added to the list if stock is below alert level
        assertFalse("Replenishment requests should not be empty", replenishmentRequests.isEmpty());
        Map<String, String> request = replenishmentRequests.get(0);
        assertEquals("Medication name should match in request", "Medication B", request.get("Medicine Name"));
        assertEquals("Requested quantity should be double the low stock alert level", "10", request.get("Requested Quantity"));
        assertEquals("Request status should be pending approval", "Pending Approval", request.get("Status"));
    }
}
