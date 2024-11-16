package user;

import java.util.List;
import java.util.Map;

public interface User {

    boolean login(String password);
    void changePassword(String newPassword, List<Map<String, String>> selectedList);
    void displayMenu();
    String getRole();
    boolean isFirstLogin();
    void setFirstLogin(boolean firstLogin);


}
