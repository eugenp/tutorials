package com.baeldung;

import com.baeldung.functional.UserService;
import com.baeldung.persistence.Cart;
import com.baeldung.persistence.Product;
import com.baeldung.persistence.ShoppingDao;
import com.baeldung.persistence.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class HexagonalArchitectureApplicationRunner implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(HexagonalArchitectureApplicationRunner.class);

    @Autowired
    private UserService userService;

    @Autowired
    ShoppingDao shoppingDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Perform one time initialization
        // In real application, such initialization would be redundant
        // there will be dedicated use cases to handle it
        User user = userService.addUser("specialone", "Special", "One");
        LOG.info("Created user {} with (ID = {})", user.getUsername(), user.getId());

        Cart cart = shoppingDao.createCart(user);
        LOG.info("Created cart (ID = {}) for the user {} (ID = {})", cart.getId(), user.getUsername(), user.getId());

        Product product = shoppingDao.createProduct("bestone");
        LOG.info("Created product {} (ID = {})", product.getProductCode(), product.getId());
    }
}
