package doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    // }

    public void viewMedicalRecord(String patientId, List<Map<String, String>> patientList){
        // MedicalRecordManagement record = medicalRecords.get(patientId);

        boolean patientFound = false;

        // Iterate over the patientList
        for (Map<String, String> patientData : patientList) {
            System.out.println("Checking patient: " + patientData);

            String id = patientData.get("Patient ID");

            // Check if the entered pid matches the id in the list
            if (id != null && id.equals(patientId)) {
                patientFound = true;
                System.out.println("\nPatient Found:");
                System.out.println("\n=== Patient Details ===");
    
                // Customize the output for each key
                String pid = patientData.getOrDefault("Patient ID", "N/A");
                String name = patientData.getOrDefault("Name", "N/A");
                String dob = patientData.getOrDefault("Date of Birth", "N/A");
                String gender = patientData.getOrDefault("Gender", "N/A");
                String blood = patientData.getOrDefault("Blood Type", "N/A");
                String contact = patientData.getOrDefault("Contact Information", "N/A");
                String pastDiagnoses = patientData.getOrDefault("Past Diagnoses", "N/A");
                String pastTreatments = patientData.getOrDefault("Past Treatment", "N/A");


                // Display each detail with a label
                System.out.println("Patient ID     : " + pid);
                System.out.println("Name           : " + name);
                System.out.println("DOB            : " + dob);
                System.out.println("Gender         : " + gender);
                System.out.println("Blood Type     : " + blood);
                System.out.println("Contact        : " + contact);
                System.out.println("Past Diagnoses : " + pastDiagnoses);
                System.out.println("Past Treatment : " + pastTreatments);
                System.out.println("========================\n");
                break; // Exit loop after finding the patient
            }
        }

        if (!patientFound) {
            System.out.println("No medical record found for Patient with ID " + patientId);
        }
           
    }

    public void updateMedicalRecord(String patientId, String appointmentId, String diagnosis, String treatment, String medication){
        if (record != null){
            if (diagnosis != null && !diagnosis.isEmpty()){
                record.addDiagnosis(diagnosis);
            }
            if (treatment != null && !treatment.isEmpty()){
                record.addDiagnosis(diagnosis);
            }
            if (medication != null && !medication.isEmpty()){
                record.addDiagnosis(diagnosis);
            }
            System.out.println("Medical record for Patient ID " + patientId + " updated successfully.");
        }else{
            System.out.println("No medical record found for Patient ID " +patientId);
        }
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
        return "Doctor ID: " + getDoctorId() + ", Name: " + getName() + ", Age: "+ age +", Gender: " + getGender() + ", Contact Number: "+getContactNumber()+ ", Available Dates: " +getAvailDateTime();
    }
}
