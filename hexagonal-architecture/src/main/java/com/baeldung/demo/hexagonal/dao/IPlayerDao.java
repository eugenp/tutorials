package com.baeldung.demo.hexagonal.dao;

import com.baeldung.demo.hexagonal.domain.Player;

public interface IPlayerDao {
	Player get(long id);

	void create(Player p);
}