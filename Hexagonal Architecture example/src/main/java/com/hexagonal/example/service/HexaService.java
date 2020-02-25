package com.hexagonal.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HexaService 
{
	@Autowired
	List<Integer> list;
	
	public void add(int number)
	{
		list.add(number);
	}
	
	public int getNumber(int index)
	{
		return list.get(index);
	}

}
