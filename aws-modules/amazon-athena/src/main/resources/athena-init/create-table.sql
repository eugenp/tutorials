CREATE EXTERNAL TABLE IF NOT EXISTS users (
  id INT,
  name STRING,
  age INT,
  city STRING
)
ROW FORMAT SERDE 'org.openx.data.jsonserde.JsonSerDe'
LOCATION 's3://baeldung-athena-tutorial-bucket/';