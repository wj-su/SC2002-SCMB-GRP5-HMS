package admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffManagement {

    public StaffManagement() {

    }

    public void viewStaff(List<Map<String, String>> staffList, String role, String gender, int minAge, int maxAge) {
        System.out.println("=== Filtered Staff List ===");
        boolean staffFound = false;

        for (Map<String, String> staff : staffList) {
            String staffRole = staff.get("Role");
            String staffGender = staff.get("Gender");
            int staffAge = Integer.parseInt(staff.get("Age"));

            if ((role == null || role.isEmpty() || staffRole.equalsIgnoreCase(role)) &&
                    (gender == null || gender.isEmpty() || staffGender.equalsIgnoreCase(gender)) &&
                    (staffAge >= minAge && staffAge <= maxAge)) {

                staffFound = true;
                System.out.println("Staff ID: " + staff.get("Staff ID"));
                System.out.println("Name: " + staff.get("Name"));
                System.out.println("Role: " + staff.get("Role"));
                System.out.println("Gender: " + staff.get("Gender"));
                System.out.println("Age: " + staff.get("Age"));
                System.out.println("-----------------------");
            }
        }

        if (!staffFound) {
            System.out.println("No staff found matching the criteria.");
        }
    }

    public void addStaff(List<Map<String, String>> staffList, String id, String name, String role, String gender, String age) {
        boolean existingStaff = false;
    
        for (Map<String, String> data : staffList) {
            String sId = data.get("Staff ID");
            if (sId.equals(id)) {
                existingStaff = true;
                break; 
            }
        }
    
        if (existingStaff) {
            System.out.println("Error: A staff member with ID " + id + " already exists.");
        } 
        else {
            Map<String, String> newStaff = new HashMap<>();
            newStaff.put("Staff ID", id);
            newStaff.put("Name", name);
            newStaff.put("Role", role);
            newStaff.put("Gender", gender);
            newStaff.put("Age", String.valueOf(age));
    
            staffList.add(newStaff);
            System.out.println("Staff member added successfully: " + name);
        }
    }
    

    public void updateStaff(List<Map<String, String>> staffList, String id, String name, String role, String gender,
            String age, String choiceUpdate) {
        boolean staffFound = false;

        if (choiceUpdate.equalsIgnoreCase("E")) {
            for (Map<String, String> staff : staffList) {
                if (staff.get("Staff ID").equals(id)) {
                    staff.put("Name", name);
                    staff.put("Role", role);
                    staff.put("Gender", gender);
                    staff.put("Age", String.valueOf(age));
                }

                staffFound = true;
                System.out.println("Staff member updated successfully: " + name);
                break;
            }
        } else {
            for (Map<String, String> staff : staffList) {
                if (staff.get("Staff ID").equals(id)) {
                    if (choiceUpdate.equalsIgnoreCase("Name")) {
                        staff.put("Name", name);
                    } else if (choiceUpdate.equalsIgnoreCase("Role")) {
                        staff.put("Role", role);
                    } else if (choiceUpdate.equalsIgnoreCase("Gender")) {
                        staff.put("Gender", gender);
                    } else if (choiceUpdate.equalsIgnoreCase("Age")) {
                        staff.put("Age", String.valueOf(age));
                    }

                    staffFound = true;
                    System.out.println("Staff member updated successfully: " + name);
                    break;
                }
            }
        }

        if (!staffFound) {
            System.out.println("No staff member found with ID: " + id);
        }
    }

    public void removeStaff(List<Map<String, String>> staffList, String id) {
        boolean staffFound = false;

        for (int i = 0; i < staffList.size(); i++) {
            Map<String, String> staff = staffList.get(i);
            if (staff.get("Staff ID").equals(id)) {
                staffList.remove(i);
                staffFound = true;
                System.out.println("Staff member removed successfully: " + id);
                break;
            }
        }

        if (!staffFound) {
            System.out.println("No staff member found with ID: " + id);
        }
    }

}
