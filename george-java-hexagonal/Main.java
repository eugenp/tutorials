class Main{
    public static void main(String[] args) {
        Book book = new Book();
        book.setId("b1");
        book.setTitle("Book1");

        Book book2 = new Book();
        book2.setId("b2");
        book2.setTitle("Book2");

        ADatabasePort adDatabasePort = new ADatabasePort();
        BookService bookService = new BookService(adDatabasePort);

        bookService.add(book);
        bookService.add(book2);

        System.out.println(bookService.search("b1"));
    }
}
