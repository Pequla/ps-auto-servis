# ps-auto-servis
Ispitni zadatak iz predmeta Projektovanje softvera na temu Aplikacija za upravljanje auto servisom

### Primer `application.properties`

Kako bi pokrenuli aplikaciju nakon kloniranja repozitorijuma poterbno je da na lokaciji `src/main/resources` napravite fajl pod istim imenom 
```properties
spring.data.web.pageable.default-page-size=10
spring.data.web.pageable.max-page-size=30
spring.datasource.url=jdbc:mysql://localhost:3306/auto_servis
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.open-in-view=true
server.port=8080
server.address=localhost

spring.servlet.multipart.enabled=true
spring.servlet.multipart.location=temp
spring.servlet.multipart.file-size-threshold=15MB
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=100MB
file.storage.location=uploads
```

> Podrazumeva se da ste vec podigli MYSQL bazu podataka iz `auto-servis.sql` fajla
