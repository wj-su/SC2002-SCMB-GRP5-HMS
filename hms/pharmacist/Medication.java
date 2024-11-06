package pharmacist;
public class Medication {
    private String medicationId;
    private String name;
    private int stockLevel;
    private int lowStockAlertLevel;

    public Medication(String medicationId, String name, int stockLevel, int lowStockAlertLevel) {
        this.medicationId = medicationId;
        this.name = name;
        updateStockLevel(stockLevel);
        setLowStockAlertLevel(lowStockAlertLevel);
    }

    public String getMedicationId() {
        return medicationId;
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

    @Override
    public String toString() {
        return "Medication [ID=" + medicationId + ", Name=" + name + ", Stock Level=" + stockLevel + ", Low Stock Alert=" + lowStockAlertLevel + "]";
    }
}
