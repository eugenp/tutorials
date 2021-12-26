package br.com.baeldung.operators;

public class Conditional {
	
	public static void verifyIfUserIsOver18AndMan(int years, boolean man) {
		
		int eighteen = 18;
		//man is a boolean variable, so to write "&& man" is the same to write "&& man == true"
		System.out.println(eighteen <= years && man);
		
		// the same of the last, but, now, in a if:
		if(eighteen <= years && man) {
			System.out.println("make something because the user is man and over 18");
		}else {
			//maybe the user is woman or under 18
			System.out.println("make other something because the user isn't man and over 18");
		}
		
	}
	
	public static void verifyIfUserIsOfTheFamilyOrAFriend(boolean family, boolean friend) {
		

		//family and friend are boolean variables, so write "family || friend" is the same to write "family == true || friend == true"
		System.out.println(family || friend);
		// the same of the last, but, now, in a if
		String message = "";
		if(family || friend) {
			message = "make something because the user is of the family or a friend ";
		}else {
			//The user isn't of the family and he isn't a friend. So, the user is a stranger
			message = "make other something because the user is a stranger";
		}
		
		System.out.println(message);
		
	}
	
	public static void verifyIfUserIsOfTheFamilyOrAFriendUsingTernary(boolean family, boolean friend) {
		

		//it is the same of the last method, but, now we are using the ternary
		String message = (family || friend) ?  "make something because the user is of the family or a friend " :  "make something because the user is of the family or a friend ";
		System.out.println(message);
		
	}


}
