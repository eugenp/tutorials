drop table if exists emp;
drop table if exists dept;


create table dept(
  deptno numeric,
  dname  varchar(14),
  loc    varchar(13),
  constraint pk_dept primary key (deptno)
);
 
create table emp(
  empno    numeric,
  ename    varchar(10),
  job      varchar(9),
  mgr      numeric,
  hiredate date,
  sal      numeric,
  comm     numeric,
  deptno   numeric,
  constraint pk_emp primary key (empno),
  constraint fk_deptno foreign key (deptno) references dept (deptno)
);

insert into dept values(10, 'ACCOUNTING', 'NEW YORK');
insert into dept values(20, 'RESEARCH', 'DALLAS');
insert into dept values(30, 'SALES', 'CHICAGO');
insert into dept values(40, 'OPERATIONS', 'BOSTON');
 
insert into emp values(
 7839, 'KING', 'PRESIDENT', null,
 to_date('17-11-1981','dd-mm-yyyy'),
 7698, null, 10
);
insert into emp values(
 7698, 'BLAKE', 'MANAGER', 7839,
 to_date('1-5-1981','dd-mm-yyyy'),
 7782, null, 20
);
insert into emp values(
 7782, 'CLARK', 'MANAGER', 7839,
 to_date('9-6-1981','dd-mm-yyyy'),
 7566, null, 30
);
insert into emp values(
 7566, 'JONES', 'MANAGER', 7839,
 to_date('2-4-1981','dd-mm-yyyy'),
 7839, null, 40
);

commit;