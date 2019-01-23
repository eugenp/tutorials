package com.calculatesum.cmdio;

import com.calculatesum.service.CalculateSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component("commandSumSmdIOAdapter")
public class CommandSumCmdIOAdapter implements CalculateSumCmdIO {
    @Autowired
    CalculateSumService calculateSumService;

    public Integer addTwoIntegers(Scanner input) {
        Integer firstInteger = input.nextInt();
        Integer secondInteger = input.nextInt();

        return calculateSumService.calculateSum(firstInteger, secondInteger);
    }
}
