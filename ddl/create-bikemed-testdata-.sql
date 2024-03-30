INSERT INTO Mitarbeiter (vorname, nachname, email, level, gehalt)
VALUES
    ('Max', 'Mustermann', 'max.mustermann@example.com', 'Mechanic', 3000.0),
    ('Anna', 'Musterfrau', 'anna.musterfrau@example.com', 'Technician', 3500.0),
    ('Peter', 'Schrauber', 'peter.schrauber@example.com', 'Engineer', 4000.0);

INSERT INTO konfiguration_entity ("stunden-satz", "werkstatt-name")
VALUES
    (50.0, 'Werkstatt 1');


INSERT INTO PRODUKT (DELIVERY_TIME_DAYS, NAME, PRICE, QUANTITY)
VALUES
    (1, 'plattenReparatur', 15.0, 25),
    (3, 'bremsen', 0.5, 100),
    (5, 'schaltung', 100.0, 10),
    (2, 'beleuchtungVorne', 10.0, 50),
    (2, 'beleuchtungHinten', 10.0, 0),
    (1, 'reflector', 5.0, 50),
    (1, 'federung', 50.0, 12),
    (1, 'rahmen', 100.0, 0),
    (1, 'gabel', 50.0, 25),
    ( 1, 'kettenantrieb', 50.0, 0),
    ( 1, 'elektrischeKomponenten', 50.0, 0),
    ( 1, 'reifen', 50.0, 0),
    ( 1, 'pedale', 50.0, 0),
    ( 1, 'sattel', 50.0, 0),
    ( 1, 'lenker', 50.0, 0),
    ( 1, 'schutzblech', 50.0, 0),
    ( 1, 'klingel', 50.0, 0),
    ( 1, 'ständer', 50.0, 0),
    ( 1, 'schloss', 50.0, 0),
    ( 1, 'korb', 50.0, 0),
    ( 1, 'gepäckträger', 50.0, 0),
    ( 1, 'flaschenhalter', 50.0, 0);
