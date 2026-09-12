package com.hospital.appointment.exception;

public class DoctorNotFoundException extends RuntimeException {

    public DoctorNotFoundException(String message) {

        super(message);
    }
}