package com.synapse.itw.repository;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.synapse.itw.model.Allergy;

@Repository
public class MoleculeRepository {
    private static final Logger logger = LoggerFactory.getLogger(MoleculeRepository.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MoleculeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Allergy> searchMoleculesByName(String name) {
        String sql = "SELECT * FROM molecules WHERE name LIKE ?";
        logger.debug("Executing query: {} with name: {}", sql, name);
        try {
            return jdbcTemplate.query(
                    sql,
                    new Object[]{"%" + name + "%"},
                    (rs, rowNum) -> new Allergy(rs.getInt("id"), rs.getString("name"))
            );
        } catch (DataAccessException e) {
            logger.error("Error searching molecules by name: {}", name, e);
            throw new RuntimeException("Error searching molecules by name", e);
        }
    }
}