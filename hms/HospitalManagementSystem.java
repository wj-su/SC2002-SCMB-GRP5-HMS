import doctor.AppointmentManagement;
import doctor.AppointmentOutcomeRecord;
import doctor.Doctor;
import doctor.MedicalRecordManagement;
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

public class HospitalManagementSystem {

	static List<Map<String, String>> patientList = new ArrayList<>();
	static List<Map<String, String>> doctorList = new ArrayList<>();
	static List<Map<String, String>> medicineList = new ArrayList<>();
	static Map<String, Map<String, List<String>>> doctorAvailability = new HashMap<>();
	// static Appointment defaultApts = new Appointment();
	// static AppointmentOutcomeRecord outcome = new AppointmentOutcomeRecord();

	public static void main(String[] args) {

		// Testing the functions! Remove when Daniel is done with his login page
		Scanner sc = new Scanner(System.in);
		int identity;

		createPatientList();
		createDoctorList();
		createDoctorAvailList();
		createPharmacistList();

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

					System.out.println("\nEnter Doctor ID: ");
					String did = sc.next();
					did = Character.toUpperCase(did.charAt(0)) + did.substring(1);
					Doctor selectedDoc = getSelectedDoctor(did);
					if (selectedDoc != null) {
						DoctorOption(did, selectedDoc);
					}
					break;

				case 3:
					System.out.println("\nEnter Pharmacist ID: ");
					String phid = sc.next();
					phid = Character.toUpperCase(did.charAt(0)) + did.substring(1);
					Pharmacist selectedPharmacist = getSelectedPharmacist(phid);
					if (SelectedPharmacist != null) {
						PharmacistOption(phid, selectedPharmacist);
					}
					break;

				case 4:
					// Admin func
					break;

			}

		} while (identity != 0);

	}

	public static void createDoctorList() {
		String csvFile = "hms\\Staff_List.csv"; // converted file path
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

				boolean isDoctor = false;
				for (int i = 0; i < headers.length; i++) {
					// Check if the header is "role" and verify if the role is "Doctor"
					if (headers[i].equalsIgnoreCase("role")) {
						if (i < columns.length && columns[i].equalsIgnoreCase("Doctor")) {
							isDoctor = true;
						}
					}

					// put header as key and cell data as value
					if (i < columns.length) {
						dataMap.put(headers[i], columns[i]);
					} else {
						dataMap.put(headers[i], ""); // empty string if column is missing
					}
				}

				// only add to the list if the role is "Doctor"
				if (isDoctor) {
					doctorList.add(dataMap);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createDoctorAvailList() {
		String csvFile = "hms\\Staff_List.csv"; // Update the path as needed
		String line;
		String csvSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			String headerLine = br.readLine();
			if (headerLine == null) {
				System.out.println("CSV file is empty");
				return;
			}
			String[] headers = headerLine.split(csvSplitBy);

			while ((line = br.readLine()) != null) {
				String[] columns = line.split(csvSplitBy);
				Map<String, String> dataMap = new HashMap<>();
				boolean isDoctor = false;

				for (int i = 0; i < headers.length; i++) {
					if (headers[i].equalsIgnoreCase("role") && i < columns.length
							&& columns[i].equalsIgnoreCase("Doctor")) {
						isDoctor = true;
					}
					if (i < columns.length) {
						dataMap.put(headers[i], columns[i]);
					} else {
						dataMap.put(headers[i], "");
					}
				}

				if (isDoctor) {
					String doctorName = dataMap.get("Name");
					String availability = dataMap.get("Availability Dates");
					if (availability != null && !availability.isEmpty()) {
						Map<String, List<String>> dateMap = new HashMap<>();

						String[] dates = availability.split("\\|");
						for (String dateEntry : dates) {
							String[] dateAndTimes = dateEntry.trim().split(" ");
							if (dateAndTimes.length >= 2) {
								String date = dateAndTimes[0].trim();
								String[] times = dateAndTimes[1].split("/");

								List<String> timeList = new ArrayList<>();
								for (String time : times) {
									timeList.add(time.trim());
								}
								dateMap.put(date, timeList);
							}
						}
						doctorAvailability.put(doctorName, dateMap);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void createPharmacistList() {
		String csvFile = "hms\\Staff_List.csv"; // converted file path
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

				boolean isPharmacist = false;
				for (int i = 0; i < headers.length; i++) {
					// Check if the header is "role" and verify if the role is "Doctor"
					if (headers[i].equalsIgnoreCase("role")) {
						if (i < columns.length && columns[i].equalsIgnoreCase("Pharmacist")) {
							isPharmacist = true;
						}
					}

					// put header as key and cell data as value
					if (i < columns.length) {
						dataMap.put(headers[i], columns[i]);
					} else {
						dataMap.put(headers[i], ""); // empty string if column is missing
					}
				}

				// only add to the list if the role is "Pharmacist "
				if (isPharmacist) {
					pharmacistList.add(dataMap);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void createMedicineList() {
		String csvFile = "hms\\Medicine_List.csv"; // converted file path
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

				medicineList.add(dataMap);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
							pContactInfo.add("NIL"); // Add "NIL" if the value is exactly "NIL"
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
							pPastDiagnoses.add("NIL"); // Add "NIL" if the value is exactly "NIL"
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
							pPastTreatments.add("NIL"); // Add "NIL" if the value is exactly "NIL"
						} else {
							String[] treatments = pastTreatments.split("\\|");
							for (String treatment : treatments) {
								pPastTreatments.add(treatment.trim());
							}
						}
					}

					String pBloodType = data.get("Blood Type");

					// Create and add Patient
					p = new Patient(pId, pName, pDOB, pContactInfo, pGender, pBloodType, pPastDiagnoses,
							pPastTreatments);
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

	public static Doctor getSelectedDoctor(String did) {

		// just to initialise
		Doctor d = null;
		boolean doctorExists = false;

		do {

			// print the list of maps to verify the data
			for (Map<String, String> data : doctorList) {

				// add pre-defined data to list

				String dId = data.get("Staff ID");

				if (dId.equals(did)) {
					String dName = data.get("Name");
					String dGender = data.get("Gender");
					String dAge = data.get("Age");
					String dContact = data.get("Contact Number");

					List<String> dAvailabilityList = new ArrayList<>();

					// Check if "Availability Dates" is present
					if (data.get("Availability Dates") != null) {
						String availDetails = data.get("Availability Dates");

						// Split by '|' to get each date and its time slots
						String[] availInfo = availDetails.split("\\|");

						// Iterate over each date entry
						for (String avail : availInfo) {
							avail = avail.trim(); // Trim any leading/trailing spaces

							// Split the entry by space to separate date and time slots
							int firstSpaceIndex = avail.indexOf(' ');

							// Ensure that the date and time slots are correctly identified
							if (firstSpaceIndex != -1) {
								String date = avail.substring(0, firstSpaceIndex).trim(); // Extract the date
								String timeSlots = avail.substring(firstSpaceIndex).trim(); // Extract the time slots

								// Split time slots by '/'
								String[] times = timeSlots.split("/");

								// Format the time slots as a comma-separated string
								String formattedTimeSlots = String.join(", ", times);

								// Create a formatted string for the availability entry
								String availabilityEntry = date + ": " + formattedTimeSlots;

								// Add this formatted string to the availability list
								dAvailabilityList.add(availabilityEntry);
							}
						}
					}

					// Example usage: printing the availability list
					for (String availability : dAvailabilityList) {
						System.out.println(availability);
					}

					// Create and add Doctor
					d = new Doctor(did, dName, dAge, dGender, dContact, dAvailabilityList);
					doctorExists = true;
					break;
				}
			}

			if (doctorExists == false) {
				System.out.println("Doctor does not exist!!");
				return d;
			}

			return d;

		} while (doctorExists == true);

	}

	public static void PatientOption(String pid, Patient p) {

		int choice = 0;
		Scanner sc = new Scanner(System.in);

		Appointment defaultApts = new Appointment();

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

					p.updatePersonalInformation(hp, email, patientList);
					break;
				case 3:
					System.out.println("View Available Appointment Slots");
					System.out.println("Do you want see all of your appointments, or based on the doctor? ");
					System.out.println("Type `all` to view all of your appointments");
					System.out.println("Type `doc` to view appointments with a specific doctor");
					String type = sc.nextLine();
					if (type.toLowerCase().equals("all")) {
						defaultApts.viewAvailableAppt(doctorAvailability);
					} else if (type.toLowerCase().equals("doc")) {
						System.out.println("Enter the doctor's name: ");
						String dn = sc.nextLine();
						// dn = Character.toUpperCase(dn.charAt(0)) + dn.substring(1).toLowerCase();
						defaultApts.viewAvailableApptByDoc(dn, doctorAvailability);
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
						// doc = Character.toUpperCase(doc.charAt(0)) + doc.substring(1).toLowerCase();
						String normalizedDoc = doc.toLowerCase();

						// check if doctor exists in doctorAvailability 
						boolean doctorFound = false;
						for (String doctorName : doctorAvailability.keySet()) {
							if (doctorName.toLowerCase().equals(normalizedDoc)) {
								doctorFound = true;
								doc = doctorName; 
								break;
							}
						}

						if (!doctorFound) {
							System.out.println("Doctor not found!");
							continue;
						}

						System.out.println("Choose a date:");
						date = sc.nextLine();
						if (!doctorAvailability.get(doc).containsKey(date)) {
							System.out.println("Date not available for the chosen doctor!");
							continue;
						}

						System.out.println("Choose an available time slot: ");
						ts = sc.nextLine();
						if (!doctorAvailability.get(doc).get(date).contains(ts)) {
							System.out.println("Timeslot not available!");
							continue;
						}

						break;
					}
					if (doc != "" && date != "" && ts != "") {
						Random rid = new Random();
						Appointment apt = new Appointment(rid.nextInt(1000), pid, doc, date, ts, "Pending");
						defaultApts.scheduleAppointment(apt, doc, date, ts, doctorAvailability);
					}
					break;
				case 5:
					System.out.println("Rescheduling appointment...");
					System.out.println("Enter the appointment ID: ");
					int aptid = sc.nextInt();
					sc.nextLine(); // consume the newline after nextInt

					if (defaultApts.appointmentExists(pid, aptid, "reschedule", doctorAvailability)) {
						System.out.println("Do you want to reschedule to a new doctor, or only change timeslot?");
						System.out.println("Yes or No?");
						String decision = sc.nextLine();
						String newDoc = "";
						if (decision.toLowerCase().equals("yes")) {
							System.out.println("Enter the new doctor's name: ");
							newDoc = sc.nextLine();
							defaultApts.viewAvailableApptByDoc(newDoc, doctorAvailability);

						}
						System.out.println("Enter the new date : ");
						String dateR = sc.nextLine();
						System.out.println("Enter the new timeslot : ");
						String timeR = sc.nextLine();
						defaultApts.rescheduleAppointment(pid, aptid, newDoc, dateR, timeR, decision,
								doctorAvailability);

					} else {
						System.out.println("Appointment doesn't exist!");
					}

					break;
				case 6:
					System.out.println("Cancelling appointment...");
					System.out.println("Enter the appointment ID: ");
					int aptId = sc.nextInt();
					sc.nextLine();
					if (!defaultApts.appointmentExists(pid, aptId, "cancel", doctorAvailability)) {
						System.out.println("Appointment doesn't exist!");
					}
					defaultApts.cancelAppointment(pid, aptId, doctorAvailability);
					break;
				case 7:
					System.out.println("Below are your appointments...");
					defaultApts.viewScheduledAppointments(pid);
					break;
				case 8:
					System.out.println("Below are your past appointment records...");
					defaultApts.viewPastAppointmentOutcomeRecords(pid);
					;
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

	public static void DoctorOption(String did, Doctor d) {

		Scanner sc = new Scanner(System.in);
		int choice = 0;

		MedicalRecordManagement mr = new MedicalRecordManagement();
		AppointmentManagement am = new AppointmentManagement();
		AppointmentOutcomeRecord outcome = new AppointmentOutcomeRecord();

		do {
			DoctorMenu();

			System.out.println("What do you want to do?");
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
				case 1:
					System.out.println("View Patient Medical Records");
					System.out.println("Which patient's medical record are you viewing?");
					System.out.println("Enter Patient ID");
					String pidD = sc.nextLine();
					pidD = Character.toUpperCase(pidD.charAt(0)) + pidD.substring(1).toLowerCase();
					mr.viewMedicalRecord(pidD, patientList);
					break;
				case 2:
					System.out.println("Update Patient Medical Records");
					System.out.println("Which patient's medical record are you updating?");
					System.out.println("Enter Patient ID");
					String pid2 = sc.nextLine();
					pid2 = Character.toUpperCase(pid2.charAt(0)) + pid2.substring(1).toLowerCase();
					System.out.println("Enter Appointment ID");
					int apt = sc.nextInt();
					sc.nextLine();
					System.out.println("Enter New Diagnosis:");
					String diag = sc.nextLine();
					System.out.println("Enter New Treatment:");
					String treat = sc.nextLine();
					System.out.println("Enter New Medication:");
					String med = sc.nextLine();
					mr.updateMedicalRecord(pid2, patientList, apt, diag, treat, med);
					break;
				case 3:
					System.out.println("View Personal Schedule");
					System.out.println("----------------------");
					System.out.println("Which date do you want to see (DD-MONTH-YYYY)?");
					String date = sc.nextLine();
					d.viewPersonalSchedule(date);
					break;
				case 4:
					System.out.println("Set Availability for Appointments");
					System.out.println("Enter date: ");
					String dateInput = sc.nextLine();
					System.out.println("Enter Time: ");
					String timeInput = sc.nextLine();
					am.setAvail(d.getName(),dateInput, timeInput, doctorAvailability);
					//System.out.println("Set Availability for Appointments");
					break;
				case 5:
					System.out.println("Accept or Decline Appointment Requests");
					System.out.println("Enter Appointment ID: ");
					int aptID = sc.nextInt();
					System.out.println("Do you want to accept or decline this appointment? (Y/N)");
					String opt = sc.next();
					if (opt.equalsIgnoreCase("Y")) {
						am.acceptAppointment(aptID);
					} else if (opt.equalsIgnoreCase("N")) {
						am.declineAppointment(aptID);
					}
					break;
				case 6:
					System.out.println("View Upcoming Appointments");
					String doc = "";
					for (Map<String, String> data : doctorList) {

						// add pre-defined data to list

						String dId = data.get("Staff ID");

						if (dId.equals(did)) {
							doc = data.get("Name");
						}
					}

					am.viewUpcomingAppointments(doc);
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

		public static void PharmacistOption(String phid, pharmacist ph) {

		Scanner sc = new Scanner(System.in);
		int choice = 0;

		MedicalRecordManagement mr = new MedicalRecordManagement();
		AppointmentOutcomeRecord outcome = new AppointmentOutcomeRecord();

		do {
    			PharmacistMenu();

			System.out.println("What do you want to do?");
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
				case 1:
					viewAppointmentOutcomeRecords();
					break;
				case 2:
		            System.out.println("Enter Appointment ID:");
                    int apptId = sc.nextInt();
                    sc.nextLine(); 
                    System.out.println("Enter new status:");
                    String status = sc.nextLine();
                    updatePrescriptionStatus(apptId, status);
                    break;
				case 3:
					viewMedicineInventory();
					break;
				case 4:
					System.out.println("Enter Medication ID:");
                    String medId = sc.nextLine();
                    System.out.println("Enter quantity needed:");
                    int qty = sc.nextInt();
                    submitReplenishmentRequest(medId, qty);
                    break;
                default: 
                    System.out.println("Please choose from 1-4 thank you!!");
                    break;
		    } 
    	}while (choice != 4);

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
	
	public static void PharmacistMenu(){ 
	    System.out.println("PharmacistMenu:");
        	System.out.println("1. View Appointment Outcomes");
        	System.out.println("2. Update Prescription Status");
        	System.out.println("3. View Medication Inventory");
        	System.out.println("4. Submit Replenishment Request");
        	System.out.println("5. logout");
	}
}
