package com.baeldung.spring.spel.examples;

import com.baeldung.spring.spel.entity.Car;
import com.baeldung.spring.spel.entity.CarPark;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.List;

public class SpelParser {
    public static void main(String[] args) {
        Car car = new Car();
        car.setMake("Good manufacturer");
        car.setModel("Model 3");
        car.setYearOfProduction(2014);

        CarPark carPark = new CarPark();

        SpelParserConfiguration config = new SpelParserConfiguration(true, true);

        StandardEvaluationContext context = new StandardEvaluationContext(carPark);

        ExpressionParser expressionParser = new SpelExpressionParser(config);
        expressionParser.parseExpression("cars[0]").setValue(context, car);

        Car result = carPark.getCars().get(0);

        System.out.println(result);
    }
}
