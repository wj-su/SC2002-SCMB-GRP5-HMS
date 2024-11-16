package user;

import java.util.List;
import java.util.Map;

public interface User {

    boolean login(String password);

    // Method to change password
    void changePassword(String newPassword, List<Map<String, String>> selectedList);

    // Method for role-specific menu
    void displayMenu();

    String getRole();

    boolean isFirstLogin();
    void setFirstLogin(boolean firstLogin);


}
