DELIMITER //
    CREATE PROCEDURE GetFoosByName(IN fooName VARCHAR(255))
        LANGUAGE SQL
        DETERMINISTIC
        SQL SECURITY DEFINER
        BEGIN
            SELECT * FROM foo WHERE name = fooName;
        END //
DELIMITER ;


DELIMITER //
    CREATE PROCEDURE GetAllFoos()
        LANGUAGE SQL
        DETERMINISTIC
        SQL SECURITY DEFINER
        BEGIN
            SELECT * FROM foo;
        END //
DELIMITER ;