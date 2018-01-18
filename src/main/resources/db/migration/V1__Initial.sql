-- SET MODE PostgreSQL;
DROP TABLE department IF EXISTS;
CREATE TABLE department(id BIGINT SERIAL PRIMARY KEY, name VARCHAR(255), manager_id BIGINT);
INSERT INTO department(name) VALUES
  ('Sales'),
  ('Marketing'),
  ('Development');

DROP TABLE employee IF EXISTS;
CREATE TABLE employee(
  id BIGINT SERIAL PRIMARY KEY, first_name VARCHAR(255), last_name VARCHAR(255), department_id BIGINT);
INSERT INTO employee(first_name, last_name, department_id) VALUES
 ('John', 'Woo', 1),
 ('Jeff', 'Dean', 2),
 ('Josh', 'Bloch', 3),
 ('Josh', 'Long', 1);

UPDATE department set manager_id = 1 where id = 1;
UPDATE department set manager_id = 2 where id = 2;
UPDATE department set manager_id = 1 where id = 3;

ALTER TABLE department add CONSTRAINT "department_manager_id" FOREIGN KEY (manager_id) REFERENCES employee(ID);
ALTER TABLE employee add CONSTRAINT "employee_department_id" FOREIGN KEY (department_id) REFERENCES department(ID);
