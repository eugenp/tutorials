package hexagonal.domain.spi;

import hexagonal.domain.model.Order;

public interface OrderPersistencePort {

    void addOrder(Order product);

}
