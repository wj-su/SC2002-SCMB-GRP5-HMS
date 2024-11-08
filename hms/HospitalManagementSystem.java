import doctor.AppointmentOutcomeRecord;
import doctor.doctor;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import patient.Appointment;
import patient.Patient;
import doctor.doctorApp;

public class HospitalManagementSystem {

	static List<Map<String, String>> patientList = new ArrayList<>();
	static Appointment defaultApts = new Appointment();
	static AppointmentOutcomeRecord outcome = new AppointmentOutcomeRecord();

	public static void main(String[] args) {

		// Testing the functions! Remove when Daniel is done with his login page
		Scanner sc = new Scanner(System.in);
		int identity;

		createPatientList();


		do {

			System.out.println("\nWho are you?"
				+ "\n(1) Patient" + "\n(2) Doctor" + "\n(3) Pharmacist" + "\n(4) Administrator" + "\n(0) Quit");
		 	identity = sc.nextInt();

			switch (identity) {
				case 1:
					System.out.println("\nEnter Patient ID: ");
					String pid = sc.next();
					pid = Character.toUpperCase(pid.charAt(0)) + pid.substring(1);
					Patient selectedPatient = getSelectedPatient(pid);
					if (selectedPatient != null) {
						PatientOption(pid, selectedPatient);
					}
					break;

				case 2:
					DoctorOption();
					getDoctorList();
					break;

				case 3:
					// Pharmacist
					break;

				case 4:
					// Admin func
					break;

			}

		} while (identity != 0);

	}

	public static void createPatientList() {
		String csvFile = "hms\\Patient_List.csv"; // converted file path
        String line;
        String csvSplitBy = ",";


        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // read the first line as the header
            String headerLine = br.readLine();
            if (headerLine == null) {
                System.out.println("CSV file is empty");
                return;
            }
            String[] headers = headerLine.split(csvSplitBy);

            // read each subsequent line
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(csvSplitBy);

                // create a map for the row data
                Map<String, String> dataMap = new HashMap<>();

                for (int i = 0; i < headers.length; i++) {
                    // put header as key and cell data as value
                    if (i < columns.length) {
                        dataMap.put(headers[i], columns[i]);
                    } else {
                        dataMap.put(headers[i], ""); // empty string if column is missing
                    }
                }

                patientList.add(dataMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public static Patient getSelectedPatient(String pid) {

		// just to initialise
		Patient p = null;	
		boolean patientExists = false;

		do {

			// print the list of maps to verify the data
			for (Map<String, String> data : patientList) {

				// add pre-defined data to list
	
				String pId = data.get("Patient ID");
	
				if (pId.equals(pid)) {
					String pName = data.get("Name");
					String pDOB = data.get("Date of Birth");
					String pGender = data.get("Gender");
					
					List<String> pContactInfo = new ArrayList<>();
					if (data.get("Contact Information") != null) {
						String contactInfo = data.get("Contact Information");
						if (contactInfo.equals("NIL")) {
							pContactInfo.add("NIL");  // Add "NIL" if the value is exactly "NIL"
						} else {
							String[] contactDetails = contactInfo.split("\\|");
							for (String contact : contactDetails) {
								pContactInfo.add(contact.trim());
							}
						}
					}
					
					List<String> pPastDiagnoses = new ArrayList<>();
					if (data.get("Past Diagnoses") != null) {
						String pastDiagnoses = data.get("Past Diagnoses");
						if (pastDiagnoses.equals("NIL")) {
							pPastDiagnoses.add("NIL");  // Add "NIL" if the value is exactly "NIL"
						} else {
							String[] diagnoses = pastDiagnoses.split("\\|");
							for (String diagnosis : diagnoses) {
								pPastDiagnoses.add(diagnosis.trim());
							}
						}
					}
					
					List<String> pPastTreatments = new ArrayList<>();
					if (data.get("Past Treatment") != null) {
						String pastTreatments = data.get("Past Treatment");
						if (pastTreatments.equals("NIL")) {
							pPastTreatments.add("NIL");  // Add "NIL" if the value is exactly "NIL"
						} else {
							String[] treatments = pastTreatments.split("\\|");
							for (String treatment : treatments) {
								pPastTreatments.add(treatment.trim());
							}
						}
					}
					
	
					String pBloodType = data.get("Blood Type");
	
	
					// Create and add Patient
					p = new Patient(pId, pName, pDOB, pContactInfo, pGender, pBloodType, pPastDiagnoses, pPastTreatments);
					patientExists = true;
					break;
					// patientList.add(patient);
				}	
			}
	
			if (patientExists == false) {
				System.out.println("Patient does not exist!!");
				return p;
			}

			return p;

		} while (patientExists == true);

	}
	

	public static void PatientOption(String pid, Patient p) {

		int choice = 0;
		Scanner sc = new Scanner(System.in);
		

		do {
			PatientMenu();

			System.out.println("What do you want to do?");
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
				case 1:
					p.viewMedicalRecord();
					break;
				case 2:
					String email, hp = "";
					String phoneRegex = "^\\d{8}$";
					Pattern phonePattern = Pattern.compile(phoneRegex);
					String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
					Pattern emailPattern = Pattern.compile(emailRegex);

					while (true) {
						System.out.println("Enter your updated phone number (type 'no' if no update): ");
						hp = sc.nextLine();
						if (hp.toLowerCase().equals("no")) {
							break;
						} else {
							Matcher checkHp = phonePattern.matcher(hp);
							if (checkHp.matches()) {
								break;
							} else {
								System.out.println("\nInvalid phone number! Please enter a valid phone number!!\n");
							}
						}
					}

					while (true) {
						System.out.println("Enter your updated email address (type 'no' if no update): ");
						email = sc.nextLine();
						if (email.toLowerCase().equals("no")) {
							break;
						} else {
							Matcher checkEmail = emailPattern.matcher(email);
							if (checkEmail.matches()) {
								break;
							} else {
								System.out.println("\nInvalid email! Please enter a valid email address!!\n");
							}
						}
					}

					p.updatePersonalInformation(hp, email);
					break;
				case 3:
					// HAVENT CONNECT WITH DOCTORS
					System.out.println("View Available Appointment Slots");
					System.out.println("Do you want see all of your appointments, or based on the doctor? ");
					System.out.println("Type `all` to view all of your appointments");
					System.out.println("Type `doc` to view appointments with a specific doctor");
					String type = sc.nextLine();
					if (type.toLowerCase().equals("all")) {
						defaultApts.viewAvailableAppt();
					} else if (type.toLowerCase().equals("doc")) {
						System.out.println("Enter the doctor's name: ");
						String dn = sc.nextLine();
						dn = Character.toUpperCase(dn.charAt(0)) + dn.substring(1).toLowerCase();
						defaultApts.viewAvailableApptByDoc(dn);
					} else {
						System.out.println("Please choose either `all` or `doc`!");
					}

					break;
				case 4:
					System.out.println("Scheduling an appointment...");
					String doc = "";
					String date = "";
					String ts = "";
					while (true) {
						System.out.println("Choose a doctor: ");
						doc = sc.nextLine();
						doc = Character.toUpperCase(doc.charAt(0)) + doc.substring(1).toLowerCase();
						if (!defaultApts.getApptSlots().containsKey(doc)) {
							System.out.println("Doctor not found!");
							continue;
						}

						System.out.println("Choose a date:");
						date = sc.nextLine();
						if (!defaultApts.getApptSlots().get(doc).containsKey(date)) {
							System.out.println("Date not available for the chosen doctor!");
							continue;
						}

						System.out.println("Choose an available time slot: ");
						ts = sc.nextLine();
						if (!defaultApts.getApptSlots().get(doc).get(date).contains(ts)) {
							System.out.println("Timeslot not available!");
							continue;
						}

						break;
					}
					if (doc != "" && date != "" && ts != "") {
						Random rid = new Random();
						Appointment apt = new Appointment(rid.nextInt(1000), pid, doc, date, ts, "Pending");
						defaultApts.scheduleAppointment(apt, doc, date, ts);
					}
					break;
				case 5:
					System.out.println("Rescheduling appointment...");
					System.out.println("Enter the doctor's name you have an appointment with: ");
					String docO = sc.nextLine();
					docO = Character.toUpperCase(docO.charAt(0)) + docO.substring(1).toLowerCase();
					System.out.println("Enter the old date: ");
					String dateO = sc.nextLine();
					System.out.println("Enter the old timeslot : ");
					String timeO = sc.nextLine();
					// defaultApts.appointmentExists(docO, dateO, timeO, "reschedule");
					if (defaultApts.appointmentExists(pid, docO, dateO, timeO, "reschedule")) {
						System.out.println("Do you want to reschedule to a new doctor, or only change timeslot?");
						System.out.println("Yes or No?");
						String decision = sc.nextLine();
						String newDoc = "";
						if (decision.toLowerCase().equals("yes")) {
							System.out.println("Enter the new doctor's name: ");
							newDoc = sc.nextLine();
							newDoc = Character.toUpperCase(newDoc.charAt(0)) + newDoc.substring(1).toLowerCase();
						}
						System.out.println("Enter the new date : ");
						String dateR = sc.nextLine();
						System.out.println("Enter the new timeslot : ");
						String timeR = sc.nextLine();
						defaultApts.rescheduleAppointment(pid, docO, dateO, timeO, newDoc, dateR, timeR, decision);
					}
					else {
						System.out.println("Appointment doesn't exist!");  
					}

					break;
				case 6:
					System.out.println("Cancelling appointment...");
					System.out.println("Enter the doctor's name you cancelling an appointment with: ");
					String docC = sc.nextLine();
					docC = Character.toUpperCase(docC.charAt(0)) + docC.substring(1).toLowerCase();
					System.out.println("Enter the cancelled date : ");
					String dateC = sc.nextLine();
					System.out.println("Enter the cancelled timeslot : ");
					String timeC = sc.nextLine();
					defaultApts.appointmentExists(pid, docC, dateC, timeC, "cancel");

					defaultApts.cancelAppointment(pid, docC, dateC, timeC);
					break;
				case 7:
					System.out.println("Below are your appointments...");
					defaultApts.viewScheduledAppointments(pid);
					break;
				case 8:
					System.out.println("Below are your past appointment records...");
					defaultApts.viewPastAppointmentOutcomeRecords(pid);;
					break;
				case 9:
					System.out.println("You have logged out!");
					return;
				default:
					System.out.println("Babes choose options 1 - 9 pls :(");
					break;
			}
		} while (choice != 9);	

		
		// sc.close();
	}

	public static void DoctorOption() {

		Scanner sc = new Scanner(System.in);
		int choice = 0;

		

		do {
			DoctorMenu();

			System.out.println("What do you want to do?");
			choice = sc.nextInt();
			sc.nextLine();
			
			switch (choice) {
				case 1:
					System.out.println("View Patient Medical Records");
					break;
				case 2:
					System.out.println("Update Patient Medical Records");
					break;
				case 3:
					System.out.println("View Personal Schedule");
					break;
				case 4:
					System.out.println("Set Availability for Appointments");
					break;
				case 5:
					System.out.println("Accept or Decline Appointment Requests");
					break;
				case 6:
					System.out.println("View Upcoming Appointments");
					break;
				case 7:
					System.out.println("Record Appointment Outcome...");
					System.out.println("Enter Patient Id: ");
					String pid = sc.nextLine();
					pid = Character.toUpperCase(pid.charAt(0)) + pid.substring(1);
					System.out.println("Enter Appointment Id: ");
					int aptid = sc.nextInt();
					sc.nextLine();
					System.out.println("Enter Service Type: ");
					String st = sc.nextLine();
					System.out.println("Do you have something to prescribe the patient?");
					String needMed = sc.nextLine();
					String mn = "";
					String ms = "";
					if (needMed.toLowerCase().equals("yes")) {
						System.out.println("Enter Medication Name: ");
						mn = sc.nextLine();
						System.out.println("Enter Medication Status: ");
						ms = sc.nextLine();
					}
					System.out.println("Enter Consultation Notes: ");
					String consultNotes = sc.nextLine();

					outcome.addOutcomeRecord(pid, aptid, st, mn, ms, consultNotes);

					System.out.println("\n");
					break;
				case 8:
					System.out.println("You have logged out!");
					return;
				default:
					System.out.println("Babes choose options 1 - 8 :(");
					break;
			}
		} while (choice != 9);

	}

	public static void PatientMenu() {
		System.out.println("\nPatient Menu:");
		System.out.println("- View Medical Record (1)");
		System.out.println("- Update Personal Information (2)");
		System.out.println("- View Available Appointment Slots (3)");
		System.out.println("- Schedule an Appointment (4)");
		System.out.println("- Reschedule an Appointment (5)");
		System.out.println("- Cancel an Appointment (6)");
		System.out.println("- View Scheduled Appointments (7)");
		System.out.println("- View Past Appointments Outcome Records (8)");
		System.out.println("- Logout (9)");
	}

	public static void DoctorMenu() {
		System.out.println("---------------------------------------------");
		System.out.println("|                Doctor Menu                 |");
		System.out.println("----------------------------------------------");
		System.out.println("|  - View Patient Medical Records        (1) | ");
		System.out.println("|  - Update Patient Medical Records      (2) | ");
		System.out.println("|  - View Personal Schedule              (3) | ");
		System.out.println("|  - Set Availability for Appointments   (4) | ");
		System.out.println("|  - Accept/Decline Appointment Requests (5) | ");
		System.out.println("|  - View Upcoming Appointments          (6) | ");
		System.out.println("|  - Record Appointment Outcome          (7) | ");
		System.out.println("|  - Logout                              (8) | ");
		System.out.println("----------------------------------------------");

	}

	public static void getDoctorList(){
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

                // Assuming columns: [ID, Name, Gender, ContactNumber, Availability, Role]
                String role = columns[5];  // Assuming "Role" is the 6th column

                // Check if this line represents a doctor
                if (role.equalsIgnoreCase("doctor")) {
                    String doctorId = columns[0];
                    String name = columns[1];
                    String gender = columns[2];
                    String contactNumber = columns[3];
                    String availDateTime = columns[4];

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