package mocks

import spock.lang.Specification


class BookServiceTest extends Specification {
    def "should retrieve book details and verify method calls"() {
        given:
        def bookRepository = Mock(BookRepository) {
            findById(1L) >> new Book("Effective Java", "Joshua Bloch")
            findById(2L) >> null
        }
        def bookService = new BookService(bookRepository)

        when:
        Book effectiveJava = bookService.getBookDetails(1L)
        Book unknownBook = bookService.getBookDetails(2L)

        then:
        1 * bookRepository.findById(1L)
        1 * bookRepository.findById(2L)
        effectiveJava.title == "Effective Java"
        unknownBook == null
    }
}