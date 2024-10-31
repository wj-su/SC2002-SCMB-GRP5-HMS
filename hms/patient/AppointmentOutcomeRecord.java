package patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pharmacist.Medication;

import patient.Appointment;

public class AppointmentOutcomeRecord {
    private String serviceType;
    private Map<String, String> prescribedMeds;
    private String consultNotes;

    private List<Appointment> apptRecords = new ArrayList<>();

    private Map<Integer, Map<String, Object>> outcomeRecords;

    public AppointmentOutcomeRecord() {
        Appointment appt = new Appointment();
        this.apptRecords = appt.getAppointments();
        this.outcomeRecords = new HashMap<>();

        // test case
        for (int i = 1; i <= 5; i++) { // Generating 5 fake records
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

            // Add the appointment details to the outcome records
            outcomeRecords.put(i, apptDetails);
        }
    }


    // public AppointmentOutcomeRecord(String st, List<Medication> pm, String cn) {
    //     this.serviceType = st; // consultation, x-ray, blood-test, surgery
    //     this.prescribedMeds = pm;
    //     this.consultNotes = cn;

    //     Appointment appt = new Appointment();
    //     this.apptRecords = appt.getAppointments();
    //     this.outcomeRecords = new HashMap<>();
    // }

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

    public void addOutcomeRecord(int id, String service, String medName, String medStatus, String consultNotes) {
        Map<String, Object> apptDetails = new HashMap<>();

        if (apptRecords.size() > 0) {
            for (Appointment apt : apptRecords) {
                System.out.println("Appointment Id: " + apt.getId());
                System.out.println("Appointment Doctor: " + apt.getDoctor());
                System.out.println("Appointment Date: " + apt.getDate());
                System.out.println("Appointment Time: " + apt.getTimeslot());
                System.out.println("Appointment Status: " + apt.getStatus());
                System.out.println("\n");
                if (apt.getId() == id && apt.getStatus() == "Completed") {
                    apptDetails.put("date", apt.getDate());
                    apptDetails.put("stype", service);

                    Map<String, String> prescribedMedications = new HashMap<>();
                    prescribedMedications.put("name", medName);
                    prescribedMedications.put("status", medStatus);

                    apptDetails.put("pmeds", prescribedMedications);
                    apptDetails.put("cnotes", consultNotes);

                    outcomeRecords.put(apt.getId(), apptDetails);

                    // update values for apppointment outcome record class as well
                    this.serviceType = service;
                    this.prescribedMeds = prescribedMedications;
                    this.consultNotes = consultNotes;

                }
                else if (apt.getId() != id) {
                    System.out.println((apt.getId() != id) + " and " + apt.getId() + " != " + id);

                    System.out.println("No such appointment exists under that id!");
                }

                else if (apt.getStatus() != "Completed") {
                    System.out.println("You have not completed your appointment yet!");
                }
                
            }
        }
    }

    public void getOutcomeRecords() {
        if (outcomeRecords.isEmpty()) {
            System.out.println("We have 0 outcome records under your name.");
        }
        else {
            for (Map.Entry<Integer, Map<String, Object>> entry : outcomeRecords.entrySet()) {
                Integer appointmentId = entry.getKey();
                Map<String, Object> details = entry.getValue();
    
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
