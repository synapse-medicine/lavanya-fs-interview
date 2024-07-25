package com.synapse.itw.model;

public class PatientAllergy {
    private int patientId;
    private int moleculeId;

    public PatientAllergy(int patientId, int moleculeId) {
        this.patientId = patientId;
        this.moleculeId = moleculeId;
    }

    // Getters and Setters
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getMoleculeId() {
        return moleculeId;
    }

    public void setMoleculeId(int moleculeId) {
        this.moleculeId = moleculeId;
    }

    @Override
    public String toString() {
        return "PatientAllergy{" +
                "patientId=" + patientId +
                ", moleculeId=" + moleculeId +
                '}';
    }
}