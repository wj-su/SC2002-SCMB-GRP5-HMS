package pharmacist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import staff.Staff;
import user.User;

public class Pharmacist extends Staff implements User {
    private List<Prescription> managedPrescriptions;
    private String role;
    private String password;

    public Pharmacist(String pharmacistId, String name, String age, String gender) {
        super(pharmacistId, name, age, gender);
        this.managedPrescriptions = new ArrayList<>();
        this.role = "Pharmacist";
        this.password = "password";

    }

    @Override
    public String getRole() {
        return this.role;
    }

    public static Map<String, Boolean> loginStatus = new HashMap<>();

    @Override
    public boolean isFirstLogin() {
        return loginStatus.getOrDefault(getStaffId(), true);
    }

    @Override
    public void setFirstLogin(boolean firstLogin) {
        loginStatus.put(this.getStaffId(), firstLogin);
    }


    @Override
    public boolean login(String pw) {
        return this.password.equals(pw);
    }

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

    public List<Prescription> getManagedPrescriptions() {
        return managedPrescriptions;
    }

    public void setManagedPrescriptions(List<Prescription> managedPrescriptions) {
        this.managedPrescriptions = managedPrescriptions;
    }

    public void addPrescription(Prescription prescription) {
        this.managedPrescriptions.add(prescription);
    }

    public void viewPrescriptions() {
        System.out.println("Prescriptions managed by Pharmacist " + getName() + ":");
        for (Prescription prescription : managedPrescriptions) {
            System.out.println(prescription);
        }
    }

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
