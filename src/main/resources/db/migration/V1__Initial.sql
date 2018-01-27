-- SET MODE PostgreSQL;
-- DROP TABLE department IF EXISTS;
CREATE TABLE department (
  id         BIGINT SERIAL PRIMARY KEY,
  name       VARCHAR(255),
  manager_id BIGINT
);
INSERT INTO department(name) VALUES
  ('Sales'),
  ('Marketing'),
  ('Development');

-- DROP TABLE employee IF EXISTS;
CREATE TABLE employee(
  id            BIGINT SERIAL PRIMARY KEY,
  first_name    VARCHAR(255),
  last_name     VARCHAR(255),
  department_id BIGINT
);
INSERT INTO employee(first_name, last_name, department_id) VALUES
 ('John', 'Woo', 1),
 ('Jeff', 'Dean', 2),
 ('Josh', 'Bloch', 3),
 ('Josh', 'Long', 1);

UPDATE department set manager_id = 1 where id = 1;
UPDATE department set manager_id = 2 where id = 2;
UPDATE department set manager_id = 1 where id = 3;

-- DROP TABLE client IF EXISTS;
CREATE TABLE client (
  id         BIGINT SERIAL PRIMARY KEY,
  name       VARCHAR(255),
  street     VARCHAR(255),
  city       VARCHAR(255),
  state      VARCHAR(255),
  zipcode    VARCHAR(255),
  account_id BIGINT
);
INSERT INTO client (name, street, city, state, zipcode, account_id) VALUES
  ('Berry', '3104 Doctors Drive', 'Los Angeles', 'California', '90017', 1),
  ('Ask Media', '2259 Oak Street', 'Old Forge', 'New York', '13420', 2),
  ('Pearl Softwares', '590 Sumner Street', 'Los Angeles', 'California', '90071', 3),
  ('Fairiprises', '1856 Redbud Drive', 'Long Island City', 'New York', '11101', 4),
  ('Icebergarts', '2465 Hillhaven Drive', 'Los Angeles', 'California', '90017', 1),
  ('Fluxystems', '4539 Plainfield Avenue', 'Waterloo', 'New York', '13165', 2),
  ('Bluetronics', '705 Gore Street', 'Houston', 'Texas', '77032', 3),
  ('Wizardman', '918 Melrose Street', 'Spokane Valley', 'Washington', '99206', 4),
  ('Spriteshade', '4524 Tree Frog Lane', 'AUSTIN', 'Texas', '73301', 1),
  ('Driftgate', '115 Union Street', 'Seattle', 'Washington', '98109', 2);


ALTER TABLE department
  ADD CONSTRAINT "department_manager_id" FOREIGN KEY (manager_id) REFERENCES employee (id);
ALTER TABLE employee
  ADD CONSTRAINT "employee_department_id" FOREIGN KEY (department_id) REFERENCES department (id);
ALTER TABLE client
  ADD CONSTRAINT "client_account_id" FOREIGN KEY (account_id) REFERENCES employee (id);
