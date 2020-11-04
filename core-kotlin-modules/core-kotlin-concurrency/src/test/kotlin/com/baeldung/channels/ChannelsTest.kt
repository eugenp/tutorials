package com.baeldung.channels

import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ChannelsTest {
    @Test
    fun should_pass_data_from_one_coroutine_to_another() {
        runBlocking {
            // given
            val channel = Channel<String>()

            // when
            launch { // coroutine1
                channel.send("Hello World!")
            }
            val result = async { // coroutine 2
                channel.receive()
            }

            // then
            assertThat(result.await()).isEqualTo("Hello World!")
        }
    }
}