package patient;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private String id;
    private String name;
    private String dob;
    private String gender;
    private List<String> contactInformation;
    private String blood;
    private List<String> pastDiagnoses;
    private List<String> pastTreatments;

    public Patient() {
        // just to initialise empty constructor
    }

    public Patient(String id, String name, String dob, List<String> ci, String gender, String blood, List<String> pd, List<String> pt) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.contactInformation = new ArrayList<>(ci);
        this.gender = gender;
        this.blood = blood;
        this.pastDiagnoses = new ArrayList<>(pd);
        this.pastTreatments = new ArrayList<>(pt);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDOB(String dob) {
        this.dob = dob;
    }

    public String getDOB() {
        return this.dob;
    }

    public void setGender(String g) {
        this.gender = g;
    }

    public String getGender() {
        return this.gender;
    }

    public void setContactInformation(List<String> ci) {
        this.contactInformation = ci;
    }

    public List<String> getContactInformation() {
        return this.contactInformation;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getBlood() {
        return this.blood;
    }

    public void setPastDiagnoses(List<String> pastDiagnoses) {
        this.pastDiagnoses = pastDiagnoses;
    }

    public List<String> getPastDiagnoses() {
        return this.pastDiagnoses;
    }

    public void setPastTreatments(List<String> pastTreatments) {
        this.pastTreatments = pastTreatments;
    }

    public List<String> getPastTreatments() {
        return this.pastTreatments;
    }

    public void updatePersonalInformation(String phone, String email) {
        if (!email.equalsIgnoreCase("no")) {
            this.contactInformation.set(0, email);
        }
        if (!phone.equalsIgnoreCase("no")) {
            this.contactInformation.set(1, phone);
        }
    }
    

    public void viewMedicalRecord() {
        System.out.println("Patient's Id: " + getId());
        System.out.println("Patient's Name: " + getName());
        System.out.println("Patient's Date of Birth: " + getDOB());
        System.out.println("Patient's Gender: " + getGender());
        System.out.println("Patient's Contact Information: " + getContactInformation().get(1) + " (phone) & " + getContactInformation().get(0) + " (email)");
        System.out.println("Patient's Blood Type: " + getBlood());
        System.out.println("Patient's Past Diagnoses and Treatments: ");
        System.out.println("----- Past Diagnoses ----- ");
        for (int i = 0; i < pastDiagnoses.size(); i++) {
            System.out.println(pastDiagnoses.get(i));
        }
        System.out.println("----- Past Treatments ----- ");
        for (int i = 0; i < pastTreatments.size(); i++) {
            System.out.println(pastTreatments.get(i));
        }
    }

}