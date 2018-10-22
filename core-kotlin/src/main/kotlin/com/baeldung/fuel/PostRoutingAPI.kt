package com.baeldung.fuel

import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.util.FuelRouting

sealed class PostRoutingAPI : FuelRouting {

    override val basePath = "https://jsonplaceholder.typicode.com"

    class posts(val id: String, override val body: String?): PostRoutingAPI()

    class comments(val postId: String, override val body: String?): PostRoutingAPI()

    override val method: Method
        get() {
            return when(this) {
                is PostRoutingAPI.posts -> Method.GET
                is PostRoutingAPI.comments -> Method.GET
            }
        }

    override val path: String
        get() {
            return when(this) {
                is PostRoutingAPI.posts -> "/posts"
                is PostRoutingAPI.comments -> "/comments"
            }
        }

    override val params: List<Pair<String, Any?>>?
        get() {
            return when(this) {
                is PostRoutingAPI.posts -> listOf("id" to this.id)
                is PostRoutingAPI.comments -> listOf("postId" to this.postId)
            }
        }

    override val headers: Map<String, String>?
        get() {
            return null
        }
}
