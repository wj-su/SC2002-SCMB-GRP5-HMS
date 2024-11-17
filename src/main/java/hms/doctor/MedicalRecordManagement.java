package hms.doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import hms.patient.Appointment;

/**
 * Manages medical records for patients, including viewing and updating records
 * Integrates with appointmnets to ensure medical records are tied to valid appointments
 */

public class MedicalRecordManagement {

    private String patientId;
    private String name;
    private String bloodType;
    private List<String> diagnoses;
    private List<String> treatments;
    private List<String> medications;
    private List<Appointment> appts = new ArrayList<>();

    /**
     * Default constructor 
     */
    public MedicalRecordManagement() {}

    /**
     * Constructor to initialize medical record details for a patient
     * 
     * @param patientId The unique ID of the patient
     * @param name      The name of the patient
     * @paran bloodType The blood type of the patient
     */
    public MedicalRecordManagement(String patientId, String name, String bloodType) {
        this.patientId = patientId;
        this.name = name;
        this.bloodType = bloodType;
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.medications = new ArrayList<>();
    }

    /***
     * Views the medical record for a specified patient
     * 
     * @param patientId     The unique ID of the patients
     * @param patientList   A list of patient records containing details like ID, and medical history
     */
    public void viewMedicalRecord(String patientId, List<Map<String, String>> patientList) {

        boolean patientFound = false;
        
        for (Map<String, String> patientData : patientList) {
            
            String id = patientData.get("Patient ID");

            if (id != null && id.equals(patientId)) {
                patientFound = true;
                System.out.println("\nPatient Found:");
                System.out.println("\n=== Patient Details ===");

                
                String pid = patientData.getOrDefault("Patient ID", "N/A");
                String name = patientData.getOrDefault("Name", "N/A");
                String dob = patientData.getOrDefault("Date of Birth", "N/A");
                String gender = patientData.getOrDefault("Gender", "N/A");
                String blood = patientData.getOrDefault("Blood Type", "N/A");
                String contact = patientData.getOrDefault("Contact Information", "N/A");
                String pastDiagnoses = patientData.getOrDefault("Past Diagnoses", "N/A");
                String pastTreatments = patientData.getOrDefault("Past Treatment", "N/A");

                
                System.out.println("Patient ID     : " + pid);
                System.out.println("Name           : " + name);
                System.out.println("DOB            : " + dob);
                System.out.println("Gender         : " + gender);
                System.out.println("Blood Type     : " + blood);
                System.out.println("Contact        : " + contact);
                System.out.println("Past Diagnoses : " + pastDiagnoses);
                System.out.println("Past Treatment : " + pastTreatments);
                System.out.println("========================\n");
                break; 
            }
        }

        if (!patientFound) {
            System.out.println("No medical record found for Patient with ID " + patientId);
        }

    }

    /**
     * Updates the medical record for a specified patient based on appointment details
     * 
     * @param patientId     The unique ID of the patient
     * @param patientList   A list of patient records containing details like ID, name, and medical history 
     * @param appointmentId The ID of the appointment associated with the update
     * @param diagnosis     The diagnosis to add to the patient's record
     * @param treatment     The treatment to add to the patient's record
     * @param medication    The medication to add to the patient's record
     */

    public void updateMedicalRecord(String patientId, List<Map<String, String>> patientList, int appointmentId,
            String diagnosis, String treatment, String medication) {

        boolean appointmentExists = false;
        this.appts = Appointment.getAllAppointments();


        for (Appointment apt : appts) {
            if (apt.getId() == appointmentId) {
                appointmentExists = true;
                break;
            }
        }

        boolean patientFound = false;

        if (appointmentExists == true) {
            for (Map<String, String> patientData : patientList) {
                String id = patientData.get("Patient ID");

               
                if (id != null && id.equalsIgnoreCase(patientId)) {
                    patientFound = true;

                    
                    String pastDiagnoses = patientData.getOrDefault("Past Diagnoses", "");
                    String pastTreatments = patientData.getOrDefault("Past Treatment", "");

                   
                    if (diagnosis != null && !diagnosis.isEmpty()) {
                        if (pastDiagnoses.equalsIgnoreCase("NIL")) {
                            pastDiagnoses = diagnosis; 
                        } else {
                            pastDiagnoses = pastDiagnoses.isEmpty() ? diagnosis : pastDiagnoses + " | " + diagnosis;
                        }
                        patientData.put("Past Diagnoses", pastDiagnoses);
                    }

                    if (treatment != null && !treatment.isEmpty()) {
                        if (pastTreatments.equalsIgnoreCase("NIL")) {
                            pastTreatments = treatment; 
                        } else {
                            pastTreatments = pastTreatments.isEmpty() ? treatment : pastTreatments + " | " + treatment;
                        }
                        patientData.put("Past Treatment", pastTreatments);
                    }

                    
                    System.out.println("Updated Diagnoses: " + patientData.get("Past Diagnoses"));
                    System.out.println("Updated Treatment: " + patientData.get("Past Treatment"));
                    System.out.println("========================\n");
                    break; 
                }
            }

            for (Appointment apt : appts) {
                if (apt.getId() == appointmentId) {
                    apt.setStatus("Completed");
                    break;
                }
            }

            if (!patientFound) {
                System.out.println("No medical record found for Patient with ID " + patientId);
            }
        }
        else {
            System.out.println("Patient has not done such appointment!");
        }
    }
}
