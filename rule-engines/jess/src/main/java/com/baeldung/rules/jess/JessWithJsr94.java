package com.baeldung.rules.jess;

import com.baeldung.rules.jess.model.Answer;
import com.baeldung.rules.jess.model.Question;

import javax.rules.*;
import javax.rules.admin.RuleAdministrator;
import javax.rules.admin.RuleExecutionSet;
import javax.rules.admin.RuleExecutionSetCreateException;
import javax.rules.admin.RuleExecutionSetRegisterException;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class JessWithJsr94 {
    public static final String RULES_BONUS_FILE = "/bonus.clp";
    public static final String RULES_URI = "com/baeldung/rules/bonus";
    private static final String RULE_SERVICE_PROVIDER = "jess.jsr94";
    private static Logger log = Logger.getLogger("JessWithData");

    public static void main(String[] args) throws Exception {
        RuleServiceProvider ruleServiceProvider = instantiateJessInstance();

        // load our rules and register them with the rules provider
        registerRules(RULES_BONUS_FILE, RULES_URI, ruleServiceProvider);

        runRules(RULES_URI, ruleServiceProvider);
    }

    private static RuleServiceProvider instantiateJessInstance() throws ClassNotFoundException, ConfigurationException {
        // Load the rule service provider of the reference implementation.
        // Loading this class will automatically register this provider with the provider manager.
        Class.forName(RULE_SERVICE_PROVIDER + ".RuleServiceProviderImpl");

        //  Get the rule service provider from the provider manager.
        return RuleServiceProviderManager.getRuleServiceProvider(RULE_SERVICE_PROVIDER);
    }

    private static void runRules(String rulesURI, RuleServiceProvider ruleServiceProvider)
      throws ConfigurationException, RuleSessionTypeUnsupportedException, RuleSessionCreateException, RuleExecutionSetNotFoundException, RemoteException, InvalidRuleSessionException {
        // Get a RuleRuntime and invoke the rule engine.
        RuleRuntime ruleRuntime = ruleServiceProvider.getRuleRuntime();

        //Create a statelessRuleSession.
        StatelessRuleSession statelessRuleSession = (StatelessRuleSession) ruleRuntime.createRuleSession(rulesURI, new HashMap(), RuleRuntime.STATELESS_SESSION_TYPE);

        calculateResults(statelessRuleSession);

        statelessRuleSession.release();
    }

    private static void calculateResults(StatelessRuleSession statelessRuleSession) throws InvalidRuleSessionException, RemoteException {
        List data = prepareData();

        // execute the rules
        List results = statelessRuleSession.executeRules(data);

        checkResults(results);
    }

    private static List prepareData() {
        List data = new ArrayList();
        data.add(new Question("Can I have a bonus?", -5));
        return data;
    }

    private static void checkResults(List results) {
        Iterator itr = results.iterator();
        while (itr.hasNext()) {
            Object obj = itr.next();
            if (obj instanceof Answer) {
                log.info(obj.toString());
            }
        }
    }

    private static void registerRules(String rulesFile, String rulesURI, RuleServiceProvider serviceProvider) throws ConfigurationException, RuleExecutionSetCreateException, IOException, RuleExecutionSetRegisterException {
        //  Get the rule administrator.
        RuleAdministrator ruleAdministrator = serviceProvider.getRuleAdministrator();

        // load the rules
        InputStream ruleInput = JessWithJsr94.class.getResourceAsStream(rulesFile);
        HashMap vendorProperties = new HashMap();

        // Create the RuleExecutionSet
        RuleExecutionSet ruleExecutionSet = ruleAdministrator
          .getLocalRuleExecutionSetProvider(vendorProperties)
          .createRuleExecutionSet(ruleInput, vendorProperties);

        // Register the rule execution set.
        ruleAdministrator.registerRuleExecutionSet(rulesURI, ruleExecutionSet, vendorProperties);
    }
}