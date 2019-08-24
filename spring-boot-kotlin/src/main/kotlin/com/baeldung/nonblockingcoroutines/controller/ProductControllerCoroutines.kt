package com.baeldung.nonblockingcoroutines.controller

import com.baeldung.nonblockingcoroutines.model.Product
import com.baeldung.nonblockingcoroutines.repository.ProductRepositoryCoroutines
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange

class ProductControllerCoroutines {
    @Autowired
    lateinit var webClient: WebClient

    @Autowired
    lateinit var productRepository: ProductRepositoryCoroutines

    @GetMapping("/{id}")
    suspend fun findOne(@PathVariable id: Int): Product? {
        return productRepository.getProductById(id)
    }

    @GetMapping("/{id}/stock")
    suspend fun findOneInStock(@PathVariable id: Int): ProductStockView {
        val product: Deferred<Product?> = GlobalScope.async {
            productRepository.getProductById(id)
        }
        val quantity: Deferred<Int> = GlobalScope.async {
            webClient.get()
              .uri("/stock-service/product/$id/quantity")
              .accept(APPLICATION_JSON)
              .awaitExchange().awaitBody<Int>()
        }
        return ProductStockView(product.await()!!, quantity.await())
    }

    @FlowPreview
    @GetMapping("/")
    fun findAll(): Flow<Product> {
        return productRepository.getAllProducts()
    }
}