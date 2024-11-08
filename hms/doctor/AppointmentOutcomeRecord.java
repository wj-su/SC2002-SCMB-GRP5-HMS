package doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import patient.Appointment;

public class AppointmentOutcomeRecord {
    private String serviceType;
    private Map<String, String> prescribedMeds;
    private String consultNotes;

    private List<Appointment> apptRecords = new ArrayList<>();

    private static Map<String, Map<Integer, Map<String, Object>>> outcomeRecords = new HashMap<>();

    public AppointmentOutcomeRecord() {
 
        // test case
        for (int i = 1; i <= 5; i++) {
            // Create an appointment details map
            Map<String, Object> apptDetails = new HashMap<>();
            apptDetails.put("date", "31/10/2024");
            apptDetails.put("stype", (i % 2 == 0) ? "Consultation" : "X-Ray");

            // Create prescribed medications map
            Map<String, String> prescribedMedications = new HashMap<>();
            prescribedMedications.put("name", "Medication " + i);
            prescribedMedications.put("status", (i % 2 == 0) ? "Dispensed" : "Pending");

            // Add prescribed medications and consultation notes
            apptDetails.put("pmeds", prescribedMedications);
            apptDetails.put("cnotes", "Consultation notes for appointment " + i);

            // Retrieve or create the map for the patient
            String patientId = "P1001";
            Map<Integer, Map<String, Object>> patientRecords = outcomeRecords.get(patientId);
            
            if (patientRecords == null) {
                patientRecords = new HashMap<>();
                outcomeRecords.put(patientId, patientRecords);
            }

            // Add the appointment details to the patient's records
            patientRecords.put(i, apptDetails);
        }
    }


    public void setServiceType(String st) {
        this.serviceType = st;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public void setPrescribedMeds(Map<String, String> pm) {
        this.prescribedMeds = pm;
    }

    public Map<String, String> getPrescribedMeds() {
        return this.prescribedMeds;
    }

    public void setConsultNotes(String cn) {
        this.consultNotes = cn;
    }

    public String getConsultNotes() {
        return this.consultNotes;
    }

    public static Map<String, Map<Integer, Map<String, Object>>> getAllOutcomeRecords() {
        return outcomeRecords;
    }

    public void addOutcomeRecord(String pid, int id, String service, String medName, String medStatus, String consultNotes) {
        Map<String, Object> apptDetails = new HashMap<>();
    
        this.apptRecords = Appointment.getAllAppointments();
        boolean found = false;
    
        for (Appointment apt : apptRecords) {
            if (apt.getId() == id && apt.getStatus().equals("Completed")) {
                found = true;
    
                // Populate appointment details
                apptDetails.put("date", apt.getDate());
                apptDetails.put("stype", service);
    
                // Set prescribed medications and consultation notes
                Map<String, String> prescribedMedications = new HashMap<>();
                prescribedMedications.put("name", medName);
                prescribedMedications.put("status", medStatus);
    
                apptDetails.put("pmeds", prescribedMedications);
                apptDetails.put("cnotes", consultNotes);
    
                // Update outcome records map
                outcomeRecords.computeIfAbsent(pid, k -> new HashMap<>()).put(apt.getId(), apptDetails);
    
                // Update instance fields
                this.serviceType = service;
                this.prescribedMeds = prescribedMedications;
                this.consultNotes = consultNotes;
    
                break;
            }
        }
    
        if (!found) {
            boolean idExists = apptRecords.stream().anyMatch(apt -> apt.getId() == id);
            System.out.println(idExists ? "You have not completed your appointment yet!" : "No such appointment exists under that id!");
        }
    }

    public void viewAllOutcomeRecords() {
        if (outcomeRecords.isEmpty()) {
            System.out.println("We have 0 outcome records.");
        }
        else {
            for (Map.Entry<String, Map<Integer, Map<String, Object>>> patientEntry : outcomeRecords.entrySet()) {
                String patientId = patientEntry.getKey();
                Map<Integer, Map<String, Object>> appointments = patientEntry.getValue();
    
                System.out.println("Patient Id: " + patientId);
    
                for (Map.Entry<Integer, Map<String, Object>> appointmentEntry : appointments.entrySet()) {
                    Integer appointmentId = appointmentEntry.getKey();
                    Map<String, Object> details = appointmentEntry.getValue();
    
                    System.out.println("Appointment Id: " + appointmentId);
                    System.out.println("Appointment Date: " + details.get("date"));
                    System.out.println("Appointment Service: " + details.get("stype"));
    
                    Map<String, String> prescribedMed = (Map<String, String>) details.get("pmeds");
                    System.out.println("Prescribed Medication: " + prescribedMed.get("name") + " (" + prescribedMed.get("status") + ")");
                    
                    System.out.println("Consultation Notes: " + details.get("cnotes"));
                    System.out.println("\n"); 
                }
            }
        }
    }

    //Check whether patient has past records.
    public void checkPatientRecords(String patientId) {
        if (!outcomeRecords.containsKey(patientId) || outcomeRecords.get(patientId).isEmpty()) {
            System.out.println("Patient " + patientId + " has no past appointment records.");
        } else {
            System.out.println("Patient " + patientId + " has past appointment records.");
        }
    }
}