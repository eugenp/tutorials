package com.baeldung.mockk

import io.mockk.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class BasicMockKUnitTest {

    @Test
    fun givenServiceMock_whenCallingMockedMethod_thenCorrectlyVerified() {
        // given
        val service = mockk<TestableService>()
        every { service.getDataFromDb("Expected Param") } returns "Expected Output"
        // when
        val result = service.getDataFromDb("Expected Param")
        // then
        verify { service.getDataFromDb("Expected Param") }
        assertEquals("Expected Output", result)
    }

    @Test
    fun givenServiceSpy_whenMockingOnlyOneMethod_thenOtherMethodsShouldBehaveAsOriginalObject() {
        // given
        val service = spyk<TestableService>()
        every { service.getDataFromDb(any()) } returns "Mocked Output"
        // when checking mocked method
        val firstResult = service.getDataFromDb("Any Param")
        // then
        assertEquals("Mocked Output", firstResult)
        // when checking not mocked method
        val secondResult = service.doSomethingElse("Any Param")
        // then
        assertEquals("I don't want to!", secondResult)
    }

    @Test
    fun givenRelaxedMock_whenCallingNotMockedMethod_thenReturnDefaultValue() {
        // given
        val service = mockk<TestableService>(relaxed = true)
        // when
        val result = service.getDataFromDb("Any Param")
        // then
        assertEquals("", result)
    }

    @Test
    fun givenObject_whenMockingIt_thenMockedMethodShouldReturnProperValue() {
        // given
        val service = TestableService()
        mockkObject(service)
        // when calling not mocked method
        val firstResult = service.getDataFromDb("Any Param")
        // then return real response
        assertEquals("Value from DB", firstResult)

        // when calling mocked method
        every { service.getDataFromDb(any()) } returns "Mocked Output"
        val secondResult = service.getDataFromDb("Any Param")
        // then return mocked response
        assertEquals("Mocked Output", secondResult)
    }

    @Test
    fun givenMock_whenCapturingParamValue_thenProperValueShouldBeCaptured() {
        // given
        val service = mockk<TestableService>()
        val slot = slot<String>()
        every { service.getDataFromDb(capture(slot)) } returns "Expected Output"
        // when
        service.getDataFromDb("Expected Param")
        // then
        assertEquals("Expected Param", slot.captured)
    }

    @Test
    fun givenMock_whenCapturingParamsValues_thenProperValuesShouldBeCaptured() {
        // given
        val service = mockk<TestableService>()
        val list = mutableListOf<String>()
        every { service.getDataFromDb(capture(list)) } returns "Expected Output"
        // when
        service.getDataFromDb("Expected Param 1")
        service.getDataFromDb("Expected Param 2")
        // then
        assertEquals(2, list.size)
        assertEquals("Expected Param 1", list[0])
        assertEquals("Expected Param 2", list[1])
    }


}