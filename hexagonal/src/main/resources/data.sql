DROP TABLE IF EXISTS asset;
	
CREATE TABLE asset (
  asset_id INT AUTO_INCREMENT  PRIMARY KEY,
  asset_type_id NUMBER NOT NULL,
  manufacturer VARCHAR(250) NOT NULL,
  status VARCHAR(250) DEFAULT NULL,
  purchase_date DATE NULL,
  sell_date DATE NULL
);
 
INSERT INTO asset (asset_type, manufacturer, status, purchase_date, sell_date ) VALUES
  ('1', 'HHH', 'SOLD',TO_DATE('02-01-2019','DD-MM-YYYY'),TO_DATE('04-02-2019','DD-MM-YYYY')),
  ('3', 'Finolex', 'UNSOLD'),
  ('5', 'Anchor', 'UNSOLD', TO_DATE('03-04-2019','DD-MM-YYYY'));
  
CREATE TABLE asset_type (
  type_id INT PRIMARY KEY,
  asset_type_name VARCHAR(250) NOT NULL,
  asset_type_description VARCHAR(250) DEFAULT NULL,
  units_for_measurement VARCHAR(250) DEFAULT NULL
);
  
INSERT INTO asset_type (asset_type_name, asset_type_description, units_for_measurement ) VALUES
  (1, 'Analog Reader', 'Used to take reading', 'NA'),
  (3, 'Cable', 'Used for connection', 'Meter'),
  (4, 'Screw', 'For hitching', 'kilogram'),
  (5, 'Switch Board', 'Used for fixing switches', 'NA');