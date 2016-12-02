import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Generics {

	// definition of a generic method
	public static <T> List<T> fromArrayToList(T[] a) {
		List<T> list = new ArrayList<>();
		Arrays.stream(a).forEach(list::add);
		return list;
	}

	// example of a generic method that has Number as an upper bound for T
	public static <T extends Number> List<T> fromArrayToListWithUpperBound(T[] a) {
		List<T> list = new ArrayList<>();
		Arrays.stream(a).forEach(list::add);
		return list;
	}

	// example of a generic method with a wild card, this method can be used
	// with a list of any subtype of Building
	public static boolean paintAllBuildings(List<? extends Building> buildings) {
		for (Building building : buildings) {
			building.paint();
		}
		return true;
	}

}