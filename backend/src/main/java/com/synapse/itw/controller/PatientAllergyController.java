package com.synapse.itw.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synapse.itw.model.Allergy;
import com.synapse.itw.service.PatientAllergyService;

@RestController
@RequestMapping("/api/v1/patient-allergies/")
public class PatientAllergyController {
    private static final Logger logger = LoggerFactory.getLogger(PatientAllergyController.class);
    private final PatientAllergyService patientAllergyService;

    @Autowired
    public PatientAllergyController(PatientAllergyService patientAllergyService) {
        this.patientAllergyService = patientAllergyService;
    }

    @GetMapping("{id}/allergies")
    public ResponseEntity<List<Allergy>> getPatientAllergies(@PathVariable String id) {
        try {
            int patientId = Integer.parseInt(id);
            return ResponseEntity.ok(patientAllergyService.getPatientAllergies(patientId));
        } catch (NumberFormatException e) {
            logger.error("Error occurred while getting allergies for patientID : {} : error: {}", id, e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("{id}/allergies")
    public ResponseEntity<String> addPatientAllergy(@PathVariable String id, @RequestBody int moleculeId) {
        int patientId = Integer.parseInt(id);
        try {
            boolean added = patientAllergyService.addPatientAllergy(patientId, moleculeId);
            if (added) {
                return ResponseEntity.ok("{\"message\": \"Allergy added successfully\"}");
            } else {
                return ResponseEntity.status(409).body("{\"message\": \"Allergy already exists\"}");
            }
        } catch (NumberFormatException e) {
            logger.error("Error occurred while adding allergies for patientID : {} : moleculeID: {}", patientId, moleculeId);
            return ResponseEntity.badRequest().body("{\"message\": \"Invalid patient ID\"}");
        }
    }
}