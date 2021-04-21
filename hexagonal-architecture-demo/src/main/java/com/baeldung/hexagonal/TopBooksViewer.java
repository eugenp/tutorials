package com.baeldung.hexagonal;

public class TopBooksViewer {

    public static void main(String[] args) {
        ProcessUserInput userInput = new ConsoleProcessUserInputImpl();
        PrintBooksList booksList = new ConsolePrintBookListImpl();
        LoadBooksList loadBooksList = new MockLoadBooksListImpl();

        TopBooksViewerCore core = new TopBooksViewerCore(loadBooksList, booksList, userInput);

        core.showTopBooks();
    }
}
