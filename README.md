# FullStack EmpireTech Test for Pleno

## Para executar
## Back-end(port 8080)
### Add S3 config
```txt
bucket-name: 
acess-key: 
secret-key: 
region-static:
```
### Configure databases para conexão multi-tenants
```txt
tenants:
  datasources:
    tenant1:
      url: jdbc:postgresql://localhost:5432/tenant1
      username:
      password:
      driverClassName: ${spring.jpa.driver-class-name}
    tenant2:
      url: jdbc:postgresql://localhost:5433/tenant2
      username:
      password:
      driverClassName: ${spring.jpa.driver-class-name}
```
#### Criar Databases em seus links
```sql 
CREATE TABLE "tenant1";
CREATE TABLE "tenant12";
...
```

### *Need start redis in port 6379

## Front-end (port 4200)

### executar comandos

```npm
ng build
ng serve
```
