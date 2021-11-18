# DOTS--API
Spring Boot backend API implements for dots ecomerce web app

Note: 
+ It might took a while to load all the dependencies and configure
+ Make sure to change your application.properties settings adapt to your database management system
+ Run your database management system first before running the app

Basic settings for application.properties:
```java

server.port=8080

//  Database config 
spring.datasource.url=jdbc:mysql://localhost:3306/dots_db?useSSL=false
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql = true
spring.jpa.properties.javax.persistence.validation.mode=none


```

