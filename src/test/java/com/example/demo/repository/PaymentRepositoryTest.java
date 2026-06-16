package com.example.demo.repository;

import com.example.demo.model.Booking;
import com.example.demo.model.Payment;
import com.example.demo.model.Terrain;
import com.example.demo.model.enums.BookingStatus;
import com.example.demo.model.enums.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PaymentRepositoryTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TerrainRepository terrainRepository;

    private Booking createBooking() {
        Terrain terrain = Terrain.builder()
                .ownerId(1L)
                .title("Payment Test Field")
                .location("Payment City")
                .areaSize(new BigDecimal("1000.00"))
                .pricePerDay(new BigDecimal("100.00"))
                .isAvailable(true)
                .build();
        terrainRepository.save(terrain);

        Booking booking = Booking.builder()
                .terrain(terrain)
                .renterId(2L)
                .startDate(LocalDateTime.now().plusDays(1))
                .endDate(LocalDateTime.now().plusDays(3))
                .totalPrice(new BigDecimal("200.00"))
                .status(BookingStatus.APPROVED)
                .build();
        return bookingRepository.save(booking);
    }

    @Test
    void testSavePayment() {
        Booking booking = createBooking();

        Payment payment = Payment.builder()
                .booking(booking)
                .paymentMethod("Credit Card")
                .amountPaid(new BigDecimal("200.00"))
                .paymentDate(LocalDateTime.now())
                .status(PaymentStatus.PAID)
                .transactionId("TXN-TEST-001")
                .build();

        Payment saved = paymentRepository.save(payment);

        assertNotNull(saved.getId());
        assertEquals(PaymentStatus.PAID, saved.getStatus());
        assertEquals("TXN-TEST-001", saved.getTransactionId());
    }

    @Test
    void testFindPaymentById() {
        Booking booking = createBooking();

        Payment payment = Payment.builder()
                .booking(booking)
                .paymentMethod("Bank Transfer")
                .amountPaid(new BigDecimal("200.00"))
                .paymentDate(LocalDateTime.now())
                .status(PaymentStatus.PAID)
                .build();

        Payment saved = paymentRepository.save(payment);
        Optional<Payment> found = paymentRepository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("Bank Transfer", found.get().getPaymentMethod());
    }
}
