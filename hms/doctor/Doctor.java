package doctor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import patient.Appointment;
import staff.Staff;
import user.User;

public class Doctor extends Staff implements User {
    private List<Appointment> appointments;
    private String role;
    private String password;

    public Doctor(String doctorId, String name, String age, String gender) {
        super(doctorId, name, age, gender);
        this.role = "Doctor";
        this.password = "password";
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
        for (Map<String, String> doctorData : selectedList) {
            if (doctorData.get("Staff ID").equals(this.getStaffId())) {
                doctorData.put("Password", pw); // Update the password in the list
                doctorData.put("FirstLogin", "false"); // Update the first login status
                break;
            }
        }
    
        System.out.println("Password has been changed successfully.");
    }

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
        System.out.println("|  - Logout                              (8) | ");
        System.out.println("----------------------------------------------");

    }

    @Override
    public String getRole() {
        return this.role;
    }

    public void viewPersonalSchedule(String date) {
        this.appointments = Appointment.getAllAppointments();
        System.out.println("Personal Appointments for Dr. " + getName());

        boolean foundAppointments = false;

        for (Appointment apt : appointments) {
            // Check if the appointment is for this doctor, is on the specified date, and is
            // confirmed
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

        // If no appointments were found, display a message
        if (!foundAppointments) {
            System.out.println("No appointments found for Dr. " + getName() + " on " + date);
        }
    }

}
