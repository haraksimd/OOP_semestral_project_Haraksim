DROP TABLE IF EXISTS todolist;
DROP TABLE IF EXISTS zaznamy;
DROP TABLE IF EXISTS spravy;
DROP TABLE IF EXISTS zviera;
DROP TABLE IF EXISTS clovek;

DROP SCHEMA IF EXISTS zoo ;
DROP DATABASE IF EXISTS db_zoo;

CREATE DATABASE IF NOT EXISTS db_zoo;

CREATE SCHEMA IF NOT EXISTS zoo DEFAULT CHARACTER SET utf8;

USE zoo;

CREATE TABLE IF NOT EXISTS zoo.clovek(
	id_clovek INT NOT NULL AUTO_INCREMENT,
    meno VARCHAR(45) NULL,
    priezvisko VARCHAR(45) NULL,
    oddelenie VARCHAR(100) NULL,
    cislo VARCHAR(10) NULL,
    login VARCHAR(20) UNIQUE,
    heslo VARCHAR(20),
    pozicia INT NOT NULL, 
    
    PRIMARY KEY (id_clovek)
);

CREATE TABLE IF NOT EXISTS zoo.zviera(
	id_zviera INT NOT NULL AUTO_INCREMENT,
    meno VARCHAR(45) NULL,
    druh VARCHAR(100) NULL,
    sekcia VARCHAR(50) NULL,
    
    PRIMARY KEY (id_zviera)
);

CREATE TABLE IF NOT EXISTS zoo.zaznamy(
	id_zaznam INT NOT NULL AUTO_INCREMENT,
	datum DATE NOT NULL,
    sprava VARCHAR(2000) NULL,
    id_zviera INT NOT NULL,
    id_clovek INT NOT NULL,
    
    
    PRIMARY KEY (id_zaznam),
    FOREIGN KEY (id_zviera) REFERENCES zviera (id_zviera),
    FOREIGN KEY (id_clovek) REFERENCES clovek (id_clovek)
);

CREATE TABLE IF NOT EXISTS zoo.spravy(
	id_spravy INT NOT NULL AUTO_INCREMENT,
    datum DATE NOT NULL,
    sprava VARCHAR(2500) NULL,
    id_cloveko INT NOT NULL,
    id_clovekp INT NOT NULL,
    
    
    PRIMARY KEY (id_spravy),
    CONSTRAINT prijmatel FOREIGN KEY (id_clovekp) REFERENCES zoo.clovek(id_clovek),
    CONSTRAINT odosielatel FOREIGN KEY (id_cloveko) REFERENCES zoo.clovek(id_clovek)
);

CREATE TABLE IF NOT EXISTS zoo.todolist(
	id_todolist INT NOT NULL auto_increment,
    uloha VARCHAR(1000) NULL,
    datum DATE NOT NULL,
    finished VARCHAR(20) NOT NULL,
    id_clovekautor INT NOT NULL,
    id_clovekkomu INT NOT NULL,
    
    PRIMARY KEY (id_todolist),
    CONSTRAINT autor FOREIGN KEY (id_clovekautor) REFERENCES zoo.clovek(id_clovek),
    CONSTRAINT komu FOREIGN KEY (id_clovekkomu) REFERENCES zoo.clovek(id_clovek)
);

INSERT INTO clovek(id_clovek, meno, priezvisko, oddelenie, cislo, login, heslo, pozicia) 
VALUES(1,'admin','admin','admin','admin','admin','admin',2),
(2,'jozko','ferko','zverolekar','123','jozko','ferko',1),
(3,'typek','dodko','vodic','1234','typek','dodko',0);

INSERT INTO clovek(meno,priezvisko,oddelenie,cislo,login,heslo,pozicia)
VALUES('no','no','no','no','no','no',2);

INSERT INTO zviera(id_zviera, meno, druh, sekcia)
VALUES(1,'Pablo','opica','opice A4'),
(2,'Tomas','pavian','opice A5');


INSERT INTO zaznamy(id_zaznam, datum, sprava, id_clovek, id_zviera)
VALUES(1,'2020-01-28','dobre mu je', 1, 1);

INSERT INTO todolist(id_todolist, uloha, datum, finished, id_clovekautor,id_clovekkomu)
VALUES(1,'umy Pabla','2020-01-29','no',1,2);

INSERT INTO todolist(uloha,datum,finished,id_clovekautor,id_clovekkomu)
VALUES('umy tomasa','2020-02-29','no',2,2),
('umy sam seba','2020-02-29','no',1,1);

INSERT INTO spravy(sprava,datum,id_cloveko,id_clovekp)VALUES('ahoj','2020-02-29','2','1');
INSERT INTO spravy(sprava,datum,id_cloveko,id_clovekp)VALUES('ahoj','2020-02-29','2','3');

SELECT * FROM spravy INNER JOIN clovek ON spravy.id_cloveko = clovek.id_clovek WHERE id_clovekp = 3;
SELECT * FROM zaznamy INNER JOIN clovek ON zaznamy.id_clovek = clovek.id_clovek WHERE id_clovek = 1;