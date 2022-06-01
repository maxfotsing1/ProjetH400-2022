CREATE DATABASE BDprojecct;
USE BDprojecct;

CREATE TABLE `Personnes` (
	`idpersonnes` INT NOT NULL AUTO_INCREMENT,
	`firstName` varchar(255) NOT NULL,
	`lastName` varchar(255) NOT NULL,
	`dateofbirth` DATE NOT NULL,
	`numeroRN` TEXT NOT NULL,
	`adresse` varchar(255) NOT NULL,
	`tel` TEXT(255) NOT NULL,
	PRIMARY KEY (`idpersonnes`)
);

CREATE TABLE `Patients` (
	`idpatients` INT NOT NULL AUTO_INCREMENT,
	`idpersonnes` INT NOT NULL,
	`pseudo` varchar(255) NOT NULL,
	`mdp` varchar(255) NOT NULL,
	PRIMARY KEY (`idpatients`)
);

CREATE TABLE `Docteur` (
	`iddocteur` INT NOT NULL AUTO_INCREMENT,
	`idpersonnes` INT NOT NULL,
	`pseudodocteur` varchar(255) NOT NULL,
	`mdpdocteur` varchar(255) NOT NULL,
	PRIMARY KEY (`iddocteur`)
);

CREATE TABLE `RDV` (
	`idrdv` INT NOT NULL AUTO_INCREMENT,
	`patients` INT NOT NULL,
	`docteur` INT NOT NULL,
	`daterdv` DATE NOT NULL,
	`heurerdv` TIME NOT NULL,
	`presence` enum('NO','YES','CANCELLED') NOT NULL DEFAULT 'NO',
	`pec` enum('NO','YES','Done') NOT NULL DEFAULT 'NO',
	PRIMARY KEY (`idRDV`)
);

ALTER TABLE `Patients` ADD CONSTRAINT `Patients_fk0` FOREIGN KEY (`idpersonnes`) REFERENCES `Personnes`(`idpersonnes`);

ALTER TABLE `Docteur` ADD CONSTRAINT `Docteur_fk0` FOREIGN KEY (`idpersonnes`) REFERENCES `Personnes`(`idpersonnes`);

ALTER TABLE `RDV` ADD CONSTRAINT `RDV_fk0` FOREIGN KEY (`patients`) REFERENCES `Patients`(`idpatients`);

ALTER TABLE `RDV` ADD CONSTRAINT `RDV_fk1` FOREIGN KEY (`docteur`) REFERENCES `Docteur`(`iddocteur`);


INSERT INTO `personnes` (`idpersonnes`, `firstName`, `lastName`, `dateofbirth`, `numeroRN`, `adresse`, `tel`) VALUES (NULL, 'Fred', 'Murisier', '1996-04-19', '1234556', 'Genève', '+33450405218');
INSERT INTO `personnes` (`idpersonnes`, `firstName`, `lastName`, `dateofbirth`, `numeroRN`, `adresse`, `tel`) VALUES (NULL, 'Bob', 'Dupont', '1998-05-29', '1222256', 'Paris', '+33489755218');
INSERT INTO `personnes` (`idpersonnes`, `firstName`, `lastName`, `dateofbirth`, `numeroRN`, `adresse`, `tel`) VALUES (NULL, 'Magda', 'Bialas', '1997-01-03', '091243869', 'Varsovie', '+33489755314');


INSERT INTO `docteur` (`iddocteur`, `idpersonnes`, `pseudodocteur`, `mdpdocteur`) VALUES (NULL, '1', 'pseudoFred', 'mdp1');
INSERT INTO `docteur` (`iddocteur`, `idpersonnes`, `pseudodocteur`, `mdpdocteur`) VALUES (NULL, '2', 'pseudoBob', 'mdp2'); 
INSERT INTO `docteur` (`iddocteur`, `idpersonnes`, `pseudodocteur`, `mdpdocteur`) VALUES (NULL, '3', 'pseudoMagda', 'mdp3');




INSERT INTO `patients` (`idpatients`, `idpersonnes`, `pseudo`, `mdp`) VALUES (NULL, '1', 'pseudoCamille', 'mdpCamille'); 
INSERT INTO `patients` (`idpatients`, `idpersonnes`, `pseudo`, `mdp`) VALUES (NULL, '2', 'pseudoCloe', 'mdpCloe'); 
INSERT INTO `patients` (`idpatients`, `idpersonnes`, `pseudo`, `mdp`) VALUES (NULL, '3', 'pseudoJeanPascal', 'mdpJeanPascal'); 

INSERT INTO `rdv` (`idrdv`, `patients`, `docteur`, `daterdv`, `heurerdv`, `presence`, `pec`) VALUES (NULL, '1', '1', '2022-05-25', '12:22:00', '0', '0');
INSERT INTO `rdv` (`idrdv`, `patients`, `docteur`, `daterdv`, `heurerdv`, `presence`, `pec`) VALUES (NULL, '1', '1', '2022-05-30', '13:00:00', '0', '0'); 
INSERT INTO `rdv` (`idrdv`, `patients`, `docteur`, `daterdv`, `heurerdv`, `presence`, `pec`) VALUES (NULL, '2', '2', '2022-05-30', '8:30:00', '0', '0'); 
