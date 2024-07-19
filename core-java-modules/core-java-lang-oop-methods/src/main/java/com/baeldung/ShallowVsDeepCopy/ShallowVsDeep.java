package shallowVsDeepCopy;

public class ShallowVsDeep {
    public static void main(String[] args) {
         ShallowClass firstShallowObj = new ShallowClass(); 
        firstShallowObj.list.add("Hello");  
        firstShallowObj.list.add(10.5f);  

        ShallowClass secShallowObj = firstShallowObj.shallowCopy();

        secShallowObj.list.set(1, "World");

        System.out.println("The value of list in firstShallowObj is: " + firstShallowObj.list);

        System.out.println("\nThe value of list in secShallowObj is: " + secShallowObj.list);

        DeepClass firstDeepObj = new DeepClass();
        firstDeepObj.getList().add("Hello");  
        firstDeepObj.getList().add(10.5f);
		
        DeepClass secDeepObj = new DeepClass(firstDeepObj);


        ((MutableProperty) secDeepObj.getList().get(0)).setValue("World"); 
        secDeepObj.getList().set(1, "Updated Hello");

        System.out.println("\nThe value of list in firstDeepObj is: " + firstDeepObj.getList());
        System.out.println("\nThe value of list in secDeepObj is: " + secDeepObj.getList());
    }
}