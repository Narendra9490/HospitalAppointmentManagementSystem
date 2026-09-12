package com.hospital.appointment.ui;

import com.hospital.appointment.model.Appointment;
import com.hospital.appointment.model.Patient;
import com.hospital.appointment.service.AppointmentService;
import com.hospital.appointment.service.PatientService;
import com.hospital.appointment.util.DisplayUtil;
import com.hospital.appointment.util.InputUtil;
import com.hospital.appointment.util.ValidationUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class PatientMenu {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm");

    public PatientMenu(
            PatientService patientService,
            AppointmentService appointmentService) {

        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    public void start() {

        boolean running = true;

        while (running) {

            DisplayUtil.printSectionHeader(
                    "PATIENT MANAGEMENT"
            );

            System.out.println("1. Register Patient");
            System.out.println("2. View Patient");
            System.out.println("3. View All Patients");
            System.out.println("4. Update Patient");
            System.out.println("5. Delete Patient");
            System.out.println("6. View Patient History");
            System.out.println("7. Back");

            DisplayUtil.printSeparator();

            int choice = InputUtil.readInt(
                    "Enter your choice: "
            );

            try {

                switch (choice) {

                    case 1:
                        registerPatient();
                        break;

                    case 2:
                        viewPatient();
                        break;

                    case 3:
                        viewAllPatients();
                        break;

                    case 4:
                        updatePatient();
                        break;

                    case 5:
                        deletePatient();
                        break;

                    case 6:
                        viewPatientHistory();
                        break;

                    case 7:
                        running = false;
                        break;

                    default:
                        DisplayUtil.printError(
                                "Invalid choice. Please select 1-7."
                        );
                }

            } catch (RuntimeException e) {

                DisplayUtil.printError(
                        e.getMessage()
                );
            }

            if (running) {
                InputUtil.pause();
            }
        }
    }

    // ---------------------------------------------------------
    // REGISTER PATIENT
    // ---------------------------------------------------------

    private void registerPatient() {

        DisplayUtil.printSectionHeader(
                "REGISTER NEW PATIENT"
        );

        String name;

        while (true) {

            name = InputUtil.readString(
                    "Enter patient name: "
            );

            if (ValidationUtil.isValidName(name)) {
                break;
            }

            DisplayUtil.printError(
                    "Name must contain at least 2 characters."
            );
        }

        int age;

        while (true) {

            age = InputUtil.readInt(
                    "Enter age: "
            );

            if (ValidationUtil.isValidAge(age)) {
                break;
            }

            DisplayUtil.printError(
                    "Age must be between 1 and 120."
            );
        }

        String gender;

        while (true) {

            gender = InputUtil.readString(
                    "Enter gender (Male/Female/Other): "
            );

            if (gender.equalsIgnoreCase("Male")
                    || gender.equalsIgnoreCase("Female")
                    || gender.equalsIgnoreCase("Other")) {

                gender =
                        capitalizeFirstLetter(gender);

                break;
            }

            DisplayUtil.printError(
                    "Please enter Male, Female or Other."
            );
        }

        String phone;

        while (true) {

            phone = InputUtil.readString(
                    "Enter phone number: "
            );

            if (ValidationUtil.isValidPhone(phone)) {
                break;
            }

            DisplayUtil.printError(
                    "Enter a valid 10-digit Indian mobile number."
            );
        }

        String email;

        while (true) {

            email = InputUtil.readString(
                    "Enter email: "
            );

            if (ValidationUtil.isValidEmail(email)) {
                break;
            }

            DisplayUtil.printError(
                    "Enter a valid email address."
            );
        }

        Patient patient =
                patientService.registerPatient(
                        name,
                        age,
                        gender,
                        phone,
                        email
                );

        DisplayUtil.printSuccess(
                "Patient registered successfully."
        );

        DisplayUtil.printSeparator();

        System.out.println(
                "Patient ID : " + patient.getPatientId()
        );

        System.out.println(
                "Name       : " + patient.getName()
        );

        System.out.println(
                "Age        : " + patient.getAge()
        );

        System.out.println(
                "Gender     : " + patient.getGender()
        );

        System.out.println(
                "Phone      : " + patient.getPhone()
        );

        System.out.println(
                "Email      : " + patient.getEmail()
        );

        DisplayUtil.printSeparator();
    }

    // ---------------------------------------------------------
    // VIEW PATIENT
    // ---------------------------------------------------------

    private void viewPatient() {

        DisplayUtil.printSectionHeader(
                "VIEW PATIENT"
        );

        int patientId = InputUtil.readInt(
                "Enter patient ID: "
        );

        Patient patient =
                patientService.getPatient(patientId);

        printPatientTableHeader();

        printPatientRow(patient);

        DisplayUtil.printSeparator();
    }

    // ---------------------------------------------------------
    // VIEW ALL PATIENTS
    // ---------------------------------------------------------

    private void viewAllPatients() {

        DisplayUtil.printSectionHeader(
                "ALL REGISTERED PATIENTS"
        );

        Map<Integer, Patient> patients =
                patientService.getAllPatients();

        if (patients.isEmpty()) {

            DisplayUtil.printInfo(
                    "No patients are registered."
            );

            return;
        }

        printPatientTableHeader();

        for (Patient patient : patients.values()) {

            printPatientRow(patient);
        }

        DisplayUtil.printSeparator();
    }

    // ---------------------------------------------------------
    // UPDATE PATIENT
    // ---------------------------------------------------------

    private void updatePatient() {

        DisplayUtil.printSectionHeader(
                "UPDATE PATIENT"
        );

        int patientId = InputUtil.readInt(
                "Enter patient ID: "
        );

        Patient patient =
                patientService.getPatient(patientId);

        System.out.println();
        System.out.println(
                "Current patient details:"
        );

        printPatientTableHeader();

        printPatientRow(patient);

        DisplayUtil.printSeparator();

        String name;

        while (true) {

            name = InputUtil.readString(
                    "Enter new name: "
            );

            if (ValidationUtil.isValidName(name)) {
                break;
            }

            DisplayUtil.printError(
                    "Invalid name."
            );
        }

        int age;

        while (true) {

            age = InputUtil.readInt(
                    "Enter new age: "
            );

            if (ValidationUtil.isValidAge(age)) {
                break;
            }

            DisplayUtil.printError(
                    "Age must be between 1 and 120."
            );
        }

        String gender;

        while (true) {

            gender = InputUtil.readString(
                    "Enter new gender (Male/Female/Other): "
            );

            if (gender.equalsIgnoreCase("Male")
                    || gender.equalsIgnoreCase("Female")
                    || gender.equalsIgnoreCase("Other")) {

                gender =
                        capitalizeFirstLetter(gender);

                break;
            }

            DisplayUtil.printError(
                    "Invalid gender."
            );
        }

        String phone;

        while (true) {

            phone = InputUtil.readString(
                    "Enter new phone number: "
            );

            if (ValidationUtil.isValidPhone(phone)) {
                break;
            }

            DisplayUtil.printError(
                    "Invalid phone number."
            );
        }

        String email;

        while (true) {

            email = InputUtil.readString(
                    "Enter new email: "
            );

            if (ValidationUtil.isValidEmail(email)) {
                break;
            }

            DisplayUtil.printError(
                    "Invalid email."
            );
        }

        patient.setName(name);
        patient.setAge(age);
        patient.setGender(gender);
        patient.setPhone(phone);
        patient.setEmail(email);

        DisplayUtil.printSuccess(
                "Patient details updated successfully."
        );
    }

    // ---------------------------------------------------------
    // DELETE PATIENT
    // ---------------------------------------------------------

    private void deletePatient() {

        DisplayUtil.printSectionHeader(
                "DELETE PATIENT"
        );

        int patientId = InputUtil.readInt(
                "Enter patient ID: "
        );

        Patient patient =
                patientService.getPatient(patientId);

        System.out.println();
        System.out.println(
                "Patient to be deleted:"
        );

        printPatientTableHeader();

        printPatientRow(patient);

        DisplayUtil.printSeparator();

        String confirmation =
                InputUtil.readString(
                        "Are you sure you want to delete? (Y/N): "
                );

        if (!confirmation.equalsIgnoreCase("Y")) {

            DisplayUtil.printInfo(
                    "Delete operation cancelled."
            );

            return;
        }

        patientService.deletePatient(patientId);

        DisplayUtil.printSuccess(
                "Patient deleted successfully."
        );
    }

    // ---------------------------------------------------------
    // PATIENT HISTORY
    // ---------------------------------------------------------

    private void viewPatientHistory() {

        DisplayUtil.printSectionHeader(
                "PATIENT APPOINTMENT HISTORY"
        );

        int patientId = InputUtil.readInt(
                "Enter patient ID: "
        );

        Patient patient =
                patientService.getPatient(patientId);

        List<Appointment> appointments =
                appointmentService.getPatientHistory(
                        patientId
                );

        System.out.println();
        System.out.println(
                "Patient : " + patient.getName()
        );

        System.out.println(
                "Patient ID : " + patient.getPatientId()
        );

        System.out.println();

        if (appointments.isEmpty()) {

            DisplayUtil.printInfo(
                    "No appointment history found."
            );

            return;
        }

        System.out.println(
                "------------------------------------------------------------------------------------------------"
        );

        System.out.printf(
                "| %-8s | %-10s | %-10s | %-12s | %-12s |%n",
                "Appt ID",
                "Doctor ID",
                "Date",
                "Time",
                "Status"
        );

        System.out.println(
                "------------------------------------------------------------------------------------------------"
        );

        for (Appointment appointment : appointments) {

            System.out.printf(
                    "| %-8d | %-10d | %-10s | %-12s | %-12s |%n",
                    appointment.getAppointmentId(),
                    appointment.getDoctorId(),
                    appointment.getAppointmentDate()
                            .format(DATE_FORMATTER),
                    appointment.getAppointmentTime()
                            .format(TIME_FORMATTER),
                    appointment.getStatus()
            );
        }

        System.out.println(
                "------------------------------------------------------------------------------------------------"
        );
    }

    // ---------------------------------------------------------
    // TABLE METHODS
    // ---------------------------------------------------------

    private void printPatientTableHeader() {

        DisplayUtil.printSeparator();

        System.out.printf(
                "| %-6s | %-20s | %-5s | %-8s | %-12s | %-28s |%n",
                "ID",
                "Name",
                "Age",
                "Gender",
                "Phone",
                "Email"
        );

        DisplayUtil.printSeparator();
    }

    private void printPatientRow(Patient patient) {

        System.out.printf(
                "| %-6d | %-20s | %-5d | %-8s | %-12s | %-28s |%n",
                patient.getPatientId(),
                patient.getName(),
                patient.getAge(),
                patient.getGender(),
                patient.getPhone(),
                patient.getEmail()
        );
    }

    // ---------------------------------------------------------
    // HELPER METHOD
    // ---------------------------------------------------------

    private String capitalizeFirstLetter(String value) {

        return value.substring(0, 1).toUpperCase()
                + value.substring(1).toLowerCase();
    }
}