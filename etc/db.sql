CREATE SEQUENCE gun_ids
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE guns
	(id INTEGER DEFAULT nextval('gun_ids'),
	name TEXT NOT NULL,
	CONSTRAINT guns_key PRIMARY KEY(id));

INSERT INTO guns(name) VALUES('Benelli SuperNova');
INSERT INTO guns(name) VALUES('ТОЗ-106');