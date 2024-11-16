package pharmacist;
public class Prescription {
    private String prescriptionId;
    private String appointmentId;
    private String medicationName;
    private String dosage;
    private String status; // status: pending or dispensed 

    public Prescription() {
        
    }

    public Prescription(String prescriptionId, String appointmentId, String medicationName, String dosage) {
        this.prescriptionId = prescriptionId;
        this.appointmentId = appointmentId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.status = "Pending";
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public String getStatus() {
        return status;
    }

    
    public void updateStatus(String status) {
        if (status.equals("Pending") || status.equals("Dispensed")) {
            this.status = status;
        } else {
            System.out.println("Invalid status: " + status);
        }
    }

    
}
