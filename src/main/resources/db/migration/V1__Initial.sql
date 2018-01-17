DROP TABLE departments IF EXISTS;
CREATE TABLE departments(id SERIAL, name VARCHAR(255));
INSERT INTO departments(name) VALUES
  ('Sales'),
  ('Marketing'),
  ('Development');

DROP TABLE employee IF EXISTS;
CREATE TABLE employee(
  id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255), id_department INT,
  FOREIGN KEY (id_department) REFERENCES departments(ID));
INSERT INTO employee(first_name, last_name, id_department) VALUES
 ('John', 'Woo', 1),
 ('Jeff', 'Dean', 2),
 ('Josh', 'Bloch', 3),
 ('Josh', 'Long', 1);
