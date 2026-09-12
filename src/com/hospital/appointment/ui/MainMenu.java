package com.hospital.appointment.ui;

import com.hospital.appointment.service.AppointmentService;
import com.hospital.appointment.service.DoctorService;
import com.hospital.appointment.service.PatientService;
import com.hospital.appointment.util.DisplayUtil;
import com.hospital.appointment.util.InputUtil;

public class MainMenu {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    private final PatientMenu patientMenu;
    private final DoctorMenu doctorMenu;
    private final AppointmentMenu appointmentMenu;

    public MainMenu(
            PatientService patientService,
            DoctorService doctorService,
            AppointmentService appointmentService) {

        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;

        this.patientMenu =
                new PatientMenu(patientService, appointmentService);

        this.doctorMenu =
                new DoctorMenu(doctorService, appointmentService);

        this.appointmentMenu =
                new AppointmentMenu(
                        appointmentService,
                        patientService,
                        doctorService
                );
    }

    public void start() {

        boolean running = true;

        while (running) {

            DisplayUtil.printMainHeader();

            System.out.println();
            System.out.println("1. Patient Management");
            System.out.println("2. Doctor Management");
            System.out.println("3. Appointment Management");
            System.out.println("4. Exit");

            DisplayUtil.printSeparator();

            int choice = InputUtil.readInt(
                    "Enter your choice: "
            );

            switch (choice) {

                case 1:
                    patientMenu.start();
                    break;

                case 2:
                    doctorMenu.start();
                    break;

                case 3:
                    appointmentMenu.start();
                    break;

                case 4:

                    running = false;

                    DisplayUtil.printSuccess(
                            "Thank you for using Hospital Appointment Management System."
                    );

                    break;

                default:

                    DisplayUtil.printError(
                            "Invalid choice. Please select 1-4."
                    );
            }
        }
    }
}