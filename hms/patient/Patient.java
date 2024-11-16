package patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import user.User;

public class Patient implements User {
    private String id;
    private String name;
    private String dob;
    private String gender;
    private List<String> contactInformation;
    private String blood;
    private List<String> pastDiagnoses;
    private List<String> pastTreatments;
    private String password;
    private String role;

    public Patient() {
        // just to initialise empty constructor
    }

    public Patient(String id, String name, String dob, List<String> ci, String gender, String blood, List<String> pd,
            List<String> pt) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.contactInformation = new ArrayList<>(ci);
        this.gender = gender;
        this.blood = blood;
        this.pastDiagnoses = new ArrayList<>(pd);
        this.pastTreatments = new ArrayList<>(pt);
        this.role = "Patient";
        this.password = "password";
    }

    public static Map<String, Boolean> loginStatus = new HashMap<>();

    @Override
    public boolean isFirstLogin() {
        return loginStatus.getOrDefault(this.getId(), true); // Default to true
    }

    @Override
    public void setFirstLogin(boolean firstLogin) {
        loginStatus.put(this.getId(), firstLogin);
    }

    @Override
    public String getRole() {
        return this.role;
    }

    @Override
    public boolean login(String pw) {
        return this.password.equals(pw);
    }

    @Override
    public void changePassword(String pw, List<Map<String, String>> selectedList) {
        this.password = pw; // Update the local instance variable
    
        // Update the password and FirstLogin fields in the selected list
        for (Map<String, String> patientData : selectedList) {
            if (patientData.get("Patient ID").equals(this.getId())) {
                patientData.put("Password", pw); // Update the password in the list
                patientData.put("FirstLogin", "false"); // Update the first login status
                break;
            }
        }
    
        System.out.println("Password has been changed successfully.");
    }
    

    @Override
    public void displayMenu() {
        System.out.println("---------------------------------------------");
        System.out.println("|                Patient Menu                 |");
        System.out.println("----------------------------------------------");
        System.out.println("|- View Medical Record                    (1) |");
        System.out.println("|- Update Personal Information            (2) |");
        System.out.println("|- View Available Appointment Slots       (3) |");
        System.out.println("|- Schedule an Appointment                (4) |");
        System.out.println("|- Reschedule an Appointment              (5) |");
        System.out.println("|- Cancel an Appointment                  (6) |");
        System.out.println("|- View Scheduled Appointments            (7) |");
        System.out.println("|- View Past Appointments Outcome Records (8) |");
        System.out.println("|- Export your own data                   (9) |");
        System.out.println("|- View Billing Info                      (10) |");
        System.out.println("|- Pay A Bill                             (11) |");
        System.out.println("|- Rate Doctor                            (12) |");
        System.out.println("|- Logout                                 (13) |");
        System.out.println("----------------------------------------------");
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

    public void updatePersonalInformation(String phone, String email, List<Map<String, String>> patientList) {
        if (!email.equalsIgnoreCase("no")) {
            this.contactInformation.set(0, email);
        }
        if (!phone.equalsIgnoreCase("no")) {
            this.contactInformation.set(1, phone);
        }

        String updatedEmail = email.equalsIgnoreCase("no") ? this.contactInformation.get(0) : email;
        String updatedPhone = phone.equalsIgnoreCase("no") ? this.contactInformation.get(1) : phone;
        String updatedContactInfo = updatedEmail + "|" + updatedPhone;

        for (Map<String, String> patientData : patientList) {
            String pid = patientData.get("Patient ID");

            if (pid != null && pid.equalsIgnoreCase(id)) {
                patientData.put("Contact Information", updatedContactInfo);

                System.out.println("Updated Contact Information: " + patientData.get("Contact Information"));
                System.out.println("========================\n");
                break;
            }
        }
    }

    public void viewMedicalRecord() {
        System.out.println("Patient's Id: " + getId());
        System.out.println("Patient's Name: " + getName());
        System.out.println("Patient's Date of Birth: " + getDOB());
        System.out.println("Patient's Gender: " + getGender());
        System.out.println("Patient's Contact Information: " + getContactInformation().get(1) + " (phone) & "
                + getContactInformation().get(0) + " (email)");
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