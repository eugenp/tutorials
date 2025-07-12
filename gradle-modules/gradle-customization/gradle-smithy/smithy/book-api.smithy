$version: "2"

namespace com.baeldung.smithy.books

// use aws.protocols#restJson1
/// A simple book management service
@aws.protocols#restJson1
service BookManagementService {
    version: "1.0"
    resources: [
        Book
    ]
}

/// Represents a book in the bookstore
resource Book {
    identifiers: {
        bookId: BookId
    }
    properties: {
        title: String
        author: String
        isbn: String
        publishedYear: Integer
    }
    create: CreateBook
    read: GetBook
    list: ListBooks
    operations: [
        RecommendBook
    ]
}

/// Unique identifier for a book
@pattern("^[a-zA-Z0-9-_]+$")
string BookId

/// Recommend a book
@readonly
@http(method: "GET", uri: "/recommend/{bookId}")
operation RecommendBook {
    input: RecommendBookInput
    output: RecommendBookOutput
}

/// Creates a new book
@http(method: "POST", uri: "/books")
operation CreateBook {
    input: CreateBookInput
    output: CreateBookOutput
    errors: [
        ValidationException
    ]
}

/// Retrieves a specific book by ID
@readonly
@http(method: "GET", uri: "/books/{bookId}")
operation GetBook {
    input: GetBookInput
    output: GetBookOutput
    errors: [
        BookNotFoundException
    ]
}

/// Lists all books with optional pagination
@readonly
@http(method: "GET", uri: "/books")
operation ListBooks {
    input: ListBooksInput
    output: ListBooksOutput
}

/// Input structure for creating a book
structure CreateBookInput {
    @required
    title: String

    @required
    author: String

    @required
    isbn: String

    publishedYear: Integer
}

/// Output structure for book creation
structure CreateBookOutput {
    @required
    bookId: BookId

    @required
    title: String

    @required
    author: String

    @required
    isbn: String

    publishedYear: Integer
}

/// Input structure for getting a book
structure GetBookInput {
    @required
    @httpLabel
    bookId: BookId
}

/// Output structure for getting a book
structure GetBookOutput {
    @required
    bookId: BookId

    @required
    title: String

    @required
    author: String

    @required
    isbn: String

    publishedYear: Integer
}

/// Input structure for recommending a book
structure RecommendBookInput {
    @required
    @httpLabel
    bookId: BookId
}

/// Output structure for recommending a book
structure RecommendBookOutput {
    @required
    bookId: BookId

    @required
    title: String

    @required
    author: String
}

/// Input structure for listing books
structure ListBooksInput {
    @httpQuery("maxResults")
    maxResults: Integer

    @httpQuery("nextToken")
    nextToken: String
}

/// Output structure for listing books
structure ListBooksOutput {
    @required
    books: BookList

    nextToken: String
}

/// List of books
list BookList {
    member: BookSummary
}

/// Summary information about a book
structure BookSummary {
    @required
    bookId: BookId

    @required
    title: String

    @required
    author: String

    @required
    isbn: String

    publishedYear: Integer
}

/// Exception thrown when a book is not found
@error("client")
@httpError(404)
structure BookNotFoundException {
    @required
    message: String
}

/// Exception thrown when input validation fails
@error("client")
@httpError(400)
structure ValidationException {
    @required
    message: String
}
