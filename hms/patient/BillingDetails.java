package patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import doctor.AppointmentOutcomeRecord;

public class BillingDetails {
    private Map<String, Map<Integer, Map<String, Object>>> outrecs = new HashMap<>();
    private static final double CONSULTATION_RATE = 30.0;


    public BillingDetails(){
        this.outrecs = AppointmentOutcomeRecord.getAllOutcomeRecords();
    }    

    public void ViewBillingInfo(String patientId, int appointmentId, List<Map<String, String>> medicineList) {
        if (outrecs.containsKey(patientId) && outrecs.get(patientId).containsKey(appointmentId)) {
            Map<String, Object> appointmentDetails = outrecs.get(patientId).get(appointmentId);
            double totalCost = CONSULTATION_RATE; // Start with consultation fee
    
            System.out.println("Consultation Fee: $" + CONSULTATION_RATE);
    
            List<Map<String, String>> medications = (List<Map<String, String>>) appointmentDetails.get("pmeds");
            System.out.println("Prescribed Medications:");
    
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
    
                System.out.println(" - " + medName + " (Quantity: " + quantity + ", Unit Price: $" + pricePerUnit + ")");
                System.out.println("   Medication Cost: $" + medCost);
    
                
                totalCost += medCost;
            }
    
            System.out.println("Total Cost for Appointment ID " + appointmentId + ": $" + totalCost);
        } else {
            System.out.println("No appointment record found for Patient ID: " + patientId + " and Appointment ID: " + appointmentId);
        }
    }


}

