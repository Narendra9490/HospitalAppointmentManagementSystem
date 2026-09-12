package com.hospital.appointment.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.hospital.appointment.exception.AppointmentNotFoundException;
import com.hospital.appointment.exception.SlotAlreadyBookedException;
import com.hospital.appointment.model.Appointment;
import com.hospital.appointment.model.AppointmentStatus;
import com.hospital.appointment.repository.AppointmentRepository;
import com.hospital.appointment.util.IdGenerator;

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final PatientService patientService;

    private final DoctorService doctorService;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            PatientService patientService,
            DoctorService doctorService) {

        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public Appointment bookAppointment(
            int patientId,
            int doctorId,
            LocalDate date,
            LocalTime time) {

        // 1. Validate patient
        patientService.getPatient(patientId);

        // 2. Validate doctor
        doctorService.getDoctor(doctorId);

        // 3. Prevent past appointments
        LocalDateTime appointmentDateTime =
                LocalDateTime.of(date, time);

        if (appointmentDateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "Cannot book an appointment in the past."
            );
        }
        if (!isValidTimeSlot(time)) {
            throw new IllegalArgumentException(
                    "Invalid time slot. Please select one of the available slots."
            );
        }
        
        

        // 4. Check slot
        if (appointmentRepository.isSlotBooked(
                doctorId,
                date,
                time)) {

            throw new SlotAlreadyBookedException(
                    "Selected slot is already booked."
            );
        }

        // 5. Generate appointment ID
        int appointmentId =
                IdGenerator.generateAppointmentId();

        // 6. Create appointment
        Appointment appointment =
                new Appointment(
                        appointmentId,
                        patientId,
                        doctorId,
                        date,
                        time
                );

        // 7. Save
        appointmentRepository.save(appointment);

        return appointment;
    }

    public void cancelAppointment(int appointmentId) {

        Appointment appointment =
                appointmentRepository.findByAppointmentId(
                        appointmentId
                );

        if (appointment == null) {

            throw new AppointmentNotFoundException(
                    "Appointment not found."
            );
        }

        if (appointment.getStatus()
                == AppointmentStatus.CANCELLED) {

            throw new IllegalStateException(
                    "Appointment is already cancelled."
            );
        }

        appointment.setStatus(
                AppointmentStatus.CANCELLED
        );
    }

    public List<Appointment> getDoctorSchedule(
            int doctorId) {

        doctorService.getDoctor(doctorId);

        return appointmentRepository
                .findByDoctorId(doctorId);
    }

    public List<Appointment> getPatientHistory(
            int patientId) {

        patientService.getPatient(patientId);

        return appointmentRepository
                .findByPatientId(patientId);
    }
    public Appointment getAppointment(int appointmentId) {

        Appointment appointment =
                appointmentRepository
                        .findByAppointmentId(appointmentId);

        if (appointment == null) {

            throw new AppointmentNotFoundException(
                    "Appointment with ID "
                            + appointmentId
                            + " not found."
            );
        }

        return appointment;
    }
    public Appointment getAppointmentBySlot(int doctorId,
            LocalDate date,
            LocalTime time) {

Appointment appointment =
appointmentRepository.findBySlot(doctorId, date, time);

if (appointment == null
|| appointment.getStatus() == AppointmentStatus.CANCELLED) {

throw new AppointmentNotFoundException(
"No active appointment found for this slot."
);
}

return appointment;
}
    public void rescheduleAppointment(
            int appointmentId,
            LocalDate newDate,
            LocalTime newTime) {

        Appointment appointment =
                getAppointment(appointmentId);

        if (appointment.getStatus()
                == AppointmentStatus.CANCELLED) {

            throw new IllegalStateException(
                    "Cancelled appointment cannot be rescheduled."
            );
        }

        if (newDate.isBefore(LocalDate.now())) {

            throw new IllegalArgumentException(
                    "Cannot reschedule to a past date."
            );
        }

        int doctorId =
                appointment.getDoctorId();

        if (appointmentRepository.isSlotBooked(
                doctorId,
                newDate,
                newTime)) {

            throw new SlotAlreadyBookedException(
                    "The selected new slot is already booked."
            );
        }

        appointmentRepository.deleteBySlot(
                doctorId,
                appointment.getAppointmentDate(),
                appointment.getAppointmentTime()
        );

        appointment.setStatus(
                AppointmentStatus.BOOKED
        );

        Appointment rescheduledAppointment =
                new Appointment(
                        appointment.getAppointmentId(),
                        appointment.getPatientId(),
                        appointment.getDoctorId(),
                        newDate,
                        newTime
                );

        appointmentRepository.save(
                rescheduledAppointment
        );
    }
    private boolean isValidTimeSlot(LocalTime time) {

        LocalTime[] availableTimes = {
            LocalTime.of(9, 0),
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(12, 0),
            LocalTime.of(14, 0),
            LocalTime.of(15, 0),
            LocalTime.of(16, 0),
            LocalTime.of(17, 0)
        };

        for (LocalTime availableTime : availableTimes) {
            if (availableTime.equals(time)) {
                return true;
            }
        }

        return false;
    }
}