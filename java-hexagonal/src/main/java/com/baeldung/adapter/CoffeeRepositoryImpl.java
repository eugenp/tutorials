package com.baeldung.adapter;

import com.baeldung.core.domain.Coffee;
import com.baeldung.port.CoffeeRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class CoffeeRepositoryImpl implements CoffeeRepository {

	private Map<String, Coffee> CoffeeStore = new HashMap<String, Coffee>();

	@Override
	public void createCoffee(Coffee Coffee) {
		CoffeeStore.put(Coffee.getName(), Coffee);
	}

	@Override
	public Coffee getCoffee(String name) {
		return CoffeeStore.get(name);
	}

	@Override
	public List<Coffee> getAllCoffee() {
		return CoffeeStore.values().stream().collect(Collectors.toList());
	}

}
