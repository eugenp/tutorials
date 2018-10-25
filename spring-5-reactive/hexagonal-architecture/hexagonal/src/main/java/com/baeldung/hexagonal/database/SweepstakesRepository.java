package com.baeldung.hexagonal.database;

import java.util.Map;
import java.util.Optional;

import com.baeldung.hexagonal.domain.Sweepstake;
import com.baeldung.hexagonal.domain.SweepstakeId;

/**
 * 
 * Interface for accessing sweepstakes in database
 *
 */
public interface SweepstakesRepository {
	 /**
	   * Find Sweepstake by id
	   */
	  Optional<Sweepstake> findById(SweepstakeId id);

	  /**
	   * Save Sweepstake
	   */
	  Optional<SweepstakeId> save(Sweepstake ticket);

	  /**
	   * Get all Sweepstakes
	   */
	  Map<SweepstakeId, Sweepstake> findAll();

	  /**
	   * Delete all Sweepstake
	   */
	  void deleteAll();
}
