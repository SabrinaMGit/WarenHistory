CREATE TABLE if not exists product_data (
    id serial primary key,
    datenquelle TEXT,
    sgtin TEXT NOT NULL,
    produktbezeichnung TEXT ,
    produktionscharge INT,
    verpackungsdatum DATE ,
    mhd DATE,
    aufladewerttti INT,
    zieltemperatur INT,
    p1 INT,
    p2 INT,
    p3 INT,
    p4 INT,
    p5 INT,
    p6 INT,
    p7 INT,
    p8 INT,
    p9 INT,
    p10 INT,
    zeitstempel TEXT ,
    laenge double precision,
    breite double precision,
    rot INT,
    grün INT,
    blau INT,
    erwartete_temperatur INT,
    resthaltbarkeit INT
);

INSERT INTO product_data(id, datenquelle, sgtin, produktbezeichnung, produktionscharge, verpackungsdatum, mhd,
                         aufladewerttti, zieltemperatur, p1, p2, p3, p4,p5,p6,p7, p8,p9,p10, zeitstempel,
                         laenge, breite,rot, grün,blau, erwartete_temperatur, resthaltbarkeit)
VALUES (1,'Wolf Qualitätskontrolle', '01/04014116000003/21/0221T4YE', 'Original Thueringer Rostbratwurst', 4711, '2021-05-16','2021-06-30',58,7,0,0,0,0,0,0,0,0,0,0,'20210301_204823',6.906,50.834,24,30,20,10,20)
