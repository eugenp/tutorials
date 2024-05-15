DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `FIND_CAR_BY_YEAR`(in p_year int)
begin
SELECT ID, MODEL, YEAR
    FROM CAR
    WHERE YEAR = p_year;
end$$
DELIMITER ;
