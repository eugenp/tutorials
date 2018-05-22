package com.baeldung.performancetests.model.destination;

import com.baeldung.performancetests.model.source.SourceOrder;
import com.google.common.base.Objects;
import com.googlecode.jmapper.annotations.JMap;
import com.googlecode.jmapper.annotations.JMapConversion;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
public class Order {
    @JMap
    private User orderingUser;
    @JMap
    private List<Product> orderedProducts;
    @JMap("status")
    private OrderStatus orderStatus;
    @JMap
    private LocalDate orderDate;
    @JMap
    private LocalDate orderFinishDate;
    @JMap
    private PaymentType paymentType;
    @JMap
    private Discount discount;
    @JMap
    private int orderId;
    @JMap
    private DeliveryData deliveryData;
    @JMap
    private Shop offeringShop;

    public Order() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (o.getClass() == SourceOrder.class) {
            SourceOrder order =
              (SourceOrder) o;
            return Objects.equal(orderingUser, order.getOrderingUser()) &&
                    Objects.equal(orderedProducts, order.getOrderedProducts()) &&
                    orderStatus.ordinal() == order.getStatus().ordinal() &&
                    Objects.equal(orderDate, order.getOrderDate()) &&
                    Objects.equal(orderFinishDate, order.getOrderFinishDate()) &&
                    paymentType.ordinal() == order.getPaymentType().ordinal() &&
                    Objects.equal(discount, order.getDiscount()) &&
                    Objects.equal(deliveryData, order.getDeliveryData());
        }
        if (o.getClass() != getClass()) return false;
        Order order = (Order) o;
        return Objects.equal(orderingUser, order.orderingUser) &&
                Objects.equal(orderedProducts, order.orderedProducts) &&
                orderStatus == order.orderStatus &&
                Objects.equal(orderDate, order.orderDate) &&
                Objects.equal(orderFinishDate, order.orderFinishDate) &&
                paymentType == order.paymentType &&
                Objects.equal(discount, order.discount) &&
                Objects.equal(deliveryData, order.deliveryData);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderingUser, orderedProducts, orderStatus, orderDate, orderFinishDate, paymentType, discount, deliveryData);
    }

    public User getOrderingUser() {
        return orderingUser;
    }

    public void setOrderingUser(User orderingUser) {
        this.orderingUser = orderingUser;
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<Product> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus status) {
        this.orderStatus = status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getOrderFinishDate() {
        return orderFinishDate;
    }

    public void setOrderFinishDate(LocalDate orderFinishDate) {
        this.orderFinishDate = orderFinishDate;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public DeliveryData getDeliveryData() {
        return deliveryData;
    }

    public void setDeliveryData(DeliveryData deliveryData) {
        this.deliveryData = deliveryData;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Order(User orderingUser, List<Product> orderedProducts, OrderStatus orderStatus, LocalDate orderDate, LocalDate orderFinishDate, PaymentType paymentType, Discount discount, int orderId, DeliveryData deliveryData, Shop offeringShop) {

        this.orderingUser = orderingUser;
        this.orderedProducts = orderedProducts;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
        this.orderFinishDate = orderFinishDate;
        this.paymentType = paymentType;
        this.discount = discount;
        this.orderId = orderId;
        this.deliveryData = deliveryData;
        this.offeringShop = offeringShop;
    }

    public Shop getOfferingShop() {
        return offeringShop;
    }

    public void setOfferingShop(Shop offeringShop) {
        this.offeringShop = offeringShop;
    }



    @JMapConversion(from = "status", to = "orderStatus")
    public OrderStatus conversion(com.baeldung.performancetests.model.source.OrderStatus status) {
        OrderStatus orderStatus = null;
        switch(status) {
            case CREATED:
                orderStatus = OrderStatus.CREATED;
                break;
            case FINISHED:
                orderStatus = OrderStatus.FINISHED;
                break;

            case CONFIRMED:
                orderStatus = OrderStatus.CONFIRMED;
                break;

            case COLLECTING:
                orderStatus = OrderStatus.COLLECTING;
                break;

            case IN_TRANSPORT:
                orderStatus = OrderStatus.IN_TRANSPORT;
                break;
        }
        return orderStatus;
    }

    @JMapConversion(from = "paymentType", to = "paymentType")
    public PaymentType conversion(com.baeldung.performancetests.model.source.PaymentType type) {
        PaymentType paymentType = null;
        switch(type) {
            case CARD:
                paymentType = PaymentType.CARD;
                break;

            case CASH:
                paymentType = PaymentType.CASH;
                break;

            case TRANSFER:
                paymentType = PaymentType.TRANSFER;
                break;
        }
        return paymentType;
    }


}
