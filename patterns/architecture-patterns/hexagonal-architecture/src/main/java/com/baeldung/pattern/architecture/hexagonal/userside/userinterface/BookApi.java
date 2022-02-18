package com.baeldung.pattern.architecture.hexagonal.userside.userinterface;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baeldung.pattern.architecture.hexagonal.domain.entity.Book;
import com.baeldung.pattern.architecture.hexagonal.domain.interactor.IBookService;
import com.google.gson.Gson;

@WebServlet(urlPatterns = "/books", name = "BookApi")
public class BookApi extends HttpServlet {
    private IBookService bookService;

    public BookApi(IBookService bookService) {
        this.bookService = bookService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Book> books = bookService.getBooks();
        String bookListString = new Gson().toJson(books);
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(bookListString);
        out.flush();
    }
}
