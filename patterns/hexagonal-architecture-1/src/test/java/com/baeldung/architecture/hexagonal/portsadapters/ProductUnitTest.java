package com.baeldung.architecture.hexagonal.portsadapters;

import com.baeldung.architecture.hexagonal.service.PriceFetcher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

public class ProductUnitTest {

  ProductImpl product;
  PriceFetcher priceFetcher;

  @BeforeEach
  public void setup() {
    priceFetcher = Mockito.mock(PriceFetcher.class);
    product = new ProductImpl();
    product.priceFetcher = priceFetcher;
  }

  @Test
  public void shouldReturnPrice_whenValidProductIdAndQuantity() {
    Mockito.when(priceFetcher.getPrice(anyString(), anyInt())).thenReturn(100);
    int price = product.getPrice("1", 10);
    Assertions.assertThat(price).isEqualTo(100);
  }

  @Test
  public void shouldThrowsException_whenProductIdIsNull() {
    Mockito.when(priceFetcher.getPrice(anyString(), anyInt())).thenReturn(100);
    Assertions.assertThatThrownBy(() ->
        product.getPrice(null, 10))
        .hasMessage("Quantity or product value is invalid");
  }

  @Test
  public void shouldThrowsException_whenQuantityIsExceeded() {
    Mockito.when(priceFetcher.getPrice(anyString(), anyInt())).thenReturn(100);
    Assertions.assertThatThrownBy(() ->
        product.getPrice("1", 11))
        .hasMessage("Quantity or product value is invalid");
  }

  @Test
  public void shouldThrowsException_whenQuantityIsZero() {
    Mockito.when(priceFetcher.getPrice(anyString(), anyInt())).thenReturn(100);
    Assertions.assertThatThrownBy(() ->
        product.getPrice("1", 0))
        .hasMessage("Quantity or product value is invalid");
  }
}
