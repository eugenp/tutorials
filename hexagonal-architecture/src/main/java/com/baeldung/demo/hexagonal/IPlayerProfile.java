package com.baeldung.demo.hexagonal;

import com.baeldung.demo.hexagonal.domain.Player;

public interface IPlayerProfile {
	Player getPlayerProfile(long id);

	void createPlayerProfile(Player p);
}