package patient;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int id;
    private String name;
    private String dob;
    private String gender;
    private String phone;
    private String email;
    private String blood;
    private List<String> pastDiagnoses;
    private List<String> pastTreatments;

    public Patient(int id, String name, String dob, String gender, String phone, String email, String blood, List<String> pd, List<String> pt) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.blood = blood;
        this.pastDiagnoses = new ArrayList<>(pd);
        this.pastTreatments = new ArrayList<>(pt);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
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
        if (!email.equals("no")) {
            this.email = email;
        }
        if (!phone.equals("no")) {
            this.phone = phone;
        }
    }

    public void viewMedicalRecord() {
        System.out.println("Patient's Id: " + getId());
        System.out.println("Patient's Name: " + getName());
        System.out.println("Patient's Date of Birth: " + getDOB());
        System.out.println("Patient's Gender: " + getGender());
        System.out.println("Patient's Contact Information: " + getPhone() + " (phone) & " + getEmail() + " (email)");
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