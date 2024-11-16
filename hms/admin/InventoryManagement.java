package admin;

import static java.lang.Integer.parseInt;
import java.util.List;
import java.util.Map;

/**
 * Manages the inventory of medications in the hospital system.
 * Provides functionalities to view, update, and approve replenishment requests for stock levels.
 */
public class InventoryManagement {

    /**
     * Default constructor.
     */
    public InventoryManagement() {}

    /**
     * Displays all medications in the inventory along with their details.
     * Alerts if stock is below the low stock level.
     *
     * @param medicineList A list of medicines with their details such as name, stock level, and price.
     */
    public void viewAllMedicationInventory(List<Map<String, String>> medicineList) {
        if (medicineList.isEmpty()) {
            System.out.println("The medicine list is empty. Please load data first.");
            return;
        }

        for (Map<String, String> medicineData : medicineList) {
            System.out.println("=== Medication Details ===");

            String mn = medicineData.getOrDefault("Medicine Name", "N/A");
            String is = medicineData.getOrDefault("Initial Stock", "N/A");
            String ls = medicineData.getOrDefault("Low Stock Level Alert", "N/A");
            String pr = medicineData.getOrDefault("Price", "N/A");

            System.out.println("Medicine Name           : " + mn);
            System.out.println("Initial Stock           : " + is);
            System.out.println("Low Stock Level Alert   : " + ls);
            System.out.println("Price                   : " + pr);
            if (parseInt(is) <= parseInt(ls)) {
                System.out.println("Your current stock quantity is LOW. Please submit replenishment request to admin!");
            }
            System.out.println("========================\n");
        }
    }

    /**
     * Displays all replenishment requests along with their details and status.
     *
     * @param replenishmentRequests A list of replenishment requests containing medicine name, quantity, and status.
     */
    public void viewReplenishmentRequests(List<Map<String, String>> replenishmentRequests) {
        if (replenishmentRequests.isEmpty()) {
            System.out.println("No replenishment requests available.");
            return;
        }

        System.out.println("\n=== Replenishment Requests ===");
        for (Map<String, String> request : replenishmentRequests) {
            System.out.println("Medicine Name       : " + request.get("Medicine Name"));
            System.out.println("Requested Quantity  : " + request.get("Requested Quantity"));
            System.out.println("Status              : " + request.get("Status"));
            System.out.println("-------------------------------");
        }
    }

    /**
     * Updates the stock level of a specific medicine in the inventory.
     *
     * @param medicineList  A list of medicines with their details.
     * @param medicineName  The name of the medicine to update.
     * @param newStockLevel The new stock level to set.
     */
    public void updateStockLevel(List<Map<String, String>> medicineList, String medicineName, String newStockLevel) {
        boolean found = false;

        for (Map<String, String> medicine : medicineList) {
            if (medicine.get("Medicine Name").equalsIgnoreCase(medicineName)) {
                medicine.put("Initial Stock", String.valueOf(newStockLevel));
                found = true;
                System.out.println("Updated stock level for " + medicineName + " to " + newStockLevel);
                break;
            }
        }

        if (!found) {
            System.out.println("Medicine " + medicineName + " not found in inventory.");
        }
    }

    /**
     * Updates the low stock alert level for a specific medicine.
     *
     * @param medicineList   A list of medicines with their details.
     * @param medicineName   The name of the medicine to update.
     * @param newAlertLevel  The new low stock alert level to set.
     */
    public void updateLowStockAlert(List<Map<String, String>> medicineList, String medicineName, String newAlertLevel) {
        boolean found = false;

        for (Map<String, String> medicine : medicineList) {
            if (medicine.get("Medicine Name").equalsIgnoreCase(medicineName)) {
                medicine.put("Low Stock Level Alert", String.valueOf(newAlertLevel));
                found = true;
                System.out.println("Updated low stock alert for " + medicineName + " to " + newAlertLevel);
                break;
            }
        }

        if (!found) {
            System.out.println("Medicine " + medicineName + " not found in inventory.");
        }
    }

    /**
     * Approves a replenishment request for a specific medicine and updates its stock level in the inventory.
     *
     * @param medicineList           A list of medicines with their details.
     * @param replenishmentRequests  A list of replenishment requests.
     * @param medicineName           The name of the medicine to approve.
     */
    public void approveReplenishmentRequest(List<Map<String, String>> medicineList,
            List<Map<String, String>> replenishmentRequests, String medicineName) {
                boolean found = false;

        for (Map<String, String> request : replenishmentRequests) {
            if (request.get("Medicine Name").equalsIgnoreCase(medicineName)) {
                request.put("Status", "Approved");
                System.out.println("Updated replenishment request for: " + medicineName);

                for (Map<String, String> medicine : medicineList) {
                    if (medicine.get("Medicine Name").equalsIgnoreCase(medicineName)) {
                        medicine.put("Initial Stock", request.get("Requested Quantity"));
                        System.out.println("Updated stock level for " + medicineName + " to " + request.get("Requested Quantity") );
                        break;
                    }
                }


                break;
            }
        }

        if (replenishmentRequests.isEmpty()) {
            System.out.println("No medications require replenishment.");
        } else {
            System.out.println("Replenishment requests processed successfully.");
        }
    }

}
