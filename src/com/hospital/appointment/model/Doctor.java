package com.hospital.appointment.model;

public class Doctor {

    private int doctorId;
    private String name;
    private Specialization specialization;
    private String phone;
    private String email;
    private double consultationFee;

    public Doctor(int doctorId,
                  String name,
                  Specialization specialization,
                  String phone,
                  String email,
                  double consultationFee) {

        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
        this.consultationFee = consultationFee;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getName() {
        return name;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }
}