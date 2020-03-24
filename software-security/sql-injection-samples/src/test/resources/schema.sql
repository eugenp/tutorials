create table Accounts (
	id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	customer_id varchar(16) not null,
	acc_number varchar(16) not null,
	branch_id decimal(8,0),
	balance decimal(16,4)	
);
