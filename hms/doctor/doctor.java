package doctor;

import java.util.ArrayList;
import java.util.List;
import patient.Appointment;

public class doctor {
    private String doctorId;
    private String name;
    private String gender;
    private String contactNumber;
    private List<String> availDateTime;
    private List<Appointment> appointments;

    public doctor(String doctorId, String name, String gender, String contactNumber, List<String> availDateTime){
        this.doctorId = doctorId;
        this.name = name;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.availDateTime = availDateTime;
        this.appointments = new ArrayList<>();
    }
    
    public String getDoctorId(){
        return doctorId;
    }

    public String getName(){
        return name;
    }

    public String getGender(){
        return gender;
    }

    public String getContactNumber(){
        return contactNumber;
    }

    public List<String> getAvailDateTime(){
        return availDateTime;
    }

    public void viewUpcomingAppointments(){
        System.out.println("Upcoming Appointments for Dr. " + name);
        for (Appointment appointment : appointments){
            if (appointment.getStatus().equals("Confirmed")){
                System.out.println(appointment);
            }
        }
    }

    public void acceptAppointment(int appointmentId){
        for (Appointment appointment : appointments){
            if (appointment.getId() == appointmentId && appointment.getStatus().equals("Pending")){ //link with patients appointment
                appointment.setStatus("Confirmed");
                System.out.println("Appointment ID "+ appointmentId + "confirmed.");
                return;
            }
        }

        System.out.println("Appointment ID "+ appointmentId+ " not found or already confirmed.");
    }

    public void declineAppointment(int AppointmentId){
        for(Appointment appointment : appointments){
            if(appointment.getId() == AppointmentId && appointment.getStatus().equals("Pending")){
                appointment.setStatus("Cancelled");
                System.out.println("Appointment ID "+AppointmentId+" cancelled.");
                return;
            }
        }

        System.out.println("Appointment ID "+AppointmentId+" not found or already cancelled.");
    }

    @Override

    public String toString(){
        return "Doctor ID: " + getDoctorId() + ", Name: " + getName() + ", Gender: " + getGender() + ", Contact Number: "+getContactNumber()+ ", Available Dates: " +getAvailDateTime();
    }
}
