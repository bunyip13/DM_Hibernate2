CREATE TABLE ACCOUNT(id int primary key auto_increment, clientid int, amount int, iban VARCHAR(30));
CREATE TABLE CLIENT(id int primary key auto_increment, name VARCHAR(30), surname VARCHAR(30));