package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

/**
 * The core domain of the application {@Link GroceryCart}
 */
@Entity
public class GroceryCart {

    @Column(name = "product_id", columnDefinition = "bigint(5)")
    private Long id;

    @Column(name = "product_price", columnDefinition = "decimal(10,2)")
    private BigDecimal price;

    @Column(name = "product_quantity", columnDefinition = "integer(2)")
    private int quantity;


    //STANDARD GETTERS & SETTERS

    /**
     * Gets id
     *
     * @return the value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the value of id
     *
     * @param id the value of id to be set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets price
     *
     * @return the value of price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the value of price
     *
     * @param price the value of price to be set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets quantity
     *
     * @return the value of quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of quantity
     *
     * @param quantity the value of quantity to be set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
