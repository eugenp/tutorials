package com.baeldung.kotlin;

import org.junit.Test
import org.mockito.Mockito

class LibraryManagementTest {
    @Test(expected = IllegalStateException::class)
    fun whenBookIsNotAvailable_thenAnExceptionIsThrown() {
        val mockBookService = Mockito.mock(BookService::class.java)

        Mockito.`when`(mockBookService.inStock(100)).thenReturn(false)

        val manager = LendBookManager(mockBookService)

        manager.checkout(100, 1)
    }

    @Test
    fun whenBookIsAvailable_thenLendMethodIsCalled() {
        val mockBookService = Mockito.mock(BookService::class.java)

        Mockito.`when`(mockBookService.inStock(100)).thenReturn(true)

        val manager = LendBookManager(mockBookService)

        manager.checkout(100, 1)

        Mockito.verify(mockBookService).lend(100, 1)
    }
}