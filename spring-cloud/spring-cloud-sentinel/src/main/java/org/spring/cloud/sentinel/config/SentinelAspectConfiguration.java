package org.spring.cloud.sentinel.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SentinelAspectConfiguration {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    @PostConstruct
    public void initFlowRules() {
        List<FlowRule> flowRules = new ArrayList<>();
        FlowRule flowRule = new FlowRule();
        // Defined resource
        flowRule.setResource("greeting");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // number of requests that QPS can pass in a second
        flowRule.setCount(1);
        flowRules.add(flowRule);
        FlowRuleManager.loadRules(flowRules);
    }

}
