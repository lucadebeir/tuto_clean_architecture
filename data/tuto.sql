-- --------------------------------------------------------
-- Hôte :                        127.0.0.1
-- Version du serveur:           5.7.22-log - MySQL Community Server (GPL)
-- SE du serveur:                Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Listage de la structure de la base pour tuto
DROP DATABASE IF EXISTS `tuto`;
CREATE DATABASE IF NOT EXISTS `tuto` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tuto`;

-- Listage de la structure de la table tuto. bepartof
DROP TABLE IF EXISTS `bepartof`;
CREATE TABLE IF NOT EXISTS `bepartof` (
  `idPerson` int(11) NOT NULL,
  `idTeam` int(11) NOT NULL,
  KEY `FK__person` (`idPerson`),
  KEY `FK__team` (`idTeam`),
  CONSTRAINT `FK__person` FOREIGN KEY (`idPerson`) REFERENCES `person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK__team` FOREIGN KEY (`idTeam`) REFERENCES `team` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Listage des données de la table tuto.bepartof : ~13 rows (environ)
/*!40000 ALTER TABLE `bepartof` DISABLE KEYS */;
INSERT IGNORE INTO `bepartof` (`idPerson`, `idTeam`) VALUES
	(1, 2),
	(2, 2),
	(3, 2),
	(4, 2),
	(5, 2),
	(6, 2),
	(3, 3),
	(4, 3),
	(5, 3),
	(6, 3),
	(7, 3),
	(8, 3),
	(9, 3);
/*!40000 ALTER TABLE `bepartof` ENABLE KEYS */;

-- Listage de la structure de la table tuto. person
DROP TABLE IF EXISTS `person`;
CREATE TABLE IF NOT EXISTS `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lastname` varchar(30) NOT NULL,
  `firstname` varchar(40) NOT NULL,
  `age` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- Listage des données de la table tuto.person : ~9 rows (environ)
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT IGNORE INTO `person` (`id`, `lastname`, `firstname`, `age`) VALUES
	(1, 'Tata', 'Toto', 20),
	(2, 'Tata', 'Toto', 20),
	(3, 'Tata', 'Toto', 20),
	(4, 'Tata', 'Toto', 20),
	(5, 'Tata', 'Toto', 20),
	(6, 'Daa', 'Alphonse', 23),
	(7, 'Daa', 'Alphonse', 23),
	(8, 'Daa', 'Alphonse', 23),
	(9, 'debeir', 'luca', 25);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;

-- Listage de la structure de la table tuto. team
DROP TABLE IF EXISTS `team`;
CREATE TABLE IF NOT EXISTS `team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  KEY `Index 1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Listage des données de la table tuto.team : ~2 rows (environ)
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT IGNORE INTO `team` (`id`, `name`) VALUES
	(2, 'OM'),
	(3, 'OL');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
