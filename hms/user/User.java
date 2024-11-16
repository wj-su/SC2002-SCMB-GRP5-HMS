package user;

import java.util.List;
import java.util.Map;

public interface User {

    /*
     * Authenticates the user with the provided password
     * 
     * @param password The password entered by the user
     * @return true if the password is correct
     */
    boolean login(String password);

    /*
     * Changes the user's password and updates the relevant records
     * 
     * @param newPassword The new password to set
     * @param selectedList A list of records to update the password in
     */
    void changePassword(String newPassword, List<Map<String, String>> selectedList);
    
    /*
     * Display the menu options available to the user
     */
    void displayMenu();

    /*
     * Retrieves the role of the user
     * 
     * @return The role of the user as a String
     */
    String getRole();

    /*
     * Checks if the user is logging in for the first time
     */
    boolean isFirstLogin();

    /*
     * Sets whether the user is logging in for the first time.
     * 
     * @param firstLogin true if it is the user's first login, otherwise, false.
     */
    void setFirstLogin(boolean firstLogin);


}
