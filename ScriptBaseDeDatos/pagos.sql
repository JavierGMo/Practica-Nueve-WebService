-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 21-05-2020 a las 23:27:25
-- Versión del servidor: 10.4.11-MariaDB
-- Versión de PHP: 7.4.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `pagos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagobanco`
--

CREATE TABLE `pagobanco` (
  `IDPAGOBANCO` int(11) NOT NULL,
  `CUENTA` varchar(50) NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `MONTO` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `pagobanco`
--

INSERT INTO `pagobanco` (`IDPAGOBANCO`, `CUENTA`, `NOMBRE`, `MONTO`) VALUES
(1, '1231', 'javier', 5000),
(2, '552233', 'Javire ', 300),
(3, '55335590', 'Javier Alonso', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pagopaypal`
--

CREATE TABLE `pagopaypal` (
  `IDPAGOPP` int(11) NOT NULL,
  `CUENTA` varchar(50) NOT NULL,
  `NOMBRE` varchar(50) NOT NULL,
  `MONTO` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `pagopaypal`
--

INSERT INTO `pagopaypal` (`IDPAGOPP`, `CUENTA`, `NOMBRE`, `MONTO`) VALUES
(1, 'correo@das.com', 'Javier Mora', 3000),
(2, 'corrxxeo@das.com', 'jaciel', 12123),
(3, 'correochido@correochido.com', 'Peterson', 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `pagobanco`
--
ALTER TABLE `pagobanco`
  ADD PRIMARY KEY (`IDPAGOBANCO`);

--
-- Indices de la tabla `pagopaypal`
--
ALTER TABLE `pagopaypal`
  ADD PRIMARY KEY (`IDPAGOPP`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `pagobanco`
--
ALTER TABLE `pagobanco`
  MODIFY `IDPAGOBANCO` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pagopaypal`
--
ALTER TABLE `pagopaypal`
  MODIFY `IDPAGOPP` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
