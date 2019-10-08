# course-management-software
![Course Magement Software Demo](demo/demo.gif)  

This is a JavaFX application for managing courses, departments, majors, and concentrations for a university. This repository has a database backup containing sample data for testing this application.

The administrator view will allow the user to modify the content, which will allow them to add new Courses, Programs, Departments as well as modify existing ones. The student view will allow the user to view the courses in each of the departments and programs. They can view the prerequisites, antirequisites, and descriptions for any courses they are interested in.  

## Run this project  
Maven can be used to build/run this program through the command line.  
To run this project, input the following command while in the project directory.

    mvn clean javafx:run

Alternatively this project can be imported into IntelliJ to  build and run.

**Default Credentials**  
For the administrator user:  
username: admin  
password: admin

For the student user:  
username: student  
password: student


**Start the Database**  
Docker can be used to run a PostgreSQL within a container. To start the postgres server run the following commands from the root of the project directory:
```bash
docker build -t postgresql .
docker run --rm -p 5432:5432 --name pg postgresql
```
This will create a docker image with the name postgresql from the Dockerfile and then run the image while mapping the hosts port 5432 to the containers port 5432 (postgres).

To access the running database from the host the following command can be used `psql -h localhost -d docker -U docker`, the password when connecting will be `docker`.

## Create a backup of the existing database  
On the host machine run the following command in the base project directory to create a database dump which will be in the file database_backup.sql

    pg_dump -h localhost -p 5432 -U docker -f database/database_backup.sql



## Credits  
**Icons**  
The icons are from Font Awesome and were converted into png files [fontawesome.com/license](https://fontawesome.com/license)

**Maven**  
The maven build files that were modified for this project can be found at  
[github.com/openjfx/samples/tree/master/IDE/IntelliJ/Modular/Maven](https://github.com/openjfx/samples/tree/master/IDE/IntelliJ/Modular/Maven)   Copyright (c) 2019, Gluon
