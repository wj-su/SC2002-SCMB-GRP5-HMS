package hms.doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hms.patient.Appointment;

/**
 * Manages appointment outcomes, including recording and viewing details
 * like service types, prescribed medications, and consultation notes
 */

public class AppointmentOutcomeRecord {
    private String serviceType;
    private Map<String, String> prescribedMeds;
    private String consultNotes;
    private List<Appointment> apptRecords = new ArrayList<>();
    private static Map<String, Map<Integer, Map<String, Object>>> outcomeRecords = new HashMap<>();

    /**
     * Default constructor
     */
    public AppointmentOutcomeRecord() {}

    /**
     * Sets the type of service provided during the appointment
     * 
     * @param st The service type
     */
    public void setServiceType(String st) {
        this.serviceType = st;
    }

    /**
     * Retrieves the type of service provided during the appointment
     * 
     * @return The service type
     */
    public String getServiceType() {
        return this.serviceType;
    }

    /**
     * Sets the prescribed medications for the appointment
     * 
     * @param pm A map of medication details (name, status, quantity).
     */
    public void setPrescribedMeds(Map<String, String> pm) {
        this.prescribedMeds = pm;
    }

    /**
     * Retrieves the prescribed medications for the appointment.
     * 
     * @return A map of medication details (name, status, quantity).
     */
    public Map<String, String> getPrescribedMeds() {
        return this.prescribedMeds;
    }

    /**
     * Sets the consultation notes for the appointment.
     * 
     * @param cn The consultation notes
     */
    public void setConsultNotes(String cn) {
        this.consultNotes = cn;
    }

    /**
     * Retrieves the consultation notes for the appointment.
     * 
     * @return The consultation notes
     */
    public String getConsultNotes() {
        return this.consultNotes;
    }

    /**
     * Retrieves all recorded appointment outcomes
     * 
     * @return A map containing all outcome records
     */

    public static Map<String, Map<Integer, Map<String, Object>>> getAllOutcomeRecords() {
        return outcomeRecords;
    }

    /**
     * Adds an outcome record for a specific appointment
     * 
     * @param pid           The patient ID
     * @param id            The appointment ID
     * @param service       The type of service provided
     * @param medName       The name of the prescribed medication
     * @param medStatus     The status of the prescribed medication
     * @param medQuantity   The quantity of the prescribed medication
     * @param consultNotes  The consultation notes 
     */
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

                
                Map<String, String> prescribedMedications = new HashMap<>();
                prescribedMedications.put("name", medName);
                prescribedMedications.put("status", medStatus);
                prescribedMedications.put("quantity", medQuantity);

                medications.add(prescribedMedications);

                
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

    /**
     * Views all recorded appointment outcomes for all patients
     * Display details such as service type, medications, and consultation notes
     */
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
}