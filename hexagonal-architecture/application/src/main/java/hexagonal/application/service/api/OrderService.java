package hexagonal.application.service.api;

import hexagonal.domain.model.Order;

public interface OrderService {

    void createOrder(Order product);

}
