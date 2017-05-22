CREATE DATABASE IF NOT EXISTS `deal_users`;
USE `deal_users`;
--
-- Table structure for table `role`
--

-- Table: users
CREATE TABLE users (
  id                        INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username                  VARCHAR(255) NOT NULL,
  password                  VARCHAR(255) NOT NULL,
  is_registration_confirmed TINYINT(1)   NOT NULL DEFAULT 0,
  login                     VARCHAR(65)  NOT NULL
)
  ENGINE = InnoDB;

-- Table: roles
CREATE TABLE roles (
  id   INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
)
  ENGINE = InnoDB;

-- Table for mapping user and roles: user_roles
CREATE TABLE user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id),

  UNIQUE (user_id, role_id)
)
  ENGINE = InnoDB;

-- Insert data
INSERT INTO users VALUES (1, 'admin', '$2a$11$s66VKWJRvCLFt4gwqSy4Me5eWv2g915ALuZv3E8V5MLXdfBz75BHS', '1', 'admin');

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');
INSERT INTO roles VALUES (3, 'ROLE_NOT_ACTIVATED_USER');
INSERT INTO roles VALUES (4, 'ROLE_ANONYMOUS');

INSERT INTO user_roles VALUES (1, 2);

/*ALTER TABLE `deal_users`.`users`
  ADD COLUMN `is_registration_confirmed` TINYINT(1) NOT NULL DEFAULT 0 AFTER `password`;*/

ALTER TABLE `deal_users`.`users`
  ADD COLUMN `key_for_registration_confirm` VARCHAR(45) NULL
  AFTER `is_registration_confirmed`,
  ADD UNIQUE INDEX `key_UNIQUE` (`key_for_registration_confirm` ASC),
  ADD COLUMN `first_name` VARCHAR(65) NULL
  AFTER `login`,
  ADD COLUMN `gender` ENUM ('male', 'female') NULL
  AFTER `first_name`,
  ADD COLUMN `date_of_birth` DATE NULL
  AFTER `gender`,
  ADD COLUMN `country` VARCHAR(65) NULL DEFAULT NULL
  AFTER `date_of_birth`,
  ADD COLUMN `date_of_registration` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
  AFTER `login`,
  ADD COLUMN `path_to_vatar` VARCHAR(255) NULL DEFAULT NULL
  AFTER `country`;


CREATE TABLE `deal_users`.`user_files` (
  `id`           INT(11)      NOT NULL AUTO_INCREMENT,
  `type`         VARCHAR(45)  NOT NULL,
  `path_to_file` VARCHAR(255) NOT NULL,
  `service_name` VARCHAR(255) NULL DEFAULT NULL,
  `user_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;


ALTER TABLE `deal_users`.`services`
  ADD CONSTRAINT `user_id`
FOREIGN KEY (`id`)
REFERENCES `deal_users`.`users` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `deal_users`.`user_files`
  ADD CONSTRAINT `user_id_key`
FOREIGN KEY (`user_id`)
REFERENCES `deal_users`.`users` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


/*ALTER TABLE `deal_users`.`user_files`
  ADD COLUMN `service_name` VARCHAR(255) NULL DEFAULT NULL
  AFTER `path_to_file`;

ALTER TABLE `deal_users`.`user_files`
  ADD COLUMN `user_id` INT(11) NULL DEFAULT NULL AFTER `service_name`;
*/