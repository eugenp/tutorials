package com.baeldung;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import com.baeldung.service.CartService;

public class Controller {
	
	public static void main(String[] args) {
		
		port(8080);
		
        get("/view/:id", (req, res) -> {
        	res.type("application/json");
        	return CartService.view(req);
        });
        
        get("/view", (req, res) -> {
            res.type("application/json");
        	return CartService.view();
        });
        
        post("/add/:name/:price", (req, res) -> {
        	res.type("application/json");
        	return CartService.add(req);
        });
        
    }
	
}
