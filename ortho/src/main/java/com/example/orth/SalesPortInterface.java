package com.example.orth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface SalesPortInterface {
	@PostMapping("create")
	public void create(@RequestBody CorporateSales request);

	@GetMapping("view/{id}")
	public CorporateSales view(@PathVariable Integer salesId);
}