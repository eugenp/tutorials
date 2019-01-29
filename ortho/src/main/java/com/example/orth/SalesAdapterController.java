package com.example.orth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/corporateSales/")
public class SalesAdapterController implements SalesPortInterface {
	@Autowired
	private SalesService salesService;
	@Override
	public void create(@RequestBody CorporateSales request) {
		salesService.create(request.getItem(),request.getRegion(),request.getAmount());
	}
	@Override
	public CorporateSales view(@PathVariable Integer id) {
		CorporateSales sales = salesService.view(id);
		return sales;
	}
}
