package com.baeldung.pattern.hexagonal;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PersistenceFileAdapter implements StockTradeBookPersistence {
    private List<TradeBook> tradeBookList;

    PersistenceFileAdapter(String filePath) {
        tradeBookList = new ArrayList<>();
        try (Scanner scanner = new Scanner(getFile(filePath))) {
            while (scanner.hasNext()){
                TradeBook tradeBook = parseTradeBook(scanner.nextLine());
                tradeBookList.add(tradeBook);
            }
            System.out.printf("%s trade book(s) loaded%n", tradeBookList.size());
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    @Override
    public boolean add(String stockCode, String action, double price, int volume) {
        TradeBook newTradeBook = new TradeBook(stockCode, action, price, volume);
        tradeBookList.add(newTradeBook);
        return true;
    }

    @Override
    public TradeBook get(String stockCode, String action, double price) {
        for (TradeBook tradeBook: tradeBookList) {
            if (tradeBook.getStockCode().equalsIgnoreCase(stockCode) &&
                tradeBook.getAction().equalsIgnoreCase(action) &&
                tradeBook.getPrice() == price ) {
                return tradeBook;
            }
        }
        return null;
    }

    private File getFile(String filePath) throws FileNotFoundException {
        URL resource = getClass().getClassLoader().getResource(filePath);
        if (resource == null) {
            throw new FileNotFoundException("File not found");
        }
        return new File(resource.getFile());
    }

    private TradeBook parseTradeBook(String line) {
        String[] input = line.split(",");
        return new TradeBook(input[0], input[1], Double.parseDouble(input[2]), Integer.parseInt(input[3]));
    }
}
