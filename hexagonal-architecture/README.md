The intent of the code snippets is supposed to be a visualization of the approach to Hexagonal architecture design. As such focus has been on the package structure and not on the test cases

The application tries to save a student details to a flat file and get the same through
1. Command line option
2. REST API

To-do work is to be done saving and getting the details from embedded H2 database. The necessary dependencies have been added though.

Steps
=====
1. mvn clean package
2. cd target
3. Save student in student.txt
   java -jar parent-1.0.0-SNAPSHOT.jar --operation=CREATE --name=PETER --age=23 
4. Get student by id
   java -jar parent-1.0.0-SNAPSHOT.jar --operation=GET --id=<< id stored in student.txt >>
