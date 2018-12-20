-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: employee_db
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employee_pass`
--

DROP TABLE IF EXISTS `employee_pass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_pass` (
  `passwords` char(36) NOT NULL,
  `employee_idFK` int(5) NOT NULL,
  PRIMARY KEY (`employee_idFK`),
  CONSTRAINT `employee_pass_ibfk_1` FOREIGN KEY (`employee_idFK`) REFERENCES `employees` (`employee_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_pass`
--

LOCK TABLES `employee_pass` WRITE;
/*!40000 ALTER TABLE `employee_pass` DISABLE KEYS */;
INSERT INTO `employee_pass` VALUES ('9d5d2167033a5fe74276ceff66f68b61',1926),('1e18004ce7ac61fced1116367677569c',2071),('4a4a5c0ae5f1785fb25e2c310274b07b',3550),('91b07b3169d8a7cb6de940142187c8df',4298),('9a454ed7b681568cba5da7cafe78ee0e',5409),('046d93852f6419ac27a74b4ebcaa32e4',6652);
/*!40000 ALTER TABLE `employee_pass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `employee_id` int(5) NOT NULL,
  `employee_name` blob NOT NULL,
  `dept_number` int(5) NOT NULL,
  `annual_salary` int(10) NOT NULL,
  `raise` decimal(5,2) NOT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `idemployees_UNIQUE` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1926,_binary 'Andrews',10,29000,0.10),(2071,_binary 'Cooper',14,30250,0.12),(3550,_binary 'Feldman',10,24175,0.07),(4298,_binary 'Palmer',35,33400,0.11),(5409,_binary 'Shields',10,27500,0.08),(6652,_binary 'Wolfe',31,31773,0.10);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-19 16:36:57
