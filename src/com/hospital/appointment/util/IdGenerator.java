package com.hospital.appointment.util;

public class IdGenerator {

    private static int patientId = 1001;
    private static int doctorId = 2001;
    private static int appointmentId = 5001;

    public static int generatePatientId() {

        return patientId++;
    }

    public static int generateDoctorId() {

        return doctorId++;
    }

    public static int generateAppointmentId() {

        return appointmentId++;
    }
}