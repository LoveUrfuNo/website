DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_logins` (
  `id` INT(11) NOT NULL ,
  `username`  VARCHAR(64) NOT NULL,
  `series`    VARCHAR(64) NOT NULL,
  `token`     VARCHAR(64) NOT NULL,
  `last_used` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins`
  DISABLE KEYS */;
INSERT INTO `persistent_logins`
VALUES (1, 'admin', 'gxOpX0DvRz5b+KDUAZwWsA==', 'PpH73MJu1AMIw/6fMrVWZA==', '2015-10-31 13:01:52');
/*!40000 ALTER TABLE `persistent_logins`
  ENABLE KEYS */;
UNLOCK TABLES;

