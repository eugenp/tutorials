package com.baeldung.kotlin.delegates

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class InterfaceDelegationTest {

    @Test
    fun `when delegated implementation is used then it works as expected`() {
        val producer = EnhancedProducer(ProducerImpl())
        assertThat(producer.produce()).isEqualTo("ProducerImpl and EnhancedProducer")
    }

    @Test
    fun `when composite delegation is used then it works as expected`() {
        val service = CompositeService()
        assertThat(service.processMessage("message")).isEqualTo("MessageServiceImpl: message")
        assertThat(service.processUser("user")).isEqualTo("UserServiceImpl: user")
    }

    @Test
    fun `when decoration is used then delegate knows nothing about it`() {
        val service = ServiceDecorator()
        service.serve {
            assertThat(it).isEqualTo(1)
        }
    }
}