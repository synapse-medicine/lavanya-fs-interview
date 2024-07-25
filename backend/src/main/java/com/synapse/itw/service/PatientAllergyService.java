package com.synapse.itw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synapse.itw.model.Allergy;
import com.synapse.itw.repository.PatientAllergyRepository;

@Service
public class PatientAllergyService {

    private final PatientAllergyRepository patientAllergyRepository;

    @Autowired
    public PatientAllergyService(PatientAllergyRepository patientAllergyRepository) {
        this.patientAllergyRepository = patientAllergyRepository;
    }

    public List<Allergy> getPatientAllergies(int patientId) {
        return patientAllergyRepository.getAllergies(patientId);
    }

    public boolean addPatientAllergy(int patientId, int moleculeId) {
        return patientAllergyRepository.addAllergy(patientId, moleculeId);
    }
}