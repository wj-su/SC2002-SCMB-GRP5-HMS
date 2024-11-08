package doctor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class doctorApp {
    public static void main(String[] args) {
        String csvFile = "hms\\Staff_List.csv";  // Replace with the actual path
        String line;
        String csvSplitBy = ",";

        List<doctor> doctors = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read the header line (if any)
            String headerLine = br.readLine();

            // Read each subsequent line
            while ((line = br.readLine()) != null) {
                // Split the line into columns
                String[] columns = line.split(csvSplitBy);

                // Check if the line has enough columns (6 columns expected)
                if (columns.length < 6) {
                    //System.out.println("Skipping line due to insufficient columns: " + line);
                    continue;
                }

                // Assuming columns are structured as:
                // [ID, Name, Role, Gender, ContactNumber, Availability]
                String role = columns[2].trim();  // Role is in the third column

                // Check if this line represents a doctor
                if (role.equalsIgnoreCase("Doctor")) {
                    String doctorId = columns[0].trim();
                    String name = columns[1].trim();
                    String gender = columns[3].trim();
                    String contactNumber = columns[4].trim();
                    String availDateTime = columns[5].trim();

                    // Create a Doctor object and add it to the list
                    doctor doctor = new doctor(doctorId, name, gender, contactNumber, availDateTime);
                    doctors.add(doctor);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print out the list of doctors
        for (doctor doctor : doctors) {
            System.out.println(doctor);
        }
    }
}
