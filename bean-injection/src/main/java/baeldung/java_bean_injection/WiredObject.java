package baeldung.java_bean_injection;

public class WiredObject {
    private String myWiredString;
    
    public void setMyWiredString(String myWiredString){
        this.myWiredString  = myWiredString;
    }
    
    public String getMyWiredString(){
        return this.myWiredString;
    }
}
