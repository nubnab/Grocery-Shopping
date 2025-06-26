# Grocery Shopping 
This is a simple backend grocery shopping API written in Java and Spring. 

## Project overview

This program aims to calculate the shortest route possible between warehouses in order to complete an order.

---

## Prerequisites
- Docker

  or

- Java JDK 8 or later
- PostgreSQL
  
---

## Features

- Add new product to db or update existing one
- Creating an order and calculating best route
- Viewing orders and their routes

---

## Building
Clone the repository
```
git clone https://github.com/nubnab/Grocery-Shopping.git && cd Grocery-Shopping
```
---
#### Running with Docker
```
docker compose up -d
```

### Uninstall
```
docker compose down --rmi all --volumes
```
---
#### Building manually
```
./mvnw clean package
```
#### Run jar
```
java -jar ./backend/target/backend-0.0.1-SNAPSHOT.jar
```
---

## API Endpoints
| Method    | Endpoint               | Description                       | Request Body (JSON)                                                                               |
|-----------|------------------------|-----------------------------------|---------------------------------------------------------------------------------------------------|
| `POST`    | `/products`            | Create a product                  | `{"name": "string", "quantity": "number", "price": "number", location={"x": number, "y": number}}`|
| `GET`     | `/products`            | Get all products                  | -                                                                                                 |
| `PUT`     | `/products{id}`        | Update a product                  | `{"name": "string", "quantity": "number", "price": "number", location={"x": number, "y": number}}`|
| `DELETE`  | `/products/{id}`       | Delete a product                  | -                                                                                                 |
| `POST`    | `/orders`              | Create an order                   | `[{"name": "string", "quantity": "number]`                                                        |
| `GET`     | `/orders{id}`          | Check status of existing order    | -                                                                                                 |
| `GET`     | `/routes?orderId={id}` | Check route of an existing order  | -                                                                                                 |
