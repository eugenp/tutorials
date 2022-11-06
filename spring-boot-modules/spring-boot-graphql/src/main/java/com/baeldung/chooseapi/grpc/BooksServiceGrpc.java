package com.baeldung.chooseapi.grpc;

import com.baeldung.chooseapi.BooksServiceGrpc.BooksServiceImplBase;
import com.baeldung.chooseapi.BooksServiceOuterClass.BooksRequest;
import com.baeldung.chooseapi.BooksServiceOuterClass.BooksResponse;

import com.baeldung.chooseapi.dtos.Book;
import com.baeldung.chooseapi.services.BooksService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import com.baeldung.chooseapi.BooksServiceOuterClass.BookProto;
import com.baeldung.chooseapi.BooksServiceOuterClass.AuthorProto;

import java.util.List;

@GrpcService
public class BooksServiceGrpc extends BooksServiceImplBase {

    private final BooksService booksService;

    public BooksServiceGrpc(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public void books(
      BooksRequest request, StreamObserver<BooksResponse> responseObserver) {
        List<Book> books = booksService.getBooks();

        BooksResponse.Builder responseBuilder = BooksResponse.newBuilder();
        books.forEach(book -> responseBuilder.addBook(mapBookToProto(book)));

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    private BookProto mapBookToProto(Book book) {
        return BookProto.newBuilder()
          .setTitle(book.getTitle())
          .setAuthor(AuthorProto.newBuilder()
            .setFirstName(book.getAuthor().getFirstName())
            .setLastName(book.getAuthor().getLastName())
            .build())
          .setYear(book.getYear())
          .build();
    }

}
