package hms.patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hms.user.User;

/**
 * Represents a patient in the hospital system.
 * Implements the User interface to provide functionalities such as login, updating personal information, and viewing medical records.
 */
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

    /**
     * Default constructor.
     */
    public Patient() {}

    /**
     * Constructor to initialize a patient object.
     *
     * @param id                The unique ID of the patient.
     * @param name              The name of the patient.
     * @param dob               The date of birth of the patient.
     * @param ci                The contact information of the patient (email and phone).
     * @param gender            The gender of the patient.
     * @param blood             The blood type of the patient.
     * @param pd                The past diagnoses of the patient.
     * @param pt                The past treatments of the patient.
     */
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

    /**
     * Static map to track the login status of patients.
     * Key: Patient ID, Value: true if first login, false otherwise.
     */
    public static Map<String, Boolean> loginStatus = new HashMap<>();

    /**
     * Checks if the patient is logging in for the first time.
     *
     * @return true if it is the first login, false otherwise.
     */
    @Override
    public boolean isFirstLogin() {
        return loginStatus.getOrDefault(this.getId(), true); 
    }

    /**
     * Sets the first login status for the patient.
     *
     * @param firstLogin true if it is the first login, false otherwise.
     */
    @Override
    public void setFirstLogin(boolean firstLogin) {
        loginStatus.put(this.getId(), firstLogin);
    }

    /**
     * Retrieves the role of the user.
     *
     * @return The role as a string ("Patient").
     */
    @Override
    public String getRole() {
        return this.role;
    }

    /**
     * Authenticates the patient using the provided password.
     *
     * @param pw The password entered by the patient.
     * @return true if the password matches, false otherwise.
     */
    @Override
    public boolean login(String pw) {
        return this.password.equals(pw);
    }

    /**
     * Changes the patient's password and updates the selected patient list.
     *
     * @param pw          The new password to set.
     * @param selectedList A list of patient data to update.
     */
    @Override
    public void changePassword(String pw, List<Map<String, String>> selectedList) {
        this.password = pw; 
    
        
        for (Map<String, String> patientData : selectedList) {
            if (patientData.get("Patient ID").equals(this.getId())) {
                patientData.put("Password", pw); 
                patientData.put("FirstLogin", "false"); 
                break;
            }
        }
    
        System.out.println("Password has been changed successfully.");
    }

    /**
     * Displays the menu options available to the patient.
     */
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

    /**
     * Sets the ID of the patient.
     *
     * @param id The new ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retrieves the ID of the patient.
     *
     * @return The ID of the patient.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Sets the name of the patient.
     *
     * @param name The new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the name of the patient.
     *
     * @return The name of the patient.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the date of birth of the patient.
     *
     * @param dob The new date of birth to set.
     */
    public void setDOB(String dob) {
        this.dob = dob;
    }

    /**
     * Retrieves the date of birth of the patient.
     *
     * @return The date of birth.
     */
    public String getDOB() {
        return this.dob;
    }

    /**
     * Sets the gender of the patient.
     *
     * @param g The gender to set (e.g., "Male", "Female").
     */
    public void setGender(String g) {
        this.gender = g;
    }

    /**
     * Retrieves the gender of the patient.
     *
     * @return The gender of the patient.
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Sets the contact information for the patient.
     *
     * @param ci A list containing the patient's contact information (e.g., email and phone number).
     */
    public void setContactInformation(List<String> ci) {
        this.contactInformation = ci;
    }

    /**
     * Retrieves the contact information of the patient.
     *
     * @return A list containing the patient's contact information.
     */
    public List<String> getContactInformation() {
        return this.contactInformation;
    }

    /**
     * Sets the blood type of the patient.
     *
     * @param blood The blood type to set (e.g., "A+", "O-").
     */
    public void setBlood(String blood) {
        this.blood = blood;
    }

    /**
     * Retrieves the blood type of the patient.
     *
     * @return The blood type of the patient.
     */
    public String getBlood() {
        return this.blood;
    }

    /**
     * Sets the past diagnoses for the patient.
     *
     * @param pastDiagnoses A list of past diagnoses to set.
     */
    public void setPastDiagnoses(List<String> pastDiagnoses) {
        this.pastDiagnoses = pastDiagnoses;
    }

    /**
     * Retrieves the past diagnoses of the patient.
     *
     * @return A list of the patient's past diagnoses.
     */
    public List<String> getPastDiagnoses() {
        return this.pastDiagnoses;
    }

    /**
     * Sets the past treatments for the patient.
     *
     * @param pastTreatments A list of past treatments to set.
     */
    public void setPastTreatments(List<String> pastTreatments) {
        this.pastTreatments = pastTreatments;
    }

    /**
     * Retrieves the past treatments of the patient.
     *
     * @return A list of the patient's past treatments.
     */
    public List<String> getPastTreatments() {
        return this.pastTreatments;
    }

    /**
     * Updates the patient's contact information in the system.
     *
     * @param phone        The updated phone number (or "no" to keep unchanged).
     * @param email        The updated email (or "no" to keep unchanged).
     * @param patientList  The list of patient records to update.
     */
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

    /**
     * Displays the patient's medical record, including personal and medical details.
     */
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