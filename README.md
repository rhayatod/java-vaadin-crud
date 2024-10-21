# java-vaadin-crud

this is a sample from java spring boot for demo CRUD using vaadin
also can genderate report as PDF

before running this code. first setup database (this sample code using mariadb)

step :
1. install mariadb
2. create db name --> demo_vaadin
3. set user name : root
4. set password : root
5. open sql script demo_vaadin.sql (locate at directory sql) run sql script
6. table user will create in database demo_vaadin 
7. open application.properties in directory resources, check line : 	spring.datasource.url=jdbc:mariadb://127.0.0.1:3306/demo_vaadin
8. change ip_address base on database server ip address
9. run maven command using mvn clean in java source folder
10. copy jar file and running with server (apache tomcat, jetty etc) 
11. open browser with address http://localhost:8080/