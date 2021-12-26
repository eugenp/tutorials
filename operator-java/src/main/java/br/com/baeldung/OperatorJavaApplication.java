package br.com.baeldung;

import br.com.baeldung.operators.Arithmetic;
import br.com.baeldung.operators.Assingment;
import br.com.baeldung.operators.BitWiseBitShift;
import br.com.baeldung.operators.Comparison;
import br.com.baeldung.operators.Conditional;
import br.com.baeldung.operators.EqualityAndRelational;
import br.com.baeldung.operators.OtherAssingment;
import br.com.baeldung.operators.Unary;


public class OperatorJavaApplication {

	public static void main(String[] args) {
		
		System.out.println("Using Arithmetic operators");
		Arithmetic.useAdditiveToConcatenation();
		Arithmetic.useAdditiveToArihmeticOperation();
		Arithmetic.subtract();
		Arithmetic.multiply();
		Arithmetic.divide();
		
		System.out.println("Using Assingment operators");
		Assingment.assignmentSample();
		
		System.out.println("Using BitWise and BitShift operators");
		BitWiseBitShift.useBitwiseAnd();
		BitWiseBitShift.useBitwiseExclusiveOr();
		BitWiseBitShift.useBitwiseInclusiveOr();
		BitWiseBitShift.useSignedLeftShift();
		BitWiseBitShift.useSignedRightShift();
		BitWiseBitShift.useUnaryBitWiseComplement();
		BitWiseBitShift.useUnsignedRightShift();
		
		
		System.out.println("Using Comparison operators");
		Comparison.verifyIfAirplaneIsAnInstanceOfCar();
		Comparison.verifyIfAirplaneIsAnInstanceOfTransport();
		Comparison.verifyIfCarIsAnInstanceOfTransport();
		Comparison.verifyTransportInstanceAsAirplane();
		Comparison.verifyTransportInstanceAsAirplaneButInstantiatedAsCar();
		Comparison.verifyTransportInstanceAsCar();
		Comparison.verifyTransportInstanceAsCarButInstantiatedAsAirplane();
		
		System.out.println("Using Conditional operators");
		Conditional.verifyIfUserIsOfTheFamilyOrAFriend(true, true);
		Conditional.verifyIfUserIsOfTheFamilyOrAFriend(true, false);
		Conditional.verifyIfUserIsOfTheFamilyOrAFriend(false, true);
		Conditional.verifyIfUserIsOfTheFamilyOrAFriend(false, false);
		
		Conditional.verifyIfUserIsOfTheFamilyOrAFriendUsingTernary(false, false);
		Conditional.verifyIfUserIsOfTheFamilyOrAFriendUsingTernary(true, true);
		Conditional.verifyIfUserIsOfTheFamilyOrAFriendUsingTernary(false, true);
		Conditional.verifyIfUserIsOfTheFamilyOrAFriendUsingTernary(true, false);
		
		Conditional.verifyIfUserIsOver18AndMan(14, false);
		Conditional.verifyIfUserIsOver18AndMan(14, true);
		Conditional.verifyIfUserIsOver18AndMan(20, false);
		Conditional.verifyIfUserIsOver18AndMan(21, true);
		
		System.out.println("Using Euqality and Relational operators");
		EqualityAndRelational.testEqualTo();
		EqualityAndRelational.testGreaterThan();
		EqualityAndRelational.testGreaterThanOrEqualTo();
		EqualityAndRelational.testGreaterThanOrEqualTo();
		EqualityAndRelational.testLessThan();
		EqualityAndRelational.testLessThanOrEqualTo();
		EqualityAndRelational.testNotEqualTo();
		
		System.out.println("Using Unary operators");
		Unary.changeBooleanValue();
		Unary.changePositiveToNegative();
		Unary.decrement_1();
		Unary.increment_1();
		Unary.showPositive();
		
		
		System.out.println("Using Other Assingment operators");
		OtherAssingment.useOtherOperators();
	}

}
