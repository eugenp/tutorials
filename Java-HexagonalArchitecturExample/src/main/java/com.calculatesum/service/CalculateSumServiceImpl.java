package com.calculatesum.service;

import org.springframework.stereotype.Service;

@Service("calculateSumService")
public class CalculateSumServiceImpl implements CalculateSumService {
    public Integer calculateSum(Integer firstInteger, Integer secondInteger) {
        Integer sum = null;

        if (firstInteger != null && secondInteger != null) {
            sum = firstInteger + secondInteger;
        }

        return sum;
    }
}
