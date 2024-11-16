package patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import doctor.AppointmentOutcomeRecord;

public class BillingDetails {
    private Map<String, Map<Integer, Map<String, Object>>> outrecs = new HashMap<>();
    List<Map<String, Object>> billingList = new ArrayList<>();

    private static final double CONSULTATION_RATE = 30.0;

    public BillingDetails() {
        this.outrecs = AppointmentOutcomeRecord.getAllOutcomeRecords();
        printOutcomeRecords();
    }

    public void printOutcomeRecords() {
        // Check if the outrecs map is not empty
        if (this.outrecs != null && !this.outrecs.isEmpty()) {
            // Iterate through each entry in the outer map (which is keyed by patientId)
            for (Map.Entry<String, Map<Integer, Map<String, Object>>> patientEntry : this.outrecs.entrySet()) {
                String patientId = patientEntry.getKey();
                Map<Integer, Map<String, Object>> appointments = patientEntry.getValue();

                System.out.println("Patient ID: " + patientId);

                // Iterate through each appointment for the current patient
                for (Map.Entry<Integer, Map<String, Object>> appointmentEntry : appointments.entrySet()) {
                    Integer appointmentId = appointmentEntry.getKey();
                    Map<String, Object> appointmentDetails = appointmentEntry.getValue();

                    System.out.println("  Appointment ID: " + appointmentId);

                    // Print all the details of the appointment
                    for (Map.Entry<String, Object> detailEntry : appointmentDetails.entrySet()) {
                        String key = detailEntry.getKey();
                        Object value = detailEntry.getValue();
                        System.out.println("    " + key + ": " + value);
                    }
                    System.out.println("-----");
                }
            }
        } else {
            System.out.println("No outcome records available.");
        }
    }

    public void initializeBills(List<Map<String, String>> medicineList) {
        // Loop through all patient records in outrecs
        for (Map.Entry<String, Map<Integer, Map<String, Object>>> patientEntry : outrecs.entrySet()) {
            String patientId = patientEntry.getKey();
            Map<Integer, Map<String, Object>> appointments = patientEntry.getValue();

            // Loop through each appointment for the patient
            for (Map.Entry<Integer, Map<String, Object>> appointmentEntry : appointments.entrySet()) {
                Integer appointmentId = appointmentEntry.getKey();
                Map<String, Object> appointmentDetails = appointmentEntry.getValue();
                double totalCost = CONSULTATION_RATE; // Start with consultation fee

                // Print consultation fee
                System.out.println("Consultation Fee for Patient " + patientId + ", Appointment ID: " + appointmentId
                        + ": $" + CONSULTATION_RATE);

                // Access prescribed medications
                List<Map<String, String>> medications = (List<Map<String, String>>) appointmentDetails.get("pmeds");
                System.out.println("Prescribed Medications for Appointment ID " + appointmentId + ":");

                // Loop through the medications and calculate costs
                for (Map<String, String> med : medications) {
                    String medName = med.get("name");
                    int quantity = Integer.parseInt(med.get("quantity"));

                    double pricePerUnit = 0.0;
                    // Find the medication's price from medicineList
                    for (Map<String, String> medicineData : medicineList) {
                        if (medicineData.get("Medicine Name").equalsIgnoreCase(medName)) {
                            pricePerUnit = Double.parseDouble(medicineData.get("Price"));
                            break;
                        }
                    }

                    double medCost = pricePerUnit * quantity;

                    // Print medication details and cost
                    System.out.println(
                            " - " + medName + " (Quantity: " + quantity + ", Unit Price: $" + pricePerUnit + ")");
                    System.out.println("   Medication Cost: $" + medCost);

                    totalCost += medCost; // Add medication cost to total
                }

                // Print total cost for the appointment
                System.out.println("Total Cost for Appointment ID " + appointmentId + ": $" + totalCost);

                // Generate a billing record for the current appointment
                Random rid = new Random();
                Map<String, Object> billingRecord = new HashMap<>();
                billingRecord.put("billingId", rid.nextInt(1000)); // Generate random billing ID
                billingRecord.put("patientId", patientId); // Store patient ID
                billingRecord.put("appointmentId", String.valueOf(appointmentId)); // Store appointment ID
                billingRecord.put("totalAmount", totalCost); // Store the total cost
                billingRecord.put("isPaid", false); // Initialize as unpaid

                // Add the billing record to the billing list
                billingList.add(billingRecord);
            }
        }
    }

    public void viewAllBills(String loggedInPatientId) {
        if (billingList.isEmpty()) {
            System.out.println("No billing records available.");
            return;
        }
    
        boolean billsFound = false;
    
        // Loop through the billing list
        for (Map<String, Object> bill : billingList) {
            // Extract details from each billing record
            int billingId = (int) bill.get("billingId");
            String appointmentId = (String) bill.get("appointmentId");
            double totalAmount = (double) bill.get("totalAmount");
            boolean isPaid = (boolean) bill.get("isPaid");
    
            // Check if this billing record belongs to the logged-in patient
            for (Map.Entry<String, Map<Integer, Map<String, Object>>> patientEntry : outrecs.entrySet()) {
                String patientId = patientEntry.getKey();
                Map<Integer, Map<String, Object>> patientAppointments = patientEntry.getValue();
    
                // Check if the current bill matches an appointment for this patient
                if (patientId.equals(loggedInPatientId)) {
                    // Loop through the patient's appointments to find a matching appointmentId
                    for (Map.Entry<Integer, Map<String, Object>> appointmentEntry : patientAppointments.entrySet()) {
                        Integer appointmentIdFromOutrecs = appointmentEntry.getKey();
                        if (appointmentIdFromOutrecs.toString().equals(appointmentId)) {
                            // This bill belongs to the logged-in patient
                            billsFound = true;
                            // Print the billing information
                            System.out.println("Billing ID: " + billingId);
                            System.out.println("Appointment ID: " + appointmentId);
                            System.out.println("Total Amount: $" + totalAmount);
                            System.out.println("Paid Status: " + (isPaid ? "Paid" : "Unpaid"));
                            System.out.println("-----");
                        }
                    }
                }
            }
        }
    
        // If no bills were found for the logged-in patient, print a message
        if (!billsFound) {
            System.out.println("No bills found for Patient ID: " + loggedInPatientId);
        }
    }

    public void makePayment(int billingId) {
        boolean billFound = false;
    
        // Loop through the billing list to find the bill with the given billingId
        for (Map<String, Object> bill : billingList) {
            // Extract billingId from the bill
            int currentBillingId = (int) bill.get("billingId");
    
            // Check if the current bill matches the provided billingId
            if (currentBillingId == billingId) {
                // Change the "isPaid" value to true
                bill.put("isPaid", true);
    
                // Print a confirmation message
                System.out.println("Payment made successfully for Billing ID: " + billingId);
                billFound = true;
                break;  // Exit the loop once the bill is updated
            }
        }
    
        // If the bill with the provided billingId was not found, print an error message
        if (!billFound) {
            System.out.println("Bill with Billing ID " + billingId + " not found.");
        }
    }
    
    

    // public void ViewBillingInfo(String patientId, int appointmentId,
    // List<Map<String, String>> medicineList) {
    // if (outrecs.containsKey(patientId) &&
    // outrecs.get(patientId).containsKey(appointmentId)) {
    // Map<String, Object> appointmentDetails =
    // outrecs.get(patientId).get(appointmentId);
    // double totalCost = CONSULTATION_RATE; // Start with consultation fee

    // System.out.println("Consultation Fee: $" + CONSULTATION_RATE);

    // List<Map<String, String>> medications = (List<Map<String, String>>)
    // appointmentDetails.get("pmeds");
    // System.out.println("Prescribed Medications:");

    // for (Map<String, String> med : medications) {
    // String medName = med.get("name");
    // int quantity = Integer.parseInt(med.get("quantity"));

    // double pricePerUnit = 0.0;
    // for (Map<String, String> medicineData : medicineList) {
    // if (medicineData.get("Medicine Name").equalsIgnoreCase(medName)) {
    // pricePerUnit = Double.parseDouble(medicineData.get("Price"));
    // break;
    // }
    // }

    // double medCost = pricePerUnit * quantity;

    // System.out.println(" - " + medName + " (Quantity: " + quantity + ", Unit
    // Price: $" + pricePerUnit + ")");
    // System.out.println(" Medication Cost: $" + medCost);

    // totalCost += medCost;
    // }

    // System.out.println("Total Cost for Appointment ID " + appointmentId + ": $" +
    // totalCost);
    // } else {
    // System.out.println("No appointment record found for Patient ID: " + patientId
    // + " and Appointment ID: " + appointmentId);
    // }
    // }

    public void makePayment(String patientId, int appointmentId, List<Map<String, String>> medicineList) {

    }

}
