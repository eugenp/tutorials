INSERT INTO Item(id,name) VALUES (1,'Table');
INSERT INTO Item(id,name) VALUES (2,'Bottle');

INSERT INTO Characteristic(id,item_id, type) VALUES (1,1,'Rigid');
INSERT INTO Characteristic(id,item_id,type) VALUES (2,1,'Big');
INSERT INTO Characteristic(id,item_id,type) VALUES (3,2,'Fragile');
INSERT INTO Characteristic(id,item_id,type) VALUES (4,2,'Small');