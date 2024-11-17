package hms.patient;

import hms.doctor.AppointmentOutcomeRecord;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages patient appointments in the hospital system.
 * Provides functionalities for scheduling, rescheduling, canceling, viewing appointments, and recording outcomes.
 */
public class Appointment {
    /**
     * The ID of the appointment.
     */
    private int id;

    /**
     * The ID of the patient associated with the appointment.
     */
    private String patientId;

    /**
     * The name of the doctor for the appointment.
     */
    private String doctor;

    /**
     * The date of the appointment in DD-Mmm-YYY.
     */
    private String date;

    /**
     * The timeslot for the appointment in 24 hours e.g., 10:00.
     */
    private String timeslot;

    /**
     * The status of the appointment (e.g., "Pending", "Scheduled", "Completed").
     */
    private String status;

    /**
     * The rating of the appointment given by the patient (e.g., a value between 1 and 5).
     */
    private int rating;

    /**
     * Static list to store all appointments.
     */
    private static List<Appointment> appointments = new ArrayList<>();

    /**
     * Map to store appointment outcome records.
     * Key: Patient ID, Value: A nested map of appointment details where:
     * - Key: Appointment ID
     * - Value: A map containing the appointment details (e.g., date, service type, prescribed medications, notes).
     */
    private Map<String, Map<Integer, Map<String, Object>>> aptOutcomeRecs = new HashMap<>();

    /**
     * Default constructor.
     */
    public Appointment() {}

    /**
     * Constructor to initialize an appointment.
     *
     * @param id      The unique ID of the appointment.
     * @param pid     The ID of the patient.
     * @param doc     The name of the doctor.
     * @param date    The date of the appointment.
     * @param time    The timeslot of the appointment.
     * @param status  The status of the appointment (e.g., "Pending").
     */
    public Appointment(int id, String pid, String doc, String date, String time, String status) {
        this.id = id;
        this.patientId = pid;
        this.doctor = doc;
        this.date = date;
        this.timeslot = time;
        this.status = status; 
        this.rating = -1;
    }

    /**
     * Sets the ID of the appointment.
     *
     * @param id The unique ID to assign to the appointment.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the ID of the appointment.
     *
     * @return The unique ID of the appointment.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the patient ID associated with the appointment.
     *
     * @param pid The patient ID to assign.
     */
    public void setPatientId(String pid) {
        this.patientId = pid;
    }

    /**
     * Retrieves the patient ID associated with the appointment.
     *
     * @return The patient ID.
     */
    public String getPatientId() {
        return this.patientId;
    }

    /**
     * Sets the name of the doctor assigned to the appointment.
     *
     * @param doc The doctor's name.
     */
    public void setDoctor(String doc) {
        this.doctor = doc;
    }

    /**
     * Retrieves the name of the doctor assigned to the appointment.
     *
     * @return The doctor's name.
     */
    public String getDoctor() {
        return this.doctor;
    }

    /**
     * Sets the date of the appointment.
     *
     * @param date The date of the appointment (e.g., "YYYY-MM-DD").
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Retrieves the date of the appointment.
     *
     * @return The date of the appointment.
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Sets the timeslot for the appointment.
     *
     * @param time The timeslot for the appointment (e.g., "10:00 AM - 11:00 AM").
     */
    public void setTimeslot(String time) {
        this.timeslot = time;
    }

    /**
     * Retrieves the timeslot of the appointment.
     *
     * @return The timeslot of the appointment.
     */
    public String getTimeslot() {
        return this.timeslot;
    }

    /**
     * Sets the status of the appointment.
     *
     * @param status The status of the appointment (e.g., "Scheduled", "Completed").
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retrieves the status of the appointment.
     *
     * @return The status of the appointment.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Retrieves the list of all appointments.
     *
     * @return A list of all appointments.
     */
    public static List<Appointment> getAllAppointments() {
        return appointments;
    }

    /**
     * Sets the rating for the appointment.
     *
     * @param rating The rating for the appointment (e.g., a value between 1 and 5).
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Retrieves the rating for the appointment.
     *
     * @return The rating of the appointment.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Displays all available appointments for doctors.
     *
     * @param doctorList          A list of doctors and their details.
     * @param doctorAvailability  A map of doctor availability with dates and timeslots.
     */
    public void viewAvailableAppt(List<Map<String, String>> doctorList,
            Map<String, Map<String, List<String>>> doctorAvailability) {
        for (String doctor : doctorAvailability.keySet()) {

            String gender = "Unknown";

            for (Map<String, String> dc : doctorList) {
                if (dc.get("Name").equals(doctor)) {
                    gender = dc.get("Gender");
                    break;
                }
            }

            System.out.println("Doctor: " + doctor + " (" + gender + ")");

            double totalRating = 0;
            int ratingCount = 0;

            for (Appointment appt : Appointment.getAllAppointments()) {
                if (appt.getDoctor().equals(doctor) && appt.getRating() > -1) {
                    totalRating += appt.getRating();
                    ratingCount++;
                }
            }

            double averageRating = (ratingCount > 0) ? totalRating / ratingCount : 0;
            System.out.printf("  Average Rating: %.2f\n", averageRating);

            Map<String, List<String>> dates = doctorAvailability.get(doctor);
            List<String> sortedDates = new ArrayList<>(dates.keySet());
            Collections.sort(sortedDates);

            for (String date : sortedDates) {
                List<String> times = dates.get(date);
                String timesStr = String.join(", ", times);

                System.out.println("  Available Date: " + date);
                System.out.println("  Available Timing: " + timesStr);
            }
            System.out.println();
        }
    }

    /**
     * Displays available appointments for a specific doctor.
     * The method also calculates and displays the average rating of the doctor.
     *
     * @param doc                The name of the doctor to check availability for.
     * @param doctorList         A list of maps containing doctor details, such as their name and gender.
     * @param doctorAvailability A map of doctors' availability, where the key is the doctor's name,
     *                           and the value is a map with dates as keys and available timeslots as values.
     */
    public void viewAvailableApptByDoc(String doc, List<Map<String, String>> doctorList, Map<String, Map<String, List<String>>> doctorAvailability) {
        String normalizedDocName = doc.trim().toLowerCase();
        

        boolean doctorFound = false;
        for (String doctorName : doctorAvailability.keySet()) {
            
            if (doctorName.toLowerCase().equals(normalizedDocName)) {
                doctorFound = true;
                Map<String, List<String>> dates = doctorAvailability.get(doctorName);

                String gender = "Unknown";

                for (Map<String, String> dc : doctorList) {
                    if (dc.get("Name").trim().toLowerCase().equals(normalizedDocName)) {
                        gender = dc.get("Gender");
                        break;
                    }
                }

                System.out.println("Doctor: " + doc + " (" + gender + ")");
                double totalRating = 0;
                int ratingCount = 0;

                for (Appointment appt : Appointment.getAllAppointments()) {
                    if (appt.getDoctor().equals(doctorName) && appt.getRating() > -1) {
                        totalRating += appt.getRating();
                        ratingCount++;
                    }
                }

                double averageRating = (ratingCount > 0) ? totalRating / ratingCount : 0;
                System.out.printf("  Average Rating: %.2f\n", averageRating);

                List<String> sortedDates = new ArrayList<>(dates.keySet());
                Collections.sort(sortedDates);

                for (String date : sortedDates) {
                    List<String> times = dates.get(date);
                    String timesStr = String.join(", ", times);

                    System.out.println("  Available Date: " + date);
                    System.out.println("  Available Timing: " + timesStr);
                }
                System.out.println();
                break;
            }
        }

        if (!doctorFound) {
            System.out.println("No such doctor available!");
        }
    }

    /**
     * Schedules a new appointment and updates the doctor's availability.
     *
     * @param apt               The appointment to schedule.
     * @param doc               The name of the doctor.
     * @param date              The date of the appointment.
     * @param ts                The timeslot of the appointment.
     * @param doctorAvailability The map of doctor availability.
     */
    public void scheduleAppointment(Appointment apt, String doc, String date, String ts,
            Map<String, Map<String, List<String>>> doctorAvailability) {
        appointments.add(apt);
        doctorAvailability.get(doc).get(date).remove(ts); 

        System.out.println("Your appointment has been scheduled successfully! :D");
        System.out.println("Appointment Details for " + apt.getPatientId());
        System.out.println("Doctor's Name: " + apt.getDoctor());
        System.out.println("Appointment's Date: " + apt.getDate());
        System.out.println("Timeslot Chosen: " + apt.getTimeslot());

    }
    /**
     * Checks if an appointment exists for a specific patient based on the given appointment ID.
     * If the appointment exists, it displays the details of the appointment and, if the type is "reschedule",
     * also displays the available appointments for the same doctor.
     *
     * @param pid                The ID of the patient.
     * @param aptId              The ID of the appointment to check.
     * @param type               The type of action to perform ("reschedule" to view available appointments for rescheduling).
     * @param doctorAvailability A map of doctor availability with dates and timeslots.
     * @param doctorList         A list of maps containing doctor details, such as their name and gender.
     * @return true if the appointment exists, false otherwise.
     */

    public boolean appointmentExists(String pid, int aptId, String type,
            Map<String, Map<String, List<String>>> doctorAvailability, List<Map<String, String>> doctorList) {
        if (appointments.isEmpty()) {
            System.out.println("No appointments has been scheduled!");
        } else {
            for (Appointment appt : appointments) {

                if (appt.getPatientId().equals(pid)) {
                    if (appt.getId() == aptId) {
                        System.out.println("You currently have an appointment with " + appt.getDoctor() + " on "
                                + appt.getDate() + " at " + appt.getTimeslot());
                        if (type.toLowerCase().equals("reschedule")) {
                            System.out.println("\nThese are our currently available appointments: ");
                            viewAvailableApptByDoc(appt.getDoctor(), doctorList, doctorAvailability);
                        }
                        return true;
                    }
                }
            }
        }
        return false;

    }

    /**
     * Reschedules an existing appointment, allowing changes to doctor, date, or timeslot.
     *
     * @param pid                The ID of the patient.
     * @param aptId              The ID of the appointment to reschedule.
     * @param newDoc             The new doctor's name.
     * @param date               The new date for the appointment.
     * @param time               The new timeslot for the appointment.
     * @param dec                Decision to change the doctor ("yes" to change the doctor).
     * @param doctorAvailability The map of doctor availability.
     */
    public void rescheduleAppointment(String pid, int aptId, String newDoc, String date, String time, String dec,
            Map<String, Map<String, List<String>>> doctorAvailability) {

        if (appointments.isEmpty()) {
            System.out.println("No appointments has been scheduled!");
        } else {
            for (Appointment appt : appointments) {
                if (appt.getPatientId().equals(pid) && appt.getId() == aptId) {

                    String freeDate = appt.getDate();
                    String freeTimeSlot = appt.getTimeslot();
                    String freeDoc = appt.getDoctor();
                    System.out.println("Selected Appointment Doctor: " + freeDoc);
                    System.out.println("Selected Appointment Date: " + freeDate);
                    System.out.println("Selected Appointment Time Slot: " + freeTimeSlot);

                    if (dec.toLowerCase().equals("yes")) {
                        if (doctorAvailability.containsKey(freeDoc)) {
                            if (doctorAvailability.get(freeDoc).containsKey(freeDate)) {
                                doctorAvailability.get(freeDoc).get(freeDate).add(freeTimeSlot);
                            }
                        }

                        if (doctorAvailability.containsKey(newDoc)
                                && doctorAvailability.get(newDoc).containsKey(date)) {
                            if (doctorAvailability.get(newDoc).get(date).contains(time)) {
                                doctorAvailability.get(newDoc).get(date).remove(time);
                                appt.setDoctor(newDoc);
                                appt.setDate(date);
                                appt.setTimeslot(time);
                                appt.setStatus("Rescheduled");
                                System.out.println("You have rescheduled an appointment with " + appt.getDoctor()
                                        + " on " + appt.getDate() + " at " + appt.getTimeslot());
                            }
                        } else {
                            System.out.println("The selected new timeslot is not available!");
                        }
                    } else {
                        if (appt.getDoctor().equals(freeDoc) && appt.getDate().equals(freeDate)
                                && appt.getTimeslot().equals(freeTimeSlot)) {

                            if (doctorAvailability.containsKey(freeDoc)) {
                                if (doctorAvailability.get(freeDoc).containsKey(freeDate)) {
                                    doctorAvailability.get(freeDoc).get(freeDate).add(freeTimeSlot);
                                }
                            }

                            if (doctorAvailability.containsKey(freeDoc)
                                    && doctorAvailability.get(freeDoc).containsKey(date)) {
                                if (doctorAvailability.get(freeDoc).get(date).contains(time)) {
                                    doctorAvailability.get(freeDoc).get(date).remove(time);
                                    appt.setDate(date);
                                    appt.setTimeslot(time);
                                    appt.setStatus("Rescheduled");
                                    System.out.println("You have rescheduled an appointment with " + appt.getDoctor()
                                            + " on " + appt.getDate() + " at " + appt.getTimeslot());
                                }
                            } else {
                                System.out.println("The selected new timeslot is not available!");
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Cancels an appointment and updates the doctor's availability.
     *
     * @param pid                The ID of the patient.
     * @param aptId              The ID of the appointment to cancel.
     * @param doctorAvailability The map of doctor availability.
     */
    public void cancelAppointment(String pid, int aptId, Map<String, Map<String, List<String>>> doctorAvailability) {
        Appointment appointmentToRemove = null;

        if (appointments.isEmpty()) {
            System.out.println("No appointments has been scheduled!");
        } else {
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

    /**
     * Displays all scheduled appointments for a specific patient.
     *
     * @param pid The ID of the patient.
     */
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

    /**
     * Displays the outcome records of past appointments for a specific patient.
     *
     * @param pid The ID of the patient.
     */
    public void viewPastAppointmentOutcomeRecords(String pid) {
        this.aptOutcomeRecs = AppointmentOutcomeRecord.getAllOutcomeRecords();

        if (aptOutcomeRecs.isEmpty()) {
            System.out.println("We have 0 outcome records.");
        } else {

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

                    List<Map<String, String>> medications = (List<Map<String, String>>) details.get("pmeds");
                    System.out.println("Prescribed Medications:");
                    for (Map<String, String> med : medications) {
                        System.out.println(" - " + med.get("name") + " (" + med.get("status") + ")");
                    }

                    System.out.println("Consultation Notes: " + details.get("cnotes"));
                    System.out.println("\n-----------------------------------\n");
                }
            }
        }
    }

    /**
     * Rates a doctor after a completed appointment.
     *
     * @param patientId    The ID of the patient.
     * @param appointmentId The ID of the appointment.
     * @param rating       The rating (1-5) for the doctor.
     */
    public void rateDoctorAfterAppointment(String patientId, int appointmentId, int rating) {
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Please rate between 1 and 5.");
            return;
        }

        for (Appointment appt : appointments) {
            if (appt.getPatientId().equals(patientId) && appt.getId() == appointmentId) {
                if (appt.getStatus().equals("Completed")) {

                    appt.setRating(rating);

                    System.out.println("Thank you for your feedback!");
                    return;
                } else {
                    System.out.println("You cannot rate the doctor yet. Please complete your appointment first.");
                    return;
                }
            }
        }

        System.out.println("No appointment found for patient with the given appointment ID.");
    }

}