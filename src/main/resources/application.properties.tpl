spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:3306/dbms?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.data.rest.base-path=/api
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL8Dialect
spring.jpa.properties.hibernate.dialect.storage_engine=innodb
# spring.data.web.pageable.default-page-size=20
spring.jpa.show-sql=true
spring.jackson.serialization.FAIL_ON_EMPTY_BEANS=false
# logging.level.com.accustratus.wms=DEBUG

# App Properties
app.jwtSecret= mySecrect
app.jwtExpirationMs= 86400000