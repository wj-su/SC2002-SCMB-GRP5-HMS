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
        // Map<Integer, Map<String, Object>> patientP1001Appointments = new HashMap<>();

        // // Create appointment record with Appointment ID "343"
        // Map<String, Object> appointmentDetails = new HashMap<>();
        
        // // Adding appointment details
        // appointmentDetails.put("date", "12-Nov-2024");  // Appointment Date
        // appointmentDetails.put("stype", "Consultation");  // Service Type
        
        // // Create a list of medications
        // List<Map<String, String>> medications = new ArrayList<>();
        // Map<String, String> medication = new HashMap<>();
        // medication.put("quantity", "15");  // Quantity of the medication
        // medication.put("name", "Painkiller");  // Medication Name
        // medication.put("status", "Pending");  // Medication Status
        
        // // Add the medication to the list
        // medications.add(medication);
        
        // // Add medications to the appointment details
        // appointmentDetails.put("pmeds", medications);
        
        // // Adding consultation notes
        // appointmentDetails.put("cnotes", "IDK");

        // // Add the appointment details for Appointment ID 343 to the patient's records
        // patientP1001Appointments.put(343, appointmentDetails);

        // // Add this patient's appointments to the main records (outrecs)
        // outcomeRecords.put("P1001", patientP1001Appointments);
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

    public void addOutcomeRecord(String pid, int id, String service, String medName, String medStatus, String medQuantity,
            String consultNotes) {
        Map<String, Object> apptDetails = new HashMap<>();

        this.apptRecords = Appointment.getAllAppointments();
        boolean found = false;

        for (Appointment apt : apptRecords) {
            if (apt.getId() == id && apt.getStatus().equals("Completed")) {
                found = true;

                apptDetails.put("date", apt.getDate());
                apptDetails.put("stype", service);

                List<Map<String, String>> medications = (List<Map<String, String>>) apptDetails.getOrDefault("pmeds",
                        new ArrayList<>());

                // Add new medication
                Map<String, String> prescribedMedications = new HashMap<>();
                prescribedMedications.put("name", medName);
                prescribedMedications.put("status", medStatus);
                prescribedMedications.put("quantity", medQuantity);

                medications.add(prescribedMedications);

                // Update the "pmeds" list
                apptDetails.put("pmeds", medications);
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
            System.out.println(idExists ? "You have not completed your appointment yet!"
                    : "No such appointment exists under that id!");
        }
    }

    public void viewAllOutcomeRecords() {
        if (outcomeRecords.isEmpty()) {
            System.out.println("We have 0 outcome records.");
        } else {
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

                    List<Map<String, String>> medications = (List<Map<String, String>>) details.get("pmeds");
                    System.out.println("Prescribed Medications:");
                    for (Map<String, String> med : medications) {
                        System.out.println(" - " + med.get("name") + " (" + med.get("status") + ")");
                    }

                    System.out.println("Consultation Notes: " + details.get("cnotes"));
                    System.out.println("\n");
                }
            }
        }
    }

    // Check whether patient has past records.
    public void checkPatientRecords(String patientId) {
        if (!outcomeRecords.containsKey(patientId) || outcomeRecords.get(patientId).isEmpty()) {
            System.out.println("Patient " + patientId + " has no past appointment records.");
        } else {
            System.out.println("Patient " + patientId + " has past appointment records.");
        }
    }
}