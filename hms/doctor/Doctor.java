package doctor;

import java.util.ArrayList;
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
    // private  Map<String, MedicalRecordManagement> medicalRecords;

    public Doctor(String doctorId, String name, String age,String gender, String contactNumber, List<String> availDateTime){
        this.doctorId = doctorId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.availDateTime = availDateTime;
        this.appointments = new ArrayList<>();
        // this.medicalRecords = new HashMap<>();
    }
    
    public String getDoctorId(){
        return doctorId;
    }

    public String getName(){
        return name;
    }

    public String getAge(){
        return age;
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

    // public void addMedicalRecord(MedicalRecordManagement record){
    //     medicalRecords.put(record.getPatientId(), record);
    // 

    @Override

    public String toString(){
        return "Doctor ID: " + getDoctorId() + ", Name: " + getName() + ", Age: "+ age +", Gender: " + getGender() + ", Contact Number: "+getContactNumber()+ ", Available Dates: " +getAvailDateTime();
    }
}
