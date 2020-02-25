package com.baeldung.hexagonal.architecture.adapters;

import com.baeldung.hexagonal.architecture.core.Brewery;
import com.baeldung.hexagonal.architecture.dtos.BreweryDto;
import com.baeldung.hexagonal.architecture.ports.BreweryPort;
import com.baeldung.hexagonal.architecture.ports.HttpPort;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BreweryServiceAdapter implements BreweryPort {

    private final static String URL = "https://api.openbrewerydb.org/breweries?by_city=";
    private HttpPort httpClient;
    private ObjectMapper objectMapper;


    public BreweryServiceAdapter(HttpPort httpClient){
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
    }

    public List<Brewery> search(String city) throws IOException, InterruptedException {
        Object breweryResponse;
        List<BreweryDto> breweryDtos = null;

        breweryResponse = httpClient.doGet(URL + city);
        breweryDtos = Arrays.asList(objectMapper.readValue(breweryResponse.toString(), BreweryDto[].class));

        return convertDtosToDomains(breweryDtos);
    }

    private List<Brewery> convertDtosToDomains (List<BreweryDto> breweryDtos){
        final List<Brewery> breweries = new ArrayList<Brewery>();
        breweryDtos.forEach(breweryDto -> breweries.add(convertDtoToDomain(breweryDto)));
        return breweries;
    }

    private Brewery convertDtoToDomain(BreweryDto breweryDto){
        Brewery brewery = new Brewery();
        brewery.setId(breweryDto.getId());
        brewery.setName(breweryDto.getName());
        brewery.setBreweryType(breweryDto.getBreweryType());
        brewery.setCity(breweryDto.getCity());
        brewery.setState(breweryDto.getState());
        brewery.setCountry(breweryDto.getCountry());
        brewery.setWebsiteUrl(breweryDto.getWebsiteUrl());
        return brewery;
    }

}
