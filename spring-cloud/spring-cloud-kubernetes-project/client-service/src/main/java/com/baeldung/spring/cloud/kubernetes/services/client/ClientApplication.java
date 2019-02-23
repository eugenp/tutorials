package com.baeldung.spring.cloud.kubernetes.services.department;

import com.baeldung.spring.cloud.kubernetes.services.client.config.ClientConfig;
import com.baeldung.spring.cloud.kubernetes.services.client.service.TravelAgencyClientService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
public class ClientApplication implements CommandLineRunner {

    private static final Log log = LogFactory.getLog(ClientApplication.class);
    private static final String FIND_TRAVEL_DEALS_TASK = "Find new travel deals";

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private TravelAgencyClientService travelAgencyClient;

    @Autowired
    private ClientConfig clientConfig;

    private String task = FIND_TRAVEL_DEALS_TASK;

    @Bean
    private RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Value("${spring.application.name}")
    private String appName;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Client (" + appName + ":" + clientConfig.getType() + ")Started! ");
    }

    /*
     * Every 10 seconds look for new deals
     */
    @Scheduled(fixedRate = 10000)
    public void doSomeWork() throws UnknownHostException {
        if (task.equals(FIND_TRAVEL_DEALS_TASK)) {
            task = findNewDeals();
            if (task.equals(FIND_TRAVEL_DEALS_TASK)) {
                log.info("NO DEAL FOUND, I will keep looking for one ");
            }
        }
        log.info(">>> Working on " + task);
    }




    private String findNewDeals() throws UnknownHostException {
        List<String> services = this.discoveryClient.getServices();

        for (String service : services) {
            List<ServiceInstance> instances = this.discoveryClient.getInstances(service);
            for (ServiceInstance se : instances) {
                Map<String, String> metadata = se.getMetadata();
                String type = metadata.get("type");
                if ("deal".equals(type)) {

                    String from = appName + "@" + InetAddress.getLocalHost().getHostName();
                    String url = "http://" + se.getServiceId();
                    return travelAgencyClient.requestDeals(url, from);
                }
            }
        }
        return FIND_TRAVEL_DEALS_TASK;
    }
}
