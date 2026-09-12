package com.hospital.appointment.repository;

import com.hospital.appointment.model.Patient;

import java.util.HashMap;
import java.util.Map;

public class PatientRepository {

    private final Map<Integer, Patient> patients = new HashMap<>();

    public void save(Patient patient) {

        patients.put(patient.getPatientId(), patient);
    }

    public Patient findById(int patientId) {

        return patients.get(patientId);
    }

    public boolean existsById(int patientId) {

        return patients.containsKey(patientId);
    }

    public void delete(int patientId) {

        patients.remove(patientId);
    }

    public Map<Integer, Patient> findAll() {

        return patients;
    }
}