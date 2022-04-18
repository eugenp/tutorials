# SQL Inject Sandbox
A simple toolkit to learn and experiment with SQL Inject vulnerable application.

## Description
This project was written as a learning tool on SQL Injection. I used the php as the framework as it is relatively simple to understant and to change, however, as it was to be a learning tool, its code was intended to be vulnerable. The original article is on the Computer Science section at baeldung.com

## Disclaimer
As a vulnerable application, meant for educational purposes only, Its use, or parts of it, is strongly non-advisable. Use at your own risk.

## Requirements
For installing and running you will need a computer with docker and docker-compose installed.
- **Docker** - https://docs.docker.com/engine/install/
- **Docker compose** - https://docs.docker.com/compose/install/

## Instructions
1. Once you haver both docker and docker-compose installed, clone the directory with:
``git clone https:\\gitlab.com\gchehab\sql_inject_sandbox`` 
2. To start it, create the two containers (one for the apache/php application server and the other for the mariadb database backend):
``cd git_inject_sandbox && docker-compose up &``
3. To stop and destroy the environment use the command:
``cd git_inject_sandbox && docker-compose down &``
4. The application will open on http://localhost:8080. The listening port can be changed on the docker-compose.yml file.

Tip: If you, on your tests, need to reset the database to the original state, just destroy and recreate the environment (steps 3 and 2, respectively)

## Customizing
The vulnerable application is on the app/index.php file, and the database is created by the SQL scripts on the dbinit folder.

The php file can be changed even with the containers running, as it is not copied to the container.

## Change Log

- v0.1: First public release
    * Initial commit

## License
This software is licenced under GPL 3

## Author
Guilherme Chehab for Baeldung

