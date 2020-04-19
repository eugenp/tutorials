INSERT INTO post (id, title, content) VALUES (1, 'Comment 1', 'Content 1');
INSERT INTO post (id, title, content) VALUES (2, 'Comment 2', 'Content 2');
INSERT INTO post (id, title, content) VALUES (3, 'Comment 3', 'Content 3');
INSERT INTO comment (id, year, approved, content, post_id) VALUES (1, 2019, false, 'Comment 1', 1);
INSERT INTO comment (id, year, approved, content, post_id) VALUES (2, 2018, true, 'Comment 2', 1);
INSERT INTO comment (id, year, approved, content, post_id) VALUES (3, 2018, true, 'Comment 3', 2);
INSERT INTO comment (id, year, approved, content, post_id) VALUES (4, 2017, false, 'Comment 4', 3);