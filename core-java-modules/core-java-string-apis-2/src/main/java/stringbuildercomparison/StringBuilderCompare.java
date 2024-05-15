package stringbuildercomparison;

public class StringBuilderCompare {

    public static boolean compare(StringBuilder one, StringBuilder two){
        if(one.length() != two.length()){
            return false;
        }
        for(int i = 0; i < one.length(); i++){
            if(one.charAt(i) != two.charAt(i)){
                return false;
            }
        }
        return true;
    }

}
