INSERT INTO "article" ("id", "publication_date", "publication_time", "creation_date_time")
VALUES (1, PARSEDATETIME('01/01/2018', 'dd/MM/yyyy'), PARSEDATETIME('15:00', 'HH:mm'), PARSEDATETIME('31/12/2017 07:30', 'dd/MM/yyyy HH:mm'));

INSERT INTO "article" ("id", "publication_date", "publication_time", "creation_date_time")
VALUES (2, PARSEDATETIME('01/01/2018', 'dd/MM/yyyy'), PARSEDATETIME('15:30', 'HH:mm'), PARSEDATETIME('15/12/2017 08:00', 'dd/MM/yyyy HH:mm'));

INSERT INTO "article" ("id", "publication_date", "publication_time", "creation_date_time")
VALUES (3, PARSEDATETIME('15/12/2017', 'dd/MM/yyyy'), PARSEDATETIME('16:00', 'HH:mm'), PARSEDATETIME('01/12/2017 13:45', 'dd/MM/yyyy HH:mm'));