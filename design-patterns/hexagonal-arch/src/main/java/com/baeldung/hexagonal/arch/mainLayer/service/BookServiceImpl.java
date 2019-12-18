package com.baeldung.hexagonal.arch.mainLayer.service;

import com.baeldung.hexagonal.arch.mainLayer.repo.BookInMemoryRepo;
import com.baeldung.hexagonal.arch.repoLayer.BookInMemoryRepoImpl;

public class BookServiceImpl implements BookService {
    private BookInMemoryRepo bimr = new BookInMemoryRepoImpl();

	@Override
	public void buyABook() {
	    bimr.purchase();
	}

	@Override
	public void sellABook() {
	    bimr.sell();  
	}  
}
