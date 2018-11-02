# noinspection SqlNoDataSourceInspectionForFile

create table organisation.employees
(
	id int not null auto_increment
		primary key,
	first_name varchar(100) not null,
	last_name varchar(100) not null,
	gender varchar(1) not null,
	created_at datetime not null,
	updated_at datetime null,
	created_by varchar(100) not null,
	updated_by varchar(100) null
)ENGINE = InnoDB DEFAULT CHARSET = utf8;

create table organisation.emp_roles
(
	id int not null auto_increment primary key,
	employee_id int not null,
	role_name varchar(100) not null,
	created_at datetime not null,
	updated_at datetime null,
	created_by varchar(100) not null,
	updated_by varchar(100) null
)ENGINE = InnoDB DEFAULT CHARSET = utf8;
