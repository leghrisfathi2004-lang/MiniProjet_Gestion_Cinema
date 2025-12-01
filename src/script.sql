CREATE DATABASE cinema_db;
USE cinema_db;

CREATE TABLE films (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(45),
    duree INT,
    categorie VARCHAR(45)
);

CREATE TABLE seances (
    id INT AUTO_INCREMENT PRIMARY KEY,
    capacite INT,
    horaire DATE
);

CREATE TABLE films_seances (
    films_id INT,
    seances_id INT,
    PRIMARY KEY (films_id, seances_id),
    FOREIGN KEY (films_id) REFERENCES films(id),
    FOREIGN KEY (seances_id) REFERENCES seances(id)
);

CREATE TABLE spectateurs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45),
    email VARCHAR(100)
);

CREATE TABLE tickets (
    numero INT PRIMARY KEY,
    prix INT,
    seances_id INT,
    spectateurs_id INT,
    FOREIGN KEY (seances_id) REFERENCES seances(id),
    FOREIGN KEY (spectateurs_id) REFERENCES spectateurs(id)
);
