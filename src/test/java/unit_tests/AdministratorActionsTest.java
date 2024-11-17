package unit_tests;

import static org.junit.Assert.*;

import hms.admin.Administrator;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

public class AdministratorActionsTest {

    private Administrator administrator;
    private List<Map<String, String>> staffList;
    private List<Map<String, String>> appointmentList;
    private List<Map<String, String>> medicationInventory;
    private List<Map<String, String>> replenishmentRequests;

    @Before
    public void setUp() {
        // Initialize a sample administrator
        administrator = new Administrator("AD001", "John Smith", "45", "Male");

        // Initialize staff list
        staffList = new ArrayList<>();
        Map<String, String> doctor = new HashMap<>();
        doctor.put("Staff ID", "D001");
        doctor.put("Name", "Dr. Lee");
        doctor.put("Role", "Doctor");
        doctor.put("Gender", "Female");
        doctor.put("Age", "40");
        staffList.add(doctor);

        // Initialize appointment list
        appointmentList = new ArrayList<>();
        Map<String, String> appointment = new HashMap<>();
        appointment.put("Appointment ID", "A101");
        appointment.put("Patient ID", "P001");
        appointment.put("Doctor ID", "D001");
        appointment.put("Status", "Confirmed");
        appointment.put("Date", "15-Nov-2024");
        appointment.put("Time", "10:00");
        appointmentList.add(appointment);

        // Initialize medication inventory
        medicationInventory = new ArrayList<>();
        Map<String, String> medication = new HashMap<>();
        medication.put("Medicine Name", "Medication B");
        medication.put("Initial Stock", "15");
        medication.put("Low Stock Level Alert", "5");
        medicationInventory.add(medication);

        // Initialize replenishment requests
        replenishmentRequests = new ArrayList<>();
        Map<String, String> replenishmentRequest = new HashMap<>();
        replenishmentRequest.put("Medicine Name", "Medication B");
        replenishmentRequest.put("Requested Quantity", "10");
        replenishmentRequest.put("Status", "Pending Approval");
        replenishmentRequests.add(replenishmentRequest);
    }

    @Test
    public void testViewAndManageHospitalStaff() {
        // Test Case 20: View and Manage Hospital Staff
        assertFalse("Staff list should not be empty", staffList.isEmpty());

        // Add a new staff member
        Map<String, String> newStaff = new HashMap<>();
        newStaff.put("Staff ID", "N001");
        newStaff.put("Name", "Jane");
        newStaff.put("Role", "Pharmacist");
        newStaff.put("Gender", "Female");
        newStaff.put("Age", "30");
        staffList.add(newStaff);
        assertEquals("Staff list size should be 2 after adding a new staff member", 2, staffList.size());

        // Update a staff member's role
        for (Map<String, String> staff : staffList) {
            if (staff.get("Staff ID").equals("N001")) {
                staff.put("Role", "Doctor");
                assertEquals("Staff role should be updated", "Doctor", staff.get("Role"));
                break;
            }
        }

        // Remove a staff member
        staffList.removeIf(staff -> staff.get("Staff ID").equals("N001"));
        assertEquals("Staff list size should be 1 after removing a staff member", 1, staffList.size());
    }

    @Test
    public void testViewAppointmentsDetails() {
        // Test Case 21: View Appointments Details
        assertFalse("Appointment list should not be empty", appointmentList.isEmpty());

        Map<String, String> appointment = appointmentList.get(0);
        assertEquals("Appointment ID should match", "A101", appointment.get("Appointment ID"));
        assertEquals("Patient ID should match", "P001", appointment.get("Patient ID"));
        assertEquals("Doctor ID should match", "D001", appointment.get("Doctor ID"));
        assertEquals("Status should match", "Confirmed", appointment.get("Status"));
        assertEquals("Date should match", "15-Nov-2024", appointment.get("Date"));
        assertEquals("Time should match", "10:00", appointment.get("Time"));
    }

    @Test
    public void testViewAndManageMedicationInventory() {
        // Test Case 22: View and Manage Medication Inventory
        assertFalse("Medication inventory should not be empty", medicationInventory.isEmpty());

        // Update stock level of a medication
        for (Map<String, String> medication : medicationInventory) {
            if (medication.get("Medicine Name").equals("Medication B")) {
                medication.put("Initial Stock", "25");
                assertEquals("Stock level should be updated", "25", medication.get("Initial Stock"));
                break;
            }
        }
    }

    @Test
    public void testApproveReplenishmentRequests() {
        // Test Case 23: Approve Replenishment Requests
        assertFalse("Replenishment requests should not be empty", replenishmentRequests.isEmpty());

        // Approve a replenishment request
        for (Map<String, String> request : replenishmentRequests) {
            if (request.get("Medicine Name").equals("Medication B") && request.get("Status").equals("Pending Approval")) {
                request.put("Status", "Approved");
                assertEquals("Request status should be approved", "Approved", request.get("Status"));

                // Update medication inventory
                for (Map<String, String> medication : medicationInventory) {
                    if (medication.get("Medicine Name").equals(request.get("Medicine Name"))) {
                        int updatedStock = Integer.parseInt(medication.get("Initial Stock")) + Integer.parseInt(request.get("Requested Quantity"));
                        medication.put("Initial Stock", String.valueOf(updatedStock));
                        assertEquals("Stock level should be updated in inventory", "25", medication.get("Initial Stock"));
                        break;
                    }
                }
                break;
            }
        }
    }
}
