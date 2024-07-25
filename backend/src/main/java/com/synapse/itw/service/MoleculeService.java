package com.synapse.itw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synapse.itw.model.Allergy;
import com.synapse.itw.repository.MoleculeRepository;

@Service
public class MoleculeService {

    private final MoleculeRepository moleculeRepository;

    @Autowired
    public MoleculeService(MoleculeRepository moleculeRepository) {
        this.moleculeRepository = moleculeRepository;
    }

    public List<Allergy> searchMoleculesByName(String name) {
        return moleculeRepository.searchMoleculesByName(name);
    }
}
