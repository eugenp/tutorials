package com.baeldung.fuel

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson

data class Post(var userId:Int,
                var id:Int,
                var title:String,
                var body:String){


    class Deserializer : ResponseDeserializable<Array<Post>> {
        override fun deserialize(content: String): Array<Post> = Gson().fromJson(content, Array<Post>::class.java)
    }
}