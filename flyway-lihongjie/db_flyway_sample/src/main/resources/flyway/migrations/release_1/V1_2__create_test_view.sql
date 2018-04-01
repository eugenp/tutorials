CREATE OR REPLACE VIEW
    flyway_test_view AS select * from flyway_test;

ALTER VIEW flyway_test_view OWNER TO flywaydemo;
