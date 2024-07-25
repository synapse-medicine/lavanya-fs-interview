package com.synapse.itw.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synapse.itw.model.Allergy;
import com.synapse.itw.service.MoleculeService;

@RestController
@RequestMapping("/api/v1/molecules")
public class MoleculeController {
    private static final Logger logger = LoggerFactory.getLogger(MoleculeController.class);

    private final MoleculeService moleculeService;

    @Autowired
    public MoleculeController(MoleculeService moleculeService) {
        this.moleculeService = moleculeService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Allergy>> searchMolecules(@RequestParam String name) {
        // List<Allergy> molecules = moleculeService.searchMoleculesByName(name);
        // return ResponseEntity.ok(molecules);    
        List<Allergy> molecules;
        try {
            molecules = moleculeService.searchMoleculesByName(name);
            logger.debug("searchMolecules method result: {}", molecules);
        } catch (Exception e) {
            logger.error("Error occurred while searching molecules by name: {}", name, e);
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.ok(molecules);
    }
}
