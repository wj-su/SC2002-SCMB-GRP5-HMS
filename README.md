# Hospital Management System (HMS) 

## Overview

This project is a Hospital Management System (HMS) designed for managing various hospital operations efficiently. The system automates key processes such as patient management, appointment scheduling, staff management, and billing. The system aims to improve patient care, streamline administrative tasks, and ensure better management of hospital resources.

## Objective

The HMS project is intended to apply Object-Oriented (OO) concepts learned during the Object-Oriented Design & Programming course (SC2002). This project encourages the modeling, design, and development of a functional application using Java, with a focus on effective team collaboration.

## Features

### User Roles and System Capabilities

#### 1. Common Features for All Users
- **Login and Authentication**: Users can log in using a unique hospital ID and a default password. On the initial login, users are required to change the password.
- **Password Management**: Users can update their password for improved security.

#### 2. Patient Features
- **View Medical Record**: Patients can view their medical record, including Patient ID, Name, Date of Birth, Gender, Contact Information, Blood Type, and Past Diagnoses.
- **Update Personal Information**: Patients can update non-medical information, such as email address and contact number.
- **Appointment Management**: Patients can view available appointment slots, schedule appointments, reschedule appointments, and cancel appointments. Appointment status is updated based on the doctor’s response (e.g., confirmed, canceled).
- **View Appointment Outcome Records**: Patients can view the records of their past appointments, including services provided, prescribed medications, and consultation notes.

#### 3. Doctor Features
- **Medical Record Management**: Doctors can view and update the medical records of patients under their care, including adding diagnoses, prescriptions, and treatment plans.
- **Appointment Management**: Doctors can set availability for appointments, view upcoming appointments, and accept or decline appointment requests.
- **Appointment Outcome Recording**: After each appointment, doctors can record details like the type of service provided, prescribed medications, and consultation notes.

#### 4. Pharmacist Features
- **Prescription Management**: Pharmacists can view the Appointment Outcome Record and update the status of prescriptions (e.g., from pending to dispensed).
- **Inventory Management**: Pharmacists can monitor and manage the inventory of medications, including submitting replenishment requests when stock is low.

#### 5. Administrator Features
- **Staff Management**: Administrators can manage hospital staff by adding, updating, or removing members. Administrators can also filter staff based on role, gender, and other attributes.
- **Appointment Management**: Administrators have real-time access to all scheduled appointments and can view their details.
- **Inventory Management**: Administrators can manage the medication inventory, update stock levels, approve replenishment requests, and adjust low stock alert levels.

## Code Structure

The HMS project is organized into several Java classes:
- **User.java**: Represents a generic user in the system, such as a patient, doctor, or administrator.
- **Administrator.java**: Handles actions performed by administrators, such as staff and inventory management.
- **Doctor.java**: Represents a doctor and defines doctor-specific operations like managing appointments and medical records.
- **Pharmacist.java**: Manages medication inventory and prescription fulfillment.
- **Patient.java**: Represents a patient, allowing access to appointment scheduling and medical records.
- **Appointment.java**: Represents an appointment between a patient and a doctor.
- **AppointmentManagement.java**: Manages all aspects of appointment scheduling, rescheduling, and cancellation.
- **InventoryManagement.java**: Manages medication stock and pharmacy inventory.
- **MedicalRecordManagement.java**: Manages patient medical records, including diagnosis and treatment updates.
- **HospitalManagementSystem.java**: Acts as the main interface to manage the different services provided by the hospital.

## Testing

JUnit test classes have been implemented to ensure the correctness of the system:
- **AdministratorActionsTest.java**: Tests the functionalities available to administrators.
- **DoctorActionsTest.java**: Verifies operations performed by doctors.
- **PharmacistActionsTest.java**: Tests the features used by pharmacists.
- **PatientActionsTest.java**: Validates the actions available to patients.
- **LoginTests.java**: Tests the login system and password management.
- **AdditionalActionsTest.java**: Tests supplementary functionalities of the system.

## Data Management

The system uses CSV files for system initialization only:
- **Medicine_List.csv**: Stores medication information.
- **Patient_List.csv**: Stores patient details.
- **Staff_List.csv**: Stores information about hospital staff members.

Information is not persisted once the application is closed, as the CSV files are used for system initialization purposes only.

## Installation and Setup

1. **Prerequisites**: Java Development Kit (JDK) version 8 or higher.
2. **Clone the Repository**: Clone the project repository.
3. **Compile the Code**: Use a Java IDE or command line to compile the classes.
   ```bash
   javac *.java
   ```
4. **Run the Application**: Execute the main file to start the hospital management system.
   ```bash
   java HospitalManagementSystem
   ```

## Usage

- **Login**: Users log in using their credentials to access role-specific features.
- **Role-Specific Menus**: After login, users are presented with a menu based on their role.
- **Data Updates**: Information is not persisted beyond the current session.

## Development Constraints

- **Command-Line Interface**: The system uses a CLI for interaction.
- **Data Format**: Uses CSV files for system initialization (no use of databases, JSON, or XML).
- **No Database Applications**: No database application (e.g., MySQL, MS Access, etc.) is to be used.

## Possible Enhancements and Features

- **Database Integration**: Use a relational database to improve scalability and data integrity.
- **Graphical User Interface (GUI)**: Develop a GUI for easier interaction.
- **Enhanced Security**: Implement encryption for user credentials and sensitive information.
- **Cloud Integration**: Add cloud capabilities to allow remote access for authorized users.
- **Additional Features**: Explore features such as billing management, patient notifications, and detailed reporting.


## Contributors

- [@DanK2062](https://github.com/DanK2062)
- [@dayuhnah](https://github.com/dayuhnah)
- [@izysha](https://github.com/izysha)
- [@ymkatie](https://github.com/ymkatie)
- [@wj-su](https://github.com/wj-su)

