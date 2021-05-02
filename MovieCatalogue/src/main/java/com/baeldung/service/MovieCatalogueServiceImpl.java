package com.baeldung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.dao.MovieDao;
import com.baeldung.model.MovieCatalogue;

@Service 
public class MovieCatalogueServiceImpl implements MovieCatalogueService{
	@Autowired
	private MovieDao movieDao;
	
	public MovieCatalogueServiceImpl()
	{

	}
	public MovieCatalogueServiceImpl( MovieDao aMovieDao) {
		
		movieDao= aMovieDao;
	}
	
	
	@Override
	public List<MovieCatalogue> getAllMovies() {
		return movieDao.getAllMovies();
	}

}
