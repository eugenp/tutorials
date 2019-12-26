package com.baeldung.nonblockingcoroutines.repository

import com.baeldung.nonblockingcoroutines.model.Product
import org.springframework.data.r2dbc.function.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class ProductRepository(private val client: DatabaseClient) {

    fun getProductById(id: Int): Mono<Product> {
        return client.execute().sql("SELECT * FROM products WHERE id = $1")
          .bind(0, id)
          .`as`(Product::class.java)
          .fetch()
          .one()
    }

    fun addNewProduct(name: String, price: Float): Mono<Void> {
        return client.execute()
          .sql("INSERT INTO products (name, price) VALUES($1, $2)")
          .bind(0, name)
          .bind(1, price)
          .then()
    }

    fun getAllProducts(): Flux<Product> {
        return client.select().from("products")
          .`as`(Product::class.java)
          .fetch()
          .all()
    }
}