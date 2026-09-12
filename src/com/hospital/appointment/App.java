package com.hospital.appointment;

import com.hospital.appointment.repository.AppointmentRepository;
import com.hospital.appointment.repository.DoctorRepository;
import com.hospital.appointment.repository.PatientRepository;

import com.hospital.appointment.service.AppointmentService;
import com.hospital.appointment.service.DoctorService;
import com.hospital.appointment.service.PatientService;

import com.hospital.appointment.ui.MainMenu;

import com.hospital.appointment.util.InputUtil;

public class App {

    public static void main(String[] args) {

        // -------------------------------------------------
        // 1. Create repositories
        // -------------------------------------------------

        PatientRepository patientRepository =
                new PatientRepository();

        DoctorRepository doctorRepository =
                new DoctorRepository();

        AppointmentRepository appointmentRepository =
                new AppointmentRepository();


        // -------------------------------------------------
        // 2. Create services
        // -------------------------------------------------

        PatientService patientService =
                new PatientService(patientRepository);

        DoctorService doctorService =
                new DoctorService(doctorRepository);

        AppointmentService appointmentService =
                new AppointmentService(
                        appointmentRepository,
                        patientService,
                        doctorService
                );


        // -------------------------------------------------
        // 3. Create main menu
        // -------------------------------------------------

        MainMenu mainMenu =
                new MainMenu(
                        patientService,
                        doctorService,
                        appointmentService
                );


        // -------------------------------------------------
        // 4. Start application
        // -------------------------------------------------

        try {
            mainMenu.start();
        } finally {
            InputUtil.close();
        }
    }
}