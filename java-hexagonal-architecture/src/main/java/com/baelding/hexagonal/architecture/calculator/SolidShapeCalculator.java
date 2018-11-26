package com.baelding.hexagonal.architecture.calculator;

import com.baelding.hexagonal.architecture.model.SolidShape;

public interface SolidShapeCalculator extends Calculator{

	Double volume(SolidShape solidShape);
}
