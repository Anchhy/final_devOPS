package com.example.demo.repository;

import com.example.demo.model.Favorite;
import com.example.demo.model.Terrain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FavoriteRepositoryTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private TerrainRepository terrainRepository;

    private Terrain createTerrain() {
        Terrain terrain = Terrain.builder()
                .ownerId(1L)
                .title("Favorite Test Field")
                .location("Favorite City")
                .areaSize(new BigDecimal("1000.00"))
                .pricePerDay(new BigDecimal("100.00"))
                .isAvailable(true)
                .build();
        return terrainRepository.save(terrain);
    }

    @Test
    void testSaveFavorite() {
        Terrain terrain = createTerrain();

        Favorite favorite = Favorite.builder()
                .userId(2L)
                .terrain(terrain)
                .build();

        Favorite saved = favoriteRepository.save(favorite);

        assertNotNull(saved.getId());
        assertEquals(2L, saved.getUserId());
    }

    @Test
    void testFindFavoriteById() {
        Terrain terrain = createTerrain();

        Favorite favorite = Favorite.builder()
                .userId(3L)
                .terrain(terrain)
                .build();

        Favorite saved = favoriteRepository.save(favorite);
        Optional<Favorite> found = favoriteRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(3L, found.get().getUserId());
    }
}
