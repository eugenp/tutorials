package org.baeldung.bean.injection;

public class Ship {
    private Helm helm;
   
    public Ship() {
        helm = new Helm();
    }
    
    public Ship(Helm helm) {
        this.helm = helm;
    }
    
    public void setHelm(Helm helm) {
        this.helm = helm;
    }
    
    public Helm getHelm() {
    	return this.helm;
    }
}
