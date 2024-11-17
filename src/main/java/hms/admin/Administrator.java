package hms.admin;

import hms.doctor.AppointmentOutcomeRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hms.patient.Appointment;
import hms.staff.Staff;
import hms.user.User;

/**
 * Represents an Administrator in the Hospital Management System (Hms)
 * Inherits from Staff and implements the User interface
 * Provides functionalities for managing hospital staff, appointments, and inventory 
 */
public class Administrator extends Staff implements User {
    private List<Appointment> allAppts = new ArrayList<>();
    private Map<String, Map<Integer, Map<String, Object>>> orc = new HashMap<>();
    private String role;
    private String password;

    /**
     * Static map to store the login status of administrators
     * Key: Staff ID, Value: Login status (true if first login)
     */
    public static Map<String, Boolean> loginStatus = new HashMap<>();

    /**
     * Constructor to initialize an Administrator object
     * 
     * @param adminId The unique ID of the administrator
     * @param name    The name of the administrator
     * @param age     The age of the administrator
     * @param gender  The gender of the administrator
     */
    public Administrator(String adminId, String name, String age, String gender) {
        super(adminId, name, age, gender);
        this.role = "Administrator";
        this.password = "password";
    }

    /**
     * Retrieves the role of the administrator
     * 
     * @return The role as a String administrator
     */
    @Override
    public String getRole() {
        return this.role;
    }

    /**
     * Checks if the administrator is logging in for the first time
     * 
     * @return true if it is the first login. Otherwise, false
     */
    @Override
    public boolean isFirstLogin() {
        return loginStatus.getOrDefault(getStaffId(), true); 
    }

    /**
     * Sets the first login status for the administrator.
     *
     * @param firstLogin true if first login, false otherwise.
     */
    @Override
    public void setFirstLogin(boolean firstLogin) {
        loginStatus.put(this.getStaffId(), firstLogin);
    }

    /**
     * Authenticates the administrator using the provided password.
     *
     * @param pw The password entered by the user.
     * @return true if the password matches, false otherwise.
     */
    @Override
    public boolean login(String pw) {
        return this.password.equals(pw);
    }

    /**
     * Changes the administrator's password and updates the selected list of admin data.
     *
     * @param pw          The new password.
     * @param selectedList A list of admin data to update.
     */
    @Override
    public void changePassword(String pw, List<Map<String, String>> selectedList) {
        this.password = pw; 
    
        
        for (Map<String, String> adminData : selectedList) {
            if (adminData.get("Staff ID").equals(this.getStaffId())) {
                adminData.put("Password", pw); 
                adminData.put("FirstLogin", "false"); 
                break;
            }
        }
    
        System.out.println("Password has been changed successfully.");
    }

    /**
     * Displays the menu options available for the administrator.
     */
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

    /**
     * Views all appointments and their corresponding outcome records.
     */
    public void viewAppointmentsAdmin() {
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
