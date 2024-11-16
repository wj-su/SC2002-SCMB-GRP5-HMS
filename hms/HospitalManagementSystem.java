import admin.Administrator;
import admin.InventoryManagement;
import admin.StaffManagement;
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
import pharmacist.Medication;
import pharmacist.Pharmacist;
import pharmacist.Prescription;
import user.User;

public class HospitalManagementSystem {

	static List<Map<String, String>> patientList = new ArrayList<>();
	static List<Map<String, String>> staffList = new ArrayList<>();
	static List<Map<String, String>> doctorList = new ArrayList<>();
	static List<Map<String, String>> pharmacistList = new ArrayList<>();
	static List<Map<String, String>> medicineList = new ArrayList<>();
	static Map<String, Map<String, List<String>>> doctorAvailability = new HashMap<>();
	static List<Map<String, String>> replenishmentRequests = new ArrayList<>();
	static Map<String, String> loginCredentials = new HashMap<>();

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		String hospitalId;

		createPatientList();
		createDoctorList();
		createDoctorAvailList();
		createPharmacistList();
		createStaffList();
		createMedicineList();

		do {

			System.out.println("Enter Hospital ID (Enter -1 to quit): ");
			hospitalId = sc.next();

			if (!hospitalId.equals("-1")) {
				System.out.println("Enter Password: ");
				String password = sc.next();
				User user = authenticate(hospitalId, password);
				if (user != null) {
					System.out.println("Login successful as " + user.getRole());
					if (user instanceof Patient) {
						Patient p = (Patient) user;
						if (user.isFirstLogin()) {
							System.out.println("Please change your password since this is your first time logging in:");
							String newPw = sc.next();
							user.changePassword(newPw, patientList);
							loginCredentials.put(hospitalId, newPw);
							user.setFirstLogin(false);
						} else {
							System.out.println("Do you want to change your password? (y/n)");
							String opt = sc.next();

							if (opt.equalsIgnoreCase("y")) {
								System.out.println("Please enter your new password:");
								String newPw = sc.next();
								user.changePassword(newPw, patientList);
								loginCredentials.put(hospitalId, newPw);
							}
						}
						PatientOption(hospitalId, p);
						System.out.println("Patient authenticated: " + p.getName());
					} else if (user instanceof Doctor) {
						Doctor d = (Doctor) user;
						if (user.isFirstLogin()) {
							System.out.println("Please change your password since this is your first time logging in:");
							String newPw = sc.next();
							user.changePassword(newPw, staffList);
							loginCredentials.put(hospitalId, newPw);
							user.setFirstLogin(false);
						} else {
							System.out.println("Do you want to change your password? (y/n)");
							String opt = sc.next();

							if (opt.equalsIgnoreCase("y")) {
								System.out.println("Please enter your new password:");
								String newPw = sc.next();
								user.changePassword(newPw, staffList);
								loginCredentials.put(hospitalId, newPw);
							}
						}
						DoctorOption(hospitalId, d);
						System.out.println("Doctor authenticated: " + d.getName());
					} else if (user instanceof Pharmacist) {
						Pharmacist pm = (Pharmacist) user;
						if (user.isFirstLogin()) {
							System.out.println("Please change your password since this is your first time logging in:");
							String newPw = sc.next();
							user.changePassword(newPw, staffList);
							loginCredentials.put(hospitalId, newPw);
							user.setFirstLogin(false);
						} else {
							System.out.println("Do you want to change your password? (y/n)");
							String opt = sc.next();

							if (opt.equalsIgnoreCase("y")) {
								System.out.println("Please enter your new password:");
								String newPw = sc.next();
								user.changePassword(newPw, staffList);
								loginCredentials.put(hospitalId, newPw);
							}
						}
						PharmacistOption(hospitalId, pm);
						System.out.println("Pharmacist authenticated: " + pm.getName());
					} else if (user instanceof Administrator) {
						Administrator ad = (Administrator) user;
						if (user.isFirstLogin()) {
							System.out.println("Please change your password since this is your first time logging in:");
							String newPw = sc.next();
							user.changePassword(newPw, staffList);
							loginCredentials.put(hospitalId, newPw);
							user.setFirstLogin(false);
						} else {
							System.out.println("Do you want to change your password? (y/n)");
							String opt = sc.next();

							if (opt.equalsIgnoreCase("y")) {
								System.out.println("Please enter your new password:");
								String newPw = sc.next();
								user.changePassword(newPw, staffList);
								loginCredentials.put(hospitalId, newPw);
							}
						}
						AdministratorOption(hospitalId, ad);
						System.out.println("Administrator authenticated: " + ad.getName());
					}
				} else {
					System.out.println("Login failed. Invalid credentials.");
				}

			} else {
				System.out.println("You have exited the program.");
				break;
			}
		} while (!hospitalId.equals("-1"));
	}

	public static User authenticate(String hospitalId, String password) {
		for (Map<String, String> data : patientList) {
			String storedUserId = data.get("Patient ID");
			String storedPassword = data.getOrDefault("Password", "password"); // Default to "password" if not present

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

			Patient patient = new Patient(
					storedUserId,
					data.get("Name"),
					data.get("Date of Birth"),
					pContactInfo,
					data.get("Gender"),
					data.get("Blood Type"),
					pPastDiagnoses,
					pPastTreatments);

			if (storedUserId.equals(hospitalId) && storedPassword.equals(password)) {
				return patient;
			}
		}

		for (Map<String, String> data : staffList) {
			String storedUserId = data.get("Staff ID");
			String storedPassword = data.getOrDefault("Password", "password"); // Default to "password" if not present

			String currentRole = data.get("Role");
			if (storedUserId.equals(hospitalId) && storedPassword.equals(password)) {
				switch (currentRole) {
					case "Doctor":
						return new Doctor(storedUserId, data.get("Name"), data.get("Age"), data.get("Gender"));
					case "Pharmacist":
						return new Pharmacist(storedUserId, data.get("Name"), data.get("Age"), data.get("Gender"));
					case "Administrator":
						return new Administrator(storedUserId, data.get("Name"), data.get("Age"), data.get("Gender"));
				}
			}
		}

		return null; // Return null if authentication fails
	}

	// public static User authenticate(String hospitalId, String password) {
	// for (Map<String, String> data : patientList) {
	// String storedUserId = data.get("Patient ID");
	// String storedPassword = data.get("Password") != null ? data.get("Password") :
	// "password"; // Default password

	// List<String> pContactInfo = new ArrayList<>();
	// if (data.get("Contact Information") != null) {
	// String contactInfo = data.get("Contact Information");
	// if (contactInfo.equals("NIL")) {
	// pContactInfo.add("NIL"); // Add "NIL" if the value is exactly "NIL"
	// } else {
	// String[] contactDetails = contactInfo.split("\\|");
	// for (String contact : contactDetails) {
	// pContactInfo.add(contact.trim());
	// }
	// }
	// }

	// List<String> pPastTreatments = new ArrayList<>();
	// if (data.get("Past Treatment") != null) {
	// String pastTreatments = data.get("Past Treatment");
	// if (pastTreatments.equals("NIL")) {
	// pPastTreatments.add("NIL"); // Add "NIL" if the value is exactly "NIL"
	// } else {
	// String[] treatments = pastTreatments.split("\\|");
	// for (String treatment : treatments) {
	// pPastTreatments.add(treatment.trim());
	// }
	// }
	// }

	// List<String> pPastDiagnoses = new ArrayList<>();
	// if (data.get("Past Diagnoses") != null) {
	// String pastDiagnoses = data.get("Past Diagnoses");
	// if (pastDiagnoses.equals("NIL")) {
	// pPastDiagnoses.add("NIL"); // Add "NIL" if the value is exactly "NIL"
	// } else {
	// String[] diagnoses = pastDiagnoses.split("\\|");
	// for (String diagnosis : diagnoses) {
	// pPastDiagnoses.add(diagnosis.trim());
	// }
	// }
	// }

	// //String storedPassword = Patient.passwords.getOrDefault(storedUserId,
	// "password");

	// Patient patient = new Patient(
	// storedUserId,
	// data.get("Name"),
	// data.get("Date of Birth"),
	// pContactInfo,
	// data.get("Gender"),
	// data.get("Blood Type"),
	// pPastDiagnoses,
	// pPastTreatments);

	// if (!Patient.loginStatus.containsKey(storedUserId)) {
	// patient.setFirstLogin(true);
	// }

	// if (storedUserId.equals(hospitalId) && patient.login(password)) {
	// return patient;
	// }
	// }

	// for (Map<String, String> data : staffList) {
	// String storedUserId = data.get("Staff ID");
	// String currentRole = data.get("Role");

	// Doctor doc = new Doctor(hospitalId, data.get("Name"), data.get("Age"),
	// data.get("Gender")); // Mock doctor
	// // object

	// Pharmacist pharmacist = new Pharmacist(hospitalId, data.get("Name"),
	// data.get("Age"), data.get("Gender")); // Mock
	// // doctor
	// // object

	// Administrator administrator = new Administrator(hospitalId, data.get("Name"),
	// data.get("Age"),
	// data.get("Gender")); // Mock doctor object

	// if (storedUserId.equals(hospitalId)) {
	// if (currentRole.equals("Doctor") && doc.login(password)) {
	// return doc;
	// } else if (currentRole.equals("Pharmacist") && pharmacist.login(password)) {
	// return pharmacist;
	// } else if (currentRole.equals("Administrator") &&
	// administrator.login(password)) {
	// return administrator;
	// }
	// }
	// }

	// // Return null if not found
	// return null;
	// }

	public static void createStaffList() {
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

				for (int i = 0; i < headers.length; i++) {
					// put header as key and cell data as value
					if (i < columns.length) {
						dataMap.put(headers[i], columns[i]);
					} else {
						dataMap.put(headers[i], ""); // empty string if column is missing
					}
				}

				staffList.add(dataMap);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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

				Map<String, String> doctorGender = new HashMap<>();

				if (isDoctor) {
					String doctorName = dataMap.get("Name");
					String availability = dataMap.get("Availability Dates");
					String gender = dataMap.get("Gender");

					doctorGender.put(doctorName, gender);

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
						// doctorAvailability.put(doctorName, dateMap);
						Map<String, Object> doctorDetails = new HashMap<>();
						doctorDetails.put("Gender", gender);
						doctorDetails.put("Availability", dateMap);

						doctorAvailability.put(doctorName, doctorDetails);
					}
				
			}
		}

	}catch(

	IOException e)
	{
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
		String csvFile = "hms\\Medicine_List.csv"; // Path to your CSV file
		String line;
		String csvSplitBy = ",";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			// Read the first line as the header
			String headerLine = br.readLine();
			if (headerLine == null) {
				System.out.println("CSV file is empty");
				return;
			}

			// Remove BOM if present and split headers
			headerLine = headerLine.replace("\uFEFF", ""); // Remove BOM
			String[] headers = headerLine.split(csvSplitBy);

			// Trim headers to avoid leading/trailing spaces
			for (int i = 0; i < headers.length; i++) {
				headers[i] = headers[i].trim();
			}

			while ((line = br.readLine()) != null) {
				String[] columns = line.split(csvSplitBy);

				// Create a map for the row data
				Map<String, String> dataMap = new HashMap<>();

				for (int i = 0; i < headers.length; i++) {
					// Put header as key and cell data as value
					if (i < columns.length) {
						dataMap.put(headers[i], columns[i].trim()); // Trim column values
					} else {
						dataMap.put(headers[i], ""); // Empty string if column is missing
					}
				}

				// Add the map to the medicine list
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

	public static void PatientOption(String pid, Patient p) {

		int choice = 0;
		Scanner sc = new Scanner(System.in);

		Appointment defaultApts = new Appointment();
		ExportData exportData = new ExportData();
		AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord();

		do {
			p.displayMenu();

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
					System.out.println("Exporting your Records...");
					exportData.patientViewOrExportRecords(pid, AppointmentOutcomeRecord.getAllOutcomeRecords(), true);
					break;

				case 10:
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
		ExportData exportData = new ExportData();

		do {
			d.displayMenu();

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
					am.setAvail(d.getName(), dateInput, timeInput, doctorAvailability);
					// System.out.println("Set Availability for Appointments");
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
					String mq = "";
					if (needMed.toLowerCase().equals("yes")) {
						System.out.println("Enter Medication Name: ");
						mn = sc.nextLine();
						System.out.println("Enter Medication Status: ");
						ms = sc.nextLine();
						System.out.println("Enter Medication Quantity: ");
						mq = sc.nextLine();
					}
					System.out.println("Enter Consultation Notes: ");
					String consultNotes = sc.nextLine();

					outcome.addOutcomeRecord(pid, aptid, st, mn, ms, mq, consultNotes);

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

	public static void PharmacistOption(String phid, Pharmacist ph) {

		Scanner sc = new Scanner(System.in);
		int choice = 0;

		MedicalRecordManagement mr = new MedicalRecordManagement();
		AppointmentOutcomeRecord outcome = new AppointmentOutcomeRecord();
		Medication md = new Medication();
		Prescription pcp = new Prescription();

		do {
			ph.displayMenu();

			System.out.println("What do you want to do?");
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
				case 1:
					outcome.viewAllOutcomeRecords();
					break;
				case 2:
					System.out.println("Enter Patient ID:");
					String ptId = sc.nextLine();
					System.out.println("Enter Appointment ID:");
					String apptId = sc.nextLine();
					System.out.println("Which prescription are you trying to update?");
					String presc = sc.nextLine();
					System.out.println("Enter new status (Pending/Dispensed):");
					String status = sc.nextLine();
					ph.updatePrescriptionStatus(ptId, apptId, presc, status, outcome.getAllOutcomeRecords());
					break;
				case 3:
					md.viewMedicationInventory(medicineList);
					break;
				case 4:
					ph.submitReplenishmentRequest(medicineList, replenishmentRequests);
					break;
				case 5:
					System.out.println("You have logged out!");
					return;
				default:
					System.out.println("Please choose from 1-5 thank you!!");
					break;
			}
		} while (choice != 4);

	}

	public static void AdministratorOption(String aid, Administrator ad) {
		StaffManagement sm = new StaffManagement();
		Appointment ap = new Appointment();
		InventoryManagement im = new InventoryManagement();

		Scanner sc = new Scanner(System.in);
		int choice;
		do {
			ad.displayMenu();

			System.out.println("What do you want to do?");
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
				case 1:
					System.out.println("Do you want to view or manage the staffs? (V/M)");
					String adOpt = sc.nextLine();
					if (adOpt.equalsIgnoreCase("V")) {
						System.out.println("Enter role to filter (or press Enter to skip): ");
						String role = sc.nextLine().trim();
						role = role.isEmpty() ? null : role;

						System.out.println("Enter gender to filter (or press Enter to skip): ");
						String gender = sc.nextLine().trim();
						gender = gender.isEmpty() ? null : gender;

						System.out.println("Enter minimum age to filter: ");
						int minAge = sc.nextInt();

						System.out.println("Enter maximum age to filter: ");
						int maxAge = sc.nextInt();

						sm.viewStaff(staffList, role, gender, minAge, maxAge);
					} else if (adOpt.equalsIgnoreCase("M")) {
						System.out.println("Do you want to Add(a), Update(u) OR remove (r) staffs?");
						String manageOpt = sc.nextLine();
						if (manageOpt.equalsIgnoreCase("a")) {
							System.out.println("Enter a new staff ID: ");
							String newId = sc.nextLine();
							System.out.println("Enter Staff Name:");
							String newName = sc.nextLine();
							System.out.println("Enter Staff Role:");
							String newRole = sc.nextLine();
							System.out.println("Enter Staff Gender:");
							String newGender = sc.nextLine();
							System.out.println("Enter Staff Age:");
							String newAge = sc.nextLine();
							sm.addStaff(staffList, newId, newName, newRole, newGender, newAge);
						} else if (manageOpt.equalsIgnoreCase("u")) {
							System.out.println("Enter the Staff ID you want to update:");
							String updatedId = sc.nextLine();
							System.out.println("Do you want to update everything or only a specific field? (E/S)");
							String updateChoice = sc.nextLine();
							if (updateChoice.equalsIgnoreCase("E")) {
								System.out.println("Enter Staff Name:");
								String upName = sc.nextLine();
								System.out.println("Enter Staff Role:");
								String upRole = sc.nextLine();
								System.out.println("Enter Staff Gender:");
								String upGender = sc.nextLine();
								System.out.println("Enter Staff Age:");
								String upAge = sc.nextLine();
								sm.updateStaff(staffList, updatedId, upName, upRole, upGender, upAge, updateChoice);
							} else {
								String updatedName = "";
								String updatedRole = "";
								String updatedGender = "";
								String updatedAge = "";
								System.out.println("Which field you want to update? (Name/Role/Gender/Age)");
								String fieldChoice = sc.nextLine();
								if (fieldChoice.equalsIgnoreCase("name")) {
									System.out.println("Enter the new name:");
									updatedName = sc.nextLine();
									updateChoice = "Name";
								} else if (fieldChoice.equalsIgnoreCase("role")) {
									System.out.println("Enter the new role:");
									updatedRole = sc.nextLine();
									updateChoice = "Role";
								} else if (fieldChoice.equalsIgnoreCase("Gender")) {
									System.out.println("Enter the new gender:");
									updatedGender = sc.nextLine();
									updateChoice = "Gender";
								} else if (fieldChoice.equalsIgnoreCase("age")) {
									System.out.println("Enter the new age:");
									updatedAge = sc.nextLine();
									updateChoice = "Age";
								}
								sm.updateStaff(staffList, updatedId, updatedName, updatedRole, updatedGender,
										updatedAge, updateChoice);
							}
						} else if (manageOpt.equalsIgnoreCase("r")) {
							System.out.println("Enter the staff id you want to remove:");
							String removeId = sc.nextLine();
							sm.removeStaff(staffList, removeId);
						}
					}
					break;
				case 2:
					ad.viewAppointmentsAdmin();
					break;
				case 3:
					System.out.println(
							"Do you want to update current stock level or change the low stock level alert? (U/C)");
					String stockOption = sc.nextLine();
					if (stockOption.equalsIgnoreCase("U")) {
						System.out.println("Enter the medicine you want to restock:");
						String medName = sc.nextLine();
						System.out.println("Enter the quantity you want to replenish:");
						String quantity = sc.nextLine();
						im.updateStockLevel(medicineList, medName, quantity);
					} else if (stockOption.equalsIgnoreCase("C")) {
						System.out.println("Enter the medicine you want to change the low stock value:");
						String newVal = sc.nextLine();
						System.out.println("Enter the quantity you want to change to:");
						String newQuant = sc.nextLine();
						im.updateLowStockAlert(medicineList, newVal, newQuant);
					}
					break;
				case 4:
					im.viewReplenishmentRequests(replenishmentRequests);
					System.out.println("Enter medication name to approve:");
					String Appr = sc.nextLine();
					im.approveReplenishmentRequest(medicineList, replenishmentRequests, Appr);

					break;
				case 5:
					System.out.println("You have logged out!");
					return;
				default:
					System.out.println("Please choose from 1-4 thank you!!");
					break;
			}
		} while (choice != 4);

	}
}
