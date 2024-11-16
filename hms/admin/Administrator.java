package admin;

import doctor.AppointmentOutcomeRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import patient.Appointment;
import staff.Staff;
import user.User;

public class Administrator extends Staff implements User {
    private List<Appointment> allAppts = new ArrayList<>();
    private Map<String, Map<Integer, Map<String, Object>>> orc = new HashMap<>();
    private String role;
    private String password;

    public Administrator(String adminId, String name, String age, String gender) {
        super(adminId, name, age, gender);
        this.role = "Administrator";
        this.password = "password";
    }

    @Override
    public String getRole() {
        return this.role;
    }

   public static Map<String, Boolean> loginStatus = new HashMap<>();

    @Override
    public boolean isFirstLogin() {
        return loginStatus.getOrDefault(getStaffId(), true); // Default to true
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
        this.password = pw; // Update the local instance variable
    
        // Update the password and FirstLogin fields in the selected list
        for (Map<String, String> adminData : selectedList) {
            if (adminData.get("Staff ID").equals(this.getStaffId())) {
                adminData.put("Password", pw); // Update the password in the list
                adminData.put("FirstLogin", "false"); // Update the first login status
                break;
            }
        }
    
        System.out.println("Password has been changed successfully.");
    }


    @Override
    public void displayMenu() {
        System.out.println("---------------------------------------------");
        System.out.println("|                 Admin Menu                 |");
        System.out.println("----------------------------------------------");
        System.out.println("|  - View and Manage Hopsital Staff       (1)| ");
        System.out.println("|  - View Appointment Details             (2)| ");
        System.out.println("|  - View and Manage Medication Inventory (3)| ");
        System.out.println("|  - Approve Replenishment Requests       (4)| ");
        System.out.println("|  - Logout                               (5)| ");
        System.out.println("----------------------------------------------");

    }




    public void viewAppointmentsAdmin() {
        // Fetch all appointments and outcome records
        this.allAppts = Appointment.getAllAppointments();
        this.orc = AppointmentOutcomeRecord.getAllOutcomeRecords();
    
        System.out.println("=== All Appointments ===");
        for (Appointment apt : allAppts) {
            System.out.println("Patient ID       : " + apt.getPatientId());
            System.out.println("Appointment ID   : " + apt.getId());
            System.out.println("Appointment Date : " + apt.getDate());
            System.out.println("Appointment Time : " + apt.getTimeslot());
            System.out.println("Appointment Status: " + apt.getStatus());
            System.out.println("-----------------------------");
        }
    
        System.out.println("\n=== Outcome Records ===");
        for (Map.Entry<String, Map<Integer, Map<String, Object>>> patientEntry : orc.entrySet()) {
            String patientId = patientEntry.getKey();
            Map<Integer, Map<String, Object>> appointments = patientEntry.getValue();
    
            System.out.println("Patient ID: " + patientId);
            for (Map.Entry<Integer, Map<String, Object>> appointmentEntry : appointments.entrySet()) {
                Integer appointmentId = appointmentEntry.getKey();
                Map<String, Object> details = appointmentEntry.getValue();
    
                System.out.println("  Appointment ID    : " + appointmentId);
                System.out.println("  Appointment Date  : " + details.get("date"));
                System.out.println("  Appointment Service: " + details.get("stype"));
    
                List<Map<String, String>> medications = (List<Map<String, String>>) details.get("pmeds");
                System.out.println("  Prescribed Medications:");
                for (Map<String, String> med : medications) {
                    System.out.println("    - " + med.get("name") + " (" + med.get("status") + ")");
                }
    
                System.out.println("  Consultation Notes: " + details.get("cnotes"));
                System.out.println("-----------------------------");
            }
        }
    }
    

}
