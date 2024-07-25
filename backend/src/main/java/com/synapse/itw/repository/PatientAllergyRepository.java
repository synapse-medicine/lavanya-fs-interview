package com.synapse.itw.repository;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.synapse.itw.model.Allergy;
import com.synapse.itw.model.PatientAllergy;


@Repository
public class PatientAllergyRepository {
    private static final Logger logger = LoggerFactory.getLogger(MoleculeRepository.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PatientAllergyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Allergy> getAllergies(int patientId) {
        String sql = "SELECT m.id, m.name FROM molecules m JOIN patient_allergy pa ON m.id = pa.molecule_id WHERE pa.patient_id = ?";
        logger.info("Executing query: {} for patientID : {}", sql, patientId);
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new Allergy(
                rs.getInt("id"), 
                rs.getString("name")
            ), patientId);
        } catch (DataAccessException e) {
            logger.error("Error getting the allergies of patientID : {}", patientId, e);
            throw new RuntimeException("Error fetching allergies", e);
        }
    }

    public List<PatientAllergy> getPatientAllergyRows(int patientId) {
        String sql = "SELECT * FROM patient_allergy WHERE patient_id = ?";
        logger.info("Executing query: {} for patientID : {}", sql, patientId);
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> new PatientAllergy(
                rs.getInt("patient_id"), 
                rs.getInt("molecule_id")
            ), patientId);
        } catch (DataAccessException e) {
            logger.error("Error getting the allergies of patientID : {}", patientId, e);
            throw new RuntimeException("Error fetching patient allergy rows", e);
        }
    }

    @Transactional
    public boolean addAllergy(int patientId, int moleculeId) {
        try {
            String sql = "SELECT * FROM patient_allergy WHERE patient_id = ? AND molecule_id = ?";
            logger.info("Executing query: {} for patientID : {}, and moleculeID : {} ", sql, patientId, moleculeId );
            List<PatientAllergy> existingAllergies = this.jdbcTemplate.query(
                "SELECT * FROM patient_allergy WHERE patient_id = ? AND molecule_id = ?",
                new Object[]{patientId, moleculeId},
                (rs, rowNum) -> new PatientAllergy(rs.getInt("patient_id"), rs.getInt("molecule_id"))
            );

            if (!existingAllergies.isEmpty()) {
                logger.info("Executing query: {} for patientID : {}, and moleculeID : {}  - Allergy already exists.", sql, patientId, moleculeId);
                return false; // Allergy already exists
            } else {
                String insertSql = "INSERT INTO patient_allergy (patient_id, molecule_id) VALUES (?, ?)";
                jdbcTemplate.update(insertSql, patientId, moleculeId);
                logger.info("Executing query: {} for patientID : {}, and moleculeID : {}  - Allergy added successfully.", insertSql, patientId, moleculeId);
                return true; // Allergy added successfully
            }
        } catch (Exception e) {
            logger.error("Error adding the allergy {} for patientID : {}", moleculeId, patientId);
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Allergy> findAllergy(int patientId, int moleculeId) {
        String sql = "SELECT m.id, m.name FROM molecules m JOIN patient_allergy pa ON m.id = pa.molecule_id WHERE pa.patient_id = ? AND pa.molecule_id = ?";
        logger.info("Executing query: {} for patientID : {}, and moleculeID : {} ", sql, patientId, moleculeId );
        try {
            List<Allergy> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Allergy(
                rs.getInt("id"), 
                rs.getString("name")
            ), patientId, moleculeId);
            return result.stream().findFirst();
        } catch (DataAccessException e) {
            logger.error("Error finding the allergy : {} for patientID : {}", sql, patientId);
            throw new RuntimeException("Error finding allergy", e);
        }
    }
}

