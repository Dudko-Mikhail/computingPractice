-- MySQL Workbench Synchronization
-- Generated: 2021-08-08 20:10
-- Model: New Model
-- Version: 1.0
-- Project: Name of the project
-- Author: Misha

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `souvenirs` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE IF NOT EXISTS `souvenirs`.`maker` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `souvenirs`.`souvenir` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `maker_id` INT(11) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `production_date` DATE NOT NULL,
  `price` DECIMAL(12,2) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_souvenir_maker_idx` (`maker_id` ASC) VISIBLE,
  CONSTRAINT `fk_souvenir_maker`
    FOREIGN KEY (`maker_id`)
    REFERENCES `souvenirs`.`maker` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;