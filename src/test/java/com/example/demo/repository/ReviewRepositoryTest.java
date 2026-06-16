package com.example.demo.repository;

import com.example.demo.model.Review;
import com.example.demo.model.Terrain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TerrainRepository terrainRepository;

    private Terrain createTerrain() {
        Terrain terrain = Terrain.builder()
                .ownerId(1L)
                .title("Review Test Field")
                .location("Review City")
                .areaSize(new BigDecimal("1000.00"))
                .pricePerDay(new BigDecimal("100.00"))
                .isAvailable(true)
                .build();
        return terrainRepository.save(terrain);
    }

    @Test
    void testSaveReview() {
        Terrain terrain = createTerrain();

        Review review = Review.builder()
                .terrain(terrain)
                .userId(2L)
                .rating(5)
                .comment("Excellent terrain!")
                .build();

        Review saved = reviewRepository.save(review);

        assertNotNull(saved.getId());
        assertEquals(5, saved.getRating());
        assertEquals("Excellent terrain!", saved.getComment());
    }

    @Test
    void testFindReviewById() {
        Terrain terrain = createTerrain();

        Review review = Review.builder()
                .terrain(terrain)
                .userId(3L)
                .rating(4)
                .comment("Good terrain")
                .build();

        Review saved = reviewRepository.save(review);
        Optional<Review> found = reviewRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(4, found.get().getRating());
    }
}
