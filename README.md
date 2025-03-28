# A product management system with CRUD, AWS storage, and real-time updates

## Para executar
## Back-end(port 8080)
### Add S3 config
```txt
bucket-name: 
acess-key: 
secret-key: 
region-static:
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
Acess `http://localhost:8080/api/login` and send POST request with JSON (**username and password**).

### Front-end
```txt
http://localhost:4200/login 
http://localhost:4200/products
```
