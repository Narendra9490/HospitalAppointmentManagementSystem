package com.hospital.appointment.repository;

import com.hospital.appointment.model.Appointment;
import com.hospital.appointment.model.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentRepository {

    private final Map<String, Appointment> appointments = new HashMap<>();

    private String generateSlotKey(int doctorId,
                                   LocalDate date,
                                   LocalTime time) {

        return doctorId + "_" + date + "_" + time;
    }

    public void save(Appointment appointment) {

        String key = generateSlotKey(
                appointment.getDoctorId(),
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime()
        );

        appointments.put(key, appointment);
    }

    public boolean isSlotBooked(
            int doctorId,
            LocalDate date,
            LocalTime time) {

        String key = generateSlotKey(
                doctorId,
                date,
                time
        );

        Appointment appointment =
                appointments.get(key);

        if (appointment == null) {
            return false;
        }

        return appointment.getStatus()
                != AppointmentStatus.CANCELLED;
    }
    public Appointment findBySlot(int doctorId,
                                  LocalDate date,
                                  LocalTime time) {

        String key = generateSlotKey(doctorId, date, time);

        return appointments.get(key);
    }

    public Appointment findByAppointmentId(int appointmentId) {

        for (Appointment appointment : appointments.values()) {

            if (appointment.getAppointmentId() == appointmentId) {
                return appointment;
            }
        }

        return null;
    }

    public List<Appointment> findByDoctorId(int doctorId) {

        List<Appointment> result = new ArrayList<>();

        for (Appointment appointment : appointments.values()) {

            if (appointment.getDoctorId() == doctorId) {
                result.add(appointment);
            }
        }

        return result;
    }

    public List<Appointment> findByPatientId(int patientId) {

        List<Appointment> result = new ArrayList<>();

        for (Appointment appointment : appointments.values()) {

            if (appointment.getPatientId() == patientId) {
                result.add(appointment);
            }
        }

        return result;
    }

    public Map<String, Appointment> findAll() {

        return appointments;
    }
    public void deleteBySlot(
            int doctorId,
            LocalDate date,
            LocalTime time) {

        String key = generateSlotKey(
                doctorId,
                date,
                time
        );

        appointments.remove(key);
    }
}