package hexagonal.domain.model;

public class Order {

    private Integer orderId;
    private String name;

    public Order() {
        super();
    }

    public Order(Integer orderId, String name, String description) {
        this.orderId = orderId;
        this.name = name;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
