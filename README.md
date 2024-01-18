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
CREATE DATABASE "tenant1";
INSERT INTO user(name, username, password) VALUES ('user', 'username_tenant1', '$2a$10$FCIFxGfXNnoh5kV3bt1/.OWrCRwwu3Nf6YiOG4i20bFCtKxHv8BY2');
-- password 123
...

CREATE DATABASE "tenant12";
INSERT INTO user(name, username, password) VALUES ('user', 'username_tenant2', '$2a$10$FCIFxGfXNnoh5kV3bt1/.OWrCRwwu3Nf6YiOG4i20bFCtKxHv8BY2');
-- password 123

...
```

### *Need start redis in port 6379

## Front-end (port 4200)

### Executar comandos

```npm
ng build
ng serve
```

### Para acessar
### Back-end
```txt
http://localhost:8080/api/login
http://localhost:8080/api/products
```
For login:
Acess `http://localhost:8080/api/login` and send POST request with JSON (**username and password**) and header **X-TenantyID** with **tenant name**.

### Front-end
```txt
http://localhost:4200/login 
http://localhost:4200/products
```
