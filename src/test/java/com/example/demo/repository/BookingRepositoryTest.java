package com.example.demo.repository;

import com.example.demo.model.Booking;
import com.example.demo.model.Terrain;
import com.example.demo.model.enums.BookingStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TerrainRepository terrainRepository;

    private Terrain createTerrain() {
        Terrain terrain = Terrain.builder()
                .ownerId(1L)
                .title("Booking Test Field")
                .location("Booking City")
                .areaSize(new BigDecimal("1000.00"))
                .pricePerDay(new BigDecimal("100.00"))
                .isAvailable(true)
                .build();
        return terrainRepository.save(terrain);
    }

    @Test
    void testSaveBooking() {
        Terrain terrain = createTerrain();

        Booking booking = Booking.builder()
                .terrain(terrain)
                .renterId(2L)
                .startDate(LocalDateTime.now().plusDays(1))
                .endDate(LocalDateTime.now().plusDays(3))
                .totalPrice(new BigDecimal("200.00"))
                .status(BookingStatus.PENDING)
                .build();

        Booking saved = bookingRepository.save(booking);

        assertNotNull(saved.getId());
        assertEquals(BookingStatus.PENDING, saved.getStatus());
        assertEquals(new BigDecimal("200.00"), saved.getTotalPrice());
    }

    @Test
    void testFindBookingById() {
        Terrain terrain = createTerrain();

        Booking booking = Booking.builder()
                .terrain(terrain)
                .renterId(3L)
                .startDate(LocalDateTime.now().plusDays(5))
                .endDate(LocalDateTime.now().plusDays(7))
                .totalPrice(new BigDecimal("300.00"))
                .status(BookingStatus.APPROVED)
                .build();

        Booking saved = bookingRepository.save(booking);
        Optional<Booking> found = bookingRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(BookingStatus.APPROVED, found.get().getStatus());
    }
}
