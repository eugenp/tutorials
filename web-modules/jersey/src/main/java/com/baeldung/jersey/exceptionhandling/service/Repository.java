package com.baeldung.jersey.exceptionhandling.service;

import com.baeldung.jersey.exceptionhandling.data.Stock;
import com.baeldung.jersey.exceptionhandling.data.Wallet;
import com.baeldung.jersey.exceptionhandling.repo.Db;

public class Repository {
    public static Db<Stock> STOCKS_DB = new Db<>();
    public static Db<Wallet> WALLETS_DB = new Db<>();
}
