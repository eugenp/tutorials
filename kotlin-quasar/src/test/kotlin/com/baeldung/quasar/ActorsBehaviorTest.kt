package com.baeldung.quasar

import co.paralleluniverse.actors.Actor
import co.paralleluniverse.actors.ActorRef
import co.paralleluniverse.actors.behaviors.*
import co.paralleluniverse.fibers.Suspendable
import co.paralleluniverse.strands.SuspendableCallable
import org.junit.Test
import org.slf4j.LoggerFactory
import java.lang.Exception

class ActorsBehaviorTest {
    companion object {
        private val LOG = LoggerFactory.getLogger(ActorsBehaviorTest::class.java)
    }

    @Test
    fun requestReplyHelper() {
        data class TestMessage(val input: Int) : RequestMessage<Int>()

        val actor = object : Actor<TestMessage, Void>("requestReplyActor", null) {
            @Suspendable
            override fun doRun(): Void? {
                while (true) {
                    val msg = receive()
                    LOG.info("Processing message: {}", msg)

                    RequestReplyHelper.reply(msg, msg.input * 100)
                }
            }
        }

        val actorRef = actor.spawn()

        val result = RequestReplyHelper.call(actorRef, TestMessage(50))
        LOG.info("Received reply: {}", result)
    }

    @Test
    fun server() {
        val actor = ServerActor(object : AbstractServerHandler<Int, String, Float>() {
            @Suspendable
            override fun handleCall(from: ActorRef<*>?, id: Any?, m: Int?): String {
                LOG.info("Called with message: {} from {} with ID {}", m, from, id)
                return m.toString() ?: "None"
            }

            @Suspendable
            override fun handleCast(from: ActorRef<*>?, id: Any?, m: Float?) {
                LOG.info("Cast message: {} from {} with ID {}", m, from, id)
            }
        })

        val server = actor.spawn()

        LOG.info("Call result: {}", server.call(5))
        server.cast(2.5f)

        server.shutdown()
    }

    interface Summer {
        fun sum(a: Int, b: Int) : Int
    }

    @Test
    fun proxyServer() {
        val actor = ProxyServerActor(false, object : Summer {
            @Synchronized
            override fun sum(a: Int, b: Int): Int {
                Exception().printStackTrace()
                LOG.info("Adding together {} and {}", a, b)
                return a + b
            }
        })

        val summerActor = actor.spawn()

        val result = (summerActor as Summer).sum(1, 2)
        LOG.info("Result: {}", result)

        summerActor.shutdown()
    }

    @Test
    fun eventSource() {
        val actor = EventSourceActor<String>()
        val eventSource = actor.spawn()

        eventSource.addHandler { msg ->
            LOG.info("Sent message: {}", msg)
        }

        val name = "Outside Value"
        eventSource.addHandler { msg ->
            LOG.info("Also Sent message: {} {}", msg, name)
        }

        eventSource.send("Hello")

        eventSource.shutdown()
    }

    @Test
    fun finiteStateMachine() {
        val actor = object : FiniteStateMachineActor() {
            @Suspendable
            override fun initialState(): SuspendableCallable<SuspendableCallable<*>> {
                LOG.info("Starting")
                return SuspendableCallable { lockedState() }
            }

            @Suspendable
            fun lockedState() : SuspendableCallable<SuspendableCallable<*>> {
                return receive {msg ->
                    when (msg) {
                        "PUSH" -> {
                            LOG.info("Still locked")
                            lockedState()
                        }
                        "COIN" -> {
                            LOG.info("Unlocking...")
                            unlockedState()
                        }
                        else -> TERMINATE
                    }
                }
            }

            @Suspendable
            fun unlockedState() : SuspendableCallable<SuspendableCallable<*>> {
                return receive {msg ->
                    when (msg) {
                        "PUSH" -> {
                            LOG.info("Locking")
                            lockedState()
                        }
                        "COIN" -> {
                            LOG.info("Unlocked")
                            unlockedState()
                        }
                        else -> TERMINATE
                    }
                }
            }
        }

        val actorRef = actor.spawn()

        listOf("PUSH", "COIN", "COIN", "PUSH", "PUSH").forEach {
            LOG.info(it)
            actorRef.sendSync(it)
        }

        actorRef.shutdown()
    }
}
