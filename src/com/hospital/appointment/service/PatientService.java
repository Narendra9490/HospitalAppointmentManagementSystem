package com.hospital.appointment.service;

import com.hospital.appointment.exception.PatientNotFoundException;
import com.hospital.appointment.model.Patient;
import com.hospital.appointment.repository.PatientRepository;
import com.hospital.appointment.util.IdGenerator;

import java.util.Map;

public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {

        this.patientRepository = patientRepository;
    }

    public Patient registerPatient(String name,
                                   int age,
                                   String gender,
                                   String phone,
                                   String email) {

        int patientId = IdGenerator.generatePatientId();

        Patient patient = new Patient(
                patientId,
                name,
                age,
                gender,
                phone,
                email
        );

        patientRepository.save(patient);

        return patient;
    }

    public Patient getPatient(int patientId) {

        Patient patient = patientRepository.findById(patientId);

        if (patient == null) {

            throw new PatientNotFoundException(
                    "Patient with ID " + patientId + " not found."
            );
        }

        return patient;
    }

    public Map<Integer, Patient> getAllPatients() {

        return patientRepository.findAll();
    }

    public void deletePatient(int patientId) {

        getPatient(patientId);

        patientRepository.delete(patientId);
    }
}