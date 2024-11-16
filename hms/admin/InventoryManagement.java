package admin;

import static java.lang.Integer.parseInt;
import java.util.List;
import java.util.Map;

public class InventoryManagement {
    public InventoryManagement() {

    }

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

    public void updateStockLevel(List<Map<String, String>> medicineList, String medicineName, String newStockLevel) {
        boolean found = false;

        for (Map<String, String> medicine : medicineList) {
            if (medicine.get("Medicine Name").equalsIgnoreCase(medicineName)) {
                medicine.put("Initial Stock Level", String.valueOf(newStockLevel));
                found = true;
                System.out.println("Updated stock level for " + medicineName + " to " + newStockLevel);
                break;
            }
        }

        if (!found) {
            System.out.println("Medicine " + medicineName + " not found in inventory.");
        }
    }

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

    public void approveReplenishmentRequest(List<Map<String, String>> medicineList,
            List<Map<String, String>> replenishmentRequests, String medicineName) {
                boolean found = false;

        // Check if a replenishment request already exists for this medicine
        for (Map<String, String> request : replenishmentRequests) {
            if (request.get("Medicine Name").equalsIgnoreCase(medicineName)) {
                // Update the status of the existing request
                request.put("Status", "Approved");
                System.out.println("Updated replenishment request for: " + medicineName);
                found = true;
                break;
            }
        }
        if (found){
            for (Map<String, String> medicine : medicineList) {
                if (medicine.get("Medicine Name").equalsIgnoreCase(medicineName)) {
                    int initialStock = Integer.parseInt(medicine.get("Initial Stock"));
                    medicine.put("Initial Stock Level", String.valueOf(initialStock * 2));
                    System.out.println("Updated stock level for " + medicineName + " to " + initialStock*2 );
                    break;
                }
            }
    
        }
        if (replenishmentRequests.isEmpty()) {
            System.out.println("No medications require replenishment.");
        } else {
            System.out.println("Replenishment requests processed successfully.");
        }
    }

}
