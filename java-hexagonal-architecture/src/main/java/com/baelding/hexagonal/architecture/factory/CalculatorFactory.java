package com.baelding.hexagonal.architecture.factory;

import com.baelding.hexagonal.architecture.calculator.Calculator;
import com.baelding.hexagonal.architecture.model.GeometricEntity;

public interface CalculatorFactory {

	Calculator getCalculator(GeometricEntity geometricEntity);
}
