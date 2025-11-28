create DATABASE cinema_DB;

create table seances(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        capacite INT,
                        horaire DATE,
                        film_id INT
);
CREATE TABLE films (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      titre VARCHAR(255),
                      duree INT,
                      categorie VARCHAR(255),
                      seances_id int,
                      FOREIGN KEY (seances_id)REFERENCES seances(id)
);
ALTER TABLE seances ADD constraint FOREIGN KEY (film_id) REFERENCES films(id);
CREATE TABLE spectateurs (

                        id INT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(50) UNIQUE,
                        email VARCHAR(100) UNIQUE,
                        ticket_id INT
);
CREATE TABLE tickets (
                        number INT PRIMARY KEY,
                        prix DOUBLE,
                        spectateur_id INT,
                        seance_id INT,
                        FOREIGN KEY (spectateur_id) REFERENCES Spectateurs(id),
                        FOREIGN KEY (seance_id) REFERENCES Seances(id)
);
ALTER TABLE spectatuers ADD CONSTRAINT FOREIGN KEY (ticket_id) REFERENCES tikets(ticket_numero)