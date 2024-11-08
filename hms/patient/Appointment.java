package patient;

import doctor.AppointmentOutcomeRecord;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Appointment {
    private int id;
    private String patientId;
    private String doctor;
    // private Doctor doctor;
    private String date;
    private String timeslot;
    private String status;

    private Map<String, Map<String, List<String>>> apptSlots;

    // private AppointmentOutcomeRecord apptRecords;

    private static List<Appointment> appointments = new ArrayList<>();

    private Map<String, Map<Integer, Map<String, Object>>> aptOutcomeRecs = new HashMap<>();


    public Appointment() {
        apptSlots = new HashMap<>();
        apptSlots.put("Shaqilah", new HashMap<>() {{
            put("21/10/2024", new ArrayList<>(List.of("10:00", "14:00")));
            put("22/10/2024", new ArrayList<>(List.of("17:00", "20:00")));
        }});

        apptSlots.put("Dayana", new HashMap<>() {{
            put("21/10/2024", new ArrayList<>(List.of("08:00", "09:00")));
            put("22/10/2024", new ArrayList<>(List.of("12:00")));
        }});

        // default completed appointment (just to check if code works)
        Appointment defaultApt = new Appointment(1, "P1001",  "Shaqilah", "31/10/2024", "20:00", "Pending");
        appointments.add(defaultApt);

        Appointment defaultApt2 = new Appointment(2, "P1002", "Dayana", "31/10/2024", "15:00", "Completed");
        appointments.add(defaultApt2);
        
    }

    public Appointment(int id, String pid, String doc, String date, String time, String status) {
        this.id = id;
        this.patientId = pid;
        this.doctor = doc;
        this.date = date;
        this.timeslot = time;
        this.status = status; // Pending -> Confirmed/Accepted -> Declined/Canceled -> Completed
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setPatientId(String pid) {
        this.patientId = pid;
    }

    public String getPatientId() {
        return this.patientId;
    }

    public void setDoctor(String doc) {
        this.doctor = doc;
    }

    public String getDoctor() {
        return this.doctor;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public void setTimeslot(String time) {
        this.timeslot = time;
    }

    public String getTimeslot() {
        return this.timeslot;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public Map<String, Map<String, List<String>>> getApptSlots() {
        return this.apptSlots;
    }

    public static List<Appointment> getAllAppointments() {
        // System.out.println("Getting appointments from Appointment instance: ");
        // for (Appointment appt : appointments) {
        //     System.out.println("Patient Id: " + appt.getPatientId());
        //     System.out.println("Appointment Id: " + appt.getId());
        //     System.out.println("Appointment Doctor: " + appt.getDoctor());
        //     System.out.println("Appointment Date: " + appt.getDate());
        //     System.out.println("Appointment Time: " + appt.getTimeslot());
        //     System.out.println("Appointment Status: " + appt.getStatus());
        //     System.out.println("\n");
        // }
        return appointments;
    }

    public void viewAvailableAppt() {
        List<String> doctors = new ArrayList<>(apptSlots.keySet());

        for (int i = 0; i < doctors.size(); i++) {
            String doc = doctors.get(i);
            Map<String, List<String>> dateSlots = apptSlots.get(doc);

            System.out.println("Doctor: " + doc);

            List<String> dates = new ArrayList<>(dateSlots.keySet());
            for (int j = 0; j < dates.size(); j++) {
                String date = dates.get(j);
                List<String> timeslots = dateSlots.get(date); 

                System.out.println("Date: " + date);
                System.out.println("Available Timeslots: " + timeslots);
            }
            System.out.println("");
        }
    }

    public void viewAvailableApptByDoc(String doc) {
        if (!apptSlots.containsKey(doc)) {
            System.out.println("No such doctor available!!");
        }
        else {

            Map<String, List<String>> dateSlots = apptSlots.get(doc);

            System.out.println("Doctor: " + doc);

            List<String> dates = new ArrayList<>(dateSlots.keySet());
            for (int j = 0; j < dates.size(); j++) {
                String date = dates.get(j);
                List<String> timeslots = dateSlots.get(date); 

                System.out.println("Date: " + date);
                System.out.println("Available Timeslots: " + timeslots);
            }
            System.out.println("");
            
        }
    }


    public void scheduleAppointment(Appointment apt, String doc, String date, String ts) {
        appointments.add(apt);
        apptSlots.get(doc).get(date).remove(ts); // remove selected time

        System.out.println("Your appointment has been scheduled successfully! :D");
        System.out.println("Appointment Details for " + apt.getPatientId());
        System.out.println("Doctor's Name: " + apt.getDoctor());
        System.out.println("Appointment's Date: " + apt.getDate());
        System.out.println("Timeslot Chosen: " + apt.getTimeslot());


    }


    public boolean appointmentExists(String pid, String doc, String date, String time, String type) {
        if (appointments.isEmpty()) {
            System.out.println("No appointments has been scheduled!");
        }
        else {
            for (Appointment appt : appointments) {

                if (appt.getPatientId().equals(pid)) {
                    if (appt.getDoctor().equals(doc) && appt.getDate().equals(date) && appt.getTimeslot().equals(time)) {
                        System.out.println("You currently have an appointment with " + appt.getDoctor() + " on " + appt.getDate() + " at " + appt.getTimeslot());
                        if (type.toLowerCase().equals("reschedule")) {
                            System.out.println("\nThese are our currently available appointments: ");
                            viewAvailableApptByDoc(doc);
                        }
                        return true;
                    }
                }
            }
        }
        return false;


    }


    public void rescheduleAppointment(String pid, String doc, String oldDate, String oldTime, String newDoc, String date, String time, String dec) {

        if (appointments.isEmpty()) {
            System.out.println("No appointments has been scheduled!");
        }
        else {
            for (Appointment appt : appointments) {
                if (appt.getPatientId().equals(pid) && appt.getDoctor().equals(doc) && appt.getDate().equals(oldDate) && appt.getTimeslot().equals(oldTime)) {
                    String freeDate = appt.getDate();
                    String freeTimeSlot = appt.getTimeslot();
                    if (dec.toLowerCase().equals("yes")) {
                        // changing doctor
                        if (apptSlots.containsKey(doc)) {
                            if (apptSlots.get(doc).containsKey(freeDate)) {
                                apptSlots.get(doc).get(freeDate).add(freeTimeSlot);
                            }
                        }
                        
                        if (apptSlots.containsKey(newDoc) && apptSlots.get(newDoc).containsKey(date)) {
                            if (apptSlots.get(newDoc).get(date).contains(time)) {
                                apptSlots.get(newDoc).get(date).remove(time);
                                appt.setDoctor(newDoc);
                                appt.setDate(date);
                                appt.setTimeslot(time);
                                appt.setStatus("Rescheduled");
                                System.out.println("You have rescheduled an appointment with " + appt.getDoctor() + " on " + appt.getDate() + " at " + appt.getTimeslot());
                            }
                        }     
                        else {
                            System.out.println("The selected new timeslot is not available!");
                        }    
                    }
                    else {
                        // just changing date and time slot for same doctor
                        if (appt.getDoctor().equals(doc) && appt.getDate().equals(freeDate) && appt.getTimeslot().equals(freeTimeSlot)) {                    

                            if (apptSlots.containsKey(doc)) {
                                if (apptSlots.get(doc).containsKey(freeDate)) {
                                    apptSlots.get(doc).get(freeDate).add(freeTimeSlot);
                                }
                            }
        
                            if (apptSlots.containsKey(doc) && apptSlots.get(doc).containsKey(date)) {
                                if (apptSlots.get(doc).get(date).contains(time)) {
                                    apptSlots.get(doc).get(date).remove(time);
                                    appt.setDate(date);
                                    appt.setTimeslot(time);
                                    appt.setStatus("Rescheduled");
                                    System.out.println("You have rescheduled an appointment with " + appt.getDoctor() + " on " + appt.getDate() + " at " + appt.getTimeslot());
                                }
                            }     
                            else {
                                System.out.println("The selected new timeslot is not available!");
                            }               
                        }
                    }
                }    
            }
        }
    }
    
    public void cancelAppointment(String pid, String doc, String date, String time) {
        if (appointments.isEmpty()) {
            System.out.println("No appointments has been scheduled!");
        }
        else {
            for (Appointment appt : appointments) {
                if (appt.getPatientId().equals(pid) && appt.getDoctor().equals(doc) && appt.getDate().equals(date) && appt.getTimeslot().equals(time)) {
                    
                    if (apptSlots.containsKey(doc)) {
                        if (apptSlots.get(doc).containsKey(date)) {
                            if (!apptSlots.get(doc).get(date).contains(time)) {
                                apptSlots.get(doc).get(date).add(time);
                            }
                        }
                    }

                    appt.setStatus("Canceled");
                    System.out.println("You have canceled an appointment your appointment");
                }
            }
        }
    }

    public void viewScheduledAppointments(String pid) {

        boolean hasAppointments = false; 

        for (Appointment appt : appointments) {
            if (appt.getPatientId().equals(pid)) {
                hasAppointments = true;
                System.out.println("Patient Id: " + appt.getPatientId());
                System.out.println("Appointment Id: " + appt.getId());
                System.out.println("Appointment Doctor: " + appt.getDoctor());
                System.out.println("Appointment Date: " + appt.getDate());
                System.out.println("Appointment Time: " + appt.getTimeslot());
                System.out.println("Appointment Status: " + appt.getStatus());
                System.out.println("\n");
            }
        }

        if (!hasAppointments) {
            System.out.println("No appointments have been scheduled under you!");
        }
        
    }

    public void viewPastAppointmentOutcomeRecords(String pid) {
        this.aptOutcomeRecs = AppointmentOutcomeRecord.getAllOutcomeRecords();

        if (aptOutcomeRecs.isEmpty()) {
            System.out.println("We have 0 outcome records.");
        }
        else {
            // Check if records exist for the specified patient ID
            Map<Integer, Map<String, Object>> selectedPatientOutcomeRecords = aptOutcomeRecs.get(pid);
            
            if (selectedPatientOutcomeRecords == null || selectedPatientOutcomeRecords.isEmpty()) {
                System.out.println("No outcome records found for Patient ID: " + pid);
            } else {
                for (Map.Entry<Integer, Map<String, Object>> entry : selectedPatientOutcomeRecords.entrySet()) {
                    Integer appointmentId = entry.getKey();
                    Map<String, Object> details = entry.getValue();
                    
                    System.out.println("Patient Id: " + pid);
                    System.out.println("Appointment Id: " + appointmentId);
                    System.out.println("Appointment Date: " + details.get("date"));
                    System.out.println("Appointment Service: " + details.get("stype"));
                    
                    // Retrieve and print prescribed medications if they exist
                    Map<String, String> prescribedMed = (Map<String, String>) details.get("pmeds");
                    if (prescribedMed != null) {
                        System.out.println("Prescribed Medication: " + prescribedMed.get("name") + " (" + prescribedMed.get("status") + ")");
                    }
                    
                    System.out.println("Consultation Notes: " + details.get("cnotes"));
                    System.out.println("\n-----------------------------------\n");
                }
            }
        }
    }
}