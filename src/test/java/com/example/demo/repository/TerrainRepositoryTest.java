package com.example.demo.repository;

import com.example.demo.model.Terrain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TerrainRepositoryTest {

    @Autowired
    private TerrainRepository terrainRepository;

    @Test
    void testSaveTerrain() {
        Terrain terrain = Terrain.builder()
                .ownerId(1L)
                .title("Test Field")
                .description("A test terrain")
                .location("Test City")
                .areaSize(new BigDecimal("1000.00"))
                .pricePerDay(new BigDecimal("50.00"))
                .isAvailable(true)
                .build();

        Terrain saved = terrainRepository.save(terrain);

        assertNotNull(saved.getId());
        assertEquals("Test Field", saved.getTitle());
        assertEquals("Test City", saved.getLocation());
    }

    @Test
    void testFindTerrainById() {
        Terrain terrain = Terrain.builder()
                .ownerId(1L)
                .title("Findable Field")
                .location("Find City")
                .areaSize(new BigDecimal("2000.00"))
                .pricePerDay(new BigDecimal("75.00"))
                .isAvailable(true)
                .build();

        Terrain saved = terrainRepository.save(terrain);
        Optional<Terrain> found = terrainRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Findable Field", found.get().getTitle());
    }
}
