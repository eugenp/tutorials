package com.baeldung.kogito.rules;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

import com.baeldung.kogito.rules.model.LoanApplication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanApplicationUnit implements RuleUnitData {

    private DataStore<LoanApplication> applications = DataSource.createStore();

}
