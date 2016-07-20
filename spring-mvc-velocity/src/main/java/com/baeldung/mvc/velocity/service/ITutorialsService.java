package com.baeldung.mvc.velocity.service;

import java.util.List;

import com.baeldung.mvc.velocity.domain.Tutorial;

public interface ITutorialsService {

	public List<Tutorial> listTutorials();
}
