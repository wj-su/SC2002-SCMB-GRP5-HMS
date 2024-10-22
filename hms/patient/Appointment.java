package patient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Appointment {
    private int id;
    private String doctor;
    // private Doctor doctor;
    private String date;
    private String timeslot;
    private String status;

    private Map<String, Map<String, List<String>>> apptSlots;

    private List<Appointment> appointments = new ArrayList<>();

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
    }

    public Appointment(int id, String doc, String date, String time, String status) {
        this.id = id;
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
        System.out.println("Appointment Details for " + apt);
        System.out.println("Doctor's Name: " + apt.getDoctor());
        System.out.println("Appointment's Date: " + apt.getDate());
        System.out.println("Timeslot Chosen: " + apt.getTimeslot());


    }


    public boolean appointmentExists(String doc, String date, String time, String type) {
        if (appointments.isEmpty()) {
            System.out.println("No appointments has been scheduled!");
        }
        else {
            for (Appointment appt : appointments) {
                if (appt.getDoctor().equals(doc) && appt.getDate().equals(date) && appt.getTimeslot().equals(time)) {
                    System.out.println("You currently have an appointment with " + appt.getDoctor() + " on " + appt.getDate() + " at " + appt.getTimeslot());
                    if (type.toLowerCase().equals("reschedule")) {
                        System.out.println("\nThese are our currently available appointments: ");
                        viewAvailableApptByDoc(doc);
                    }
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        return false;


    }


    public void rescheduleAppointment(String doc, String newDoc, String date, String time, String dec) {

        if (appointments.isEmpty()) {
            System.out.println("No appointments has been scheduled!");
        }
        else {
            for (Appointment appt : appointments) {
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
    
    public void cancelAppointment(String doc, String date, String time) {
        if (appointments.isEmpty()) {
            System.out.println("No appointments has been scheduled!");
        }
        else {
            for (Appointment appt : appointments) {
                if (appt.getDoctor().equals(doc) && appt.getDate().equals(date) && appt.getTimeslot().equals(time)) {
                    
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

    public void viewScheduledAppointments() {
        if (appointments.isEmpty()) {
            System.out.println("No appointments has been scheduled!");
        }
        else {
            for (Appointment appt : appointments) {
                System.out.println("Appointment Id: " + appt.getId());
                System.out.println("Appointment Doctor: " + appt.getDoctor());
                System.out.println("Appointment Date: " + appt.getDate());
                System.out.println("Appointment Time: " + appt.getTimeslot());
                System.out.println("Appointment Status: " + appt.getStatus());
                System.out.println("\n");
            }
        }
    }

}
