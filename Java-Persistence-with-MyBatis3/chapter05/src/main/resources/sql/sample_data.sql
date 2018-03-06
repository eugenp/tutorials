

--Sample data for table ADDRESSES

INSERT INTO ADDRESSES (ADDR_ID,STREET,CITY,STATE,ZIP,COUNTRY) VALUES 
 (1,'4891 Pacific Hwy','San Diego','CA','92110','San Diego'),
 (2,'2400 N Jefferson St','Perry','FL','32347','Taylor'),
 (3,'710 N Cable Rd','Lima','OH','45825','Allen'),
 (4,'5108 W Gore Blvd','Lawton','OK','32365','Comanche');

-- Sample data for table STUDENTS

INSERT INTO STUDENTS (STUD_ID,NAME,EMAIL,PHONE,DOB,GENDER,BIO,PIC,ADDR_ID) VALUES 
 (1,'Timothy','timothy@gmail.com','123-123-1234','1988-04-25','MALE',NULL,NULL,3),
 (2,'Douglas','douglas@gmail.com','789-456-1234','1990-08-15','MALE',NULL,NULL,4);

-- Sample data for table TUTORS

INSERT INTO TUTORS (TUTOR_ID,NAME,EMAIL,PHONE,DOB,GENDER,BIO,PIC,ADDR_ID) VALUES 
 (1,'John','john@gmail.com','111-222-3333','1980-05-20','MALE',NULL,NULL,1),
 (2,'Ken','ken@gmail.com','111-222-3333','1980-05-20','MALE',NULL,NULL,1),
 (3,'Paul','paul@gmail.com','123-321-4444','1981-03-15','FEMALE',NULL,NULL,2),
 (4,'Mike','mike@gmail.com','123-321-4444','1981-03-15','MALE',NULL,NULL,2);

-- Sample data for table courses

INSERT INTO COURSES (COURSE_ID,NAME,DESCRIPTION,START_DATE,END_DATE,TUTOR_ID) VALUES 
 (1,'Quickstart Core Java','Core Java Programming','2013-03-01','2013-04-15',1),
 (2,'Quickstart JavaEE6','Enterprise App Development using JavaEE6','2013-04-01','2013-08-30',1),
 (3,'MyBatis3 Premier','MyBatis 3 framework','2013-06-01','2013-07-15',2);

-- Sample data for table COURSE_ENROLLMENT

INSERT INTO COURSE_ENROLLMENT (COURSE_ID,STUD_ID) VALUES 
 (1,1),
 (1,2),
 (2,2);

