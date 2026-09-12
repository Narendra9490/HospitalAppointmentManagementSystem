package com.hospital.appointment.service;

import com.hospital.appointment.exception.DoctorNotFoundException;
import com.hospital.appointment.model.Doctor;
import com.hospital.appointment.model.Specialization;
import com.hospital.appointment.repository.DoctorRepository;
import com.hospital.appointment.util.IdGenerator;

import java.util.Map;

public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {

        this.doctorRepository = doctorRepository;
    }

    public Doctor addDoctor(String name,
                            Specialization specialization,
                            String phone,
                            String email,
                            double consultationFee) {

        int doctorId = IdGenerator.generateDoctorId();

        Doctor doctor = new Doctor(
                doctorId,
                name,
                specialization,
                phone,
                email,
                consultationFee
        );

        doctorRepository.save(doctor);

        return doctor;
    }

    public Doctor getDoctor(int doctorId) {

        Doctor doctor = doctorRepository.findById(doctorId);

        if (doctor == null) {

            throw new DoctorNotFoundException(
                    "Doctor with ID " + doctorId + " not found."
            );
        }

        return doctor;
    }

    public Map<Integer, Doctor> getAllDoctors() {

        return doctorRepository.findAll();
    }

    public void deleteDoctor(int doctorId) {

        getDoctor(doctorId);

        doctorRepository.delete(doctorId);
    }
}