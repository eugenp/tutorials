DROP TABLE IF EXISTS asset;
	
CREATE TABLE asset (
  asset_id INT AUTO_INCREMENT  PRIMARY KEY,
  asset_type VARCHAR(250) NOT NULL,
  manufacturer VARCHAR(250) NOT NULL,
  asset_description VARCHAR(250) DEFAULT NULL,
  purchase_date DATE NULL,
  sell_date DATE NULL
);
 
INSERT INTO asset (asset_type, manufacturer, asset_description, purchase_date, sell_date ) VALUES
  ('Meter', 'HHH', 'Used to take reading'),
  ('Cable', 'Finolex', 'Used for connection'),
  ('Switch Board', 'Anchor', 'Used for fixing switches');