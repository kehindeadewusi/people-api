DROP TABLE IF EXISTS PERSON;

CREATE TABLE person (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  age INT DEFAULT NULL,
  favourite_colour VARCHAR(30) DEFAULT NULL
);

INSERT INTO person (first_name, last_name, age, favourite_colour) VALUES
  ('Keni', 'Adewusi', 19, 'Black'),
  ('James', 'Maddisson', 24, 'White'),
  ('Kanu', 'Nwankwo', 44, 'Gray'),
  ('Jayjay', 'Okocha', 47, 'Red');