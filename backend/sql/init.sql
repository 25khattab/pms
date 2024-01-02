CREATE database if not exists `trainopia_PMS`;

use `trainopia_PMS`;

SET foreign_key_checks = 0;

Drop table if exists `project`;
Drop table if exists `project_expense`;
Drop table if exists `client`;
Drop table if exists `team`;
Drop table if exists `volunteer`;
Drop table if exists `volunteer_team`;
Drop table if exists `project_details`;
Drop table if exists `client_project`;
Drop table if exists `volunteer_project`;

SET foreign_key_checks = 1;

CREATE TABLE `project_details` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`description` varchar(256) NOT NULL,
	`created_at` TIMESTAMP NOT NULL,
	`updated_at` TIMESTAMP NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `project` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `title` varchar(256) NOT NULL,
    `min_age` INT NOT NULL,
    `max_age` INT NOT NULL,
    `price` DECIMAL NOT NULL,
    `location` varchar(256) NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP NOT NULL,
    `project_details_id` INT NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `project_fk0` FOREIGN KEY (`project_details_id`) REFERENCES `project_details`(`id`)
);



CREATE TABLE `project_expense` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` varchar(256) NOT NULL,
    `price` DECIMAL NOT NULL,
    `created_at` TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP NOT NULL,
    `project_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `project_expense_fk0` FOREIGN KEY (`project_id`) REFERENCES `project`(`id`)
);



CREATE TABLE `client` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` varchar(256) NOT NULL,
	`phone_number` varchar(256) NOT NULL,
	`created_at` TIMESTAMP NOT NULL,
	`updated_at` TIMESTAMP NOT NULL,
	PRIMARY KEY (`id`)
);


CREATE TABLE `volunteer` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` varchar(256) NOT NULL,
	`created_at` TIMESTAMP NOT NULL,
	`updated_at` TIMESTAMP NOT NULL,
	PRIMARY KEY (`id`)
);


CREATE TABLE `team` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`name` varchar(256) NOT NULL,
	`created_at` TIMESTAMP NOT NULL,
	`updated_at` TIMESTAMP NOT NULL,
	`team_leader_volunteer_id` INT NOT NULL UNIQUE,
	`team_co_leader_volunteer_id` INT NOT NULL UNIQUE,
	PRIMARY KEY (`id`),
	CONSTRAINT `team_fk0` FOREIGN KEY (`team_leader_volunteer_id`) REFERENCES `volunteer`(`id`),
    CONSTRAINT `team_fk1` FOREIGN KEY (`team_co_leader_volunteer_id`) REFERENCES `volunteer`(`id`)
);

CREATE TABLE `volunteer_team` (
	`team_id` INT NOT NULL,
	`volunteer_id` INT NOT NULL,
	PRIMARY KEY (`team_id`,`volunteer_id`),
	CONSTRAINT `volunteer_team_fk0` FOREIGN KEY (`team_id`) REFERENCES `team`(`id`),
    CONSTRAINT `volunteer_team_fk1` FOREIGN KEY (`volunteer_id`) REFERENCES `volunteer`(`id`)
);

CREATE TABLE `client_project` (
	`price` DECIMAL NOT NULL,
	`created_at` TIMESTAMP NOT NULL,
	`updated_at` TIMESTAMP NOT NULL,
	`client_id` INT NOT NULL,
	`project_id` INT NOT NULL,
	PRIMARY KEY (`client_id`,`project_id`),
	CONSTRAINT `client_project_fk0` FOREIGN KEY (`client_id`) REFERENCES `client`(`id`),
    CONSTRAINT `client_project_fk1` FOREIGN KEY (`project_id`) REFERENCES `project`(`id`)
);

CREATE TABLE `volunteer_project` (
    `id` INT NOT NULL AUTO_INCREMENT,
	`expenses` DECIMAL NOT NULL,
	`created_at` TIMESTAMP NOT NULL,
	`updated_at` TIMESTAMP NOT NULL,
	`project_id` INT NOT NULL,
	`volunteer_id` INT NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `volunteer_project_fk0` FOREIGN KEY (`project_id`) REFERENCES `project`(`id`),
    CONSTRAINT `volunteer_project_fk1` FOREIGN KEY (`volunteer_id`) REFERENCES `volunteer`(`id`)
);
