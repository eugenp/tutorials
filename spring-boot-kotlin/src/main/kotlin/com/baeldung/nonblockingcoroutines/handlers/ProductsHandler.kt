package com.baeldung.nonblockingcoroutines.handlers

import com.baeldung.nonblockingcoroutines.controller.ProductStockView
import com.baeldung.nonblockingcoroutines.model.Product
import com.baeldung.nonblockingcoroutines.repository.ProductRepositoryCoroutines
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.awaitExchange
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.json

@Component
class ProductsHandler(
  @Autowired var webClient: WebClient,
  @Autowired var productRepository: ProductRepositoryCoroutines) {

    @FlowPreview
    suspend fun findAll(request: ServerRequest): ServerResponse =
      ServerResponse.ok().json().bodyAndAwait(productRepository.getAllProducts())

    suspend fun findOneInStock(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toInt()

        val product: Deferred<Product?> = GlobalScope.async {
            productRepository.getProductById(id)
        }
        val quantity: Deferred<Int> = GlobalScope.async {
            webClient.get()
              .uri("/stock-service/product/$id/quantity")
              .accept(MediaType.APPLICATION_JSON)
              .awaitExchange().awaitBody<Int>()
        }
        return ServerResponse.ok().json().bodyAndAwait(ProductStockView(product.await()!!, quantity.await()))
    }

    suspend fun findOne(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id").toInt()
        return ServerResponse.ok().json().bodyAndAwait(productRepository.getProductById(id)!!)
    }
}