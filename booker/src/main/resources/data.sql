drop table if exists book;

create TABLE book (
	  id INT AUTO_INCREMENT  PRIMARY KEY,
	  name VARCHAR(250) NOT NULL,
	  category VARCHAR(250) NOT NULL,
	  number_of_copies INT NOT NULL
);

insert into book(name,category,number_of_copies) values ('Harry Potter', 'fantasy',10);
insert into book(name,category,number_of_copies) values ('The $100 startup', 'business',10);
insert into book(name,category,number_of_copies) values ('Into the Wild', 'documentary',10);
insert into book(name,category,number_of_copies) values ('Spring Crash Course', 'technology',10);
insert into book(name,category,number_of_copies) values ('Getting Things Done','management',10);
insert into book(name,category,number_of_copies) values ('Song of Ice and Fire','Fantasy',10);


