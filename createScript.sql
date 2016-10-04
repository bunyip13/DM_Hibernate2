CREATE TABLE ACCOUNT(id int primary key auto_increment, clientid int, amount int, iban VARCHAR(30));
CREATE TABLE CLIENT(id int primary key auto_increment, name VARCHAR(30), surname VARCHAR(30));
--CREATE TABLE REGULARCLIENT(id int primary key auto_increment, age int, salary int);
--clienttype VARCHAR(30), RC_AGE int, RC_SALARY int