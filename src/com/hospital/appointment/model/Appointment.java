package com.hospital.appointment.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

    private int appointmentId;

    private int patientId;

    private int doctorId;

    private LocalDate appointmentDate;

    private LocalTime appointmentTime;

    private AppointmentStatus status;

    public Appointment(int appointmentId,
                       int patientId,
                       int doctorId,
                       LocalDate appointmentDate,
                       LocalTime appointmentTime) {

        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.status = AppointmentStatus.BOOKED;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
}