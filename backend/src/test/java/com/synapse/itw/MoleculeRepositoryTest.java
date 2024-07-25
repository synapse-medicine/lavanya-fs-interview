package com.synapse.itw;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.synapse.itw.model.Allergy;
import com.synapse.itw.repository.MoleculeRepository;

@ExtendWith(MockitoExtension.class)
public class MoleculeRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private MoleculeRepository moleculeRepository;

    @BeforeEach
    public void setUp() {
        // Any setup if needed
    }

    @Test
    public void testSearchMoleculesByNameSuccess() {
        String name = "asd";
        List<Allergy> expectedAllergies = Arrays.asList(
                new Allergy(1, "glasdegib maleate"),
                new Allergy(2, "glasdegib")
        );

        when(jdbcTemplate.query(
                anyString(),
                any(Object[].class),
                any(RowMapper.class)
        )).thenReturn(expectedAllergies);

        List<Allergy> actualAllergies = moleculeRepository.searchMoleculesByName(name);

        assertEquals(expectedAllergies, actualAllergies);
        verify(jdbcTemplate, times(1)).query(anyString(), any(Object[].class), any(RowMapper.class));
    }

    @Test
    public void testSearchMoleculesByNameNoResults() {
        String name = "NonExistingAllergy";

        when(jdbcTemplate.query(
                anyString(),
                any(Object[].class),
                any(RowMapper.class)
        )).thenReturn(Collections.emptyList());

        List<Allergy> actualAllergies = moleculeRepository.searchMoleculesByName(name);

        assertTrue(actualAllergies.isEmpty());
        verify(jdbcTemplate, times(1)).query(anyString(), any(Object[].class), any(RowMapper.class));
    }

    @Test
    public void testSearchMoleculesByNameException() {
        String name = "Allergy";

        when(jdbcTemplate.query(
                anyString(),
                any(Object[].class),
                any(RowMapper.class)
        )).thenThrow(new DataAccessException("...") {});

        Exception exception = assertThrows(RuntimeException.class, () -> {
            moleculeRepository.searchMoleculesByName(name);
        });

        assertEquals("Error searching molecules by name", exception.getMessage());
        verify(jdbcTemplate, times(1)).query(anyString(), any(Object[].class), any(RowMapper.class));
    }
}
