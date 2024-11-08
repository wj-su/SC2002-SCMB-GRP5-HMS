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
    
                
                apptDetails.put("date", apt.getDate());
                apptDetails.put("stype", service);
    
                
                Map<String, String> prescribedMedications = new HashMap<>();
                prescribedMedications.put("name", medName);
                prescribedMedications.put("status", medStatus);
    
                apptDetails.put("pmeds", prescribedMedications);
                apptDetails.put("cnotes", consultNotes);
    
                
                outcomeRecords.computeIfAbsent(pid, k -> new HashMap<>()).put(apt.getId(), apptDetails);
    
               
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