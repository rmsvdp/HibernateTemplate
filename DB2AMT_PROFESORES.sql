-- --------------------------------------------------------
-- Versión del servidor:         10.9.3-MariaDB - mariadb.org binary distribution

USE `db2amt`;

--  estructura para tabla db2amt.profesores
CREATE TABLE IF NOT EXISTS `profesores` (
  `IDN` varchar(10) COLLATE utf8mb4_spanish_ci NOT NULL,
  `APENOM` varchar(32) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `DIRECC` varchar(32) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `PROV` varchar(32) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `EMAIL` varchar(32) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`IDN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

