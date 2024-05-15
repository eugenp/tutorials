INSERT INTO `USER` (`ID`,`NAME`,`EMAIL`) VALUES (1,'user1','user1@test.com');
INSERT INTO `USER` (`ID`,`NAME`,`EMAIL`) VALUES (2,'user2','user2@test.com');
INSERT INTO `USER` (`ID`,`NAME`,`EMAIL`) VALUES (3,'user3','user3@test.com');

INSERT INTO `POST` (`ID`,`SUBJECT`,`USER_ID`) VALUES (1,'JPA Entity Graph In Action',1);

INSERT INTO `COMMENT` (`ID`,`REPLY`,`POST_ID`,`USER_ID`) VALUES (1,'Nice !!',1,2);
INSERT INTO `COMMENT` (`ID`,`REPLY`,`POST_ID`,`USER_ID`) VALUES (2,'Cool !!',1,3);
