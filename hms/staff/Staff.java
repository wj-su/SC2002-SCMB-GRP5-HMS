package staff;

public class Staff {
    private String staffId;
    private String name;
    private String gender;
    private String age;

    public Staff(String staffId, String name, String age, String gender) {
        this.staffId = staffId;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getName() {
        return name;
    }

    public void setNameId(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
