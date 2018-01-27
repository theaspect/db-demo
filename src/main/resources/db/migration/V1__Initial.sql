-- fake data generated from http://www.fakeaddressgenerator.com

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
  department_id BIGINT,
  CONSTRAINT "employee_department_id" FOREIGN KEY (department_id) REFERENCES department (id)
);
--  Cyclic dependency
ALTER TABLE department
  ADD CONSTRAINT "department_manager_id" FOREIGN KEY (manager_id) REFERENCES employee (id);

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
  account_id BIGINT,
  CONSTRAINT "client_account_id" FOREIGN KEY (account_id) REFERENCES employee (id)
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

CREATE TABLE contract(
  id BIGINT SERIAL PRIMARY KEY,
  amount BIGINT,
  date_start TIMESTAMP,
  date_end TIMESTAMP,
  client_id BIGINT,
  account_id BIGINT,
  CONSTRAINT "contract_client_id" FOREIGN KEY (client_id) REFERENCES client (id),
  CONSTRAINT "contract_account_id" FOREIGN KEY (account_id) REFERENCES employee (id)
);

INSERT INTO contract (client_id, amount, date_start, date_end, account_id) VALUES
  (1,3223,'2016-03-25','2016-05-24',1),
  (2,6346,'2017-03-09','2017-04-23',2),
  (3,6127,'2017-10-11','2017-12-06',3),
  (4,3273,'2016-09-28','2016-11-20',4),
  (5,4713,'2016-05-01','2016-06-19',1),
  (6,3034,'2016-01-12','2016-03-12',2),
  (7,9982,'2016-02-20','2016-04-08',3),
  (8,5122,'2017-07-20','2017-08-30',4),
  (9,8960,'2016-06-30','2016-07-31',1),
  (10,4628,'2016-11-03','2016-12-31',2),
  (1,8529,'2016-05-03','2016-06-23',3),
  (2,1033,'2016-05-27','2016-07-23',4),
  (3,9201,'2016-01-02','2016-03-01',1),
  (4,5256,'2017-04-25','2017-06-15',2),
  (5,8803,'2017-10-31','2017-12-14',3),
  (6,4842,'2016-09-03','2016-10-08',4),
  (7,5589,'2016-03-30','2016-05-07',1),
  (8,2421,'2017-08-19','2017-10-14',2),
  (9,2937,'2016-08-16','2016-09-26',3),
  (10,6194,'2016-10-13','2016-12-09',4);

CREATE TABLE transaction (
  id BIGINT SERIAL PRIMARY KEY,
  amount BIGINT,
  date TIMESTAMP,
  contract_id BIGINT,
  CONSTRAINT "transaction_contract_id" FOREIGN KEY (contract_id) REFERENCES contract (id)
);

-- Some of contact values missed
-- Some of contracts never paid
INSERT INTO transaction (contract_id, amount, date) VALUES
  (13,9201,'2016-02-03'),
  (6,36,'2016-02-15'),
  (6,2889,'2016-02-18'),
  (6,109,'2016-03-11'),
  -- (7,9982,'2016-03-29'),
  (1,3223,'2016-04-05'),
  (17,2317,'2016-04-11'),
  (5,2391,'2016-05-05'),
  (17,3272,'2016-05-05'),
  -- (11,3427,'2016-05-13'),
  (12,13,'2016-05-29'),
  (5,2322,'2016-05-31'),
  (12,16,'2016-06-10'),
  (11,5102,'2016-06-23'),
  -- (12,1004,'2016-07-04'),
  (9,2615,'2016-07-09'),
  (9,1077,'2016-07-09'),
  (9,5268,'2016-07-27'),
  (16,4842,'2016-09-17'),
  -- (19,2937,'2016-09-22'),
  (20,1280,'2016-10-13'),
  (4,3273,'2016-10-30'),
  (20,4914,'2016-11-04'),
  (10,4628,'2016-11-17'),
  -- (2,3448,'2017-03-20'),
  (2,2898,'2017-04-12'),
  (14,2559,'2017-05-30'),
  (14,2697,'2017-06-02'),
  (8,1617,'2017-08-12'),
  -- (18,378,'2017-08-25'),
  (8,3505,'2017-08-29'),
  (18,41,'2017-09-08'),
  (18,2002,'2017-10-02'),
  (3,921,'2017-10-26'),
  -- (3,5032,'2017-11-07'),
  (15,790,'2017-11-11'),
  (3,174,'2017-11-30'),
  (15,1842,'2017-12-09'),
  (15,6171,'2017-12-10');
