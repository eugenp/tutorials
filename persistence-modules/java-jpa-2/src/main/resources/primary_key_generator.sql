CREATE SEQUENCE article_seq MINVALUE 1 START WITH 50 INCREMENT BY 50;

INSERT INTO id_gen (gen_name, gen_val) VALUES ('id_generator', 0); 
INSERT INTO id_gen (gen_name, gen_val) VALUES ('task_gen', 10000);