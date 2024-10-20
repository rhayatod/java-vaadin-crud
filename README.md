# java-vaadin-crud

this is a sample from java spring boot for demo CRUD using vaadin

before running this code. first setup rdbms (this sample using maria db)

step :
1. install maria db
2. create db name --> demo_vaadin
3. set user name : root
4. set password : root
5. open application.properties in folder resources, check line : spring.datasource.url=jdbc:mariadb://127.0.0.1:3306/demo_vaadin
6. change ip_address base on database server ip address
7. build java source and run.. 
8. open browser with address localhost:8080 