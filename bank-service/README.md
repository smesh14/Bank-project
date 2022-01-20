#Introduction
Bank Service is app for managing  cards, it is used for ATM Service as core app

#Requirements
**Java 11**

**Maven 3.8.4+**

**Docker 20.10.12**

**mssql**

#Dependencies
Bank Service use postgres database for data store


**spring.datasource.url=jdbc:mssql://bank-service-db:1433/BANK_SERVICE**

**spring.datasource.username=sa

**spring.datasource.password=bank-service-password

to create database should run setup.bat file

#Build and deploy
* It is possible to build and deploy application with docker command **docker compose up**.

