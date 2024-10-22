import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Random;


import patient.Appointment;
import patient.Patient;

public class HospitalManagementSystem {

	public static void main(String[] args) {
		
		File patientFile = new File("Patient_List.xlsx");
		
		// should have login stuff here first, then can access menu

		int choice = 0;
		Scanner sc = new Scanner(System.in);

		List<String> pd = Arrays.asList("meet me", "at the", "apt.");
		List<String> pt = Arrays.asList("hold on", "im on my way", "yeah yeah");

		Patient p = new Patient(1, "Shaqilah", "21/10/2024", "Female", "12345678", "survivor@gmail.com", "O", pd, pt);
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
						if (hp.equals("no")) {
							break;
						}
						else {
							Matcher checkHp = phonePattern.matcher(hp);
							if (checkHp.matches()) {
								break;
							}
							else {
								System.out.println("\nInvalid phone number! Please enter a valid phone number!!\n");
							}
						}
					}
					
					while (true) {
						System.out.println("Enter your updated email address (type 'no' if no update): ");
						email = sc.nextLine();
						if (email.equals("no")) {
							break;
						}
						else {
							Matcher checkEmail = emailPattern.matcher(email);
							if (checkEmail.matches()) {
								break;
							}
							else {
								System.out.println("\nInvalid email! Please enter a valid email address!!\n");
							}
						}
					}
					
					p.updatePersonalInformation(hp, email);
					break;
				case 3:
					System.out.println("View Available Appointment Slots");
					System.out.println("Do you want see all of your appointments, or based on the doctor? ");
					System.out.println("Type `all` to view all of your appointments");
					System.out.println("Type `doc` to view appointments with a specific doctor");
					String type = sc.nextLine();
					if (type.toLowerCase().equals("all")) {
						defaultApts.viewAvailableAppt();
					}
					else if (type.toLowerCase().equals("doc")) {
						System.out.println("Enter the doctor's name: ");
						String dn = sc.nextLine();
						defaultApts.viewAvailableApptByDoc(dn);
					}
					else {
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
						Appointment apt = new Appointment(rid.nextInt(1000), doc, date, ts, "Pending");
						defaultApts.scheduleAppointment(apt, doc, date, ts);
					}
					break;
				case 5:
					System.out.println("Rescheduling appointment...");
					System.out.println("Enter the doctor's name you have an appointment with: ");
					String docO = sc.nextLine();
					System.out.println("Enter the old date: ");
					String dateO = sc.nextLine();
					System.out.println("Enter the old timeslot : ");
					String timeO = sc.nextLine();
					// defaultApts.appointmentExists(docO, dateO, timeO, "reschedule");
					if (defaultApts.appointmentExists(docO, dateO, timeO, "reschedule")) {
						System.out.println("Do you want to reschedule to a new doctor, or only change timeslot?");
						System.out.println("Yes or No?");
						String decision = sc.nextLine();
						String newDoc = "";
						if (decision.toLowerCase().equals("yes")) {
							System.out.println("Enter the new doctor's name: ");
							newDoc = sc.nextLine();
						}
						System.out.println("Enter the new date : ");
						String dateR = sc.nextLine();
						System.out.println("Enter the new timeslot : ");
						String timeR = sc.nextLine();
						defaultApts.rescheduleAppointment(docO, newDoc, dateR, timeR, decision);
					}
					
					break;
				case 6:
					System.out.println("Cancelling appointment...");
					System.out.println("Enter the doctor's name you cancelling an appointment with: ");
					String docC = sc.nextLine();
                    System.out.println("Enter the cancelled date : ");
                    String dateC = sc.nextLine();
                    System.out.println("Enter the cancelled timeslot : ");
                    String timeC = sc.nextLine();
					defaultApts.appointmentExists(docC, dateC, timeC, "cancel");

					defaultApts.cancelAppointment(docC, dateC, timeC);
					break;
				case 7:
					System.out.println("Below are your appointments...");
					defaultApts.viewScheduledAppointments();
					break;
				case 9:
					System.out.println("You have logged out!");
					break;
				default:
					System.out.println("Babes I still haven't work on the appointments stuff yet :(");
					break;
			}
		} while(choice != 9);

		sc.close();
		
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
}