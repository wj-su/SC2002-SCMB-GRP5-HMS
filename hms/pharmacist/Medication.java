package pharmacist;

import static java.lang.Integer.parseInt;
import java.util.List;
import java.util.Map;

/**
 * Represents a medication in the pharmacy inventory system.
 * Tracks medication details such as stock levels, low stock alert levels, and provides functionality
 * to view and update the inventory.
 */
public class Medication {
    private String medId;
    private String name;
    private int stockLevel;
    private int lowStockAlertLevel;

    /**
     * Default constructor.
     */
    public Medication() {}

    /**
     * Constructor to initialize a medication object.
     *
     * @param medId              The unique ID of the medication.
     * @param name               The name of the medication.
     * @param stockLevel         The initial stock level of the medication.
     * @param lowStockAlertLevel The stock level at which a low stock alert should be triggered.
     */
    public Medication(String medId, String name, int stockLevel, int lowStockAlertLevel) {
        this.medId = medId;
        this.name = name;
        updateStockLevel(stockLevel);
        setLowStockAlertLevel(lowStockAlertLevel);
    }

    /**
     * Retrieves the unique ID of the medication.
     *
     * @return The medication ID.
     */
    public String getmedId() {
        return medId;
    }

    /**
     * Retrieves the name of the medication.
     *
     * @return The medication name.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the current stock level of the medication.
     *
     * @return The stock level.
     */
    public int getStockLevel() {
        return stockLevel;
    }

    /**
     * Retrieves the low stock alert level for the medication.
     *
     * @return The low stock alert level.
     */
    public int getLowStockAlertLevel() {
        return lowStockAlertLevel;
    }

    /**
     * Updates the stock level of the medication and checks for low stock warnings.
     *
     * @param newStockLevel The new stock level to set. Must be non-negative.
     */
    public void updateStockLevel(int newStockLevel) {
        if (newStockLevel >= 0) {
            this.stockLevel = newStockLevel;
            checkLowStock();
        } else {
            System.out.println("Stock level cannot be negative.");
        }
    }

    /**
     * Sets the low stock alert level for the medication.
     *
     * @param alertLevel The low stock alert level to set. Must be non-negative.
     */
    public void setLowStockAlertLevel(int alertLevel) {
        if (alertLevel >= 0) {
            this.lowStockAlertLevel = alertLevel;
        } else {
            System.out.println("Alert level cannot be negative.");
        }
    }

    /**
     * Checks if the stock level is below or equal to the low stock alert level and issues a warning if needed.
     */
    private void checkLowStock() {
        if (this.stockLevel <= this.lowStockAlertLevel) {
            System.out.println("Warning: Stock for " + name + " is low (" + stockLevel + " remaining).");
        }
    }

    /**
     * Displays the inventory of all medications in the system.
     *
     * @param medicineList A list of maps containing medication details, such as name, stock levels, and price.
     */
    public void viewMedicationInventory(List<Map<String, String>> medicineList) {
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
            if(parseInt(is) <= parseInt(ls)){
                System.out.println("Your current stock quantity is LOW. Please submit replenishment request to admin!");
            }
            System.out.println("========================\n");
        }
    }
}
