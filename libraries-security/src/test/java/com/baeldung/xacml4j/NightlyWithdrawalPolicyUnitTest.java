package com.baeldung.xacml4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.xacml4j.v20.Xacml20TestUtility;
import org.xacml4j.v30.Attribute;
import org.xacml4j.v30.Categories;
import org.xacml4j.v30.Category;
import org.xacml4j.v30.CompositeDecisionRule;
import org.xacml4j.v30.Decision;
import org.xacml4j.v30.Entity;
import org.xacml4j.v30.RequestContext;
import org.xacml4j.v30.ResponseContext;
import org.xacml4j.v30.Result;
import org.xacml4j.v30.XacmlPolicyTestSupport;
import org.xacml4j.v30.pdp.PolicyDecisionPoint;
import org.xacml4j.v30.pdp.PolicyDecisionPointBuilder;
import org.xacml4j.v30.spi.combine.DecisionCombiningAlgorithmProviderBuilder;
import org.xacml4j.v30.spi.function.FunctionProviderBuilder;
import org.xacml4j.v30.spi.pip.PolicyInformationPointBuilder;
import org.xacml4j.v30.spi.repository.InMemoryPolicyRepository;
import org.xacml4j.v30.spi.repository.PolicyRepository;
import org.xacml4j.v30.types.DoubleExp;
import org.xacml4j.v30.types.StringExp;
import org.xacml4j.v30.types.TimeExp;

public class NightlyWithdrawalPolicyUnitTest extends XacmlPolicyTestSupport {
    
    private static final String POLICY_SET = "xacml4j/NightlyWithdrawalsPolicy.xml";

    @Test
    public void testWhenNightlyWithdrawalOver500_thenFail() throws Exception {

        PolicyDecisionPoint pdp = buildPDP(POLICY_SET);

        // Action category
        Attribute actionAttribute = Attribute.builder("urn:oasis:names:tc:xacml:1.0:action:action-id")
            .value(StringExp.of("withdrawal"))
            .build();
        Entity actionEntity = Entity.builder()
            .attribute(actionAttribute)
            .build();
        Category actionCategory = Category.builder(Categories.ACTION)
            .entity(actionEntity)
            .build();

        // Environment Category
        Attribute timeAttribute = Attribute.builder("urn:oasis:names:tc:xacml:1.0:environment:current-time")
            .includeInResult(false)
            .value(TimeExp.of("21:00:00"))
            .build();
        Entity timeEntity = Entity.builder()
            .attribute(timeAttribute)
            .build();

        Category environmentCategory = Category.builder(Categories.ENVIRONMENT)
            .entity(timeEntity)
            .build();

        // ATM category
        Attribute amountAttribute = Attribute.builder("urn:baeldung:atm:withdrawal:amount")
            .value(DoubleExp.of("1200.00"))
            .build();
        Entity atmEntity = Entity.builder()
            .attribute(amountAttribute)
            .build();

        Category atmCategory = Category.builder(Categories.parse("urn:baeldung:atm:withdrawal"))
            .entity(atmEntity)
            .build();

        RequestContext request = RequestContext.builder()
            .attributes(actionCategory, environmentCategory, atmCategory)
            .build();

        ResponseContext response = pdp.decide(request);
        assertNotNull(response);
        assertTrue("Shoud have at least one result", response.getResults() != null && !response.getResults()
            .isEmpty());

        Result result = response.getResults()
            .iterator()
            .next();
        assertTrue("Evaluation should succeed", result.getStatus()
            .isSuccess());
        assertEquals("Should DENY withdrawal", Decision.DENY, result.getDecision());

    }

    @Test
    public void testWhenNightlyWithdrawalUnder500_thenSuccess() throws Exception {

        PolicyDecisionPoint pdp = buildPDP(POLICY_SET);

        // Action category
        Attribute actionAttribute = Attribute.builder("urn:oasis:names:tc:xacml:1.0:action:action-id")
            .includeInResult(false)
            .value(StringExp.of("withdrawal"))
            .build();
        Entity actionEntity = Entity.builder()
            .attribute(actionAttribute)
            .build();
        Category actionCategory = Category.builder(Categories.ACTION)
            .entity(actionEntity)
            .build();

        // Environment Category
        Attribute timeAttribute = Attribute.builder("urn:oasis:names:tc:xacml:1.0:environment:current-time")
            .includeInResult(false)
            .value(TimeExp.of("21:00:00"))
            .build();
        Entity timeEntity = Entity.builder()
            .attribute(timeAttribute)
            .build();
        Category environmentCategory = Category.builder(Categories.ENVIRONMENT)
            .entity(timeEntity)
            .build();

        // ATM category
        Attribute amountAttribute = Attribute.builder("urn:baeldung:atm:withdrawal:amount")
            .value(DoubleExp.of("499.00"))
            .build();
        Entity atmEntity = Entity.builder()
          .attribute(amountAttribute)
          .build();
        Category atmCategory = Category.builder(Categories.parse("urn:baeldung:atm:withdrawal"))
          .entity(atmEntity)
          .build();

        RequestContext request = RequestContext.builder()
          .attributes(actionCategory, environmentCategory, atmCategory)
          .build();

        ResponseContext response = pdp.decide(request);
        assertNotNull(response);
        assertTrue("Shoud have at least one result", 
          response.getResults() != null && !response.getResults().isEmpty());

        Result result = response.getResults().iterator().next();
        assertTrue("Evaluation should succeed", result.getStatus().isSuccess());
        assertEquals("Should PERMIT withdrawal", Decision.PERMIT, result.getDecision());

    }

    @Test
    public void testWhenBusinessHoursWithdrawalOver500_thenSuccess() throws Exception {

        PolicyDecisionPoint pdp = buildPDP(POLICY_SET);

        // Action category
        Attribute actionAttribute = Attribute.builder("urn:oasis:names:tc:xacml:1.0:action:action-id")
          .includeInResult(false)
          .value(StringExp.of("withdrawal"))
          .build();
        Entity actionEntity = Entity.builder()
          .attribute(actionAttribute)
          .build();
        Category actionCategory = Category.builder(Categories.ACTION)
          .entity(actionEntity)
          .build();

        // Environment Category
        Attribute timeAttribute = Attribute.builder("urn:oasis:names:tc:xacml:1.0:environment:current-time")
          .includeInResult(false)
          .value(TimeExp.of("12:00:00"))
          .build();
        Entity timeEntity = Entity.builder()
          .attribute(timeAttribute)
          .build();
        Category environmentCategory = Category.builder(Categories.ENVIRONMENT)
          .entity(timeEntity)
          .build();

        // ATM category
        Attribute amountAttribute = Attribute.builder("urn:baeldung:atm:withdrawal:amount")
          .value(DoubleExp.of("2000.00"))
          .build();
        Entity atmEntity = Entity.builder()
          .attribute(amountAttribute)
          .build();

        Category atmCategory = Category.builder(Categories.parse("urn:baeldung:atm:withdrawal"))
          .entity(atmEntity)
          .build();

        RequestContext request = RequestContext.builder()
          .attributes(actionCategory, environmentCategory, atmCategory)
          .build();

        ResponseContext response = pdp.decide(request);
        assertNotNull(response);
        assertTrue("Shoud have at least one result", response.getResults() != null && !response.getResults()
          .isEmpty());

        Result result = response.getResults()
          .iterator()
          .next();
        assertTrue("Evaluation should succeed", result.getStatus().isSuccess());
        assertEquals("Should PERMIT withdrawal", Decision.PERMIT, result.getDecision());

    }

    private PolicyDecisionPoint buildPDP(String... policyResources) throws Exception {
        PolicyRepository repository = new InMemoryPolicyRepository("tes-repository", FunctionProviderBuilder.builder()
          .defaultFunctions()
          .build(),
        DecisionCombiningAlgorithmProviderBuilder.builder()
            .withDefaultAlgorithms()
            .create());

        List<CompositeDecisionRule> policies = new ArrayList<CompositeDecisionRule>(policyResources.length);
        for (String policyResource : policyResources) {
            CompositeDecisionRule policy = repository.importPolicy(Xacml20TestUtility.getClasspathResource(policyResource));
            log.info("Policy: {}", policy);
            policies.add(policy);
        }

        return PolicyDecisionPointBuilder.builder("testPdp")
          .policyRepository(repository)
          .pip(PolicyInformationPointBuilder.builder("testPip")
          .defaultResolvers()
          .build())
          .rootPolicy(policies.get(0))
          .build();
    }

}
