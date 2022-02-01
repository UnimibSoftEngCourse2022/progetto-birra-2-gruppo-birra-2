CREATE DATABASE  IF NOT EXISTS `progetto_brewday` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `progetto_brewday`;
-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: progetto_brewday
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `brews`
--

DROP TABLE IF EXISTS `brews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brews` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `note` longtext,
  `quantita_prodotta` float NOT NULL,
  `birraio` varchar(255) DEFAULT NULL,
  `ricetta` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `birraio` (`birraio`),
  KEY `ricetta` (`ricetta`),
  CONSTRAINT `brews_ibfk_1` FOREIGN KEY (`birraio`) REFERENCES `users` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `brews_ibfk_2` FOREIGN KEY (`ricetta`) REFERENCES `recipes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brews`
--

LOCK TABLES `brews` WRITE;
/*!40000 ALTER TABLE `brews` DISABLE KEYS */;
/*!40000 ALTER TABLE `brews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brews_equipments`
--

DROP TABLE IF EXISTS `brews_equipments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brews_equipments` (
  `birraio` varchar(255) NOT NULL,
  `strumento` int NOT NULL,
  `quantita` int NOT NULL,
  PRIMARY KEY (`birraio`,`strumento`),
  KEY `birraio` (`birraio`),
  KEY `strumento` (`strumento`),
  CONSTRAINT `brews_equipments_ibfk_1` FOREIGN KEY (`birraio`) REFERENCES `users` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `brews_equipments_ibfk_2` FOREIGN KEY (`strumento`) REFERENCES `tools` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brews_equipments`
--

LOCK TABLES `brews_equipments` WRITE;
/*!40000 ALTER TABLE `brews_equipments` DISABLE KEYS */;
/*!40000 ALTER TABLE `brews_equipments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `components`
--

DROP TABLE IF EXISTS `components`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `components` (
  `ricetta` int NOT NULL,
  `ingrediente` varchar(255) NOT NULL,
  `quantita` float NOT NULL,
  PRIMARY KEY (`ricetta`,`ingrediente`),
  KEY `ingrediente` (`ingrediente`),
  CONSTRAINT `components_ibfk_1` FOREIGN KEY (`ricetta`) REFERENCES `recipes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `components_ibfk_2` FOREIGN KEY (`ingrediente`) REFERENCES `ingredients` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `components`
--

LOCK TABLES `components` WRITE;
/*!40000 ALTER TABLE `components` DISABLE KEYS */;
/*!40000 ALTER TABLE `components` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredients`
--

DROP TABLE IF EXISTS `ingredients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredients` (
  `nome` varchar(255) NOT NULL,
  `tipo` longtext NOT NULL,
  PRIMARY KEY (`nome`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredients`
--

LOCK TABLES `ingredients` WRITE;
/*!40000 ALTER TABLE `ingredients` DISABLE KEYS */;
/*!40000 ALTER TABLE `ingredients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipes`
--

DROP TABLE IF EXISTS `recipes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipes` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` longtext NOT NULL,
  `procedimento` longtext NOT NULL,
  `descrizione` longtext,
  `autore` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `autore` (`autore`),
  CONSTRAINT `recipes_ibfk_1` FOREIGN KEY (`autore`) REFERENCES `users` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipes`
--

LOCK TABLES `recipes` WRITE;
/*!40000 ALTER TABLE `recipes` DISABLE KEYS */;
/*!40000 ALTER TABLE `recipes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipes_equipments`
--

DROP TABLE IF EXISTS `recipes_equipments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recipes_equipments` (
  `ricetta` int NOT NULL,
  `strumento` int NOT NULL,
  `quantita` int NOT NULL,
  PRIMARY KEY (`ricetta`,`strumento`),
  KEY `ricetta` (`ricetta`),
  KEY `strumento` (`strumento`),
  CONSTRAINT `recipes_equipments_ibfk_1` FOREIGN KEY (`ricetta`) REFERENCES `recipes` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `recipes_equipments_ibfk_2` FOREIGN KEY (`strumento`) REFERENCES `tools` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipes_equipments`
--

LOCK TABLES `recipes_equipments` WRITE;
/*!40000 ALTER TABLE `recipes_equipments` DISABLE KEYS */;
/*!40000 ALTER TABLE `recipes_equipments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tools`
--

DROP TABLE IF EXISTS `tools`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tools` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) NOT NULL,
  `capacita_max` float NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tools`
--

LOCK TABLES `tools` WRITE;
/*!40000 ALTER TABLE `tools` DISABLE KEYS */;
/*!40000 ALTER TABLE `tools` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `nickname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`nickname`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouses`
--

DROP TABLE IF EXISTS `warehouses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `warehouses` (
  `birraio` varchar(255) NOT NULL,
  `ingrediente` varchar(255) NOT NULL,
  `quantita` float NOT NULL,
  PRIMARY KEY (`birraio`,`ingrediente`),
  KEY `ingrediente` (`ingrediente`),
  CONSTRAINT `warehouses_ibfk_1` FOREIGN KEY (`birraio`) REFERENCES `users` (`nickname`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `warehouses_ibfk_2` FOREIGN KEY (`ingrediente`) REFERENCES `ingredients` (`nome`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouses`
--

LOCK TABLES `warehouses` WRITE;
/*!40000 ALTER TABLE `warehouses` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouses` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-27 18:33:18
