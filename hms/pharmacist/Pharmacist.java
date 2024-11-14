package pharmacist;

import java.util.ArrayList;
import java.util.List;

public class Pharmacist {
    private String pharmacistId;
    private String name;
    private String gender;
    private String age;
    private String contactNumber;
    private List<Prescription> managedPrescriptions;

    public Pharmacist(String pharmacistId, String name, String age, String gender, String contactNumber) {
        this.pharmacistId = pharmacistId;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.managedPrescriptions = new ArrayList<>();
    }

    public String getPharmacistId() {
        return pharmacistId;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public List<Prescription> getManagedPrescriptions() {
        return managedPrescriptions;
    }

    public void setManagedPrescriptions(List<Prescription> managedPrescriptions) {
        this.managedPrescriptions = managedPrescriptions;
    }

    public void addPrescription(Prescription prescription) {
        this.managedPrescriptions.add(prescription);
    }

    public void viewPrescriptions() {
        System.out.println("Prescriptions managed by Pharmacist " + getName() + ":");
        for (Prescription prescription : managedPrescriptions) {
            System.out.println(prescription);
        }
    }

    public void updatePrescriptionStatus(String prescriptionId, String newStatus) {
        for (Prescription prescription : managedPrescriptions) {
            if (prescription.getPrescriptionId().equals(prescriptionId)) {
                prescription.updateStatus(newStatus);
                System.out.println("Updated Prescription " + prescriptionId + " to " + newStatus);
            }
        }
    }

    public static void submitReplenishmentRequest(String medId, int qty) {
    if (medicineInventory.containsKey(medId)) {
        int currentQty = medicineInventory.get(medId);
        medicineInventory.put(medId, currentQty + qty);  // Update inventory
        System.out.println("Replenishment successful for Medicine ID: " + medId + ". Total quantity now: " + (currentQty + qty));
    } else {
        System.out.println("Medicine ID " + medId + " not found in inventory.");
    }
}
    @Override
    public String toString() {
        return "Pharmacist ID: " + getPharmacistId() + ", Name: " + getName() + ", Age: " + getAge() + ", Gender: " + getGender()
                + ", Contact Number: " + getContactNumber();
    }
}
