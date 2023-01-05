package MyDemoPackage;

public class MyCopyClass {
	public int DoShallowCopy()
	{
		MyData objA = new MyData(); 
		MyData objB = objA; 
		objB.i=5; 
		return objA.i;
	}
	public int DoDeepCopy()
	{
		MyData objA = new MyData(); 
		MyData objB = new MyData(); 
		objB.i=5;
		return objA.i;
	}
}
