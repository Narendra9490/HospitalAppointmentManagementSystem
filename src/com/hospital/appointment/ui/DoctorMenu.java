package com.hospital.appointment.ui;

import com.hospital.appointment.model.Appointment;
import com.hospital.appointment.model.Doctor;
import com.hospital.appointment.model.Specialization;
import com.hospital.appointment.service.AppointmentService;
import com.hospital.appointment.service.DoctorService;
import com.hospital.appointment.util.DisplayUtil;
import com.hospital.appointment.util.InputUtil;
import com.hospital.appointment.util.ValidationUtil;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class DoctorMenu {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm");

    public DoctorMenu(
            DoctorService doctorService,
            AppointmentService appointmentService) {

        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    public void start() {

        boolean running = true;

        while (running) {

            DisplayUtil.printSectionHeader(
                    "DOCTOR MANAGEMENT"
            );

            System.out.println("1. Add Doctor");
            System.out.println("2. View Doctor");
            System.out.println("3. View All Doctors");
            System.out.println("4. Search Doctor by Specialization");
            System.out.println("5. Update Doctor");
            System.out.println("6. Delete Doctor");
            System.out.println("7. View Doctor Schedule");
            System.out.println("8. Back");

            DisplayUtil.printSeparator();

            int choice = InputUtil.readInt(
                    "Enter your choice: "
            );

            try {

                switch (choice) {

                    case 1:
                        addDoctor();
                        break;

                    case 2:
                        viewDoctor();
                        break;

                    case 3:
                        viewAllDoctors();
                        break;

                    case 4:
                        searchBySpecialization();
                        break;

                    case 5:
                        updateDoctor();
                        break;

                    case 6:
                        deleteDoctor();
                        break;

                    case 7:
                        viewDoctorSchedule();
                        break;

                    case 8:
                        running = false;
                        break;

                    default:
                        DisplayUtil.printError(
                                "Invalid choice. Please select 1-8."
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

    // =========================================================
    // ADD DOCTOR
    // =========================================================

    private void addDoctor() {

        DisplayUtil.printSectionHeader(
                "ADD NEW DOCTOR"
        );

        String name;

        while (true) {

            name = InputUtil.readString(
                    "Enter doctor name: "
            );

            if (ValidationUtil.isValidName(name)) {
                break;
            }

            DisplayUtil.printError(
                    "Doctor name must contain at least 2 characters."
            );
        }

        Specialization specialization =
                selectSpecialization();

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

        double consultationFee;

        while (true) {

            consultationFee = InputUtil.readDouble(
                    "Enter consultation fee: "
            );

            if (ValidationUtil.isValidFee(
                    consultationFee)) {

                break;
            }

            DisplayUtil.printError(
                    "Consultation fee cannot be negative."
            );
        }

        Doctor doctor =
                doctorService.addDoctor(
                        name,
                        specialization,
                        phone,
                        email,
                        consultationFee
                );

        DisplayUtil.printSuccess(
                "Doctor added successfully."
        );

        DisplayUtil.printSeparator();

        System.out.println(
                "Doctor ID          : "
                        + doctor.getDoctorId()
        );

        System.out.println(
                "Name               : "
                        + doctor.getName()
        );

        System.out.println(
                "Specialization     : "
                        + doctor.getSpecialization()
        );

        System.out.println(
                "Phone              : "
                        + doctor.getPhone()
        );

        System.out.println(
                "Email              : "
                        + doctor.getEmail()
        );

        System.out.println(
                "Consultation Fee   : ₹"
                        + doctor.getConsultationFee()
        );

        DisplayUtil.printSeparator();
    }

    // =========================================================
    // SELECT SPECIALIZATION
    // =========================================================

    private Specialization selectSpecialization() {

        while (true) {

            DisplayUtil.printSectionHeader(
                    "SELECT SPECIALIZATION"
            );

            Specialization[] specializations =
                    Specialization.values();

            for (int i = 0;
                 i < specializations.length;
                 i++) {

                System.out.printf(
                        "%d. %s%n",
                        i + 1,
                        formatSpecialization(
                                specializations[i]
                        )
                );
            }

            DisplayUtil.printSeparator();

            int choice = InputUtil.readInt(
                    "Select specialization: "
            );

            if (choice >= 1
                    && choice <= specializations.length) {

                return specializations[choice - 1];
            }

            DisplayUtil.printError(
                    "Invalid specialization choice."
            );
        }
    }

    // =========================================================
    // VIEW DOCTOR
    // =========================================================

    private void viewDoctor() {

        DisplayUtil.printSectionHeader(
                "VIEW DOCTOR"
        );

        int doctorId = InputUtil.readInt(
                "Enter doctor ID: "
        );

        Doctor doctor =
                doctorService.getDoctor(doctorId);

        printDoctorTableHeader();

        printDoctorRow(doctor);

        DisplayUtil.printSeparator();
    }

    // =========================================================
    // VIEW ALL DOCTORS
    // =========================================================

    private void viewAllDoctors() {

        DisplayUtil.printSectionHeader(
                "ALL DOCTORS"
        );

        Map<Integer, Doctor> doctors =
                doctorService.getAllDoctors();

        if (doctors.isEmpty()) {

            DisplayUtil.printInfo(
                    "No doctors are registered."
            );

            return;
        }

        printDoctorTableHeader();

        for (Doctor doctor : doctors.values()) {

            printDoctorRow(doctor);
        }

        DisplayUtil.printSeparator();
    }

    // =========================================================
    // SEARCH BY SPECIALIZATION
    // =========================================================

    private void searchBySpecialization() {

        DisplayUtil.printSectionHeader(
                "SEARCH DOCTOR"
        );

        Specialization specialization =
                selectSpecialization();

        Map<Integer, Doctor> doctors =
                doctorService.getAllDoctors();

        boolean found = false;

        printDoctorTableHeader();

        for (Doctor doctor : doctors.values()) {

            if (doctor.getSpecialization()
                    == specialization) {

                printDoctorRow(doctor);

                found = true;
            }
        }

        DisplayUtil.printSeparator();

        if (!found) {

            DisplayUtil.printInfo(
                    "No doctors found for specialization: "
                            + formatSpecialization(specialization)
            );
        }
    }

    // =========================================================
    // UPDATE DOCTOR
    // =========================================================

    private void updateDoctor() {

        DisplayUtil.printSectionHeader(
                "UPDATE DOCTOR"
        );

        int doctorId = InputUtil.readInt(
                "Enter doctor ID: "
        );

        Doctor doctor =
                doctorService.getDoctor(doctorId);

        System.out.println();
        System.out.println(
                "Current doctor details:"
        );

        printDoctorTableHeader();

        printDoctorRow(doctor);

        DisplayUtil.printSeparator();

        String name;

        while (true) {

            name = InputUtil.readString(
                    "Enter new doctor name: "
            );

            if (ValidationUtil.isValidName(name)) {
                break;
            }

            DisplayUtil.printError(
                    "Invalid doctor name."
            );
        }

        Specialization specialization =
                selectSpecialization();

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

        double fee;

        while (true) {

            fee = InputUtil.readDouble(
                    "Enter new consultation fee: "
            );

            if (ValidationUtil.isValidFee(fee)) {
                break;
            }

            DisplayUtil.printError(
                    "Consultation fee cannot be negative."
            );
        }

        doctor.setName(name);
        doctor.setSpecialization(specialization);
        doctor.setPhone(phone);
        doctor.setEmail(email);
        doctor.setConsultationFee(fee);

        DisplayUtil.printSuccess(
                "Doctor details updated successfully."
        );
    }

    // =========================================================
    // DELETE DOCTOR
    // =========================================================

    private void deleteDoctor() {

        DisplayUtil.printSectionHeader(
                "DELETE DOCTOR"
        );

        int doctorId = InputUtil.readInt(
                "Enter doctor ID: "
        );

        Doctor doctor =
                doctorService.getDoctor(doctorId);

        printDoctorTableHeader();

        printDoctorRow(doctor);

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

        doctorService.deleteDoctor(doctorId);

        DisplayUtil.printSuccess(
                "Doctor deleted successfully."
        );
    }

    // =========================================================
    // DOCTOR SCHEDULE
    // =========================================================

    private void viewDoctorSchedule() {

        DisplayUtil.printSectionHeader(
                "DOCTOR SCHEDULE"
        );

        int doctorId = InputUtil.readInt(
                "Enter doctor ID: "
        );

        Doctor doctor =
                doctorService.getDoctor(doctorId);

        List<Appointment> appointments =
                appointmentService.getDoctorSchedule(
                        doctorId
                );

        System.out.println();
        System.out.println(
                "Doctor          : " + doctor.getName()
        );

        System.out.println(
                "Specialization  : "
                        + formatSpecialization(
                                doctor.getSpecialization()
                        )
        );

        System.out.println();

        if (appointments.isEmpty()) {

            DisplayUtil.printInfo(
                    "No appointments scheduled for this doctor."
            );

            return;
        }

        System.out.println(
                "------------------------------------------------------------------------------------------------"
        );

        System.out.printf(
                "| %-10s | %-10s | %-14s | %-12s | %-12s |%n",
                "Appt ID",
                "Patient ID",
                "Date",
                "Time",
                "Status"
        );

        System.out.println(
                "------------------------------------------------------------------------------------------------"
        );

        for (Appointment appointment : appointments) {

            System.out.printf(
                    "| %-10d | %-10d | %-14s | %-12s | %-12s |%n",
                    appointment.getAppointmentId(),
                    appointment.getPatientId(),
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

    // =========================================================
    // TABLE METHODS
    // =========================================================

    private void printDoctorTableHeader() {

        DisplayUtil.printSeparator();

        System.out.printf(
                "| %-6s | %-20s | %-20s | %-12s | %-28s | %-10s |%n",
                "ID",
                "Name",
                "Specialization",
                "Phone",
                "Email",
                "Fee"
        );

        DisplayUtil.printSeparator();
    }

    private void printDoctorRow(Doctor doctor) {

        System.out.printf(
                "| %-6d | %-20s | %-20s | %-12s | %-28s | %-10.2f |%n",
                doctor.getDoctorId(),
                doctor.getName(),
                formatSpecialization(
                        doctor.getSpecialization()
                ),
                doctor.getPhone(),
                doctor.getEmail(),
                doctor.getConsultationFee()
        );
    }

    // =========================================================
    // FORMAT SPECIALIZATION
    // =========================================================

    private String formatSpecialization(
            Specialization specialization) {

        String value =
                specialization.name()
                        .toLowerCase()
                        .replace("_", " ");

        String[] words = value.split(" ");

        StringBuilder result =
                new StringBuilder();

        for (String word : words) {

            result.append(
                    Character.toUpperCase(
                            word.charAt(0)
                    )
            );

            result.append(
                    word.substring(1)
            );

            result.append(" ");
        }

        return result.toString().trim();
    }
}