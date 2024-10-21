# java-vaadin-crud

this is a sample from java spring boot for demo CRUD using vaadin
also can genderate report as PDF

before running this code. first setup database (this sample code using mariadb)

step :
1. install mariadb
2. create db name --> demo_vaadin
3. set user name : root
4. set password : root
5. open application.properties in folder resources, check line : 	spring.datasource.url=jdbc:mariadb://127.0.0.1:3306/demo_vaadin
6. change ip_address base on database server ip address
7. run maven command using mvn clean in java source folder
8. copy jar file and running with server (apache tomcat, jetty etc) 
9. open browser with address http://localhost:8080/