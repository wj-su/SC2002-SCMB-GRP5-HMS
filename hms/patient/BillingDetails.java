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
        
        if (this.outrecs != null && !this.outrecs.isEmpty()) {
           
            for (Map.Entry<String, Map<Integer, Map<String, Object>>> patientEntry : this.outrecs.entrySet()) {
                String patientId = patientEntry.getKey();
                Map<Integer, Map<String, Object>> appointments = patientEntry.getValue();

                System.out.println("Patient ID: " + patientId);

                
                for (Map.Entry<Integer, Map<String, Object>> appointmentEntry : appointments.entrySet()) {
                    Integer appointmentId = appointmentEntry.getKey();
                    Map<String, Object> appointmentDetails = appointmentEntry.getValue();

                    System.out.println("  Appointment ID: " + appointmentId);

                    
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
        
        for (Map.Entry<String, Map<Integer, Map<String, Object>>> patientEntry : outrecs.entrySet()) {
            String patientId = patientEntry.getKey();
            Map<Integer, Map<String, Object>> appointments = patientEntry.getValue();

            
            for (Map.Entry<Integer, Map<String, Object>> appointmentEntry : appointments.entrySet()) {
                Integer appointmentId = appointmentEntry.getKey();
                Map<String, Object> appointmentDetails = appointmentEntry.getValue();
                double totalCost = CONSULTATION_RATE; // Start with consultation fee

                
                System.out.println("Consultation Fee for Patient " + patientId + ", Appointment ID: " + appointmentId
                        + ": $" + CONSULTATION_RATE);

                
                List<Map<String, String>> medications = (List<Map<String, String>>) appointmentDetails.get("pmeds");
                System.out.println("Prescribed Medications for Appointment ID " + appointmentId + ":");

                
                for (Map<String, String> med : medications) {
                    String medName = med.get("name");
                    int quantity = Integer.parseInt(med.get("quantity"));

                    double pricePerUnit = 0.0;
                    
                    for (Map<String, String> medicineData : medicineList) {
                        if (medicineData.get("Medicine Name").equalsIgnoreCase(medName)) {
                            pricePerUnit = Double.parseDouble(medicineData.get("Price"));
                            break;
                        }
                    }

                    double medCost = pricePerUnit * quantity;

                   
                    System.out.println(
                            " - " + medName + " (Quantity: " + quantity + ", Unit Price: $" + pricePerUnit + ")");
                    System.out.println("   Medication Cost: $" + medCost);

                    totalCost += medCost; 
                }

                
                System.out.println("Total Cost for Appointment ID " + appointmentId + ": $" + totalCost);

                
                Random rid = new Random();
                Map<String, Object> billingRecord = new HashMap<>();
                billingRecord.put("billingId", rid.nextInt(1000)); 
                billingRecord.put("patientId", patientId); 
                billingRecord.put("appointmentId", String.valueOf(appointmentId)); 
                billingRecord.put("totalAmount", totalCost); 
                billingRecord.put("isPaid", false); 

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
    
       
        for (Map<String, Object> bill : billingList) {
            
            int billingId = (int) bill.get("billingId");
            String appointmentId = (String) bill.get("appointmentId");
            double totalAmount = (double) bill.get("totalAmount");
            boolean isPaid = (boolean) bill.get("isPaid");
    
            
            for (Map.Entry<String, Map<Integer, Map<String, Object>>> patientEntry : outrecs.entrySet()) {
                String patientId = patientEntry.getKey();
                Map<Integer, Map<String, Object>> patientAppointments = patientEntry.getValue();
    
                
                if (patientId.equals(loggedInPatientId)) {
                    
                    for (Map.Entry<Integer, Map<String, Object>> appointmentEntry : patientAppointments.entrySet()) {
                        Integer appointmentIdFromOutrecs = appointmentEntry.getKey();
                        if (appointmentIdFromOutrecs.toString().equals(appointmentId)) {
                            billsFound = true;
                            
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
    
        
        if (!billsFound) {
            System.out.println("No bills found for Patient ID: " + loggedInPatientId);
        }
    }

    public void makePayment(int billingId) {
        boolean billFound = false;
    
        for (Map<String, Object> bill : billingList) {
            
            int currentBillingId = (int) bill.get("billingId");
    
            
            if (currentBillingId == billingId) {
                bill.put("isPaid", true);
    
                System.out.println("Payment made successfully for Billing ID: " + billingId);
                billFound = true;
                break;  
            }
        }
    
        
        if (!billFound) {
            System.out.println("Bill with Billing ID " + billingId + " not found.");
        }
    }

}
