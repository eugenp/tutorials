package br.com.baeldung.operators;

public class BitWiseBitShift {

	public static void useUnaryBitWiseComplement() {
		//it modifies the values of the binary, changing zero by one, and one by zero 
		int var1 = 35;
		System.out.println(getStringInBinary(var1));
		System.out.println(getStringInBinary(~var1));
	}
	
	public static void useSignedLeftShift() {
		//it cut the first N ("variable << N") binary and add N zero or N one in the end of the binary.
		int var1 = -36;
		System.out.println(getStringInBinary(var1));
		System.out.println(getStringInBinary(var1 << 1));
	}
	
	public static void useSignedRightShift() {
		//it cut the last N ("variable >> N") binary and add N zero or N one in the begin of the binary.
		//But the negative value was changed to a negative value also
		int var1 = -36;
		System.out.println(getStringInBinary(var1));
		int var2 = var1 >> 1;
		System.out.println(getStringInBinary(var2));
		//The new value is a negative value also
		System.out.println(var2);
	}
	
	public static void useUnsignedRightShift() {
		//it cut the last N ("variable >> N") binary and add N zero or N one in the begin of the binary.
		//But, this operator can change positve to negative number.
		int var1 = -36;
		System.out.println(getStringInBinary(var1));
		int var2 = var1 >>> 1; 
		System.out.println(getStringInBinary(var2));
		//The nevative value was changed to a positive value
		System.out.println(var2);  
	}
	
	public static void useBitwiseAnd() {
		//it compare both values in the binary level, bit by bit.
		//If the compared bits are 1, then the new bit will be 1, else the new bit will be 0 
		int var1 = 36;
		int var2 = 33;
		System.out.println(getStringInBinary(var1));
		System.out.println(getStringInBinary(var2));		
		System.out.println(getStringInBinary(var1 & var2));  
	}
	
	public static void useBitwiseExclusiveOr() {
		//it compare both values in the binary level, bit by bit.
		//If the compared bits are equals, then the new bit will be 0, else the new bit will be 1 
		int var1 = 36;
		int var2 = 33;
		System.out.println(getStringInBinary(var1));
		System.out.println(getStringInBinary(var2));		
		System.out.println(getStringInBinary(var1 ^ var2));  
	}
	
	public static void useBitwiseInclusiveOr() {
		//it compare both values in the binary level, bit by bit.
		//If some of both bits is 1, then the new bit will be 1, else the new bit will be 0 
		int var1 = 36;
		int var2 = 33;
		System.out.println(getStringInBinary(var1));
		System.out.println(getStringInBinary(var2));		
		System.out.println(getStringInBinary(var1 | var2));  
	}
	
	
	
	
	
	private static String getStringInBinary(int value) {

       String binaryRoot =  String.format("%8s",Integer.toBinaryString(value)).replaceAll(" ", "0");
       return binaryRoot.substring(binaryRoot.length() - 8);
	}
	
	
}
