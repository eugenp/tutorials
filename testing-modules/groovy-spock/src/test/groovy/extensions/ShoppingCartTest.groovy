package extensions

import mocks.ShoppingCart
import spock.lang.Specification

class ShoppingCartTest extends Specification {
    def "verify multiple properties of a ShoppingCart"() {
        given:
        ShoppingCart cart = new ShoppingCart()
        cart.addItem("Apple", 3)
        cart.addItem("Banana", 2)

        expect:
        with(cart) {
            totalItems == 5
            totalPrice == 10.00
            items.contains("Apple")
            items.contains("Banana")
        }
    }
}