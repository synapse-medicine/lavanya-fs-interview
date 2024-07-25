package com.synapse.itw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synapse.itw.model.Allergy;
import com.synapse.itw.model.Patient;
import com.synapse.itw.service.PatientService;


@RestController
@RequestMapping("/api/v1/patients/")
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) {
        try {
            int patientId = Integer.parseInt(id);
            return patientService.getPatientById(patientId)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build())
                    ;
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("{id}/allergies")
    public ResponseEntity<List<Allergy>> getPatientAllergies(@PathVariable String id) {
        try {
            int patientId = Integer.parseInt(id);
            return ResponseEntity.ok(patientService.getPatientAllergies(patientId));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("{id}/allergies")
    public ResponseEntity<List<Allergy>> updatePatientAllergies(@PathVariable String id, @RequestBody List<Integer> allergies) {
        try {
            int patientId = Integer.parseInt(id);
            return ResponseEntity.ok(patientService.updatePatientAllergies(patientId, allergies));
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
