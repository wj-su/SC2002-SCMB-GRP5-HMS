public class Pharmacist extends User {
    private String name;
    private String contactInfo;
    private List<Prescription> prescriptionList;

    public Pharmacist(String userId, String name) {
        super(userId, "Pharmacist");
        this.name = name;
        this.prescriptionList = new ArrayList<>();
    }

    public void addPrescription(Prescription prescription) {
        prescriptionList.add(prescription);
    }

    public void viewPrescriptions() {
        System.out.println("Viewing all prescriptions for pharmacist: " + name);
        for (Prescription prescription : prescriptionList) {
            System.out.println(prescription);
        }
    }

    public void updatePrescriptionStatus(String prescriptionId, String status) {
        for (Prescription prescription : prescriptionList) {
            if (prescription.getPrescriptionId().equals(prescriptionId)) {
                prescription.updateStatus(status);
                System.out.println("Prescription " + prescriptionId + " status updated to: " + status);
                return;
            }
        }
        System.out.println("Prescription ID not found: " + prescriptionId);
    }

    @Override
    public void displayUserInfo() {
        super.displayUserInfo();
        System.out.println("Name: " + name);
    }
}
