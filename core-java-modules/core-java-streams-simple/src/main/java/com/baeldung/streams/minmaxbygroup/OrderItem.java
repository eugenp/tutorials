package com.baeldung.streams.minmaxbygroup;

public class OrderItem {
  private Long id;
  private Double price;
  private OrderCategory category;

  // other fields

  public OrderItem(long id, OrderCategory category, double price) {
    this.id = id;
    this.category = category;
    this.price = price;
  }

  public OrderCategory getCategory() {
    return category;
  }

  public void setCategory(OrderCategory category) {
    this.category = category;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
