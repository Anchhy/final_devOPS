package com.example.demo.seeder;

import com.example.demo.model.*;
import com.example.demo.model.enums.BookingStatus;
import com.example.demo.model.enums.PaymentStatus;
import com.example.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DataSeeder implements CommandLineRunner {

    private final TerrainRepository terrainRepository;
    private final TerrainImageRepository terrainImageRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final ReviewRepository reviewRepository;
    private final FavoriteRepository favoriteRepository;

    public DataSeeder(TerrainRepository terrainRepository,
                      TerrainImageRepository terrainImageRepository,
                      BookingRepository bookingRepository,
                      PaymentRepository paymentRepository,
                      ReviewRepository reviewRepository,
                      FavoriteRepository favoriteRepository) {
        this.terrainRepository = terrainRepository;
        this.terrainImageRepository = terrainImageRepository;
        this.bookingRepository = bookingRepository;
        this.paymentRepository = paymentRepository;
        this.reviewRepository = reviewRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public void run(String... args) {
        if (terrainRepository.count() == 0) {
            System.out.println("Seeding database...");

            // Create Terrains
            Terrain terrain1 = Terrain.builder()
                    .ownerId(1L)
                    .title("Football Field A")
                    .description("Professional football field with natural grass")
                    .location("Phnom Penh, Cambodia")
                    .areaSize(new BigDecimal("5000.00"))
                    .pricePerDay(new BigDecimal("150.00"))
                    .availableFrom(LocalDateTime.now())
                    .availableTo(LocalDateTime.now().plusMonths(6))
                    .isAvailable(true)
                    .build();
            terrainRepository.save(terrain1);

            Terrain terrain2 = Terrain.builder()
                    .ownerId(2L)
                    .title("Basketball Court B")
                    .description("Indoor basketball court with wooden floor")
                    .location("Siem Reap, Cambodia")
                    .areaSize(new BigDecimal("800.00"))
                    .pricePerDay(new BigDecimal("100.00"))
                    .availableFrom(LocalDateTime.now())
                    .availableTo(LocalDateTime.now().plusMonths(3))
                    .isAvailable(true)
                    .build();
            terrainRepository.save(terrain2);

            Terrain terrain3 = Terrain.builder()
                    .ownerId(1L)
                    .title("Tennis Court C")
                    .description("Outdoor tennis court with clay surface")
                    .location("Battambang, Cambodia")
                    .areaSize(new BigDecimal("600.00"))
                    .pricePerDay(new BigDecimal("80.00"))
                    .availableFrom(LocalDateTime.now())
                    .availableTo(LocalDateTime.now().plusMonths(12))
                    .isAvailable(true)
                    .build();
            terrainRepository.save(terrain3);

            // Create Terrain Images
            TerrainImage image1 = TerrainImage.builder()
                    .terrain(terrain1)
                    .imagePath("/images/football-field-a.jpg")
                    .build();
            terrainImageRepository.save(image1);

            TerrainImage image2 = TerrainImage.builder()
                    .terrain(terrain2)
                    .imagePath("/images/basketball-court-b.jpg")
                    .build();
            terrainImageRepository.save(image2);

            TerrainImage image3 = TerrainImage.builder()
                    .terrain(terrain3)
                    .imagePath("/images/tennis-court-c.jpg")
                    .build();
            terrainImageRepository.save(image3);

            // Set main images
            terrain1.setMainImage(image1);
            terrainRepository.save(terrain1);

            terrain2.setMainImage(image2);
            terrainRepository.save(terrain2);

            // Create Bookings
            Booking booking1 = Booking.builder()
                    .terrain(terrain1)
                    .renterId(3L)
                    .startDate(LocalDateTime.now().plusDays(1))
                    .endDate(LocalDateTime.now().plusDays(3))
                    .totalPrice(new BigDecimal("450.00"))
                    .status(BookingStatus.APPROVED)
                    .build();
            bookingRepository.save(booking1);

            Booking booking2 = Booking.builder()
                    .terrain(terrain2)
                    .renterId(4L)
                    .startDate(LocalDateTime.now().plusDays(5))
                    .endDate(LocalDateTime.now().plusDays(7))
                    .totalPrice(new BigDecimal("200.00"))
                    .status(BookingStatus.PENDING)
                    .build();
            bookingRepository.save(booking2);

            // Create Payments
            Payment payment1 = Payment.builder()
                    .booking(booking1)
                    .paymentMethod("Credit Card")
                    .amountPaid(new BigDecimal("450.00"))
                    .paymentDate(LocalDateTime.now())
                    .status(PaymentStatus.PAID)
                    .transactionId("TXN-001-2024")
                    .build();
            paymentRepository.save(payment1);

            // Create Reviews
            Review review1 = Review.builder()
                    .terrain(terrain1)
                    .userId(3L)
                    .rating(5)
                    .comment("Excellent football field! Great condition.")
                    .build();
            reviewRepository.save(review1);

            Review review2 = Review.builder()
                    .terrain(terrain2)
                    .userId(4L)
                    .rating(4)
                    .comment("Nice court, but parking could be better.")
                    .build();
            reviewRepository.save(review2);

            // Create Favorites
            Favorite favorite1 = Favorite.builder()
                    .userId(3L)
                    .terrain(terrain1)
                    .build();
            favoriteRepository.save(favorite1);

            Favorite favorite2 = Favorite.builder()
                    .userId(4L)
                    .terrain(terrain3)
                    .build();
            favoriteRepository.save(favorite2);

            System.out.println("Database seeding completed!");
        } else {
            System.out.println("Database already contains data. Skipping seed.");
        }
    }
}
