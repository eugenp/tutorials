package com.baeldung.hexagonal.architecture.core.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.application.dtos.BreweryDto;
import com.baeldung.hexagonal.architecture.application.dtos.OpenBreweryDto;
import com.baeldung.hexagonal.architecture.core.domain.Brewery;
import com.baeldung.hexagonal.architecture.core.ports.BreweryPort;
import com.baeldung.hexagonal.architecture.core.ports.HttpPort;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BreweryService implements BreweryPort {

    private final static String URL = "https://api.openbrewerydb.org/breweries?by_city=";
    private HttpPort httpClient;
    private ObjectMapper objectMapper;

    public BreweryService(@Autowired HttpPort httpClient) {
        this.httpClient = httpClient;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<BreweryDto> search(String city) throws IOException, InterruptedException {
        Object breweryResponse;
        List<OpenBreweryDto> breweryDtos;

        breweryResponse = httpClient.doGet(URL + city);
        breweryDtos = Arrays.asList(objectMapper.readValue(breweryResponse.toString(), OpenBreweryDto[].class));

        List<Brewery> breweries = convertExternalDtosToDomains(breweryDtos);
        return convertDomainsToDtos(breweries);
    }

    private List<Brewery> convertExternalDtosToDomains(List<OpenBreweryDto> breweryDtos) {
        List<Brewery> breweries = new ArrayList<Brewery>();
        breweryDtos.forEach(breweryDto -> breweries.add(convertOpenBreweryDtoToDomain(breweryDto)));
        return breweries;
    }

    private Brewery convertOpenBreweryDtoToDomain(OpenBreweryDto breweryDto) {
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

    private List<BreweryDto> convertDomainsToDtos(List<Brewery> breweries) {
        List<BreweryDto> breweryDtos = new ArrayList<BreweryDto>();
        breweries.forEach(brewery -> breweryDtos.add(convertDomainToDto(brewery)));
        return breweryDtos;
    }

    private BreweryDto convertDomainToDto(Brewery brewery) {
        BreweryDto breweryDto = new BreweryDto();
        breweryDto.setName(brewery.getName());
        breweryDto.setBreweryType(brewery.getBreweryType());
        breweryDto.setCity(brewery.getCity());
        breweryDto.setState(brewery.getState());
        breweryDto.setCountry(brewery.getCountry());
        breweryDto.setWebsiteUrl(brewery.getWebsiteUrl());
        breweryDto.setAvailableListOfBeers(brewery.getBrewTypes());
        breweryDto.setAcceptsBitcoin(brewery.acceptsBitcoinAsPayMethod() ? "Yes" : "No");
        return breweryDto;
    }

}
