package doctor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import patient.Appointment;

public class MedicalRecordManagement {

    private String patientId;
    private String name;
    private String bloodType;
    private List<String> diagnoses;
    private List<String> treatments;
    private List<String> medications;
    private List<Appointment> appts = new ArrayList<>();

    public MedicalRecordManagement() {

    }

    public MedicalRecordManagement(String patientId, String name, String bloodType) {
        this.patientId = patientId;
        this.name = name;
        this.bloodType = bloodType;
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.medications = new ArrayList<>();
    }

    public void viewMedicalRecord(String patientId, List<Map<String, String>> patientList) {
        // MedicalRecordManagement record = medicalRecords.get(patientId);

        boolean patientFound = false;

        // Iterate over the patientList
        for (Map<String, String> patientData : patientList) {
            

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
            // Iterate over the patientList
            for (Map<String, String> patientData : patientList) {
                String id = patientData.get("Patient ID");

                // Check if the entered pid matches the id in the list
                if (id != null && id.equalsIgnoreCase(patientId)) {
                    patientFound = true;

                    // Retrieve existing past diagnoses and treatments, or initialize if not present
                    String pastDiagnoses = patientData.getOrDefault("Past Diagnoses", "");
                    String pastTreatments = patientData.getOrDefault("Past Treatment", "");

                    // Append new diagnosis and treatment to the existing values
                    if (diagnosis != null && !diagnosis.isEmpty()) {
                        if (pastDiagnoses.equalsIgnoreCase("NIL")) {
                            pastDiagnoses = diagnosis; // Replace "NIL" with the first valid diagnosis
                        } else {
                            pastDiagnoses = pastDiagnoses.isEmpty() ? diagnosis : pastDiagnoses + " | " + diagnosis;
                        }
                        patientData.put("Past Diagnoses", pastDiagnoses);
                    }

                    if (treatment != null && !treatment.isEmpty()) {
                        if (pastTreatments.equalsIgnoreCase("NIL")) {
                            pastTreatments = treatment; // Replace "NIL" with the first valid treatment
                        } else {
                            pastTreatments = pastTreatments.isEmpty() ? treatment : pastTreatments + " | " + treatment;
                        }
                        patientData.put("Past Treatment", pastTreatments);
                    }

                    // Display updated details
                    System.out.println("Updated Diagnoses: " + patientData.get("Past Diagnoses"));
                    System.out.println("Updated Treatment: " + patientData.get("Past Treatment"));
                    System.out.println("========================\n");
                    break; // Exit loop after finding and updating the patient
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
    

    @Override
    public String toString() {
        return "Patient ID: " + patientId + ", Name: " + name + ", Blood Type: " + bloodType + "\nDiagnoses: "
                + diagnoses + "\nTreatments: " + treatments + "\nMedications: " + medications;
    }
}
