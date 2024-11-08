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
    private String date;
    private String timeslot;
    private String status;

    private static List<Appointment> appointments = new ArrayList<>();

    private Map<String, Map<Integer, Map<String, Object>>> aptOutcomeRecs = new HashMap<>();


    public Appointment() {
        
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


    public static List<Appointment> getAllAppointments() {
        return appointments;
    }

    public void viewAvailableAppt(Map<String, Map<String, List<String>>> doctorAvailability) {
        for (String doctor : doctorAvailability.keySet()) {
            System.out.println("Doctor: " + doctor);

            Map<String, List<String>> dates = doctorAvailability.get(doctor);
            for (String date : dates.keySet()) {
                List<String> times = dates.get(date);
                String timesStr = String.join(", ", times); // Join times into a single string for display

                System.out.println("  Available Date: " + date);
                System.out.println("  Available Timing: " + timesStr);
            }
            System.out.println(); 
        }
    }
    

    public void viewAvailableApptByDoc(String doc, Map<String, Map<String, List<String>>> doctorAvailability) {
        String normalizedDocName = doc.trim().toLowerCase();
    
        boolean doctorFound = false;
        for (String doctorName : doctorAvailability.keySet()) {
            if (doctorName.toLowerCase().equals(normalizedDocName)) {
                doctorFound = true;
                Map<String, List<String>> dateSlots = doctorAvailability.get(doctorName);
    
                System.out.println("Doctor: " + doctorName);
    
                // Iterate over each date and its timeslots
                for (String date : dateSlots.keySet()) {
                    List<String> timeslots = dateSlots.get(date);
                    String timesStr = String.join(", ", timeslots); 
    
                    System.out.println("  Available Date: " + date);
                    System.out.println("  Available Timing: " + timesStr);
                }
                System.out.println(); 
                break;
            }
        }
    
        // If no matching doctor is found
        if (!doctorFound) {
            System.out.println("No such doctor available!");
        }
    }
    

    public void scheduleAppointment(Appointment apt, String doc, String date, String ts, Map<String, Map<String, List<String>>> doctorAvailability) {
        appointments.add(apt);
        doctorAvailability.get(doc).get(date).remove(ts); // remove selected time

        System.out.println("Your appointment has been scheduled successfully! :D");
        System.out.println("Appointment Details for " + apt.getPatientId());
        System.out.println("Doctor's Name: " + apt.getDoctor());
        System.out.println("Appointment's Date: " + apt.getDate());
        System.out.println("Timeslot Chosen: " + apt.getTimeslot());


    }


    public boolean appointmentExists(String pid, int aptId, String type, Map<String, Map<String, List<String>>> doctorAvailability) {
        if (appointments.isEmpty()) {
            System.out.println("No appointments has been scheduled!");
        }
        else {
            for (Appointment appt : appointments) {

                if (appt.getPatientId().equals(pid)) {
                    if (appt.getId() == aptId) {
                        System.out.println("You currently have an appointment with " + appt.getDoctor() + " on " + appt.getDate() + " at " + appt.getTimeslot());
                        if (type.toLowerCase().equals("reschedule")) {
                            System.out.println("\nThese are our currently available appointments: ");
                            viewAvailableApptByDoc(appt.getDoctor(), doctorAvailability);
                        }
                        return true;
                    }
                }
            }
        }
        return false;


    }

    public void rescheduleAppointment(String pid, int aptId, String newDoc, String date, String time, String dec, Map<String, Map<String, List<String>>> doctorAvailability) {

        if (appointments.isEmpty()) {
            System.out.println("No appointments has been scheduled!");
        }
        else {
            for (Appointment appt : appointments) {
                if (appt.getPatientId().equals(pid) && appt.getId() == aptId) {
                    
                    String freeDate = appt.getDate();
                    String freeTimeSlot = appt.getTimeslot();
                    String freeDoc = appt.getDoctor();
                    System.out.println("Selected Appointment Doctor: " + freeDoc);
                    System.out.println("Selected Appointment Date: " + freeDate);
                    System.out.println("Selected Appointment Time Slot: " + freeTimeSlot);

                    if (dec.toLowerCase().equals("yes")) {
                        // changing doctor
                        if (doctorAvailability.containsKey(freeDoc)) {
                            if (doctorAvailability.get(freeDoc).containsKey(freeDate)) {
                                doctorAvailability.get(freeDoc).get(freeDate).add(freeTimeSlot);
                            }
                        }
                        
                        if (doctorAvailability.containsKey(newDoc) && doctorAvailability.get(newDoc).containsKey(date)) {
                            if (doctorAvailability.get(newDoc).get(date).contains(time)) {
                                doctorAvailability.get(newDoc).get(date).remove(time);
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
                        if (appt.getDoctor().equals(freeDoc) && appt.getDate().equals(freeDate) && appt.getTimeslot().equals(freeTimeSlot)) {                    

                            if (doctorAvailability.containsKey(freeDoc)) {
                                if (doctorAvailability.get(freeDoc).containsKey(freeDate)) {
                                    doctorAvailability.get(freeDoc).get(freeDate).add(freeTimeSlot);
                                }
                            }
        
                            if (doctorAvailability.containsKey(freeDoc) && doctorAvailability.get(freeDoc).containsKey(date)) {
                                if (doctorAvailability.get(freeDoc).get(date).contains(time)) {
                                    doctorAvailability.get(freeDoc).get(date).remove(time);
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

    
    public void cancelAppointment(String pid, int aptId, Map<String, Map<String, List<String>>> doctorAvailability) {
        Appointment appointmentToRemove = null;

        if (appointments.isEmpty()) {
            System.out.println("No appointments has been scheduled!");
        }
        else {
            for (Appointment appt : appointments) {

                if (appt.getPatientId().equals(pid) && appt.getId() == aptId) {
                    String freeDate = appt.getDate();
                    String freeTimeSlot = appt.getTimeslot();
                    String freeDoc = appt.getDoctor();
                    System.out.println("Selected Appointment Doctor: " + freeDoc);
                    System.out.println("Selected Appointment Date: " + freeDate);
                    System.out.println("Selected Appointment Time Slot: " + freeTimeSlot);

                    if (doctorAvailability.containsKey(freeDoc)) {
                        if (doctorAvailability.get(freeDoc).containsKey(freeDate)) {
                            if (!doctorAvailability.get(freeDoc).get(freeDate).contains(freeTimeSlot)) {
                                doctorAvailability.get(freeDoc).get(freeDate).add(freeTimeSlot);
                            }
                        }
                    }

                    appt.setStatus("Cancelled");
                    appointmentToRemove = appt;
                    break;
                }
            }
        }

        if (appointmentToRemove != null) {
            appointments.remove(appointmentToRemove);

            System.out.println("You have canceled your appointment!");
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