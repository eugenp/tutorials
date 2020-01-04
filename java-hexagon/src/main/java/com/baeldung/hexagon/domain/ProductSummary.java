/**
 * 
 */
package com.baeldung.hexagon.domain;

public class ProductSummary {

    private String name;
    private int totalQuantity;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the totalQuantity
     */
    public int getTotalQuantity() {
        return totalQuantity;
    }

    /**
     * @param totalQuantity the totalQuantity to set
     */
    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    
    
    @Override
    public String toString() {
            return "Total Quantity of " + this.name + " is " + this.totalQuantity;
    }

}
