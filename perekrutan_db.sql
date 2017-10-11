-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 26, 2016 at 06:40 PM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `perekrutan_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `karyawan_baru`
--

CREATE TABLE IF NOT EXISTS `karyawan_baru` (
  `id_karyawan` varchar(5) NOT NULL,
  `nama_karyawan` varchar(30) NOT NULL,
  `posisi_karyawan` varchar(30) NOT NULL,
  `tgl_masuk` date DEFAULT NULL,
  `status` tinytext NOT NULL,
  `gapok` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `karyawan_baru`
--

INSERT INTO `karyawan_baru` (`id_karyawan`, `nama_karyawan`, `posisi_karyawan`, `tgl_masuk`, `status`, `gapok`) VALUES
('K001', 'ANA', 'ADMINISTRASI', '2016-01-15', 'tetap', 2350000),
('K002', 'CACA', 'KASIR', '2016-01-19', 'kontrak', 2200000),
('K003', 'DEDE', 'SALES', '2016-03-15', 'kontrak', 2500000),
('K004', 'EKA', 'SALES', '2016-03-15', 'kontrak', 2500000),
('K005', 'FEMI', 'OPT. TELEPON', '2016-03-23', 'kontrak', 2000000),
('K006', 'GITA', 'OPT. TELEPON', '2016-03-23', 'kontrak', 2000000),
('K007', 'HANA', 'ADMINISTRASI', '2016-03-23', 'kontrak', 2350000),
('K008', 'IKA', 'KASIR', '2016-04-07', 'kontrak', 2200000),
('K009', 'JONO', 'IT', '2016-04-22', 'kontrak', 2650000);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `username` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`username`, `password`) VALUES
('admin', '12345');

-- --------------------------------------------------------

--
-- Table structure for table `masa_kerja`
--

CREATE TABLE IF NOT EXISTS `masa_kerja` (
  `id_masakerja` varchar(5) NOT NULL,
  `nama_masakerja` varchar(30) NOT NULL,
  `posisi_masakerja` varchar(30) NOT NULL,
  `jenkel_masakerja` varchar(9) NOT NULL,
  `alamat_masakerja` varchar(50) NOT NULL,
  `telepon_masakerja` varchar(12) NOT NULL,
  `tempatlahir_masakerja` varchar(20) NOT NULL,
  `tanggallahir_masakerja` date NOT NULL,
  `agama_masakerja` varchar(10) NOT NULL,
  `pendidikan_masakerja` varchar(5) NOT NULL,
  `status_masakerja` varchar(13) NOT NULL,
  `tanggalmasuk` date NOT NULL,
  `tanggalkeluar` date NOT NULL,
  `masakerja` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `masa_kerja`
--

INSERT INTO `masa_kerja` (`id_masakerja`, `nama_masakerja`, `posisi_masakerja`, `jenkel_masakerja`, `alamat_masakerja`, `telepon_masakerja`, `tempatlahir_masakerja`, `tanggallahir_masakerja`, `agama_masakerja`, `pendidikan_masakerja`, `status_masakerja`, `tanggalmasuk`, `tanggalkeluar`, `masakerja`) VALUES
('M001', 'ANA', 'ADMINISTRASI', 'wanita', 'JAKARTA', '081345876543', 'JOGJA', '1993-10-06', 'HINDU', 'D3', 'BELUM MENIKAH', '2016-01-15', '2016-05-10', '3'),
('M002', 'CACA', 'KASIR', 'wanita', 'JAKARTA', '081345876890', 'TANGERANG', '1990-07-07', 'KRISTEN', 'SMP', 'JANDA', '2016-01-19', '2016-05-10', '3'),
('M003', 'DEDE', 'SALES', 'pria', 'JAKARTA', '081245674576', 'GARUT', '1992-04-08', 'ISLAM', 'SMA', 'MENIKAH', '2016-03-15', '2016-05-10', '1'),
('M004', 'EKA', 'SALES', 'pria', 'JAKARTA', '081245674587', 'BANDUNG', '1992-03-20', 'ISLAM', 'SMA', 'MENIKAH', '2016-03-15', '2016-05-10', '1 bulan'),
('M005', 'FEMI', 'OPT. TELEPON', 'wanita', 'JAKARTA', '081245674592', 'TANGERANG', '1992-03-20', 'ISLAM', 'SMA', 'MENIKAH', '2016-03-23', '2016-05-10', '1'),
('M006', 'GITA', 'OPT. TELEPON', 'wanita', 'JAKARTA', '087712457890', 'SEMARANG', '1990-03-22', 'ISLAM', 'SMA', 'MENIKAH', '2016-03-23', '2016-05-10', '1'),
('M007', 'HANA', 'OPT. TELEPON', 'wanita', 'JAKARTA', '087712457875', 'BANJAR', '1990-05-24', 'ISLAM', 'SMA', 'MENIKAH', '2016-03-23', '2016-05-10', '1'),
('M008', 'IKA', 'KASIR', 'wanita', 'JAKARTA', '087712457880', 'BREBES', '1990-05-15', 'ISLAM', 'SMA', 'MENIKAH', '2016-04-07', '2016-05-10', '1'),
('M009', 'JONO', 'IT', 'pria', 'JAKARTA', '087712456850', 'BOGOR', '1992-06-21', 'ISLAM', 'SMA', 'MENIKAH', '2016-04-09', '2016-05-10', '1'),
('M010', 'KIKI', 'IT', 'pria', 'JAKARTA', '087712455863', 'JAKARTA', '1989-06-21', 'KRISTEN', 'D3', 'MENIKAH', '2015-06-15', '2016-05-10', '11');

-- --------------------------------------------------------

--
-- Table structure for table `pelamar`
--

CREATE TABLE IF NOT EXISTS `pelamar` (
  `id_pelamar` varchar(5) NOT NULL,
  `nama_pelamar` varchar(30) NOT NULL,
  `jenkel_pelamar` varchar(9) NOT NULL,
  `alamat_pelamar` varchar(50) NOT NULL,
  `telepon_pelamar` varchar(12) NOT NULL,
  `tempat_lahir_pelamar` varchar(20) NOT NULL,
  `tgl_lahir_pelamar` date NOT NULL,
  `agama_pelamar` varchar(10) NOT NULL,
  `pendidikan_pelamar` varchar(5) NOT NULL,
  `status_pelamar` varchar(13) NOT NULL,
  `keahlian_pelamar` varchar(50) NOT NULL,
  `pengalaman_pelamar` varchar(50) NOT NULL,
  `posisi_pelamar` varchar(30) NOT NULL,
  `tgl_pelamar` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pelamar`
--

INSERT INTO `pelamar` (`id_pelamar`, `nama_pelamar`, `jenkel_pelamar`, `alamat_pelamar`, `telepon_pelamar`, `tempat_lahir_pelamar`, `tgl_lahir_pelamar`, `agama_pelamar`, `pendidikan_pelamar`, `status_pelamar`, `keahlian_pelamar`, `pengalaman_pelamar`, `posisi_pelamar`, `tgl_pelamar`) VALUES
('P001', 'ANA', 'wanita', 'JAKARTA', '081311245642', 'BANDUNG', '1988-06-17', 'ISLAM', 'SMA', 'MENIKAH', 'mengetik dengan cepat', 'pernah bekerja sebagai administrasi', 'ADMINISTRASI', '2014-06-12'),
('P002', 'BENI', 'pria', 'BANDUNG', '081312345679', 'BANDUNG', '1991-01-12', 'HINDU', 'SMP', 'DUDA', 'mengerti mesin', 'tahu jabodetabek', 'DRIVER', '2016-01-03'),
('P003', 'CACA', 'wanita', 'JAKARTA', '081511234521', 'TANGERANG', '1991-01-11', 'BUDHA', 'SMA', 'MENIKAH', 'pandai hitung', 'cepat menghitung', 'KASIR', '2016-01-01'),
('P004', 'DEDE', 'pria', 'JAKARTA', '081245674576', 'GARUT', '1992-04-08', 'ISLAM', 'SMA', 'MENIKAH', 'jualan', 'jual barang', 'SALES', '2016-04-12'),
('P005', 'EKA', 'pria', 'JAKARTA', '081245674589', 'BANDUNG', '1992-03-20', 'ISLAM', 'SMA', 'MENIKAH', 'jualan', 'jual barang', 'SALES', '2016-04-12'),
('P006', 'FEMI', 'wanita', 'JAKARTA', '081245674592', 'TANGERANG', '1992-03-20', 'ISLAM', 'SMA', 'MENIKAH', 'komunikasi baik', 'komunikasi', 'OPT. TELEPON', '2016-04-14'),
('P007', 'GITA', 'wanita', 'JAKARTA', '087712457890', 'SEMARANG', '1990-03-22', 'ISLAM', 'SMA', 'MENIKAH', 'suka bicara', 'suka bicara', 'OPT. TELEPON', '2016-04-15'),
('P008', 'HANA', 'wanita', 'JAKARTA', '087712457875', 'BANJAR', '1990-05-24', 'ISLAM', 'SMA', 'MENIKAH', 'ketik 10 jari', 'buat rekap data', 'ADMINISTRASI', '2016-04-15'),
('P009', 'IKA', 'wanita', 'JAKARTA', '087712457880', 'BREBES', '1990-05-15', 'ISLAM', 'SMA', 'MENIKAH', 'terima uang', 'kasir indomaret', 'KASIR', '2016-04-15'),
('P010', 'JONO', 'pria', 'JAKARTA', '087712456850', 'BOGOR', '1992-06-21', 'ISLAM', 'SMA', 'MENIKAH', 'trouble network', 'IT Support', 'IT', '2016-04-15');

-- --------------------------------------------------------

--
-- Table structure for table `permintaan_karyawan`
--

CREATE TABLE IF NOT EXISTS `permintaan_karyawan` (
  `posisi_permintaan` varchar(30) NOT NULL,
  `kebutuhan_permintaan` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `permintaan_karyawan`
--

INSERT INTO `permintaan_karyawan` (`posisi_permintaan`, `kebutuhan_permintaan`) VALUES
('ADMINISTRASI', 2),
('DRIVER', 3),
('IT', 1),
('KASIR', 1),
('OB', 2),
('OPT. TELEPON', 2),
('SALES', 5),
('TECHNICAL SUPPORT', 4);

-- --------------------------------------------------------

--
-- Table structure for table `tes_calon_karyawan`
--

CREATE TABLE IF NOT EXISTS `tes_calon_karyawan` (
  `id_tes` varchar(5) NOT NULL,
  `nama_tes` varchar(30) NOT NULL,
  `posisi_tes` varchar(30) NOT NULL,
  `interview_tes` varchar(5) NOT NULL,
  `psikotes_tes` varchar(5) NOT NULL,
  `kemampuan_tes` varchar(5) NOT NULL,
  `hasil_tes` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tes_calon_karyawan`
--

INSERT INTO `tes_calon_karyawan` (`id_tes`, `nama_tes`, `posisi_tes`, `interview_tes`, `psikotes_tes`, `kemampuan_tes`, `hasil_tes`) VALUES
('T001', 'ANA', 'ADMINISTRASI', 'lolos', 'lolos', 'lolos', 'lolos'),
('T002', 'BENI', 'DRIVER', 'lolos', 'lolos', 'gagal', 'gagal'),
('T003', 'CACA', 'KASIR', 'lolos', 'lolos', 'lolos', 'lolos'),
('T004', 'DEDE', 'SALES', 'lolos', 'lolos', 'lolos', 'lolos'),
('T005', 'EKA', 'SALES', 'lolos', 'lolos', 'lolos', 'lolos'),
('T006', 'FEMI', 'OPT. TELEPON', 'lolos', 'lolos', 'lolos', 'lolos'),
('T007', 'GITA', 'OPT. TELEPON', 'lolos', 'lolos', 'lolos', 'lolos'),
('T008', 'HANA', 'ADMINISTRASI', 'lolos', 'lolos', 'lolos', 'lolos'),
('T009', 'IKA', 'KASIR', 'lolos', 'lolos', 'lolos', 'lolos'),
('T010', 'JONO', 'IT', 'lolos', 'lolos', 'lolos', 'lolos');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `karyawan_baru`
--
ALTER TABLE `karyawan_baru`
 ADD PRIMARY KEY (`id_karyawan`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
 ADD PRIMARY KEY (`username`);

--
-- Indexes for table `masa_kerja`
--
ALTER TABLE `masa_kerja`
 ADD PRIMARY KEY (`id_masakerja`);

--
-- Indexes for table `pelamar`
--
ALTER TABLE `pelamar`
 ADD PRIMARY KEY (`id_pelamar`);

--
-- Indexes for table `permintaan_karyawan`
--
ALTER TABLE `permintaan_karyawan`
 ADD PRIMARY KEY (`posisi_permintaan`);

--
-- Indexes for table `tes_calon_karyawan`
--
ALTER TABLE `tes_calon_karyawan`
 ADD PRIMARY KEY (`id_tes`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
