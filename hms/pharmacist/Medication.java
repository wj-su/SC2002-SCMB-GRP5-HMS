package pharmacist;

import static java.lang.Integer.parseInt;
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
