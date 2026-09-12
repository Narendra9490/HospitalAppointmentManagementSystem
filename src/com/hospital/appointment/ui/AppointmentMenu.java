package com.hospital.appointment.ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.hospital.appointment.exception.AppointmentNotFoundException;
import com.hospital.appointment.model.Appointment;
import com.hospital.appointment.model.Doctor;
import com.hospital.appointment.model.Patient;
import com.hospital.appointment.service.AppointmentService;
import com.hospital.appointment.service.DoctorService;
import com.hospital.appointment.service.PatientService;
import com.hospital.appointment.util.DisplayUtil;
import com.hospital.appointment.util.InputUtil;

public class AppointmentMenu {

    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm");

    /*
     * Hospital working slots.
     *
     * Later we can move this into DoctorSchedule
     * and allow every doctor to have different timings.
     */
    private static final LocalTime[] AVAILABLE_TIMES = {

            LocalTime.of(9, 0),
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(12, 0),
            LocalTime.of(14, 0),
            LocalTime.of(15, 0),
            LocalTime.of(16, 0),
            LocalTime.of(17, 0)
    };

    public AppointmentMenu(
            AppointmentService appointmentService,
            PatientService patientService,
            DoctorService doctorService) {

        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public void start() {

        boolean running = true;

        while (running) {

            DisplayUtil.printSectionHeader(
                    "APPOINTMENT MANAGEMENT"
            );

            System.out.println("1. Book Appointment");
            System.out.println("2. Cancel Appointment");
            System.out.println("3. Reschedule Appointment");
            System.out.println("4. View Available Slots");
            System.out.println("5. View Appointment");
            System.out.println("6. Doctor Schedule");
            System.out.println("7. Patient History");
            System.out.println("8. Back");

            DisplayUtil.printSeparator();

            int choice = InputUtil.readInt(
                    "Enter your choice: "
            );

            try {

                switch (choice) {

                    case 1:
                        bookAppointment();
                        break;

                    case 2:
                        cancelAppointment();
                        break;

                    case 3:
                        rescheduleAppointment();
                        break;

                    case 4:
                        viewAvailableSlots();
                        break;

                    case 5:
                        viewAppointment();
                        break;

                    case 6:
                        viewDoctorSchedule();
                        break;

                    case 7:
                        viewPatientHistory();
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
    // BOOK APPOINTMENT
    // =========================================================

    private void bookAppointment() {

        DisplayUtil.printSectionHeader(
                "BOOK APPOINTMENT"
        );

        int patientId = InputUtil.readInt(
                "Enter patient ID: "
        );

        Patient patient =
                patientService.getPatient(patientId);

        int doctorId = InputUtil.readInt(
                "Enter doctor ID: "
        );

        Doctor doctor =
                doctorService.getDoctor(doctorId);

        System.out.println();
        System.out.println(
                "Patient : " + patient.getName()
        );

        System.out.println(
                "Doctor  : " + doctor.getName()
        );

        System.out.println(
                "Specialization : "
                        + doctor.getSpecialization()
        );

        System.out.println();

        LocalDate date = InputUtil.readDate(
                "Enter appointment date (DD-MM-YYYY): "
        );

        /*
         * Before asking for time, show available slots.
         */
        showAvailableSlots(
                doctorId,
                date
        );

        LocalTime time = InputUtil.readTime(
                "Enter appointment time (HH:mm): "
        );

        Appointment appointment =
                appointmentService.bookAppointment(
                        patientId,
                        doctorId,
                        date,
                        time
                );

        DisplayUtil.printSuccess(
                "Appointment booked successfully."
        );

        DisplayUtil.printSeparator();

        System.out.println(
                "Appointment ID : "
                        + appointment.getAppointmentId()
        );

        System.out.println(
                "Patient        : "
                        + patient.getName()
        );

        System.out.println(
                "Doctor         : "
                        + doctor.getName()
        );

        System.out.println(
                "Date           : "
                        + date.format(DATE_FORMATTER)
        );

        System.out.println(
                "Time           : "
                        + time.format(TIME_FORMATTER)
        );

        System.out.println(
                "Status         : "
                        + appointment.getStatus()
        );

        DisplayUtil.printSeparator();
    }

    // =========================================================
    // CANCEL APPOINTMENT
    // =========================================================

    private void cancelAppointment() {

        DisplayUtil.printSectionHeader(
                "CANCEL APPOINTMENT"
        );

        int appointmentId = InputUtil.readInt(
                "Enter appointment ID: "
        );

        Appointment appointment =
                appointmentService.getAppointment(
                        appointmentId
                );

        printAppointmentDetails(
                appointment
        );

        DisplayUtil.printSeparator();

        String confirmation =
                InputUtil.readString(
                        "Confirm cancellation? (Y/N): "
                );

        if (!confirmation.equalsIgnoreCase("Y")) {

            DisplayUtil.printInfo(
                    "Cancellation aborted."
            );

            return;
        }

        appointmentService.cancelAppointment(
                appointmentId
        );

        DisplayUtil.printSuccess(
                "Appointment cancelled successfully."
        );
    }

    // =========================================================
    // RESCHEDULE
    // =========================================================

    private void rescheduleAppointment() {

        DisplayUtil.printSectionHeader(
                "RESCHEDULE APPOINTMENT"
        );

        int appointmentId = InputUtil.readInt(
                "Enter appointment ID: "
        );

        Appointment appointment =
                appointmentService.getAppointment(
                        appointmentId
                );

        System.out.println();
        System.out.println(
                "Current appointment:"
        );

        printAppointmentDetails(
                appointment
        );

        DisplayUtil.printSeparator();

        LocalDate newDate = InputUtil.readDate(
                "Enter new date (DD-MM-YYYY): "
        );

        showAvailableSlots(
                appointment.getDoctorId(),
                newDate
        );

        LocalTime newTime = InputUtil.readTime(
                "Enter new time (HH:mm): "
        );

        appointmentService.rescheduleAppointment(
                appointmentId,
                newDate,
                newTime
        );

        DisplayUtil.printSuccess(
                "Appointment rescheduled successfully."
        );
    }

    // =========================================================
    // AVAILABLE SLOTS
    // =========================================================

    private void viewAvailableSlots() {

        DisplayUtil.printSectionHeader(
                "VIEW AVAILABLE SLOTS"
        );

        int doctorId = InputUtil.readInt(
                "Enter doctor ID: "
        );

        Doctor doctor =
                doctorService.getDoctor(
                        doctorId
                );

        LocalDate date = InputUtil.readDate(
                "Enter date (DD-MM-YYYY): "
        );

        System.out.println();

        System.out.println(
                "Doctor : " + doctor.getName()
        );

        System.out.println(
                "Date   : "
                        + date.format(DATE_FORMATTER)
        );

        showAvailableSlots(
                doctorId,
                date
        );
    }

    // =========================================================
    // SLOT ENGINE
    // =========================================================

    private void showAvailableSlots(
            int doctorId,
            LocalDate date) {

        System.out.println();

        DisplayUtil.printSeparator();

        System.out.printf(
                "| %-8s | %-15s | %-15s |%n",
                "Slot",
                "Time",
                "Status"
        );

        DisplayUtil.printSeparator();

        int slotNumber = 1;

        for (LocalTime time : AVAILABLE_TIMES) {

            boolean booked =
                    isSlotBooked(
                            doctorId,
                            date,
                            time
                    );

            String status =
                    booked
                            ? "BOOKED"
                            : "AVAILABLE";

            System.out.printf(
                    "| %-8d | %-15s | %-15s |%n",
                    slotNumber,
                    time.format(TIME_FORMATTER),
                    status
            );

            slotNumber++;
        }

        DisplayUtil.printSeparator();
    }

    private boolean isSlotBooked(
            int doctorId,
            LocalDate date,
            LocalTime time) {

        try {

            appointmentService
                    .getAppointmentBySlot(
                            doctorId,
                            date,
                            time
                    );

            return true;

        } catch (AppointmentNotFoundException e) {

            return false;
        }
    }

    // =========================================================
    // VIEW APPOINTMENT
    // =========================================================

    private void viewAppointment() {

        DisplayUtil.printSectionHeader(
                "VIEW APPOINTMENT"
        );

        int appointmentId = InputUtil.readInt(
                "Enter appointment ID: "
        );

        Appointment appointment =
                appointmentService.getAppointment(
                        appointmentId
                );

        printAppointmentDetails(
                appointment
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
                doctorService.getDoctor(
                        doctorId
                );

        List<Appointment> appointments =
                appointmentService.getDoctorSchedule(
                        doctorId
                );

        System.out.println();
        System.out.println(
                "Doctor : " + doctor.getName()
        );

        System.out.println(
                "Specialization : "
                        + doctor.getSpecialization()
        );

        System.out.println();

        if (appointments.isEmpty()) {

            DisplayUtil.printInfo(
                    "No appointments found."
            );

            return;
        }

        printAppointmentTable(
                appointments
        );
    }

    // =========================================================
    // PATIENT HISTORY
    // =========================================================

    private void viewPatientHistory() {

        DisplayUtil.printSectionHeader(
                "PATIENT HISTORY"
        );

        int patientId = InputUtil.readInt(
                "Enter patient ID: "
        );

        Patient patient =
                patientService.getPatient(
                        patientId
                );

        List<Appointment> appointments =
                appointmentService.getPatientHistory(
                        patientId
                );

        System.out.println();
        System.out.println(
                "Patient : " + patient.getName()
        );

        if (appointments.isEmpty()) {

            DisplayUtil.printInfo(
                    "No appointment history found."
            );

            return;
        }

        printAppointmentTable(
                appointments
        );
    }

    // =========================================================
    // APPOINTMENT DETAILS
    // =========================================================

    private void printAppointmentDetails(
            Appointment appointment) {

        System.out.println();

        System.out.println(
                "Appointment ID : "
                        + appointment.getAppointmentId()
        );

        System.out.println(
                "Patient ID     : "
                        + appointment.getPatientId()
        );

        System.out.println(
                "Doctor ID      : "
                        + appointment.getDoctorId()
        );

        System.out.println(
                "Date           : "
                        + appointment.getAppointmentDate()
                        .format(DATE_FORMATTER)
        );

        System.out.println(
                "Time           : "
                        + appointment.getAppointmentTime()
                        .format(TIME_FORMATTER)
        );

        System.out.println(
                "Status         : "
                        + appointment.getStatus()
        );
    }

    // =========================================================
    // APPOINTMENT TABLE
    // =========================================================

    private void printAppointmentTable(
            List<Appointment> appointments) {

        DisplayUtil.printSeparator();

        System.out.printf(
                "| %-10s | %-10s | %-10s | %-12s | %-12s |%n",
                "Appt ID",
                "Patient ID",
                "Doctor ID",
                "Date",
                "Status"
        );

        DisplayUtil.printSeparator();

        for (Appointment appointment :
                appointments) {

            System.out.printf(
                    "| %-10d | %-10d | %-10d | %-12s | %-12s |%n",
                    appointment.getAppointmentId(),
                    appointment.getPatientId(),
                    appointment.getDoctorId(),
                    appointment.getAppointmentDate()
                            .format(DATE_FORMATTER),
                    appointment.getStatus()
            );
        }

        DisplayUtil.printSeparator();
    }
}