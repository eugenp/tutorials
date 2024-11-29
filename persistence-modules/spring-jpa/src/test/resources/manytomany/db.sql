CREATE TABLE course (
  id bigint auto_increment NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE student (
  id bigint auto_increment NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE course_like (
  student_id bigint auto_increment NOT NULL,
  course_id bigint auto_increment NOT NULL,
  PRIMARY KEY (student_id, course_id),
  CONSTRAINT fk_course_like__student FOREIGN KEY (student_id) REFERENCES student (id),
  CONSTRAINT fk_course_like__course FOREIGN KEY (course_id) REFERENCES course (id)
);



CREATE TABLE course_rating (
  course_id bigint auto_increment NOT NULL,
  student_id bigint auto_increment NOT NULL,
  rating int auto_increment NOT NULL,
  PRIMARY KEY (course_id, student_id),
  CONSTRAINT fk_course_rating__student FOREIGN KEY (student_id) REFERENCES student (id),
  CONSTRAINT fk_course_rating__course FOREIGN KEY (course_id) REFERENCES course (id)
);



CREATE TABLE course_registration (
  id bigint auto_increment NOT NULL,
  grade int auto_increment,
  registered_at datetime NOT NULL,
  course_id bigint auto_increment NOT NULL,
  student_id bigint auto_increment NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_course_registration__student FOREIGN KEY (student_id) REFERENCES student (id),
  CONSTRAINT fk_course_registration__course FOREIGN KEY (course_id) REFERENCES course (id)
);

