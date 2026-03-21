# Apache Seata Example Project

This project shows an example of using Apache Seata for distributed transactions.

## Service Structure

This project represents 4 services:
* `apache-seata-shop-service`
* `apache-seata-inventory-service`
* `apache-seata-order-service`
* `apache-seata-billing-service`

All of these work with a Postgres database, and additionally the Shop service makes API calls to the other three,
such that all of this is considered to be a single distributed transaction.

## Starting The Project

We can start the project using Docker Compose.

```shell
$ docker compose up
```

This will start 6 containers:

* Apache Seata
* Postgres
* `apache-seata-shop-service`
* `apache-seata-inventory-service`
* `apache-seata-order-service`
* `apache-seata-billing-service`

Where `apache-seaa-shop-service` acts as the entrypoint into the application.

# Testing

We can make HTTP calls into the application by making POST calls to the `/shop/{mode}` endpoint of the `apache-seata-shop-service`.

If the `{mode}` parameter is set to `shop`, `inventory`, `order` or `billing` then that service will fail during the transaction.
Anything else and the call will be successful.

For example:
```shell
$ curl -X POST localhost:8080/shop/order
```

Will make a request that fails within the Order service.

# Database Access

We can access the database used by these services using Docker:

```shell
$ docker exec -it apache-seata-postgres-1 psql --user seata seata
```

This opens a psql prompt inside the database, allowing us to explore the state of all the tables.