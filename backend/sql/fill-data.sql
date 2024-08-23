DELIMITER $$

-- Drop the procedures if they already exist
DROP PROCEDURE IF EXISTS GenerateProjectDetails $$
DROP PROCEDURE IF EXISTS GenerateProjects $$
DROP PROCEDURE IF EXISTS GenerateProjectExpenses $$

CREATE PROCEDURE GenerateProjectDetails()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE description VARCHAR(100);
    DECLARE images_folder_url VARCHAR(100);
    DECLARE deleted BOOLEAN;

    WHILE i <= 10000
        DO
            SET description = CONCAT('Project ', i);
            SET images_folder_url = CONCAT('/images/project_', i);


            INSERT INTO `project_details` (`description`, `images_folder_url`) VALUES (description, images_folder_url);

            SET i = i + 1;
        END WHILE;
END $$

CREATE PROCEDURE GenerateProjects()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE title VARCHAR(100);
    DECLARE min_age INT;
    DECLARE max_age INT;
    DECLARE price DECIMAL(10, 2);
    DECLARE location VARCHAR(100);
    DECLARE project_details_id INT;

    WHILE i <= 10000
        DO
            SET title = CONCAT('Project ', i);
            SET min_age = FLOOR(RAND() * 80) + 10; -- Random age between 10 and 90
            SET max_age = FLOOR(RAND() * (100 - min_age)) + min_age; -- Random age between min_age and 100
            SET price = ROUND(RAND() * 900 + 100, 2); -- Random price between 100 and 1000
            SET location = CONCAT('Location ', i);
            SET project_details_id = i;


            INSERT INTO `project` (`title`, `min_age`, `max_age`, `price`, `location`, `project_details_id`)
            VALUES (title, min_age, max_age, price, location, project_details_id);

            SET i = i + 1;
        END WHILE;
END $$
CREATE PROCEDURE GenerateProjectExpenses()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE name VARCHAR(100);
    DECLARE price DECIMAL(10, 2);
    DECLARE project_id INT;
    DECLARE deleted BOOLEAN;

    WHILE i <= 10000
        DO
            SET name = CONCAT('Expense ', i);
            SET price = ROUND(RAND() * 90 + 10, 2); -- Random price between 10 and 100
            SET project_id = FLOOR(RAND() * 1000) + 1; -- Random project_id between 1 and 100,000
            SET deleted = FLOOR(RAND() * 2); -- Randomly set deleted to true or false

            INSERT INTO `project_expense` (`name`, `price`, `project_id`)
            VALUES (name, price, project_id);

            SET i = i + 1;
        END WHILE;
END $$

DELIMITER ;
CALL GenerateProjectDetails();
CALL GenerateProjects();
CALL GenerateProjectExpenses();
