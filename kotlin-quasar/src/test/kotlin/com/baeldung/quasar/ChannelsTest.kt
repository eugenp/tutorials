package com.baeldung.quasar

import co.paralleluniverse.fibers.Suspendable
import co.paralleluniverse.kotlin.fiber
import co.paralleluniverse.strands.channels.Channels
import co.paralleluniverse.strands.channels.Selector
import com.google.common.base.Function
import org.junit.Test

class ChannelsTest {
    @Test
    fun createChannel() {
        Channels.newChannel<String>(0, // The size of the channel buffer
                Channels.OverflowPolicy.BLOCK,  // The policy for when the buffer is full
                true,  // Whether we should optimize for a single message producer
                true) // Whether we should optimize for a single message consumer
    }

    @Test
    fun blockOnMessage() {
        val channel = Channels.newChannel<String>(0, Channels.OverflowPolicy.BLOCK, true, true)

        fiber @Suspendable {
            while (!channel.isClosed) {
                val message = channel.receive()
                println("Received: $message")
            }
            println("Stopped receiving messages")
        }

        channel.send("Hello")
        channel.send("World")

        channel.close()
    }

    @Test
    fun selectReceiveChannels() {
        val channel1 = Channels.newChannel<String>(0, Channels.OverflowPolicy.BLOCK, true, true)
        val channel2 = Channels.newChannel<String>(0, Channels.OverflowPolicy.BLOCK, true, true)

        fiber @Suspendable {
            while (!channel1.isClosed && !channel2.isClosed) {
                val received = Selector.select(Selector.receive(channel1), Selector.receive(channel2))

                println("Received: $received")
            }
        }

        fiber @Suspendable {
            for (i in 0..10) {
                channel1.send("Channel 1: $i")
            }
        }

        fiber @Suspendable {
            for (i in 0..10) {
                channel2.send("Channel 2: $i")
            }
        }
    }

    @Test
    fun selectSendChannels() {
        val channel1 = Channels.newChannel<String>(0, Channels.OverflowPolicy.BLOCK, true, true)
        val channel2 = Channels.newChannel<String>(0, Channels.OverflowPolicy.BLOCK, true, true)

        fiber @Suspendable {
            for (i in 0..10) {
                Selector.select(
                        Selector.send(channel1, "Channel 1: $i"),
                        Selector.send(channel2, "Channel 2: $i")
                )
            }
        }

        fiber @Suspendable {
            while (!channel1.isClosed) {
                val msg = channel1.receive()
                println("Read: $msg")
            }
        }

        fiber @Suspendable {
            while (!channel2.isClosed) {
                val msg = channel2.receive()
                println("Read: $msg")
            }
        }
    }

    @Test
    fun tickerChannel() {
        val channel = Channels.newChannel<String>(3, Channels.OverflowPolicy.DISPLACE)

        for (i in 0..10) {
            val tickerConsumer = Channels.newTickerConsumerFor(channel)
            fiber @Suspendable {
                while (!tickerConsumer.isClosed) {
                    val message = tickerConsumer.receive()
                    println("Received on $i: $message")
                }
                println("Stopped receiving messages on $i")
            }
        }

        for (i in 0..50) {
            channel.send("Message $i")
        }

        channel.close()
    }


    @Test
    fun transformOnSend() {
        val channel = Channels.newChannel<String>(0, Channels.OverflowPolicy.BLOCK, true, true)

        fiber @Suspendable {
            while (!channel.isClosed) {
                val message = channel.receive()
                println("Received: $message")
            }
            println("Stopped receiving messages")
        }

        val transformOnSend = Channels.mapSend(channel, Function<String, String> { msg: String? -> msg?.toUpperCase() })

        transformOnSend.send("Hello")
        transformOnSend.send("World")

        channel.close()
    }

    @Test
    fun transformOnReceive() {
        val channel = Channels.newChannel<String>(0, Channels.OverflowPolicy.BLOCK, true, true)

        val transformOnReceive = Channels.map(channel, Function<String, String> { msg: String? -> msg?.reversed() })

        fiber @Suspendable {
            while (!transformOnReceive.isClosed) {
                val message = transformOnReceive.receive()
                println("Received: $message")
            }
            println("Stopped receiving messages")
        }


        channel.send("Hello")
        channel.send("World")

        channel.close()
    }
}
