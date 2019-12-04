-- SQL syntax for hsqldb 
-- http://www.hsqldb.org/doc/1.8/guide/ch09.html

insert into borrowing_record (
	rec_no, borrower_uid, book_no, borrow_date, due_date
) values (
	1, 'U0001', 'B0002', current_date - interval '16' day, current_date - interval '2' day
);
