package com.baeldung.accesing_session_attributes.business;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.baeldung.accesing_session_attributes.business.entities.NameGenderEntity;

@Service
public interface GenderAnalysisService {
    ResponseEntity<NameGenderEntity> getGenderAnalysisForName(String nameToAnalyze);
}
