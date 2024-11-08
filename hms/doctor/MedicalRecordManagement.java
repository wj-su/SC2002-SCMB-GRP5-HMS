package doctor;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecordManagement{

    private String patientId;
    private String name;
    private String bloodType;
    private List<String> diagnoses;
    private List<String> treatments;
    private List<String> medications;

    public MedicalRecordManagement(String patientId, String name, String bloodType){
        this.patientId  =patientId;
        this.name = name;
        this. bloodType = bloodType;
        this.diagnoses = new ArrayList<>();
        this.treatments = new ArrayList<>();
        this.medications = new ArrayList<>();
    }

    public String getPatientId(){
        return patientId;
    }

    public String getName(){
        return name;
    }

    public String getBloodType(){
        return bloodType;
    }
    
    public List<String> getDiagnoses(){
        return diagnoses;
    }

    public List<String> getTreatments(){
        return treatments;
    }

    public List<String> getMedications(){
        return medications;
    }

    public void addDiagnosis(String diagnosis){
        diagnoses.add(diagnosis);
    }

    public void addTreatmentt(String treatment){
        treatments.add(treatment);
    }

    public void addMedication(String medication){
        medications.add(medication);
    }

    @Override
    public String toString(){
        return "Patient ID: " +patientId+", Name: " +name+", Blood Type: " + bloodType + "\nDiagnoses: " +diagnoses+"\nTreatments: "+treatments+"\nMedications: " +medications;
    }
}


