package org.ordermanagement.copy.demo;

import org.ordermanagement.copy.demo.models.Order;
import org.ordermanagement.copy.demo.models.OrderLine;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderCopyTest {

    private static final String ORDER_ID = UUID.randomUUID().toString();
    private static final String ORDER_DESCRIPTION = "My Original Order";
    private static final String LINE_1_DESCRIPTION = "line 1";

    @Test
    @DisplayName("Shallow copy overwrites original order's data")
    public void shouldModifyOriginalOrderWhenShallowCopyingOrder() throws CloneNotSupportedException {

        // Create a dummy order
        Order originalOrder = generateDummyOrder();

        // Shallow copy our dummy order
        Order clonedOrder = originalOrder.clone();

        // Update line 1 description and quantity
        clonedOrder.getLines().get(0).setDescription("shallow copied line 1 description");
        clonedOrder.getLines().get(0).setQuantity(5);

        assertThat(originalOrder)
                .isNotSameAs(clonedOrder);
        assertThat(originalOrder.getLines().get(0).getDescription())
                .isEqualTo(clonedOrder.getLines().get(0).getDescription());
        assertThat(originalOrder.getLines().get(0).getQuantity())
                .isEqualTo(clonedOrder.getLines().get(0).getQuantity());
    }

    @Test
    @DisplayName("Deep copy retains original order intact")
    public void shouldRetainingOriginalOrderWhenDeepCopyingOrder() {

        // Create a dummy order
        Order originalOrder = generateDummyOrder();

        // Deep copy our dummy order
        Order clonedOrder = new Order(originalOrder);

        // Update line 1 description and quantity
        clonedOrder.getLines().get(0).setDescription("deep copied line 1 description");
        clonedOrder.getLines().get(0).setQuantity(5);

        // Assert that the original order line has been retained as it is
        assertThat(originalOrder.getLines().get(0).getDescription())
                .isNotEqualTo(clonedOrder.getLines().get(0).getDescription());
        assertThat(originalOrder.getLines().get(0).getQuantity())
                .isNotEqualTo(clonedOrder.getLines().get(0).getQuantity());
    }

    /**
     * A method that is used to create a dummy order.
     *
     * @return order The generated order object.
     */
    private Order generateDummyOrder() {
        OrderLine orderLine1 = new OrderLine(LINE_1_DESCRIPTION, 1);
        List<OrderLine> lines = new ArrayList<>();
        lines.add(orderLine1);
        return new Order(ORDER_ID, ORDER_DESCRIPTION, lines);
    }

}