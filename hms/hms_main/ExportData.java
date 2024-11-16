package hms_main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides functionality to export or display patient records in a structured format.
 */
public class ExportData {

    /**
     * Exports the given data to a CSV file.
     *
     * @param fileName The name of the file to export to.
     * @param rows     The rows of data to be written, where each row is a string array.
     * @param headers  The headers for the CSV file.
     */
    public void exportCSV(String fileName, List<String[]> rows, String[] headers) {
        try (FileWriter writer = new FileWriter(fileName)) {
            
            writer.append(String.join(",", headers));
            writer.append("\n");

            
            for (String[] row : rows) {
                writer.append(String.join(",", row));
                writer.append("\n");
            }

            System.out.println("Records exported successfully to: " + fileName);
        } catch (IOException e) {
            System.out.println("Error exporting records: " + e.getMessage());
        }
    }

    /**
     * Displays or exports patient records based on the given parameters.
     *
     * @param patientId      The ID of the patient whose records are to be displayed/exported.
     * @param outcomeRecords The map containing outcome records for all patients.
     * @param export         If true, the records will be exported to a CSV file; otherwise, they will be displayed.
     */
    public void patientViewOrExportRecords(String patientId,
            Map<String, Map<Integer, Map<String, Object>>> outcomeRecords, boolean export) {
        if (!outcomeRecords.containsKey(patientId)) {
            System.out.println("No records found for patient ID: " + patientId);
            return;
        }

        List<String[]> rows = new ArrayList<>();
        String[] headers = { "Appointment ID", "Date", "Service", "Medications", "Notes" };

        Map<Integer, Map<String, Object>> appointments = outcomeRecords.get(patientId);

        for (Map.Entry<Integer, Map<String, Object>> appointmentEntry : appointments.entrySet()) {
            Map<String, Object> details = appointmentEntry.getValue();
            String medications = extractMedications((List<Map<String, String>>) details.get("pmeds"));

            String[] row = {
                    String.valueOf(appointmentEntry.getKey()),
                    (String) details.get("date"),
                    (String) details.get("stype"),
                    medications,
                    (String) details.get("cnotes")
            };
            rows.add(row);
        }

        if (export) {
            exportCSV(patientId + "_records.csv", rows, headers);
        } else {
            displayRecords(headers, rows);
        }
    }

    /**
     * Extracts medication information from a list of medication details.
     *
     * @param medications The list of maps containing medication details.
     * @return A formatted string of medication names and statuses.
     */
    private String extractMedications(List<Map<String, String>> medications) {
        StringBuilder meds = new StringBuilder();
        for (Map<String, String> med : medications) {
            meds.append(med.get("name")).append(" (").append(med.get("status")).append(")");
        }
        return meds.toString().isEmpty() ? "None" : meds.toString();
    }

    /**
     * Displays the records in a tabular format in the console.
     *
     * @param headers The headers for the records.
     * @param rows    The rows of data to display.
     */
    private void displayRecords(String[] headers, List<String[]> rows) {
        System.out.println(String.join(" | ", headers));
        System.out.println("=".repeat(headers.length * 15));

        for (String[] row : rows) {
            System.out.println(String.join(" | ", row));
        }
    }

}
