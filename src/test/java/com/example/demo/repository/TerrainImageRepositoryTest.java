package com.example.demo.repository;

import com.example.demo.model.Terrain;
import com.example.demo.model.TerrainImage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TerrainImageRepositoryTest {

    @Autowired
    private TerrainImageRepository terrainImageRepository;

    @Autowired
    private TerrainRepository terrainRepository;

    private Terrain createTerrain() {
        Terrain terrain = Terrain.builder()
                .ownerId(1L)
                .title("Image Test Field")
                .location("Image City")
                .areaSize(new BigDecimal("500.00"))
                .pricePerDay(new BigDecimal("30.00"))
                .isAvailable(true)
                .build();
        return terrainRepository.save(terrain);
    }

    @Test
    void testSaveTerrainImage() {
        Terrain terrain = createTerrain();

        TerrainImage image = TerrainImage.builder()
                .terrain(terrain)
                .imagePath("/images/test.jpg")
                .build();

        TerrainImage saved = terrainImageRepository.save(image);

        assertNotNull(saved.getId());
        assertEquals("/images/test.jpg", saved.getImagePath());
    }

    @Test
    void testFindTerrainImageById() {
        Terrain terrain = createTerrain();

        TerrainImage image = TerrainImage.builder()
                .terrain(terrain)
                .imagePath("/images/find-test.jpg")
                .build();

        TerrainImage saved = terrainImageRepository.save(image);
        Optional<TerrainImage> found = terrainImageRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("/images/find-test.jpg", found.get().getImagePath());
    }
}
