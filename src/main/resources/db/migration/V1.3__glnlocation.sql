create table if not exists gln_location (
  id serial primary key,
  gln_number text NOT NULL,
  longitude text,
  latitude text,
  address text
);

INSERT INTO gln_location (id, gln_number, longitude, latitude, address) values (0, '061414100000', '51.15636', '6.74023', '119, Mainstraße 113, 41469 Neuss');
INSERT INTO gln_location (id, gln_number, longitude, latitude, address) values (1, '061414101111', '51.1675454061001', '6.707289399850051', 'Weckhovener Str. 60, 41466 Neuss');
INSERT INTO gln_location (id, gln_number, longitude, latitude, address) values (2, '061414102222', '51.184572107776745', '6.730605616962864', 'Grimlinghauserbrücke 54, 41468 Neuss');
INSERT INTO gln_location (id, gln_number, longitude, latitude, address) values (3, '061414103333', '51.209291245438024', '6.715207856078838', 'Floßhafenstraße 16, 41460 Neuss');
INSERT INTO gln_location (id, gln_number, longitude, latitude, address) values (4, '061414104444', '51.20655095226938', '6.649171576508341', 'Auf dem Berg 1, 41462 Neuss');
INSERT INTO gln_location (id, gln_number, longitude, latitude, address) values (5, '061414105555', '51.25039345706026', '6.744170757517076', 'Auf dem Tal, 3, 41469 Neuss');
INSERT INTO gln_location (id, gln_number, longitude, latitude, address) values (6, '061414106666', '51.234435132490276', '6.84232525827792', 'Im Seetank 43, 41469 Neuss');
INSERT INTO gln_location (id, gln_number, longitude, latitude, address) values (7, '061414107777', '51.22447076008258', '6.856520762432572', 'Düsseldorfer Str. 23, 41469 Düsseldorf');
INSERT INTO gln_location (id, gln_number, longitude, latitude, address) values (8, '061414108888', '51.202789914779764', '6.8542070593064315', 'Hanfenpark 33, 41469 Düsseldorf');
INSERT INTO gln_location (id, gln_number, longitude, latitude, address) values (9, '061414109999', '51.16877729901521', '6.9437749741683605', 'Im Mantelbar 65, 41469 Düsseldorf');
INSERT INTO gln_location (id, gln_number, longitude, latitude, address) values (10, '061414101010', '51.15533064512311', '6.939304457656356', 'Richrather Str. 172, 40723 Hilden');
INSERT INTO gln_location (id, gln_number, longitude, latitude, address) values (11, '061414101212', '51.200215652412425', '6.7621904835627635', 'Utewolfen Str. 59, 41469 Düseldorf ');
