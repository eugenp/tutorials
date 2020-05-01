--
-- Insert
--
select *
from 
  car_model
where
  maker_fk = :makerId and
  sku = :sku
;