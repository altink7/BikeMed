-- create user
CREATE USER bikemed WITH PASSWORD 'bikemed';

--create all databases
CREATE DATABASE lager WITH OWNER bikemed;
CREATE DATABASE werkstatt WITH OWNER bikemed;
CREATE DATABASE office WITH OWNER bikemed;


-- grant privileges
GRANT ALL PRIVILEGES ON DATABASE lager TO bikemed;
GRANT ALL PRIVILEGES ON DATABASE werkstatt TO bikemed;
GRANT ALL PRIVILEGES ON DATABASE office TO bikemed;

-- filling example data

-- connect to werkstatt
\c werkstatt

INSERT INTO Mitarbeiter (vorname, nachname, email, level, gehalt)
VALUES
    ('Max', 'Mustermann', 'max.mustermann@example.com', 'Mechanic', 3000.0),
    ('Anna', 'Musterfrau', 'anna.musterfrau@example.com', 'Technician', 3500.0),
    ('Peter', 'Schrauber', 'peter.schrauber@example.com', 'Engineer', 4000.0);

INSERT INTO konfiguration_entity ("stunden-satz", "werkstatt-name")
VALUES
    (50.0, 'Werkstatt 1');



-- connect to lager
\c lager

INSERT INTO PRODUKT (ID, DELIVERY_TIME_DAYS, NAME, PRICE, QUANTITY) VALUES
(1, 1, 'plattenReparatur', 15.0, 25)
,(2, 3, 'bremsen', 0.5, 100)
,(3, 5, 'schaltung', 100.0, 10)
,(4, 2, 'beleuchtungVorne', 10.0, 50)
,(5, 2, 'beleuchtungHinten', 10.0, 0)
,(6, 1, 'reflector', 5.0, 50)
,(7, 1, 'federung', 50.0, 12)
,(8, 1, 'rahmen', 100.0, 0)
,(9, 1, 'gabel', 50.0, 25)
,(10, 1, 'kettenantrieb', 50.0, 0)
,(11, 1, 'elektrischeKomponenten', 50.0, 0)
,(12, 1, 'reifen', 50.0, 0)
,(13, 1, 'pedale', 50.0, 0)
,(14, 1, 'sattel', 50.0, 0)
,(15, 1, 'lenker', 50.0, 0)
,(16, 1, 'schutzblech', 50.0, 0)
,(17, 1, 'klingel', 50.0, 0)
,(18, 1, 'ständer', 50.0, 0)
,(19, 1, 'schloss', 50.0, 0)
,(20, 1, 'korb', 50.0, 0)
,(21, 1, 'gepäckträger', 50.0, 0)
,(22, 1, 'flaschenhalter', 50.0, 0);

