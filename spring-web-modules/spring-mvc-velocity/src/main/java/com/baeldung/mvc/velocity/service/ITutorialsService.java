package com.baeldung.mvc.velocity.service;

import com.baeldung.mvc.velocity.domain.Tutorial;

import java.util.List;

public interface ITutorialsService {

    List<Tutorial> listTutorials();
}
