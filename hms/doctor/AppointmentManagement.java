package doctor;

import java.util.ArrayList;
import java.util.List;
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

    // public void acceptAppointment(int appointmentId){
    // for (Appointment appointment : appointments){
    // if (appointment.getId() == appointmentId &&
    // appointment.getStatus().equals("Pending")){ //link with patients appointment
    // appointment.setStatus("Confirmed");
    // System.out.println("Appointment ID "+ appointmentId + "confirmed.");
    // return;
    // }
    // }

    // System.out.println("Appointment ID "+ appointmentId+ " not found or already
    // confirmed.");
    // }

    // public void declineAppointment(int AppointmentId){
    // for(Appointment appointment : appointments){
    // if(appointment.getId() == AppointmentId &&
    // appointment.getStatus().equals("Pending")){
    // appointment.setStatus("Cancelled");
    // System.out.println("Appointment ID "+AppointmentId+" cancelled.");
    // return;
    // }
    // }

    // System.out.println("Appointment ID "+AppointmentId+" not found or already
    // cancelled.");
    // }

    public String printingOut() {
        return "AppointmentID: " + appointmentId + ", Patient ID: " + patientId + ", Date: " + date + ", Time: " + time
                + ", Status: " + status;
    }
}
