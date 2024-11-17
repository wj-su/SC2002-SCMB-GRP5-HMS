package hms.pharmacist;

/**
 * Represents a prescription in the pharmacy system.
 * Includes details such as prescription ID, appointment ID, medication name, dosage, and status.
 */
public class Prescription {
    /**
     * The unique identifier for the prescription.
     */
    private String prescriptionId;

    /**
     * The identifier of the appointment associated with the prescription.
     */
    private String appointmentId;

    /**
     * The name of the prescribed medication.
     */
    private String medicationName;

    /**
     * The dosage information for the prescribed medication.
     */
    private String dosage;

    /**
     * The status of the prescription (e.g., "Pending", "Dispensed").
     */
    private String status;

    /**
     * Default constructor.
     */
    public Prescription() {}

    /**
     * Constructor to initialize a prescription object.
     *
     * @param prescriptionId The unique ID of the prescription.
     * @param appointmentId  The ID of the appointment associated with the prescription.
     * @param medicationName The name of the medication prescribed.
     * @param dosage         The dosage of the prescribed medication.
     */
    public Prescription(String prescriptionId, String appointmentId, String medicationName, String dosage) {
        this.prescriptionId = prescriptionId;
        this.appointmentId = appointmentId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.status = "Pending";
    }

    /**
     * Retrieves the unique ID of the prescription.
     *
     * @return The prescription ID.
     */
    public String getPrescriptionId() {
        return prescriptionId;
    }

    /**
     * Retrieves the appointment ID associated with the prescription.
     *
     * @return The appointment ID.
     */
    public String getAppointmentId() {
        return appointmentId;
    }

    /**
     * Retrieves the name of the prescribed medication.
     *
     * @return The medication name.
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Retrieves the dosage of the prescribed medication.
     *
     * @return The dosage of the medication.
     */
    public String getDosage() {
        return dosage;
    }

    /**
     * Retrieves the current status of the prescription.
     * The status can be "Pending" or "Dispensed".
     *
     * @return The status of the prescription.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Updates the status of the prescription.
     *
     * @param status The new status to set. Must be either "Pending" or "Dispensed".
     */
    public void updateStatus(String status) {
        if (status.equals("Pending") || status.equals("Dispensed")) {
            this.status = status;
        } else {
            System.out.println("Invalid status: " + status);
        }
    }

    
}
