CREATE OR REPLACE PROCEDURE sum_two_numbers(
    IN num1 NUMERIC,
    IN num2 NUMERIC,
    OUT result NUMERIC
)
LANGUAGE plpgsql
AS '
BEGIN
    -- Perform the sum and store the result in the OUT parameter
    result := num1 + num2;
END;
';