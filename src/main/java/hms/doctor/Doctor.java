package hms.doctor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hms.patient.Appointment;
import hms.staff.Staff;
import hms.user.User;

/**
 * Represents Doctors in the Hospital Management System (HMS).
 * Extends the Staff class and implements the User interface.
 * Provides functionalities for managing appointments, login, and viewing schedules.
 */

public class Doctor extends Staff implements User {
    /**
     * List of appointments assigned to the doctor.
     */
    private List<Appointment> appointments;

    /**
     * The role of the staff member, initialized as "Doctor".
     */
    private String role;

    /**
     * The password for doctor login, initialized as "password".
     */
    private String password;

    /**
     * Static map to store the login status of doctors.
     * Key: Staff ID, Value: Login Status (Only True if its User's First Login)
     */

    public static Map<String, Boolean> loginStatus = new HashMap<>();

    /**
     * Constructor to initialize a Doctor object.
     * 
     * @param doctorId The unique ID of the doctor.
     * @param name     The name of the doctor.
     * @param age      The age of the doctor.
     * @param gender   The gender of the doctor.
     * 
     */

    public Doctor(String doctorId, String name, String age, String gender) {
        super(doctorId, name, age, gender);
        this.role = "Doctor";
        this.password = "password";
    }

    /**
     * Checks if the doctor if logging in for the first time.
     * 
     * @return true if it is the first login, otherwise, false.
     */

    @Override
    public boolean isFirstLogin() {
        return loginStatus.getOrDefault(getStaffId(), true); 
    }

    /**
     * Sets the first login status for the doctor
     * 
     * @param firstLogin true if it's first time, otherwise, false.
     * 
     */

    @Override
    public void setFirstLogin(boolean firstLogin) {
        loginStatus.put(this.getStaffId(), firstLogin);
    }

    /**
     * Authenticates the doctor using the provided password.
     * 
     * @param pw The password entered by the user.
     * @return true if the password matches. Otherwise, false.
     */

    @Override
    public boolean login(String pw) {
        return this.password.equals(pw);
    }

    /**
     * Changes the doctor's password and updates the selected list of doctor data.
     * 
     * @param pw            The new password.
     * @param selectedList  A list of doctor data to update.
     * 
     */

    @Override
    public void changePassword(String pw, List<Map<String, String>> selectedList) {
        this.password = pw; 
    
        
        for (Map<String, String> doctorData : selectedList) {
            if (doctorData.get("Staff ID").equals(this.getStaffId())) {
                doctorData.put("Password", pw); 
                doctorData.put("FirstLogin", "false"); 
                break;
            }
        }
    
        System.out.println("Password has been changed successfully.");
    }

    /**
     * Display the menu options for the doctor
     */

    @Override
    public void displayMenu() {
        System.out.println("---------------------------------------------");
        System.out.println("|                Doctor Menu                 |");
        System.out.println("----------------------------------------------");
        System.out.println("|  - View Patient Medical Records        (1) | ");
        System.out.println("|  - Update Patient Medical Records      (2) | ");
        System.out.println("|  - View Personal Schedule              (3) | ");
        System.out.println("|  - Set Availability for Appointments   (4) | ");
        System.out.println("|  - Accept/Decline Appointment Requests (5) | ");
        System.out.println("|  - View Upcoming Appointments          (6) | ");
        System.out.println("|  - Record Appointment Outcome          (7) | ");
        System.out.println("|  - View Own Ratings                    (8) | ");
        System.out.println("|  - Logout                              (9) | ");
        System.out.println("----------------------------------------------");

    }

    /**
     * Retrieves the role of the doctor.
     * 
     * @return The role as a string ("Doctor").
     */

    @Override
    public String getRole() {
        return this.role;
    }

    /**
     * Display the personal schedule of the doctor for a specific date
     * 
     * @param date The date for which the schedule is displayed.
     */

    public void viewPersonalSchedule(String date) {
        this.appointments = Appointment.getAllAppointments();
        System.out.println("Personal Appointments for Dr. " + getName());

        boolean foundAppointments = false;

        for (Appointment apt : appointments) {
            if (apt.getDoctor().equals(getName()) && apt.getDate().equals(date)
                    && apt.getStatus().equals("Confirmed")) {
                foundAppointments = true;
                System.out.println("Patient Id: " + apt.getPatientId());
                System.out.println("Appointment Id: " + apt.getId());
                System.out.println("Appointment Date: " + apt.getDate());
                System.out.println("Appointment Time: " + apt.getTimeslot());
                System.out.println("Appointment Status: " + apt.getStatus());
                System.out.println("\n");
            }
        }

        
        if (!foundAppointments) {
            System.out.println("No appointments found for Dr. " + getName() + " on " + date);
        }
    }

}
