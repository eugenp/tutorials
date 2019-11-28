-- SQL syntax for hsqldb 
-- http://www.hsqldb.org/doc/1.8/guide/ch09.html

insert into borrower (
	uid, first_name, last_name
) values (
	'U0001', 'John', 'Doe'
);

insert into borrower (
	uid, first_name, last_name
) values (
	'U0002', 'Mary', 'May'
);

insert into book (
	book_no, title, author, publisher, lang, isbn10, status,
	short_desc
) values (
	'B0001', 'Santa''s Story', 'Will Schwartz','ABC publisher','eng','1234523230','A',
	'A story about X''mas..'
);

insert into book (
	book_no, title, author, publisher, lang, isbn10, status,
	short_desc
) values (
	'B0002', 'Make Way for Ducklings', 'Susan Charters','Viking Books','eng','0234509091','A',
	'The most honored and enduring childen''s books..'
);

insert into borrowing_record (
	rec_no, borrower_uid, book_no, borrow_date, due_date
) values (
	1, 'U0001', 'B0002', current_date - interval '16' day, current_date - interval '2' day
);
