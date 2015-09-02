-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 01, 2015 at 12:42 PM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `absen`
--

-- --------------------------------------------------------

--
-- Table structure for table `absensi`
--

CREATE TABLE IF NOT EXISTS `absensi` (
  `Tanggal` date NOT NULL,
  `NIS` int(11) NOT NULL,
  `Jam1` enum('H','I','S','A') DEFAULT NULL,
  `Jam2` enum('H','I','S','A') DEFAULT NULL,
  `Jam3` enum('H','I','S','A') DEFAULT NULL,
  `Jam4` enum('H','I','S','A') DEFAULT NULL,
  `Kelas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `absensi`
--

INSERT INTO `absensi` (`Tanggal`, `NIS`, `Jam1`, `Jam2`, `Jam3`, `Jam4`, `Kelas`) VALUES
('2015-02-11', 1106071, 'A', 'H', 'H', 'I', 1),
('2015-02-11', 1106075, 'H', 'A', 'I', 'H', 1),
('2015-02-11', 1106081, 'S', 'I', 'H', 'S', 1),
('2015-02-11', 1106072, NULL, NULL, NULL, 'H', 2),
('2015-02-11', 1106076, NULL, NULL, NULL, 'A', 2),
('2015-02-11', 1106082, NULL, NULL, NULL, 'H', 2),
('2015-02-11', 1106073, NULL, NULL, NULL, NULL, 3),
('2015-02-11', 1106077, NULL, NULL, NULL, NULL, 3),
('2015-02-11', 1106078, NULL, NULL, NULL, NULL, 3),
('2015-02-11', 1106074, NULL, NULL, NULL, NULL, 4),
('2015-02-11', 1106079, NULL, NULL, NULL, NULL, 4),
('2015-02-11', 1106080, NULL, NULL, NULL, NULL, 4),
('2015-02-12', 1106071, 'I', 'S', 'A', 'H', 1),
('2015-02-12', 1106075, 'H', NULL, NULL, NULL, 1),
('2015-02-12', 1106081, 'H', NULL, NULL, NULL, 1),
('2015-02-13', 1106071, NULL, NULL, NULL, NULL, 1),
('2015-02-13', 1106075, NULL, NULL, NULL, NULL, 1),
('2015-02-13', 1106081, NULL, NULL, NULL, NULL, 1),
('2015-02-13', 1106072, NULL, NULL, NULL, NULL, 2),
('2015-02-13', 1106076, NULL, NULL, NULL, NULL, 2),
('2015-02-13', 1106082, NULL, NULL, NULL, NULL, 2),
('2015-02-13', 1106073, NULL, NULL, NULL, NULL, 3),
('2015-02-13', 1106077, NULL, NULL, NULL, NULL, 3),
('2015-02-13', 1106078, NULL, NULL, NULL, NULL, 3),
('2015-02-13', 1106074, NULL, NULL, NULL, NULL, 4),
('2015-02-13', 1106079, NULL, NULL, NULL, NULL, 4),
('2015-02-13', 1106080, NULL, NULL, NULL, NULL, 4),
('2015-02-12', 1106072, NULL, NULL, NULL, NULL, 2),
('2015-02-12', 1106076, NULL, NULL, NULL, NULL, 2),
('2015-02-12', 1106082, NULL, NULL, NULL, NULL, 2),
('2015-02-12', 1106073, NULL, NULL, NULL, NULL, 3),
('2015-02-12', 1106077, NULL, NULL, NULL, NULL, 3),
('2015-02-12', 1106078, NULL, NULL, NULL, NULL, 3),
('2015-02-12', 1106074, NULL, NULL, NULL, NULL, 4),
('2015-02-12', 1106079, NULL, NULL, NULL, NULL, 4),
('2015-02-12', 1106080, NULL, NULL, NULL, NULL, 4),
('2015-02-19', 1106071, NULL, NULL, NULL, NULL, 1),
('2015-02-19', 1106075, NULL, NULL, NULL, NULL, 1),
('2015-02-19', 1106081, NULL, NULL, NULL, NULL, 1),
('2015-02-19', 1106073, NULL, NULL, NULL, NULL, 3),
('2015-02-19', 1106077, NULL, NULL, NULL, NULL, 3),
('2015-02-19', 1106078, NULL, NULL, NULL, NULL, 3),
('2015-02-19', 1106072, NULL, NULL, NULL, NULL, 2),
('2015-02-19', 1106076, NULL, NULL, NULL, NULL, 2),
('2015-02-19', 1106082, NULL, NULL, NULL, NULL, 2),
('2015-02-19', 1106074, NULL, NULL, NULL, NULL, 4),
('2015-02-19', 1106079, NULL, NULL, NULL, NULL, 4),
('2015-02-19', 1106080, NULL, NULL, NULL, NULL, 4);

-- --------------------------------------------------------

--
-- Table structure for table `kelas`
--

CREATE TABLE IF NOT EXISTS `kelas` (
`ID` int(11) NOT NULL,
  `Nama` text NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `kelas`
--

INSERT INTO `kelas` (`ID`, `Nama`) VALUES
(1, 'X Informatika I'),
(2, 'X Informatika II'),
(3, 'X Geologi I'),
(4, 'X Geologi II');

-- --------------------------------------------------------

--
-- Table structure for table `siswa`
--

CREATE TABLE IF NOT EXISTS `siswa` (
  `NIS` int(11) NOT NULL,
  `Nama` text NOT NULL,
  `Jenis Kelamin` tinyint(1) NOT NULL,
  `Kelas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `siswa`
--

INSERT INTO `siswa` (`NIS`, `Nama`, `Jenis Kelamin`, `Kelas`) VALUES
(1106071, 'Mahdi Abdullah', 1, 1),
(1106072, 'Abdullah Mahdi', 1, 2),
(1106073, 'Renata Sri Irawan', 0, 3),
(1106074, 'Injilita Nansi', 0, 4),
(1106075, 'Engkit Satia Riswara', 1, 1),
(1106076, 'Restu Tiara', 0, 2),
(1106077, 'Tian Anggara', 1, 3),
(1106078, 'Ayang', 0, 3),
(1106079, 'Nda Dudud', 1, 4),
(1106080, 'Rara Dudud', 0, 4),
(1106081, 'Dendi', 1, 1),
(1106082, 'Puppey', 1, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `absensi`
--
ALTER TABLE `absensi`
 ADD KEY `NomorIndukSiswa` (`NIS`), ADD KEY `IDKelas` (`Kelas`);

--
-- Indexes for table `kelas`
--
ALTER TABLE `kelas`
 ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `siswa`
--
ALTER TABLE `siswa`
 ADD PRIMARY KEY (`NIS`), ADD KEY `Kelas_ID` (`Kelas`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `kelas`
--
ALTER TABLE `kelas`
MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `absensi`
--
ALTER TABLE `absensi`
ADD CONSTRAINT `IDKelas` FOREIGN KEY (`Kelas`) REFERENCES `kelas` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE,
ADD CONSTRAINT `NomorIndukSiswa` FOREIGN KEY (`NIS`) REFERENCES `siswa` (`NIS`) ON DELETE NO ACTION ON UPDATE CASCADE;

--
-- Constraints for table `siswa`
--
ALTER TABLE `siswa`
ADD CONSTRAINT `Kelas_ID` FOREIGN KEY (`Kelas`) REFERENCES `kelas` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
