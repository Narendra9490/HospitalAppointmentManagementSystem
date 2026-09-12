package com.hospital.appointment.repository;

import com.hospital.appointment.model.Doctor;

import java.util.HashMap;
import java.util.Map;

public class DoctorRepository {

    private final Map<Integer, Doctor> doctors = new HashMap<>();

    public void save(Doctor doctor) {

        doctors.put(doctor.getDoctorId(), doctor);
    }

    public Doctor findById(int doctorId) {

        return doctors.get(doctorId);
    }

    public boolean existsById(int doctorId) {

        return doctors.containsKey(doctorId);
    }

    public void delete(int doctorId) {

        doctors.remove(doctorId);
    }

    public Map<Integer, Doctor> findAll() {

        return doctors;
    }
}