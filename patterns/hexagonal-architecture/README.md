# Hexagonal Architecture
## 1. Overview

In this tutorial, we'll learn what is the Hexagonal Architecture and how to use it.

First, we'll go through a bit of theory. After that, we'll create an example where we'll illustrate the usage of the pattern.

## 2. What is the Hexagonal Architecture?

Hexagonal architecture is a design pattern also known as a “ports-and-adapters” architecture. It talks about isolate core business logic of an application from outside concerns. Here outside concerns can be UI, database, any external API/endpoint etc. 

Inside and outside parts both communicate with each other via ports and adapters. Here interface works as port and provides abstraction similarly implementation class works as adapter and provides concretion.

When an event comes from outside world at a port, an adapter converts it into a readable message. Then adapter passes it to the application core. Likewise application core sends a message to outer world through a port. Then an adapter converts message into receiver's readable form .

Hence using different ports and adapters makes application core totally ignorant from the nature of input and output events. 

Also an application doesn't require to have six and only six sides. It can have less or more side.

## 3. Benefits of the architecture are:
* It enables writing fast, stable tests for the business logic. Users, automation programs or batch scripts can drive business logic in isolation of database
* Independent of external services
* Easy to swap ports and adapters
* Separation enables us to quickly iterate on the outer layers without touching the inner layers
## 4. Ports

Interfaces(ports) provide abstraction. A port is simply a gateway to your application, allowing inbound and outbound flow.

An inbound port exposes application functionality to the outside world whereas an outbound port consumes services from outside world.

## 5. Adapters

Classes(adapters) provide concrete implementation. Adapters can be swapped by each other using a port.

### 5.1. Primary Adapters

Also called as driving adapters. These adapters actually drive the application. They invoke actions on the application using the inbound ports of application.
HTTP request, automatic test or integration API are examples of primary adapters. They use service interfaces (inbound ports of the application) to communicate with the core logic.

### 5.2. Secondary Adapters

Also called driven adapters. These adapters are implementations of the outbound port. These are invoked/driven by the core application.

API calls, MQ, Database connections are some examples of secondary adapters.

## 6. Let’s see an example

Let's now see an example of Hexagonal Architecture. Let's create a new application which adds book and searches book. Application's core business logic is _BookService_:

    @Service
    public class BookService {

        @Autowired
        private BookOutboundPort bookOutboundPort;

        public void addBook(String name, String author, int price) {

            Book book = new Book(name, author, price);

            bookOutboundPort.addBook(book);
        }

        public Book search(String name) {
            return bookOutboundPort.getBook(name);
        }
    }

The domain is _Book_:

    public class Book {

        private String name;

        private String author;

        private int price;
     
        // standard setters and getters
    }

_BookInboundPort_ is used as inbound port to expose book services:

    public interface BookInboundPort {

        void add(Book book);

        Book search(String bookName);
    }

_BookController_ is an inbound adapter which extends _BookInboundPort_ and exposed over Rest:

    @RestController
    public class BookController implements BookInboundPort {

        @Autowired
        private BookService bookService;

        @PostMapping(value = "/book")
        @ResponseStatus(HttpStatus.CREATED)
        public void add(@RequestBody Book book) {
            bookService.addBook(book.getName(), book.getAuthor(), book.getPrice());
        }

        @Override
        @GetMapping(value = "/book/{name}")
        public Book search(@PathVariable String name) {
            return bookService.search(name);
        }
    }

A port _BookOutboundPort_ is used as outbound port:

    public interface BookOutboundPort {

        void addBook(Book book);

        Book getBook(String bookName);
    }

BookServiceAdapter is used as outbound adapter to save and retrieve book information to and from DB:

    @Component
    public class BookRepositoryAdapter implements BookOutboundPort {

        @Autowired
        private BookRepository bookRepository;

        @Override
        public void addBook(Book book) {
            BookEntity bookEntity = new BookEntity(book.getName(), book.getAuthor(), book.getPrice());
            bookRepository.saveAndFlush(bookEntity);
        }

        @Override
        public Book getBook(String bookName) {
            BookEntity bookEntity = bookRepository.findByName(bookName);
            if (null != bookEntity) {
                return new Book(bookEntity.getName(), bookEntity.getAuthor(), bookEntity.getPrice());
            }
            return null;
        }
    }

## 7. Conclusion

In this article, we had a look at the Hexagonal Architecture. Some Benefits are:

* It makes our application core independent of any external concern. So any changes outside the core doesn't affect it.
* It makes application testing easier and independent.
