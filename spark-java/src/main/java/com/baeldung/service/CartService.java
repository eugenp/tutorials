package com.baeldung.service;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.domain.Item;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import spark.Request;

public class CartService {
	
	private static ObjectWriter mapper = new ObjectMapper().writer().withDefaultPrettyPrinter();
	private static Map<Integer,Item> cart = new HashMap<Integer,Item>();
	private static int id = 1;

    //view all items
	public static String view() throws JsonProcessingException {
		return mapper.writeValueAsString(cart);
	}
	
	//view searched item
	public static String view(Request req) throws JsonProcessingException {
		return mapper.writeValueAsString(cart.get(Integer.parseInt(req.params("id"))));
	}
	
	//add item
	public static String add(Request req) throws JsonProcessingException {
		Item item = new Item(req.params("name"), Integer.parseInt(req.params("price")));
		cart.put(id, item);
		id++;
		return mapper.writeValueAsString(item);
	}
	
}
