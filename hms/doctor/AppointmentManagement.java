package doctor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import patient.Appointment;

public class AppointmentManagement {
    private int appointmentId;
    private String patientId;
    private String doctorId;
    private String date;
    private String time;
    private String status;
    private List<Appointment> docAppts = new ArrayList<>();

    public AppointmentManagement() {

    }

    public AppointmentManagement(int appointmentId, String patientId, String doctorId, String date, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = "Pending"; // By Default
    }

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

    public void acceptAppointment(int appointmentId) {
        this.docAppts = Appointment.getAllAppointments();
        boolean appointmentFound = false;

        for (Appointment apt : docAppts) {
            if (apt.getId() == appointmentId && apt.getStatus().equals("Pending")) { // link with patient's appointment
                apt.setStatus("Confirmed");
                appointmentFound = true;
                System.out.println("Appointment ID " + appointmentId + " has been accepted.");
                break; // Exit loop after declining the appointment
            }
        }

        if (!appointmentFound) {
            System.out.println("Appointment ID " + appointmentId + " not found or already confirmed.");
        }
    }

    public void declineAppointment(int appointmentId) {
        this.docAppts = Appointment.getAllAppointments();
        boolean appointmentFound = false;

        for (Appointment apt : docAppts) {
            if (apt.getId() == appointmentId && apt.getStatus().equals("Pending")) { // link with patient's appointment
                apt.setStatus("Declined");
                appointmentFound = true;
                System.out.println("Appointment ID " + appointmentId + " has been declined.");
                break; // Exit loop after declining the appointment
            }
        }

        if (!appointmentFound) {
            System.out.println("Appointment ID " + appointmentId + " not found or already confirmed.");
        }
    }

    public void setAvail(String doctor, String date, String time,
            Map<String, Map<String, List<String>>> doctorAvailability) {
        // Ensure doctor exists in the availability map
        doctorAvailability.putIfAbsent(doctor, new HashMap<>());
        Map<String, List<String>> dates = doctorAvailability.get(doctor);

        // Check if the date exists
        if (dates.containsKey(date)) {
            List<String> times = dates.get(date);

            // Check if the time already exists for the specified date
            if (times.contains(time)) {
                System.out.println("This time slot is already set for Dr. " + doctor + " on " + date);
            } else {
                // Add the new time and sort the list
                times.add(time);
                Collections.sort(times); // Sort times to keep them in order
                System.out.println("Added new time slot for Dr. " + doctor + " on " + date + ": " + time);
            }
        } else {
            // If the date doesn't exist, create a new entry with the date and time
            dates.put(date, new ArrayList<>(List.of(time)));
            System.out.println("Added new date and time slot for Dr. " + doctor + ": " + date + " " + time);
        }
    }

    public String printingOut() {
        return "AppointmentID: " + appointmentId + ", Patient ID: " + patientId + ", Date: " + date + ", Time: " + time
                + ", Status: " + status;
    }
}
