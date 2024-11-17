package hms.staff;

/**
 * Represents a staff member in the hospital system.
 * Provides basic information such as staff ID, name, age, and gender.
 */
public class Staff {
    /**
     * The unique identifier for the staff member.
     */
    private String staffId;

    /**
     * The name of the staff member.
     */
    private String name;

    /**
     * The gender of the staff member.
     */
    private String gender;

    /**
     * The age of the staff member.
     */
    private String age;
    /**
     * Constructor to initialize a staff member's details
     * 
     * @param staffId The unique ID of the staff member
     * @param name    The name of the staff member
     * @param age     The age of the staff member
     * @param gender  The gender of the staff member
     */
    public Staff(String staffId, String name, String age, String gender) {
        this.staffId = staffId;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    /**
     * Retrieves the staff ID
     * 
     * @return The staff ID
     */
    public String getStaffId() {
        return staffId;
    }

    /**
     * Sets the staff ID
     * 
     * @param staffId The new staff ID
     */
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    /**
     * Retrieves the name of the staff member
     * 
     * @return The name of the staff member
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the staff member
     * 
     * @param name The new name of the staff member
     */
    public void setNameId(String name) {
        this.name = name;
    }

    /**
     * Retrieves the age of the staff member
     * 
     * @return The age of the staff member
     */
    public String getAge() {
        return age;
    }

    /**
     * Sets the age of the staff member
     * 
     * @param age The new age of the staff member
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * Retrieved the gender of the staff member
     * 
     * @return The gender of the staff member
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the staff member
     * 
     * @param gender The new gender of the staff member
     */
    public void setGender(String gender) {
        this.gender = gender;
    }


}
