package shallowVsDeepCopy;
import java.util.ArrayList;

public class DeepClass {
    private ArrayList<Object> list;  
    public DeepClass() {
        list = new ArrayList<>();
       
        list.add(new MutableProperty("Initial Value")); 
        list.add(20); 
    }

     public DeepClass(DeepClass original) {
        list = new ArrayList<>(original.list.size());
        for (Object item : original.list) {
            if (item instanceof MutableProperty) {
                list.add(new MutableProperty(((MutableProperty) item).getValue())); 
            } else {
                list.add(item); 
            }
        }
    }

    public ArrayList<Object> getList() {
        return list;  
    }
}

	
