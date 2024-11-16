package unit_tests;

import static org.junit.Assert.*;

import doctor.AppointmentOutcomeRecord;
import org.junit.Before;
import org.junit.Test;
import patient.Appointment;
import patient.Patient;

import java.util.*;

public class PatientActionsTest {

    private Patient patient;
    private Appointment appointmentService;
    private AppointmentOutcomeRecord outcomeRecordService;

    @Before
    public void setUp() {
        // Initialize a sample patient
        List<String> contactInfo = Arrays.asList("12345678", "patient@example.com");
        List<String> pastDiagnoses = Arrays.asList("Hypertension");
        List<String> pastTreatments = Arrays.asList("Medication A");

        patient = new Patient("P1001", "John Doe", "01-Jan-1990", contactInfo, "Male", "O+", pastDiagnoses, pastTreatments);
        appointmentService = new Appointment();
        outcomeRecordService = new AppointmentOutcomeRecord();
    }

    private Map<String, Map<String, List<String>>> parseAvailability(String availability) {
        Map<String, Map<String, List<String>>> doctorAvailability = new HashMap<>();
        Map<String, List<String>> dateMap = new HashMap<>();
        String doctorName = "Dr. Smith";
        String[] availabilityEntries = availability.split(" \\| ");
        for (String entry : availabilityEntries) {
            String[] dateAndTimes = entry.split(" ");
            if (dateAndTimes.length >= 2) {
                String date = dateAndTimes[0].trim();
                String[] times = dateAndTimes[1].split("/");

                List<String> timeList = new ArrayList<>();
                for (String time : times) {
                    timeList.add(time.trim());
                }


                dateMap.put(date, timeList);
                doctorAvailability.put(doctorName, dateMap);
            }
        }

        return doctorAvailability;
    }



    @Test
    public void testViewMedicalRecord() {
        // Test Case 1: View Medical Record
        assertNotNull("Patient ID should not be null", patient.getId());
        assertEquals("Patient ID should match", "P1001", patient.getId());
        assertEquals("Patient Name should match", "John Doe", patient.getName());
        assertEquals("Patient DOB should match", "01-Jan-1990", patient.getDOB());
        assertEquals("Patient Gender should match", "Male", patient.getGender());
        assertEquals("Contact Info should match", Arrays.asList("12345678", "patient@example.com"), patient.getContactInformation());
        assertEquals("Blood Type should match", "O+", patient.getBlood());
        assertEquals("Past Diagnoses should match", Arrays.asList("Hypertension"), patient.getPastDiagnoses());
        assertEquals("Past Treatments should match", Arrays.asList("Medication A"), patient.getPastTreatments());
    }

    @Test
    public void testUpdatePersonalInformation() {
        // Test Case 2: Update Personal Information
        String newPhoneNumber = "87654321";
        String newEmail = "new_email@example.com";
        List<Map<String, String>> patientList = new ArrayList<>();
        Map<String, String> patientData = new HashMap<>();
        patientData.put("Patient ID", patient.getId());
        patientList.add(patientData);
        patient.updatePersonalInformation(newPhoneNumber, newEmail, patientList);

        // Verify that personal information has been updated
        assertTrue("Phone number should be updated", patient.getContactInformation().contains(newPhoneNumber));
        assertTrue("Email should be updated", patient.getContactInformation().contains(newEmail));
    }

    @Test
    public void testViewAvailableAppointmentSlots() {
        // Test Case 3: View Available Appointment Slots
        String availability = "12-Nov-2024 10:00/12:00/14:00 | 13-Nov-2024 13:00/15:00 | 14-Nov-2024 16:00";
        Map<String, Map<String, List<String>>> doctorAvailability = parseAvailability(availability);

        List<Map<String, String>> doctorList = new ArrayList<>();
        Map<String, String> doctorData = new HashMap<>();
        doctorData.put("Name", "Dr. Smith");
        doctorData.put("Gender", "Male");
        doctorList.add(doctorData);

        appointmentService.viewAvailableAppt(doctorList, doctorAvailability);
        // Assert values to verify correct output
        assertEquals("Doctor availability should contain Dr. Smith", 1, doctorAvailability.size());
        assertEquals("Gender should be Male", "Male", doctorList.get(0).get("Gender"));
        assertTrue("Doctor availability should contain available timeslots for Dr. Smith", doctorAvailability.get("Dr. Smith").containsKey("12-Nov-2024"));
    }


    @Test
    public void testScheduleAppointment() {
        // Test Case 4: Schedule an Appointment
        Appointment appointment = new Appointment(101, patient.getId(), "Dr. Smith", "12-Nov-2024", "10:00", "Confirmed");
        String availability = "12-Nov-2024 10:00/12:00/14:00 | 13-Nov-2024 13:00/15:00 | 14-Nov-2024 16:00";
        Map<String, Map<String, List<String>>> doctorAvailability = parseAvailability(availability);

        appointmentService.scheduleAppointment(appointment, "Dr. Smith", "12-Nov-2024", "10:00", doctorAvailability);
        assertTrue("Appointment should be scheduled successfully", Appointment.getAllAppointments().contains(appointment));
    }

    @Test
    public void testRescheduleAppointment() {
        // Test Case 5: Reschedule an Appointment
        int appointmentId = 102;
        Appointment appointment = new Appointment(appointmentId, patient.getId(), "Dr. Smith", "12-Nov-2024", "10:00", "Confirmed");
        Appointment.getAllAppointments().add(appointment);
        String availability = "12-Nov-2024 10:00/12:00/14:00 | 13-Nov-2024 13:00/15:00 | 15-Nov-2024 11:00";
        Map<String, Map<String, List<String>>> doctorAvailability = parseAvailability(availability);

        appointmentService.rescheduleAppointment(patient.getId(), appointmentId, "Dr. Smith", "15-Nov-2024", "11:00", "no", doctorAvailability);
        assertEquals("Appointment date should be updated", "15-Nov-2024", appointment.getDate());
        assertEquals("Appointment timeslot should be updated", "11:00", appointment.getTimeslot());
    }

    @Test
    public void testCancelAppointment() {
        // Test Case 6: Cancel an Appointment
        int appointmentId = 103;
        Appointment appointment = new Appointment(appointmentId, patient.getId(), "Dr. Smith", "2024-12-12", "10:00", "Confirmed");
        Appointment.getAllAppointments().add(appointment);
        String availability = "12-Nov-2024 10:00/12:00/14:00 | 13-Nov-2024 13:00/15:00 | 14-Nov-2024 16:00";
        Map<String, Map<String, List<String>>> doctorAvailability = parseAvailability(availability);

        appointmentService.cancelAppointment(patient.getId(), appointmentId, doctorAvailability);
        assertEquals("Appointment status should be updated to canceled", "Cancelled", appointment.getStatus());
    }

    @Test
    public void testViewScheduledAppointments() {
        // Test Case 7: View Scheduled Appointments
        Appointment appointment = new Appointment(104, patient.getId(), "Dr. Smith", "13-Nov-2024", "10:00", "Confirmed");
        Appointment.getAllAppointments().add(appointment);

        appointmentService.viewScheduledAppointments(patient.getId());
    }

    @Test
    public void testViewPastAppointmentOutcomeRecords() {
        // Test Case 8: View Past Appointment Outcome Records
        // Adding an outcome record
        Appointment appointment = new Appointment(105, patient.getId(), "Dr. Smith", "13-Nov-2024", "13:00", "Completed");
        Appointment.getAllAppointments().add(appointment);

        String availability = "12-Nov-2024 10:00/12:00/14:00 | 13-Nov-2024 13:00/15:00 | 15-Nov-2024 11:00";
        Map<String, Map<String, List<String>>> doctorAvailability = parseAvailability(availability);
        appointmentService.scheduleAppointment(appointment, "Dr. Smith", "13-Nov-2024", "13:00", doctorAvailability);

        outcomeRecordService.addOutcomeRecord(patient.getId(), 105, "Consultation", "Medication A", "Pending", "15", "Consultation notes");

        // Verify the outcome record exists
        Map<String, Map<Integer, Map<String, Object>>> outcomeRecords = AppointmentOutcomeRecord.getAllOutcomeRecords();
        assertTrue("Outcome records should contain patient ID", outcomeRecords.containsKey(patient.getId()));
        assertTrue("Patient records should contain appointment ID", outcomeRecords.get(patient.getId()).containsKey(105));

        // Verify the details of the outcome record
        Map<String, Object> outcomeDetails = outcomeRecords.get(patient.getId()).get(105);
        assertEquals("Appointment date should match", "13-Nov-2024", outcomeDetails.get("date"));
        assertEquals("Service type should match", "Consultation", outcomeDetails.get("stype"));
        assertEquals("Consultation notes should match", "Consultation notes", outcomeDetails.get("cnotes"));

        List<Map<String, String>> medications = (List<Map<String, String>>) outcomeDetails.get("pmeds");
        assertNotNull("Medications should not be null", medications);
        assertEquals("Medication name should match", "Medication A", medications.get(0).get("name"));
        assertEquals("Medication status should match", "Pending", medications.get(0).get("status"));
        assertEquals("Medication quantity should match", "15", medications.get(0).get("quantity"));
    }
}
