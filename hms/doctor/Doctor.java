package doctor;

import java.util.List;
import patient.Appointment;

public class Doctor {
    private String doctorId;
    private String name;
    private String gender;
    private String age;
    private String contactNumber;
    private List<String> availDateTime;
    private List<Appointment> appointments;
    // private Map<String, MedicalRecordManagement> medicalRecords;

    public Doctor(String doctorId, String name, String age, String gender, String contactNumber,
            List<String> availDateTime) {
        this.doctorId = doctorId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.availDateTime = availDateTime;
        // this.appointments = new ArrayList<>();
        // this.medicalRecords = new HashMap<>();
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public List<String> getAvailDateTime() {
        return availDateTime;
    }

    public void viewPersonalSchedule(String date) {
        this.appointments = Appointment.getAllAppointments();
        System.out.println("Personal Appointments for Dr. " + getName());
    
        boolean foundAppointments = false;
    
        for (Appointment apt : appointments) {
            // Check if the appointment is for this doctor, is on the specified date, and is confirmed
            if (apt.getDoctor().equals(getName()) && apt.getDate().equals(date) && apt.getStatus().equals("Confirmed")) {
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
    
    @Override

    public String toString() {
        return "Doctor ID: " + getDoctorId() + ", Name: " + getName() + ", Age: " + age + ", Gender: " + getGender()
                + ", Contact Number: " + getContactNumber() + ", Available Dates: " + getAvailDateTime();
    }
}
