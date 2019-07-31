package com.baeldung.demo.hexagonal;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.demo.hexagonal.dao.PlayerDao;
import com.baeldung.demo.hexagonal.domain.Player;

public class PlayerProfileAdapter implements IPlayerProfile {
	@Autowired
	PlayerDao playerDao;

	public Player getPlayerProfile(long id) {
		return playerDao.get(id);
	}

	public void createPlayerProfile(Player p) {
		playerDao.create(p);
	}
}