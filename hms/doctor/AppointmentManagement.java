package doctor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import patient.Appointment;

/**
 * Manages appointments for doctors in the Hospital Management System (HMS).
 * Provides functionalities to view, accept, decline appointments, 
 * and manage doctor availability.
 */

public class AppointmentManagement {
    private int appointmentId;
    private String patientId;
    private String doctorId;
    private String date;
    private String time;
    private String status;
    private List<Appointment> docAppts = new ArrayList<>();

    /**
     * Default constructor
     */
    public AppointmentManagement() {}

    /**
     * Constructor to initialize an appointment.
     * 
     * @param appointmentId The unique ID of the appointment.
     * @param patientId     The ID of the patient associated with appointment.
     * @param doctorId      The ID of the doctor handling the appointment.
     * @param date          The date of the appointment.
     * @param time          The time of the appointment.
     */
    public AppointmentManagement(int appointmentId, String patientId, String doctorId, String date, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = "Pending"; 
    }

    /**
     * Views ratings for a specified doctor based on their appointments.
     * 
     * @param doc   The name of the doctor whose ratings are to be viewed.
     */

    public void viewRating(String doc) {
        this.docAppts = Appointment.getAllAppointments();
        System.out.println("Total appointments in list: " + docAppts.size());
        boolean docFound = false;
        
        for (Appointment apt : docAppts) {
            if (apt.getDoctor().equals(doc) && apt.getRating() > -1) {
                System.out.println("Checking appointment ID: " + apt.getId() + " with rating: " + apt.getRating());
                System.out.println("Patient Id: " + apt.getPatientId());
                System.out.println("Appointment Id: " + apt.getId());
                System.out.println("Appointment Date: " + apt.getDate());
                System.out.println("Appointment Time: " + apt.getTimeslot());
                System.out.println("Appointment Status: " + apt.getStatus());
                System.out.println("Appointment Rating: " + apt.getRating());
                System.out.println("\n");
                docFound = true;
            }
        }
    
        if (!docFound) {
            System.out.println("No ratings found!");
        }
    }

    /**
     * Views all upcoming appointments for a specified doctor
     * 
     * @param doct The name of the doctor whose appointments are to be viewed.
     */

    public void viewUpcomingAppointments(String doc) {
        this.docAppts = Appointment.getAllAppointments();
        System.out.println("Upcoming Appointments for Dr. " + doc);
        for (Appointment apt : docAppts) {
            if (apt.getDoctor().equals(doc) && !apt.getStatus().equals("Declined")) {
                System.out.println("Patient Id: " + apt.getPatientId());
                System.out.println("Appointment Id: " + apt.getId());
                System.out.println("Appointment Date: " + apt.getDate());
                System.out.println("Appointment Time: " + apt.getTimeslot());
                System.out.println("Appointment Status: " + apt.getStatus());
                System.out.println("\n");
            }
        }
    }

    /**
     * Accepts an appointment by its ID
     * 
     * @param appointmentId The ID of the appointment to be accepted.
     */

    public void acceptAppointment(int appointmentId) {
        this.docAppts = Appointment.getAllAppointments();
        boolean appointmentFound = false;

        for (Appointment apt : docAppts) {
            if (apt.getId() == appointmentId && apt.getStatus().equals("Pending")) { 
                apt.setStatus("Confirmed");
                appointmentFound = true;
                System.out.println("Appointment ID " + appointmentId + " has been accepted.");
                break; 
            }
        }

        if (!appointmentFound) {
            System.out.println("Appointment ID " + appointmentId + " not found or already confirmed.");
        }
    }

    /**
     * Declines an appointment by its ID
     * 
     * @param appointmentId The ID of the appointment to be declined
     */

    public void declineAppointment(int appointmentId) {
        this.docAppts = Appointment.getAllAppointments();
        boolean appointmentFound = false;

        for (Appointment apt : docAppts) {
            if (apt.getId() == appointmentId && apt.getStatus().equals("Pending")) { 
                apt.setStatus("Declined");
                appointmentFound = true;
                System.out.println("Appointment ID " + appointmentId + " has been declined.");
                break; 
            }
        }

        if (!appointmentFound) {
            System.out.println("Appointment ID " + appointmentId + " not found or already confirmed.");
        }
    }

    /**
     * Sets availability for a doctor on a specific date and time 
     * 
     * @param doctor            The name of the doctor
     * @param date              The date of the availability
     * @param time              The time of availability
     * @param doctorAvailability A map storing doctor availability information
     * 
     */

    public void setAvail(String doctor, String date, String time,
            Map<String, Map<String, List<String>>> doctorAvailability) {
        
        doctorAvailability.putIfAbsent(doctor, new HashMap<>());
        Map<String, List<String>> dates = doctorAvailability.get(doctor);

       
        if (dates.containsKey(date)) {
            List<String> times = dates.get(date);

            
            if (times.contains(time)) {
                System.out.println("This time slot is already set for Dr. " + doctor + " on " + date);
            } else {
                
                times.add(time);
                Collections.sort(times); 
                System.out.println("Added new time slot for Dr. " + doctor + " on " + date + ": " + time);
            }
        } else {
            
            dates.put(date, new ArrayList<>(List.of(time)));
            System.out.println("Added new date and time slot for Dr. " + doctor + ": " + date + " " + time);
        }
    }

    /**
     * Prints out appointment details as a string
     * 
     * @return A string representation of the appointment details
     */

    public String printingOut() {
        return "AppointmentID: " + appointmentId + ", Patient ID: " + patientId + ", Date: " + date + ", Time: " + time
                + ", Status: " + status;
    }
}
