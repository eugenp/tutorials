import java.util.Map;

public class MapValueCheck {

    public static boolean areAllValuesTrueWithReduce(Map<String, Boolean> map) {
        return map.values().stream().reduce(true, Boolean::logicalAnd);
    }

    public static boolean areAllValuesTrueWithContains(Map<String, Boolean> map) {
        return !map.containsValue(false);
    }

    public static boolean areAllValuesTrueWithAllMatch(Map<String, Boolean> map) {
        return !map.isEmpty() && map.values().stream().allMatch(value -> value);
    }

    public static boolean areAllValuesTrueWithLoop(Map<String, Boolean> map) {
        for (Boolean value : map.values()) {
            if (!value) {
                return false;
            }
        }
        return true;
    }
}