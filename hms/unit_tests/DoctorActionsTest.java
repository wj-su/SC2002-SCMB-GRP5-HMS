package unit_tests;

import static org.junit.Assert.*;

import doctor.AppointmentManagement;
import doctor.AppointmentOutcomeRecord;
import doctor.Doctor;
import doctor.MedicalRecordManagement;
import org.junit.Before;
import org.junit.Test;
import patient.Appointment;
import patient.Patient;

import java.util.*;

public class DoctorActionsTest {

    private Doctor doctor;
    private Patient patient;
    private Appointment appointmentService;
    private AppointmentOutcomeRecord outcomeRecordService;
    private MedicalRecordManagement medicalRecordService;
    private AppointmentManagement appointmentManagementService;
    private Map<String, Map<String, List<String>>> doctorAvailability;
    private List<Appointment> appointmentList;
    private List<Map<String, String>> patientList;

    @Before
    public void setUp() {
        // Initialize a sample doctor
        doctor = new Doctor("D456", "Smith", "45", "Male");

        // Initialize a sample patient
        List<String> contactInfo = Arrays.asList("12345678", "patient@example.com");
        List<String> pastDiagnoses = Arrays.asList("Hypertension");
        List<String> pastTreatments = Arrays.asList("Medication A");
        patient = new Patient("P1001", "John Doe", "01-Jan-1990", contactInfo, "Male", "O+", pastDiagnoses, pastTreatments);

        appointmentService = new Appointment();
        outcomeRecordService = new AppointmentOutcomeRecord();
        medicalRecordService = new MedicalRecordManagement();
        appointmentManagementService = new AppointmentManagement();
        doctorAvailability = new HashMap<>();
        appointmentList = new ArrayList<>();

        // Initialize patient list
        patientList = new ArrayList<>();
        Map<String, String> patientData = new HashMap<>();
        patientData.put("Patient ID", patient.getId());
        patientData.put("Name", patient.getName());
        patientData.put("Date of Birth", patient.getDOB());
        patientData.put("Gender", patient.getGender());
        patientData.put("Blood Type", patient.getBlood());
        patientData.put("Contact Information", String.join(" | ", contactInfo));
        patientData.put("Past Diagnoses", String.join(" | ", pastDiagnoses));
        patientData.put("Past Treatment", String.join(" | ", pastTreatments));
        patientList.add(patientData);
    }

    private Map<String, Map<String, List<String>>> parseAvailability(String doctorName, String availability) {
        Map<String, Map<String, List<String>>> doctorAvailability = new HashMap<>();
        Map<String, List<String>> dateMap = new HashMap<>();
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
    public void testViewPatientMedicalRecords() {
        // Test Case 9: View Patient Medical Records
        medicalRecordService.viewMedicalRecord(patient.getId(), patientList);
        Map<String, String> patientData = patientList.get(0);
        assertEquals("Patient ID should match", "P1001", patientData.get("Patient ID"));
        assertEquals("Patient Name should match", "John Doe", patientData.get("Name"));
        assertEquals("Patient DOB should match", "01-Jan-1990", patientData.get("Date of Birth"));
        assertEquals("Patient Gender should match", "Male", patientData.get("Gender"));
        assertEquals("Blood Type should match", "O+", patientData.get("Blood Type"));
        assertEquals("Past Diagnoses should match", "Hypertension", patientData.get("Past Diagnoses"));
        assertEquals("Past Treatment should match", "Medication A", patientData.get("Past Treatment"));
    }

    @Test
    public void testUpdatePatientMedicalRecords() {
        // Test Case 10: Update Patient Medical Records
        // Add an initial appointment
        Appointment appointment = new Appointment(105, patient.getId(), "Smith", "13-Nov-2024", "13:00", "Completed");
        Appointment.getAllAppointments().add(appointment);

        // Parse doctor availability
        String availability = "12-Nov-2024 10:00/12:00/14:00 | 13-Nov-2024 13:00/15:00 | 15-Nov-2024 11:00";
        doctorAvailability = parseAvailability("Smith", availability);
        appointmentService.scheduleAppointment(appointment, "Smith", "13-Nov-2024", "13:00", doctorAvailability);

        // Update medical record with new diagnosis and treatment
        String newDiagnosis = "Diabetes";
        String newTreatment = "Insulin Therapy";
        medicalRecordService.updateMedicalRecord(patient.getId(), patientList, 105, newDiagnosis, newTreatment, "");

        // Verify that the medical record is updated
        Map<String, String> patientData = patientList.get(0);
        assertTrue("Past Diagnoses should contain new diagnosis", patientData.get("Past Diagnoses").contains(newDiagnosis));
        assertTrue("Past Treatments should contain new treatment", patientData.get("Past Treatment").contains(newTreatment));
    }

    @Test
    public void testViewPersonalSchedule() {
        Doctor doctor1 = new Doctor("D455", "John", "40", "Male");

        // Test Case 11: View Personal Schedule
        Appointment appointment1 = new Appointment(101, patient.getId(), doctor1.getName(), "12-Nov-2024", "10:00", "Confirmed");
        Appointment appointment2 = new Appointment(102, patient.getId(), doctor1.getName(), "13-Nov-2024", "11:00", "Confirmed");
        Appointment.getAllAppointments().add(appointment1);
        Appointment.getAllAppointments().add(appointment2);
        int apt_found = 0;

        String availability = "12-Nov-2024 10:00/12:00/14:00 | 13-Nov-2024 13:00/15:00 | 14-Nov-2024 16:00";
        Map<String, Map<String, List<String>>> doctorAvailability = parseAvailability(doctor1.getName(), availability);

        List<Map<String, String>> doctorList = new ArrayList<>();
        Map<String, String> doctorData = new HashMap<>();
        doctorData.put("Name", doctor1.getName());
        doctorData.put("Gender", doctor1.getGender());
        doctorList.add(doctorData);


        appointmentService.viewAvailableAppt(doctorList, doctorAvailability);

        appointmentManagementService.viewUpcomingAppointments(doctor1.getName());

        List<Appointment> docAppts = Appointment.getAllAppointments();
        for (Appointment apt : docAppts) {
            if (apt.getDoctor().equals(doctor1.getName()) && !apt.getStatus().equals("Declined")) {
                apt_found++;
            }
        }


        // Assert values to verify correct output
        assertEquals("Doctor availability should contain Dr. John", 1, doctorAvailability.size());
        assertEquals("Gender should be Male", "Male", doctorList.get(0).get("Gender"));
        assertTrue("Doctor availability should contain available timeslots for Dr. John", doctorAvailability.get("John").containsKey("12-Nov-2024"));
        assertEquals("Doctor should have 2 upcoming appointments", 2, apt_found);
    }

    @Test
    public void testSetAvailabilityForAppointments() {
        // Test Case 12: Set Availability for Appointments
        appointmentManagementService.setAvail(doctor.getName(), "15-Nov-2024", "10:00", doctorAvailability);

        assertTrue("Doctor availability should contain the new date and time", doctorAvailability.get(doctor.getName()).containsKey("15-Nov-2024"));
        assertTrue("Doctor availability should contain the new time slot", doctorAvailability.get(doctor.getName()).get("15-Nov-2024").contains("10:00"));
    }

    @Test
    public void testAcceptOrDeclineAppointmentRequests() {
        // Test Case 13: Accept or Decline Appointment Requests
        Appointment appointment = new Appointment(103, patient.getId(), doctor.getName(), "2024-12-16", "09:00 AM", "Pending");
        Appointment.getAllAppointments().add(appointment);
        Appointment appointment2 = new Appointment(104, patient.getId(), doctor.getName(), "2024-12-16", "09:00 AM", "Pending");
        Appointment.getAllAppointments().add(appointment2);

        appointmentManagementService.acceptAppointment(103);
        assertEquals("Appointment status should be confirmed", "Confirmed", appointment.getStatus());

        appointmentManagementService.declineAppointment(104);
        assertEquals("Appointment status should be declined", "Declined", appointment2.getStatus());
    }

    @Test
    public void testViewUpcomingAppointments() {
        // Test Case 14: View Upcoming Appointments


        Doctor doctor2 = new Doctor("D450", "Johnny", "40", "Male");

        Appointment appointment1 = new Appointment(107, patient.getId(), doctor2.getName(), "12-Nov-2024", "10:00", "Confirmed");
        Appointment appointment2 = new Appointment(108, patient.getId(), doctor2.getName(), "13-Nov-2024", "11:00", "Confirmed");
        Appointment.getAllAppointments().add(appointment1);
        Appointment.getAllAppointments().add(appointment2);
        int apt_found = 0;

        appointmentManagementService.viewUpcomingAppointments(doctor2.getName());

        List<Appointment> docAppts = Appointment.getAllAppointments();
        for (Appointment apt : docAppts) {
            if (apt.getDoctor().equals(doctor2.getName()) && !apt.getStatus().equals("Declined")) {
                apt_found++;
            }
        }

        assertEquals("Doctor should have 2 upcoming confirmed appointments", 2, apt_found);
    }

    @Test
    public void testRecordAppointmentOutcome() {
        // Test Case 15: Record Appointment Outcome
        String serviceType = "Consultation";
        String medicationName = "Medication A";
        String medicationStatus = "Pending";
        String medicationQuantity = "15";
        String consultationNotes = "Consultation notes";

        Appointment appointment = new Appointment(105, patient.getId(), "Smith", "13-Nov-2024", "13:00", "Completed");
        Appointment.getAllAppointments().add(appointment);
        outcomeRecordService.addOutcomeRecord(patient.getId(), 105, serviceType, medicationName, medicationStatus, medicationQuantity, consultationNotes);

        outcomeRecordService.viewAllOutcomeRecords();

        Map<String, Map<Integer, Map<String, Object>>> outcomeRecords = AppointmentOutcomeRecord.getAllOutcomeRecords();
        assertTrue("Outcome records should contain patient ID", outcomeRecords.containsKey(patient.getId()));
        assertTrue("Patient records should contain appointment ID", outcomeRecords.get(patient.getId()).containsKey(105));

        Map<String, Object> outcomeDetails = outcomeRecords.get(patient.getId()).get(105);
        assertEquals("Service type should match", serviceType, outcomeDetails.get("stype"));
        assertEquals("Consultation notes should match", consultationNotes, outcomeDetails.get("cnotes"));

        List<Map<String, String>> medications = (List<Map<String, String>>) outcomeDetails.get("pmeds");
        assertNotNull("Medications should not be null", medications);
        assertEquals("Medication name should match", medicationName, medications.get(0).get("name"));
        assertEquals("Medication status should match", medicationStatus, medications.get(0).get("status"));
        assertEquals("Medication quantity should match", medicationQuantity, medications.get(0).get("quantity"));
    }
}
