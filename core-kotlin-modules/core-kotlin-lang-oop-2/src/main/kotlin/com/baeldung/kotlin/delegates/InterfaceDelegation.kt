package com.baeldung.kotlin.delegates

import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

interface Producer {

    fun produce(): String
}

class ProducerImpl : Producer {

    override fun produce() = "ProducerImpl"
}

class EnhancedProducer(private val delegate: Producer) : Producer by delegate {

    override fun produce() = "${delegate.produce()} and EnhancedProducer"
}

interface MessageService {

    fun processMessage(message: String): String
}

class MessageServiceImpl : MessageService {
    override fun processMessage(message: String): String {
        return "MessageServiceImpl: $message"
    }
}

interface UserService {

    fun processUser(userId: String): String
}

class UserServiceImpl : UserService {

    override fun processUser(userId: String): String {
        return "UserServiceImpl: $userId"
    }
}

class CompositeService : UserService by UserServiceImpl(), MessageService by MessageServiceImpl()

interface Service {

    val seed: Int

    fun serve(action: (Int) -> Unit)
}

class ServiceImpl : Service {

    override val seed = 1

    override fun serve(action: (Int) -> Unit) {
        action(seed)
    }
}

class ServiceDecorator : Service by ServiceImpl() {
    override val seed = 2
}