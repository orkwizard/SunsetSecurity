-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: Heimdall
-- ------------------------------------------------------
-- Server version	5.7.18

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
-- Table structure for table `configurationProfile`
--

DROP TABLE IF EXISTS `configurationProfile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configurationProfile` (
  `idconfigurationProfile` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT 'default',
  `database_server` varchar(45) NOT NULL DEFAULT 'localhost',
  `port` int(11) NOT NULL DEFAULT '3306',
  `scheme` varchar(45) NOT NULL DEFAULT 'Heimdall',
  `driver_class` varchar(45) NOT NULL DEFAULT 'com.mysql.jdbc.Driver',
  `email_server` varchar(200) NOT NULL DEFAULT 'smtp.sunset.com.mx',
  `email_user` varchar(45) NOT NULL DEFAULT 'orkwizard@gmail.com',
  `email_password` varchar(45) NOT NULL DEFAULT 'nopassword',
  `email_port` int(11) NOT NULL DEFAULT '587',
  PRIMARY KEY (`idconfigurationProfile`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `configurationProfile`
--

LOCK TABLES `configurationProfile` WRITE;
/*!40000 ALTER TABLE `configurationProfile` DISABLE KEYS */;
INSERT INTO `configurationProfile` VALUES (1,'Default Profile','localhost',3306,'Heimdall','com.mysql.jdbc.Driver','smtp.sunset.com.mx','orkwizard@gmail.com','nopassword',587);
/*!40000 ALTER TABLE `configurationProfile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_perms`
--

DROP TABLE IF EXISTS `roles_perms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles_perms` (
  `role` varchar(255) NOT NULL,
  `perm` varchar(255) NOT NULL,
  PRIMARY KEY (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_perms`
--

LOCK TABLES `roles_perms` WRITE;
/*!40000 ALTER TABLE `roles_perms` DISABLE KEYS */;
/*!40000 ALTER TABLE `roles_perms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `servers`
--

DROP TABLE IF EXISTS `servers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `servers` (
  `idserver` int(11) NOT NULL AUTO_INCREMENT,
  `server_name` varchar(255) DEFAULT NULL,
  `server_ip` varchar(45) DEFAULT NULL,
  `registered_date` datetime DEFAULT NULL,
  `configuration_profile` int(11) DEFAULT NULL,
  PRIMARY KEY (`idserver`),
  UNIQUE KEY `idserver_UNIQUE` (`idserver`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `servers`
--

LOCK TABLES `servers` WRITE;
/*!40000 ALTER TABLE `servers` DISABLE KEYS */;
INSERT INTO `servers` VALUES (1,'Edgar MAC','10.194.24.103',NULL,NULL);
/*!40000 ALTER TABLE `servers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `snapshot`
--

DROP TABLE IF EXISTS `snapshot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `snapshot` (
  `idsnapshot` int(11) NOT NULL AUTO_INCREMENT,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ip` varchar(45) NOT NULL,
  `hostname` varchar(200) DEFAULT NULL,
  `image` mediumblob,
  PRIMARY KEY (`idsnapshot`,`date`,`ip`),
  UNIQUE KEY `idsnapshot_UNIQUE` (`idsnapshot`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `snapshot`
--

LOCK TABLES `snapshot` WRITE;
/*!40000 ALTER TABLE `snapshot` DISABLE KEYS */;
/*!40000 ALTER TABLE `snapshot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `password_salt` varchar(255) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('eosorio','B23A4AD8567B60E2869C0D0EB3434E18F476559F3F9EC1D5AE92F08F8C522A6A4265BAD2C496B765E3AFD82FFE67541E7DF422B4FB5510C36D40F4EBCF6C6DA7','93D29D38FC0444AB276BF98296E3EE99A4D50E9926F8E7FF39F73D4856174CBF');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `username` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  PRIMARY KEY (`username`,`role`),
  KEY `fk_roles` (`role`),
  CONSTRAINT `fk_roles` FOREIGN KEY (`role`) REFERENCES `roles_perms` (`role`),
  CONSTRAINT `fk_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-25 13:37:33
