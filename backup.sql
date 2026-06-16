-- MySQL dump 10.13  Distrib 8.0.46, for Linux (x86_64)
--
-- Host: mysql    Database: Anchhy-db
-- ------------------------------------------------------
-- Server version	8.4.9

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `end_date` datetime(6) NOT NULL,
  `renter_id` bigint NOT NULL,
  `start_date` datetime(6) NOT NULL,
  `status` enum('APPROVED','CANCELLED','COMPLETED','PENDING','REJECTED') NOT NULL,
  `total_price` decimal(38,2) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `terrain_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgjx5ojtmm0c8qrgfuw0oim64i` (`terrain_id`),
  CONSTRAINT `FKgjx5ojtmm0c8qrgfuw0oim64i` FOREIGN KEY (`terrain_id`) REFERENCES `terrains` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES (1,'2026-06-16 15:32:27.767406','2026-06-19 15:32:27.766542',3,'2026-06-17 15:32:27.766530','APPROVED',450.00,'2026-06-16 15:32:27.767422',1),(2,'2026-06-16 15:32:27.771301','2026-06-23 15:32:27.770915',4,'2026-06-21 15:32:27.770906','PENDING',200.00,'2026-06-16 15:32:27.771314',2);
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorites`
--

DROP TABLE IF EXISTS `favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorites` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `terrain_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkfs6lmurovif34cq5430xcojn` (`terrain_id`),
  CONSTRAINT `FKkfs6lmurovif34cq5430xcojn` FOREIGN KEY (`terrain_id`) REFERENCES `terrains` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorites`
--

LOCK TABLES `favorites` WRITE;
/*!40000 ALTER TABLE `favorites` DISABLE KEYS */;
INSERT INTO `favorites` VALUES (1,'2026-06-16 15:32:27.791892','2026-06-16 15:32:27.791905',3,1),(2,'2026-06-16 15:32:27.794746','2026-06-16 15:32:27.794758',4,3);
/*!40000 ALTER TABLE `favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount_paid` decimal(38,2) NOT NULL,
  `payment_date` datetime(6) NOT NULL,
  `payment_method` varchar(255) NOT NULL,
  `status` enum('FAILED','PAID','REFUNDED') NOT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `booking_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKnuscjm6x127hkb15kcb8n56wo` (`booking_id`),
  CONSTRAINT `FKc52o2b1jkxttngufqp3t7jr3h` FOREIGN KEY (`booking_id`) REFERENCES `bookings` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,450.00,'2026-06-16 15:32:27.774091','Credit Card','PAID','TXN-001-2024',1);
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment` text,
  `created_at` datetime(6) DEFAULT NULL,
  `rating` int NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `terrain_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKodvnh2ymx313jcpxb9vf93yg5` (`terrain_id`),
  CONSTRAINT `FKodvnh2ymx313jcpxb9vf93yg5` FOREIGN KEY (`terrain_id`) REFERENCES `terrains` (`id`),
  CONSTRAINT `reviews_chk_1` CHECK (((`rating` <= 5) and (`rating` >= 1)))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,'Excellent football field! Great condition.','2026-06-16 15:32:27.784271',5,'2026-06-16 15:32:27.784303',3,1),(2,'Nice court, but parking could be better.','2026-06-16 15:32:27.788222',4,'2026-06-16 15:32:27.788237',4,2);
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terrain_images`
--

DROP TABLE IF EXISTS `terrain_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `terrain_images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image_path` varchar(255) NOT NULL,
  `uploaded_at` datetime(6) DEFAULT NULL,
  `terrain_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkhnnxy1fi8ovis756av1y2r5f` (`terrain_id`),
  CONSTRAINT `FKkhnnxy1fi8ovis756av1y2r5f` FOREIGN KEY (`terrain_id`) REFERENCES `terrains` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terrain_images`
--

LOCK TABLES `terrain_images` WRITE;
/*!40000 ALTER TABLE `terrain_images` DISABLE KEYS */;
INSERT INTO `terrain_images` VALUES (1,'/images/football-field-a.jpg','2026-06-16 15:32:27.730616',1),(2,'/images/basketball-court-b.jpg','2026-06-16 15:32:27.734032',2),(3,'/images/tennis-court-c.jpg','2026-06-16 15:32:27.736397',3);
/*!40000 ALTER TABLE `terrain_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `terrains`
--

DROP TABLE IF EXISTS `terrains`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `terrains` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `area_size` decimal(38,2) NOT NULL,
  `available_from` datetime(6) DEFAULT NULL,
  `available_to` datetime(6) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` text,
  `is_available` bit(1) NOT NULL,
  `location` varchar(255) NOT NULL,
  `owner_id` bigint NOT NULL,
  `price_per_day` decimal(38,2) NOT NULL,
  `title` varchar(255) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `main_image_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfdjntdoic5rjr83mg5u1nyfn1` (`main_image_id`),
  CONSTRAINT `FKmv68g47othx5j6acnjyx7jhrx` FOREIGN KEY (`main_image_id`) REFERENCES `terrain_images` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `terrains`
--

LOCK TABLES `terrains` WRITE;
/*!40000 ALTER TABLE `terrains` DISABLE KEYS */;
INSERT INTO `terrains` VALUES (1,5000.00,'2026-06-16 15:32:27.691265','2026-12-16 15:32:27.691287','2026-06-16 15:32:27.699491','Professional football field with natural grass',_binary '','Phnom Penh, Cambodia',1,150.00,'Football Field A','2026-06-16 15:32:27.753770',1),(2,800.00,'2026-06-16 15:32:27.721858','2026-09-16 15:32:27.721873','2026-06-16 15:32:27.722591','Indoor basketball court with wooden floor',_binary '','Siem Reap, Cambodia',2,100.00,'Basketball Court B','2026-06-16 15:32:27.763895',2),(3,600.00,'2026-06-16 15:32:27.726503','2027-06-16 15:32:27.726511','2026-06-16 15:32:27.726859','Outdoor tennis court with clay surface',_binary '','Battambang, Cambodia',1,80.00,'Tennis Court C','2026-06-16 15:32:27.726869',NULL);
/*!40000 ALTER TABLE `terrains` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-06-16 15:46:39
