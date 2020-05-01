package com.baeldung.quasar

import co.paralleluniverse.actors.*
import co.paralleluniverse.fibers.Suspendable
import co.paralleluniverse.strands.channels.Channels
import org.junit.Assert
import org.junit.Test
import org.slf4j.LoggerFactory
import java.util.concurrent.TimeUnit

class ActorsTest {
    companion object {
        private val LOG = LoggerFactory.getLogger(ActorsTest::class.java)
    }

    @Test
    fun createNoopActor() {
        val actor = object : Actor<Int, String>("noopActor", MailboxConfig(5, Channels.OverflowPolicy.THROW)) {
            @Suspendable
            override fun doRun(): String {
                return "Hello"
            }
        }

        actor.spawn()

        println("Noop Actor: ${actor.get()}")
    }

    @Test
    fun registerActor() {
        val actor = object : Actor<Int, String>("registerActor", null) {
            @Suspendable
            override fun doRun(): String {
                return "Hello"
            }
        }

        val actorRef = actor.spawn()
        actor.register()

        val retrievedRef = ActorRegistry.getActor<ActorRef<Int>>("registerActor")

        Assert.assertEquals(actorRef, retrievedRef)
        actor.join()
    }

    @Test
    fun registerActorNewName() {
        val actor = object : Actor<Int, String>(null, null) {
            @Suspendable
            override fun doRun(): String {
                return "Hello"
            }
        }

        val actorRef = actor.spawn()
        actor.register("renamedActor")

        val retrievedRef = ActorRegistry.getActor<ActorRef<Int>>("renamedActor")

        Assert.assertEquals(actorRef, retrievedRef)
        actor.join()
    }

    @Test
    fun retrieveUnknownActor() {
        val retrievedRef = ActorRegistry.getActor<ActorRef<Int>>("unknownActor", 1, TimeUnit.SECONDS)

        Assert.assertNull(retrievedRef)
    }

    @Test
    fun createSimpleActor() {
        val actor = object : Actor<Int, Void?>("simpleActor", null) {
            @Suspendable
            override fun doRun(): Void? {
                val msg = receive()
                LOG.info("SimpleActor Received Message: {}", msg)

                return null
            }
        }

        val actorRef = actor.spawn()

        actorRef.send(1)

        actor.join()
    }

    @Test
    fun createLoopingActor() {
        val actor = object : Actor<Int, Void?>("loopingActor", null) {
            @Suspendable
            override fun doRun(): Void? {
                while (true) {
                    val msg = receive()

                    if (msg > 0) {
                        LOG.info("LoopingActor Received Message: {}", msg)
                    } else {
                        break
                    }
                }

                return null
            }
        }

        val actorRef = actor.spawn()

        actorRef.send(3)
        actorRef.send(2)
        actorRef.send(1)
        actorRef.send(0)

        actor.join()
    }

    @Test
    fun actorBacklog() {
        val actor = object : Actor<Int, String>("backlogActor", MailboxConfig(1, Channels.OverflowPolicy.THROW)) {
            @Suspendable
            override fun doRun(): String {
                TimeUnit.MILLISECONDS.sleep(500);
                LOG.info("Backlog Actor Received: {}", receive())

                try {
                    receive()
                } catch (e: Throwable) {
                    LOG.info("==== Exception throws by receive() ====")
                    e.printStackTrace()
                }

                return "No Exception"
            }
        }

        val actorRef = actor.spawn()

        actorRef.send(1)
        actorRef.send(2)

        try {
            LOG.info("Backlog Actor: {}", actor.get())
        } catch (e: Exception) {
            // Expected
            LOG.info("==== Exception throws by get() ====")
            e.printStackTrace()
        }
    }

    @Test
    fun actorBacklogTrySend() {
        val actor = object : Actor<Int, String>("backlogTrySendActor", MailboxConfig(1, Channels.OverflowPolicy.THROW)) {
            @Suspendable
            override fun doRun(): String {
                TimeUnit.MILLISECONDS.sleep(500);
                LOG.info("Backlog TrySend Actor Received: {}", receive())

                return "No Exception"
            }
        }

        val actorRef = actor.spawn()

        LOG.info("Backlog TrySend 1: {}", actorRef.trySend(1))
        LOG.info("Backlog TrySend 1: {}", actorRef.trySend(2))

        actor.join()
    }

    @Test
    fun actorTimeoutReceive() {
        val actor = object : Actor<Int, String>("TimeoutReceiveActor", MailboxConfig(1, Channels.OverflowPolicy.THROW)) {
            @Suspendable
            override fun doRun(): String {
                LOG.info("Timeout Actor Received: {}", receive(500, TimeUnit.MILLISECONDS))

                return "Finished"
            }
        }

        val actorRef = actor.spawn()

        TimeUnit.MILLISECONDS.sleep(300)
        actorRef.trySend(1)

        actor.join()
    }


    @Test
    fun actorNonBlockingReceive() {
        val actor = object : Actor<Int, String>("NonBlockingReceiveActor", MailboxConfig(1, Channels.OverflowPolicy.THROW)) {
            @Suspendable
            override fun doRun(): String {
                LOG.info("NonBlocking Actor Received #1: {}", tryReceive())
                TimeUnit.MILLISECONDS.sleep(500)
                LOG.info("NonBlocking Actor Received #2: {}", tryReceive())

                return "Finished"
            }
        }

        val actorRef = actor.spawn()

        TimeUnit.MILLISECONDS.sleep(300)
        actorRef.trySend(1)

        actor.join()
    }

    @Test
    fun evenActor() {
        val actor = object : Actor<Int, Void?>("EvenActor", null) {
            @Suspendable
            override fun filterMessage(m: Any?): Int? {
                return when (m) {
                    is Int -> {
                        if (m % 2 == 0) {
                            m * 10
                        } else {
                            null
                        }
                    }
                    else -> super.filterMessage(m)
                }
            }

            @Suspendable
            override fun doRun(): Void? {
                while (true) {
                    val msg = receive()

                    if (msg > 0) {
                        LOG.info("EvenActor Received Message: {}", msg)
                    } else {
                        break
                    }
                }

                return null
            }
        }

        val actorRef = actor.spawn()

        actorRef.send(3)
        actorRef.send(2)
        actorRef.send(1)
        actorRef.send(0)

        actor.join()
    }

    @Test
    fun watchingActors() {
        val watched = object : Actor<Int, Void?>("WatchedActor", null) {
            @Suspendable
            override fun doRun(): Void? {
                LOG.info("WatchedActor Starting")
                receive(500, TimeUnit.MILLISECONDS)
                LOG.info("WatchedActor Finishing")
                return null
            }
        }

        val watcher = object : Actor<Int, Void?>("WatcherActor", null) {
            @Suspendable
            override fun doRun(): Void? {
                LOG.info("WatcherActor Listening")
                try {
                    LOG.info("WatcherActor received Message: {}", receive(2, TimeUnit.SECONDS))
                } catch (e: Exception) {
                    LOG.info("WatcherActor Received Exception", e)
                }
                return null
            }

            @Suspendable
            override fun handleLifecycleMessage(m: LifecycleMessage?): Int? {
                LOG.info("WatcherActor Received Lifecycle Message: {}", m)
                return super.handleLifecycleMessage(m)
            }
        }

        val watcherRef = watcher.spawn()
        TimeUnit.MILLISECONDS.sleep(200)

        val watchedRef = watched.spawn()
        watcher.link(watchedRef)

        watched.join()
        watcher.join()
    }
}
