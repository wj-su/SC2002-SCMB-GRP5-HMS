package hms.pharmacist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hms.staff.Staff;
import hms.user.User;

/**
 * Represents a pharmacist in the hospital system.
 * Extends the Staff class and implements the User interface.
 * Provides functionalities to manage prescriptions, view and update medication inventory, and submit replenishment requests.
 */
public class Pharmacist extends Staff implements User {
    private List<Prescription> managedPrescriptions;
    private String role;
    private String password;

    /**
     * Constructor to initialize a pharmacist object.
     *
     * @param pharmacistId The unique ID of the pharmacist.
     * @param name         The name of the pharmacist.
     * @param age          The age of the pharmacist.
     * @param gender       The gender of the pharmacist.
     */
    public Pharmacist(String pharmacistId, String name, String age, String gender) {
        super(pharmacistId, name, age, gender);
        this.managedPrescriptions = new ArrayList<>();
        this.role = "Pharmacist";
        this.password = "password";

    }

    /**
     * Retrieves the role of the pharmacist.
     *
     * @return The role ("Pharmacist").
     */
    @Override
    public String getRole() {
        return this.role;
    }

    /**
     * Static map to track login status of pharmacists.
     * Key: Pharmacist ID, Value: true if first login, false otherwise.
     */
    public static Map<String, Boolean> loginStatus = new HashMap<>();

    /**
     * Checks if the pharmacist is logging in for the first time.
     *
     * @return true if it is the first login, false otherwise.
     */
    @Override
    public boolean isFirstLogin() {
        return loginStatus.getOrDefault(getStaffId(), true);
    }

    /**
     * Sets the first login status for the pharmacist.
     *
     * @param firstLogin true if it is the first login, false otherwise.
     */
    @Override
    public void setFirstLogin(boolean firstLogin) {
        loginStatus.put(this.getStaffId(), firstLogin);
    }

    /**
     * Authenticates the pharmacist using the provided password.
     *
     * @param pw The password entered by the pharmacist.
     * @return true if the password matches, false otherwise.
     */
    @Override
    public boolean login(String pw) {
        return this.password.equals(pw);
    }

    /**
     * Changes the pharmacist's password and updates the selected pharmacist list.
     *
     * @param pw          The new password to set.
     * @param selectedList A list of pharmacist data to update.
     */
    @Override
    public void changePassword(String pw, List<Map<String, String>> selectedList) {
        this.password = pw; 
    
        
        for (Map<String, String> pharmacistData : selectedList) {
            if (pharmacistData.get("Staff ID").equals(this.getStaffId())) {
                pharmacistData.put("Password", pw); 
                pharmacistData.put("FirstLogin", "false"); 
                break;
            }
        }
    
        System.out.println("Password has been changed successfully.");
    }

    /**
     * Displays the menu options available to the pharmacist.
     */
    @Override
    public void displayMenu() {
        System.out.println("---------------------------------------------");
        System.out.println("|               Pharmacist Menu              |");
        System.out.println("----------------------------------------------");
        System.out.println("|- View Appointment Outcome Record        (1) |");
        System.out.println("|- Update Prescription Status             (2) |");
        System.out.println("|- View Medication Inventory              (3) |");
        System.out.println("|- Submit Replenishment Request           (4) |");
        System.out.println("|- Logout                                 (5) |");
        System.out.println("----------------------------------------------");
    }

    /**
     * Retrieves the list of managed prescriptions.
     *
     * @return The list of managed prescriptions.
     */
    public List<Prescription> getManagedPrescriptions() {
        return managedPrescriptions;
    }

    /**
     * Sets the list of managed prescriptions.
     *
     * @param managedPrescriptions The list of prescriptions to set.
     */
    public void setManagedPrescriptions(List<Prescription> managedPrescriptions) {
        this.managedPrescriptions = managedPrescriptions;
    }

    /**
     * Adds a prescription to the list of managed prescriptions.
     *
     * @param prescription The prescription to add.
     */
    public void addPrescription(Prescription prescription) {
        this.managedPrescriptions.add(prescription);
    }

    /**
     * Displays all prescriptions managed by the pharmacist.
     */
    public void viewPrescriptions() {
        System.out.println("Prescriptions managed by Pharmacist " + getName() + ":");
        for (Prescription prescription : managedPrescriptions) {
            System.out.println(prescription);
        }
    }

    /**
     * Updates the status of a specific prescription in the outcome records.
     *
     * @param patientId        The ID of the patient.
     * @param appointmentId    The ID of the appointment.
     * @param prescriptionName The name of the prescription to update.
     * @param newStatus        The new status to set.
     * @param outcomeRecords   The outcome records to update.
     */
    public void updatePrescriptionStatus(String patientId, String appointmentId, String prescriptionName, String newStatus, Map<String, Map<Integer, Map<String, Object>>> outcomeRecords) {
        boolean prescriptionUpdatedInRecord = false;
    
        
        if (outcomeRecords.containsKey(patientId)) {
            Map<Integer, Map<String, Object>> patientRecords = outcomeRecords.get(patientId);
    
            
            int apptId = Integer.parseInt(appointmentId);
    
            if (patientRecords.containsKey(apptId)) {
                Map<String, Object> details = patientRecords.get(apptId);
    
                
                List<Map<String, String>> medications = (List<Map<String, String>>) details.get("pmeds");
    
                for (Map<String, String> med : medications) {
                    if (med.get("name").equalsIgnoreCase(prescriptionName)) {
                        med.put("status", newStatus); 
                        prescriptionUpdatedInRecord = true;
                        System.out.println("Updated Prescription Status in Patient's Outcome Record for " + prescriptionName + " to " + newStatus);
                        break;
                    }
                }
    
                if (!prescriptionUpdatedInRecord) {
                    System.out.println("Prescription with name " + prescriptionName + " not found in patient's outcome records.");
                }
            } else {
                System.out.println("No appointment with ID " + appointmentId + " found for patient ID " + patientId);
            }
        } else {
            System.out.println("No outcome records found for patient ID " + patientId);
        }
        
    }

    /**
     * Submits replenishment requests for medications with low stock levels.
     *
     * @param medicineList           A list of medications with their details.
     * @param replenishmentRequests  A list to store the replenishment requests.
     */
    public void submitReplenishmentRequest(List<Map<String, String>> medicineList, List<Map<String, String>> replenishmentRequests) {
        for (Map<String, String> medicine : medicineList) {
            int initialStock = Integer.parseInt(medicine.get("Initial Stock"));
            int lowStockAlert = Integer.parseInt(medicine.get("Low Stock Level Alert"));
    
            if (initialStock <= lowStockAlert) {
                Map<String, String> request = new HashMap<>();
                request.put("Medicine Name", medicine.get("Medicine Name"));
                request.put("Requested Quantity", String.valueOf(lowStockAlert * 2)); 
                request.put("Status", "Pending Approval");
                replenishmentRequests.add(request);
    
                System.out.println("Replenishment request submitted for: " + medicine.get("Medicine Name"));
            }
        }
    
        if (replenishmentRequests.isEmpty()) {
            System.out.println("No medications require replenishment.");
        } else {
            System.out.println("Replenishment requests submitted successfully.");
        }
    }
}
