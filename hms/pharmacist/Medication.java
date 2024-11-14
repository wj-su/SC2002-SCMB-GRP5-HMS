package pharmacist;

import java.util.List;
import java.util.Map;

public class Medication {
    private String medId;
    private String name;
    private int stockLevel;
    private int lowStockAlertLevel;

    public Medication() {

    }

    public Medication(String medId, String name, int stockLevel, int lowStockAlertLevel) {
        this.medId = medId;
        this.name = name;
        updateStockLevel(stockLevel);
        setLowStockAlertLevel(lowStockAlertLevel);
    }

    public String getmedId() {
        return medId;
    }

    public String getName() {
        return name;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public int getLowStockAlertLevel() {
        return lowStockAlertLevel;
    }

    public void updateStockLevel(int newStockLevel) {
        if (newStockLevel >= 0) {
            this.stockLevel = newStockLevel;
            checkLowStock();
        } else {
            System.out.println("Stock level cannot be negative.");
        }
    }

    public void setLowStockAlertLevel(int alertLevel) {
        if (alertLevel >= 0) {
            this.lowStockAlertLevel = alertLevel;
        } else {
            System.out.println("Alert level cannot be negative.");
        }
    }

    private void checkLowStock() {
        if (this.stockLevel <= this.lowStockAlertLevel) {
            System.out.println("Warning: Stock for " + name + " is low (" + stockLevel + " remaining).");
        }
    }

    public void viewMedicationInventory(List<Map<String, String>> medicineList) {
        for (Map<String, String> medicineData : medicineList) {
            System.out.println("Checking medication: " + medicineData);

            System.out.println("\n=== Medication Details ===");

            
            String mn = medicineData.getOrDefault("Medicine Name", "N/A");
            String is = medicineData.getOrDefault("Initial Stock", "N/A");
            String ls = medicineData.getOrDefault("Low Stock Level Alert", "N/A");
            

            // Display each detail with a label
            System.out.println("Medicine Name           : " + mn);
            System.out.println("Initial Stock           : " + is);
            System.out.println("Low Stock Level Alert   : " + ls);
            System.out.println("========================\n");
        }

    }

    @Override
    public String toString() {
        return "Medication [ID=" + medId + ", Name=" + name + ", Stock Level=" + stockLevel + ", Low Stock Alert="
                + lowStockAlertLevel + "]";
    }
}
