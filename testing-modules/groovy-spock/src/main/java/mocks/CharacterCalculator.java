package mocks;

public class CharacterCalculator {

    public int countCharacterInString(String value, char characterToCount) {
        int result = 0;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == characterToCount) {
                result++;
            }
        }
        return result;
    }

}
