CREATE
database if not exists `trainopia_PMS`;

use
`trainopia_PMS`;

SET
foreign_key_checks = 0;

Drop table if exists `project`;
Drop table if exists `project_expense`;
Drop table if exists `client`;
Drop table if exists `team`;
Drop table if exists `user`;
Drop table if exists `user_login_data`;
Drop table if exists `volunteer_team`;
Drop table if exists `project_details`;
Drop table if exists `client_project`;
Drop table if exists `volunteer_project`;

SET
foreign_key_checks = 1;

CREATE TABLE `project_details`
(
    `id`                INT                                 NOT NULL AUTO_INCREMENT,
    `description`       varchar(100)                        NOT NULL,
    `images_folder_url` varchar(100)                        NOT NULL,
    `created_at`        TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`        TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `project`
(
    `id`                 INT                                 NOT NULL AUTO_INCREMENT,
    `title`              varchar(100)                        NOT NULL,
    `min_age`            INT                                 NOT NULL,
    `max_age`            INT                                 NOT NULL,
    `price`              DECIMAL                             NOT NULL,
    `location`           varchar(100)                        NOT NULL,
    `created_at`         TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`         TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    `project_details_id` INT                                 NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `project_fk0` FOREIGN KEY (`project_details_id`) REFERENCES `project_details` (`id`)
);



CREATE TABLE `project_expense`
(
    `id`         INT                                 NOT NULL AUTO_INCREMENT,
    `name`       varchar(100)                        NOT NULL,
    `price`      DECIMAL                             NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    `project_id` INT                                 NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `project_expense_fk0` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
);



CREATE TABLE `client`
(
    `id`           INT                                 NOT NULL AUTO_INCREMENT,
    `name`         varchar(100)                        NOT NULL,
    `phone_number` varchar(100)                        NOT NULL,
    `created_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
);



CREATE TABLE `user_login_data`
(
    `id`         INT                                 NOT NULL AUTO_INCREMENT,
    `user_name`  varchar(100)                        NOT NULL UNIQUE,
    `email`      varchar(100)                        NOT NULL UNIQUE,
    `password`   varchar(256)                        NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `user`
(
    `id`                 INT                                 NOT NULL AUTO_INCREMENT,
    `first_name`         varchar(100)                        NOT NULL,
    `last_name`          varchar(100)                        NOT NULL,
    `role`               varchar(100)                        NOT NUll,
    `created_at`         TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`         TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    `user_login_data_id` INT                                 NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `user_fk0` FOREIGN KEY (`user_login_data_id`) REFERENCES `user_login_data` (`id`)
);

CREATE TABLE `team`
(
    `id`                          INT                                 NOT NULL AUTO_INCREMENT,
    `name`                        varchar(100)                        NOT NULL,
    `created_at`                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`                  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    `team_leader_volunteer_id`    INT                                 NOT NULL UNIQUE,
    `team_co_leader_volunteer_id` INT                                 NOT NULL UNIQUE,
    PRIMARY KEY (`id`),
    CONSTRAINT `team_fk0` FOREIGN KEY (`team_leader_volunteer_id`) REFERENCES `volunteer` (`id`),
    CONSTRAINT `team_fk1` FOREIGN KEY (`team_co_leader_volunteer_id`) REFERENCES `volunteer` (`id`)
);

CREATE TABLE `volunteer_team`
(
    `team_id`      INT                                 NOT NULL,
    `volunteer_id` INT                                 NOT NULL,
    `created_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (`team_id`, `volunteer_id`),
    CONSTRAINT `volunteer_team_fk0` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`),
    CONSTRAINT `volunteer_team_fk1` FOREIGN KEY (`volunteer_id`) REFERENCES `volunteer` (`id`)
);

CREATE TABLE `client_project`
(
    `price`      DECIMAL                             NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at` DATETIME  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    `client_id`  INT                                 NOT NULL,
    `project_id` INT                                 NOT NULL,
    PRIMARY KEY (`client_id`, `project_id`),
    CONSTRAINT `client_project_fk0` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
    CONSTRAINT `client_project_fk1` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
);

CREATE TABLE `volunteer_project`
(
    `id`           INT                                 NOT NULL AUTO_INCREMENT,
    `expenses`     DECIMAL                             NOT NULL,
    `created_at`   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    `updated_at`   DATETIME  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL,
    `project_id`   INT                                 NOT NULL,
    `volunteer_id` INT                                 NOT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `volunteer_project_fk0` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`),
    CONSTRAINT `volunteer_project_fk1` FOREIGN KEY (`volunteer_id`) REFERENCES `volunteer` (`id`)
);


-- Sample Data Entries for Project_Details Table
INSERT INTO `project_details` (`description`, `images_folder_url`)
VALUES ('Community Cleanup Project', '/images/community_cleanup'),
       ('Youth Mentorship Program', '/images/youth_mentorship'),
       ('Food Drive Initiative', '/images/food_drive'),
       ('Environmental Awareness Workshop', '/images/environmental_awareness'),
       ('Senior Citizens Social Hour', '/images/senior_citizens_social_hour'),
       ('Art and Craft Workshop', '/images/art_and_craft_workshop'),
       ('Tech Literacy Program', '/images/tech_literacy_program'),
       ('Animal Shelter Support Project', '/images/animal_shelter_support'),
       ('Health and Fitness Camp', '/images/health_and_fitness_camp'),
       ('Community Garden Project', '/images/community_garden');

-- Sample Data Entries for Project Table
INSERT INTO `project` (`title`, `min_age`, `max_age`, `price`, `location`, `project_details_id`)
VALUES ('Community Cleanup', 18, 60, 500.00, 'City Park', 1),
       ('Youth Mentorship Program', 20, 30, 300.00, 'Community Center', 2),
       ('Food Drive', 16, 70, 200.00, 'Local Food Bank', 3),
       ('Environmental Awareness Workshop', 25, 50, 400.00, 'Nature Reserve', 4),
       ('Senior Citizens Social Hour', 60, 90, 100.00, 'Senior Living Center', 5),
       ('Art and Craft Workshop', 10, 16, 150.00, 'Community Hall', 6),
       ('Tech Literacy Program', 30, 60, 350.00, 'School Auditorium', 7),
       ('Animal Shelter Support', 18, 70, 250.00, 'Local Animal Shelter', 8),
       ('Health and Fitness Camp', 18, 40, 450.00, 'Fitness Center', 9),
       ('Community Garden Project', 25, 60, 200.00, 'City Greenhouse', 10);