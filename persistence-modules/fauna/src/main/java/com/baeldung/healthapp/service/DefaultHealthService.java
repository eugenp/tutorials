package com.baeldung.healthapp.service;

import static com.faunadb.client.query.Language.Collection;
import static com.faunadb.client.query.Language.Create;
import static com.faunadb.client.query.Language.Now;
import static com.faunadb.client.query.Language.Obj;
import static com.faunadb.client.query.Language.Value;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.healthapp.FaunaClients;
import com.baeldung.healthapp.domain.HealthData;
import com.faunadb.client.FaunaClient;
import com.faunadb.client.types.Value;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DefaultHealthService implements HealthService {

    @Autowired
    private GeoLocationService geoLocationService;

    @Autowired
    private FaunaClients faunaClients;

    @Override
    public void process(HealthData healthData) throws MalformedURLException, InterruptedException, ExecutionException {

        String region = geoLocationService.getRegion( //
            healthData.latitude(), //
            healthData.longitude());

        FaunaClient faunaClient = faunaClients.getFaunaClient(region);
        
        Value queryResponse = faunaClient.query(
            Create(Collection("healthdata"), 
                Obj("data", 
                    Obj(Map.of(
                        "userId", Value(healthData.userId()), 
                        "temperature", Value(healthData.temperature()),
                        "pulseRate", Value(healthData.pulseRate()),
                        "bpSystolic", Value(healthData.bpSystolic()),
                        "bpDiastolic", Value(healthData.bpDiastolic()),
                        "latitude", Value(healthData.latitude()),
                        "longitude", Value(healthData.longitude()),
                        "timestamp", Now()))))
            ).get();
        
        log.info("Query response received from Fauna: {}", queryResponse);
    }
}
