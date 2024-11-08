package doctor;

public class AppointmentManagement {
    private int appointmentId;
    private String patientId;
    private String doctorId;
    private String date;
    private String time;
    private String status;

    public AppointmentManagement(int appointmentId, String patientId, String doctorId, String date, String time){
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.time = time;
        this.status = "Pending"; //By Default
    }

    public int getAppointmentId(){
        return appointmentId;
    }

    public String getPatientId(){
        return patientId;
    }

    public String getDoctorId(){
        return doctorId;
    }

    public String getDate(){
        return date;
    }

    public String getTime(){
        return time;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String printingOut(){
        return "AppointmentID: " + appointmentId + ", Patient ID: " + patientId + ", Date: " +date+ ", Time: " +time + ", Status: " + status;
    }
    
}
