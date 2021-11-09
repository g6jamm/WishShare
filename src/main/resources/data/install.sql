CREATE SCHEMA IF NOT EXISTS wishshare;
USE wishshare;

DROP TABLE IF EXISTS wish;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS user
(
    user_id    INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL UNIQUE,
    birthdate  DATE         NOT NULL,
    gender     VARCHAR(10)  NOT NULL,
    password   VARCHAR(129) NOT NULL
);

CREATE TABLE IF NOT EXISTS wishlist
(
    wishlist_id INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    # token       VARCHAR(100) NOT NULL,
    user_id     INT          NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES user (user_id)
);

CREATE TABLE IF NOT EXISTS wish
(
    wish_id     INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    link        VARCHAR(255) NOT NULL,
    price       VARCHAR(255) NOT NULL,
    wishlist_id INT          NOT NULL,
    reserved    TINYINT      NOT NULL,
    FOREIGN KEY (wishlist_id)
        REFERENCES wishlist (wishlist_id)
);

-- Generate test data
INSERT INTO user (user_id, first_name, last_name, email, birthdate, gender, password)
VALUES (1, 'demo', 'demo', 'demo@demo.com', '2021-11-08', 'MALE', 'demo');

#INSERT INTO wishlist (wishlist_id, name, token, user_id)
#VALUES (1, 'Test list', 'token', 1);

#INSERT INTO wish (wish_id, name, link, price, wishlist_id, reserved)
#VALUES (1, 'Pixie hat', 'https://demo.com', '50 kr.', 1, 0);

