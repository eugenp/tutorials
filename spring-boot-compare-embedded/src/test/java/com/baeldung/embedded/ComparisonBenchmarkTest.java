package com.baeldung.embedded;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ComparisonBenchmarkTest {

    private static final String BASE_URL = "http://localhost:8080/actuator/metrics";

    @Before
    public void getAndPrintActuatorMetrics() {
        RestTemplate restTemplate = new RestTemplate();
        for (MetricConfiguration c : getMetricConfigs()) {
            getAndPrintActuatorMetric(restTemplate, c);
        }
        System.out.println("");
    }

    @Test
    public void launchBenchmark() throws Exception {
        Options opt = new OptionsBuilder()
                .include(this.getClass().getName() + ".*")
                .mode(Mode.Throughput)
                .timeUnit(TimeUnit.SECONDS)
                .warmupIterations(3)
                .warmupTime(TimeValue.seconds(10))
                .measurementIterations(3)
                .measurementTime(TimeValue.minutes(1))
                .threads(5)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();
        new Runner(opt).run();
    }

    @Benchmark
    public void benchmark() throws Exception {
        RestTemplate template = new RestTemplate();
        for (int i = 0; i < 10; i++) {
            MetricNames metricNames = template.getForObject(BASE_URL, MetricNames.class);
            metricNames.getNames().stream().forEach(n -> {
                template.getForObject(BASE_URL + "/" + n, String.class);
            });
        }
    }

    static class MetricNames {
        private String[] names;

        public List<String> getNames() {
            return Arrays.asList(this.names);
        }
    }

    static class MetricConfiguration {

        private String label;
        private String metric;
        private Class<?> type;
        private String jsonPath;

        public MetricConfiguration(String label, String metric, Class<?> type, String path) {
            this.label = label;
            this.metric = metric;
            this.type = type;
            this.jsonPath = path;
        }

        public String getLabel() {
            return label;
        }

        public Class<?> getType() {
            return type;
        }

        public String getJsonPath() {
            return jsonPath;
        }

        public String getMetric() {
            return metric;
        }
    }

    private List<MetricConfiguration> getMetricConfigs() {
        return Lists.newArrayList(
                new MetricConfiguration("jvm.memory.used", "jvm.memory.used", Integer.class, "$.measurements[0].value"),
                new MetricConfiguration("jvm.classes.loaded", "jvm.classes.loaded", Integer.class, "$.measurements[0].value"),
                new MetricConfiguration("jvm.threads.live", "jvm.threads.live", Integer.class, "$.measurements[0].value"));
    }

    private void getAndPrintActuatorMetric(RestTemplate restTemplate, MetricConfiguration c) {
        String response = restTemplate.getForObject(BASE_URL + "/" + c.getMetric(), String.class);
        String value = (JsonPath.parse(response).read(c.getJsonPath(), c.getType())).toString();
        System.out.println("Startup Metric >>> " + c.getLabel() + "=" + value);
    }
}
