package com.baeldung.application;

import com.baeldung.domain.Calculations;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class MathServiceImpl implements MathService {

    private CalculationsRepository calculationsRepository;

    @Override
    @Transactional
    public Integer add(Integer number1, Integer number2) {
        Integer sum = number1 + number2;
        Calculations calculations = Calculations.builder()
            .number1(number1)
            .number2(number2)
            .sum(sum)
            .build();
        calculationsRepository.save(calculations);
        return sum;
    }
}
