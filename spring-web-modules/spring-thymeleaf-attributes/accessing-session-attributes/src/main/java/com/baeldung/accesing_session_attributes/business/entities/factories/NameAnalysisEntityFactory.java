package com.baeldung.accesing_session_attributes.business.entities.factories;

import org.springframework.stereotype.Service;

import com.baeldung.accesing_session_attributes.business.entities.NameAgeEntity;
import com.baeldung.accesing_session_attributes.business.entities.NameAnalysisEntity;
import com.baeldung.accesing_session_attributes.business.entities.NameCountriesEntity;
import com.baeldung.accesing_session_attributes.business.entities.NameGenderEntity;

@Service
public class NameAnalysisEntityFactory {

    public NameAnalysisEntity getInstance(String nameRequest, NameGenderEntity gender, NameAgeEntity age, NameCountriesEntity countries) {
        NameAnalysisEntity nameAnalysis = new NameAnalysisEntity();
        nameAnalysis.setName(nameRequest);
        nameAnalysis.setGender(gender);
        nameAnalysis.setAge(age);
        nameAnalysis.setCountries(countries);
        return nameAnalysis;
    }

}
