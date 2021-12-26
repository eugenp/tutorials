package br.com.baeldung.operators;

public class OtherAssingment {

	public static void useOtherOperators() {

		// "variable1 += number" is the same as "variable1 = variable1 + number"
		int var1 = 5;
		var1 += 10;
		// it is the same as "var1 = var1 + 5"
		System.out.println(var1);

		// "variable2 -= number" is the same as "variable2 = variable2 - number"
		int var2 = 10;
		var2 -= 5;
		// it is the same of "var2 = var2 - 5"
		System.out.println(var2);

		// "variable3 *= number" is the same as "variable3 = variable3 * number"
		int var3 = 2;
		var3 *= 5;
		// it is the same as "var3 = var3 * 5"
		System.out.println(var3);

		// "variable4 /= number" is the same as variable4 = variable4 / number
		int var4 = 10;
		var4 /= 5;
		// it is the same as "var4 = var4 / 5"
		System.out.println(var4);

		// "variable5 %= number" is the same as "variable5 = variable5 % number"
		int var5 = 10;
		var5 %= 7;
		// it is the same as "var5 = var5 % 7"
		System.out.println(var5);

		// "variable6 <<= number" is the same as "variable6 = variable6 << number"
		int var6 = 10;
		var6 <<= 7;
		// it is the same as "var6 = var6 << 7"
		System.out.println(var6);

		// "variable7 >>= number" is the same as "variable7 = variable7 << number"
		int var7 = 10;
		var7 >>= 7;
		// it is the same as "var7 = var7 >> 7"
		System.out.println(var7);

		// "variable8 ^= number" is the same as "variable8 = variable8 ^ number"
		int var8 = 10;
		var8 ^= 7;
		// it is the same as "var8 = var8 ^ 7"
		System.out.println(var8);

		// "variable9 &= number" is the same as "variable9 = variable9 & number"
		int var9 = 10;
		var9 &= 7;
		// it is the same as "var9 = var9 & 7"
		System.out.println(var9);

		// "variable10 |= number" is the same as "variable10 = variable10 | number"
		int var10 = 10;
		var10 |= 7;
		// it is the same as "var10 = var10 | 7"
		System.out.println(var10);

	}

}
