package com.baeldung.kogito.rules;

import com.baeldung.kogito.rules.model.LoanApplication;
import lombok.Getter;
import lombok.Setter;
import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

@Getter
@Setter
public class LoanApplicationUnit implements RuleUnitData {

    private DataStore<LoanApplication> applications = DataSource.createStore();

}
