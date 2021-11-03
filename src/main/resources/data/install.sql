create schema IF NOT EXISTS wishshare;
use wishshare;

DROP TABLE IF EXISTS wish;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS user (
    user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    birthdate DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    password VARCHAR(129) NOT NULL
);

CREATE TABLE IF NOT EXISTS wishlist (
    wishlist_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id)
    REFERENCES user (user_id)
);

CREATE TABLE IF NOT EXISTS wish (
    wish_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    link VARCHAR(255) NOT NULL,
    price VARCHAR(255) NOT NULL,
    wishlist_id INT NOT NULL,
    reserved TINYINT NOT NULL,
    FOREIGN KEY (wishlist_id)
    REFERENCES wishlist (wishlist_id)
);