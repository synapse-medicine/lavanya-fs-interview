package com.synapse.itw;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.synapse.itw.model.PatientAllergy;
import com.synapse.itw.repository.PatientAllergyRepository;

public class PatientAllergyRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private PatientAllergyRepository patientAllergyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // public void testGetAllergies() {
    //     int patientId = 1;
    //     List<Allergy> expectedAllergies = Arrays.asList(
    //             new Allergy(1, "Allergy1"),
    //             new Allergy(2, "Allergy2")
    //     );

    //     when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(expectedAllergies);

    //     List<Allergy> actualAllergies = patientAllergyRepository.getAllergies(patientId);

    //     assertEquals(expectedAllergies, actualAllergies);
    //     verify(jdbcTemplate, times(1)).query(anyString(), any(Object[].class), any(RowMapper.class));
    // }

    @Test
    public void testAddAllergy() {
        int patientId = 1;
        int moleculeId = 989; // add new non existing allergy

        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class))).thenReturn(Arrays.asList());

        boolean result = patientAllergyRepository.addAllergy(patientId, moleculeId);

        assertTrue(result);
        verify(jdbcTemplate, times(1)).update(anyString(), anyInt(), anyInt());
    }

    @Test
    public void testAddAllergyException() {
        int patientId = 1;
        int moleculeId = 3; // Use a molecule ID to trigger an exception

        when(jdbcTemplate.query(anyString(), any(Object[].class), any(RowMapper.class)))
                .thenThrow(new DataAccessException("...") {});

        boolean result = patientAllergyRepository.addAllergy(patientId, moleculeId);
        assertFalse(result);
    
    }

     @Test
    public void testAddExistingAllergy() {
        int patientId = 1;
        int moleculeId = 2; // add existing allergy for patient 1

        when(jdbcTemplate.query(
                eq("SELECT * FROM patient_allergy WHERE patient_id = ? AND molecule_id = ?"),
                any(Object[].class),
                any(RowMapper.class)))
                .thenReturn(Arrays.asList(new PatientAllergy(patientId, moleculeId)));

        
        boolean result = patientAllergyRepository.addAllergy(patientId, moleculeId);

        System.out.println("Test result: " + result);
        assertFalse(result); 
        verify(jdbcTemplate, never()).update(anyString(), anyInt(), anyInt());
    }

}
