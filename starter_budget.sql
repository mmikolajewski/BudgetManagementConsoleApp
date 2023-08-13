CREATE DATABASE household_budget CHARACTER SET utf8mb4 COLLATE utf8mb4_polish_ci;

use household_budget;

CREATE TABLE transaction (
	id INT NOT NULL AUTO_INCREMENT ,
    type VARCHAR(45) NOT NULL ,
    description VARCHAR(1000) NOT NULL, 
    amount DECIMAL NOT NULL, 
    date DATE,
    PRIMARY KEY(id)
    ); 
    
    INSERT INTO transaction (type, description, amount, date)
VALUES 
('wydatek', 'drewno', 21, '1995-12-12'),
('wydatek', 'smola', 55, '1995-12-12'),
('wydatek', 'jajka', 234, '1976-08-12'),
('przychod', 'motyka', 23, '1995-11-12'),
('przychod', 'kolo', 234, '1987-05-12'),
('przychod', 'wazon', 22, '1991-06-12');