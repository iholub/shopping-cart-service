# Shopping Cart Service

## Commands
- build:
```bash
mvn clean package
```
- start database:
```bash
docker-compose up -d shopping-cart-db
```
- start the service as container in docker:
```bash
docker-compose up --build shopping-cart-service
```
- start the service as jar:
```bash
java \
  -Dspring.datasource.url=jdbc:postgresql://localhost:5432/testdb \
  -Dspring.datasource.username=testuser \
  -Dspring.datasource.password=testpass \
  -jar target/shopping-cart-service-0.0.1-SNAPSHOT.jar
```
- add order:
```bash
curl -X POST http://localhost:8080/api/orders \
   -H 'Content-Type: application/json' \
   -d '{"products": ["Math","Physics", "Chemistry"]}'
```
- get order:
```bash
curl http://localhost:8080/api/orders/1
```
