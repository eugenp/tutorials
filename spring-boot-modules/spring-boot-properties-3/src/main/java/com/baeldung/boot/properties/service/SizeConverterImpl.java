package com.baeldung.boot.properties.service;

import org.springframework.stereotype.Service;
import com.baeldung.boot.properties.config.TshirtSizeConfig;


@Service
public class SizeConverterImpl implements SizeConverterService {

    private final TshirtSizeConfig tshirtSizeConfig;

    public SizeConverterImpl(TshirtSizeConfig tshirtSizeConfig) {
        this.tshirtSizeConfig = tshirtSizeConfig;
    }

    public int  convertSize(String label, String countryCode) {
        if(countryCode == null) {
            return tshirtSizeConfig.getSimpleMapping().get(label);
        }
        return tshirtSizeConfig.getComplexMapping().get(label).get(countryCode.toLowerCase());
    }
}
