import com.springinaction.pizza.Order;
import com.springinaction.pizza.Pizza;
import com.springinaction.pizza.PricingEngine;
import java.io.Serializable;

class PricingEngineImpl implements PricingEngine, Serializable {
  public float calculateOrderTotal(Order order) {
    print "IN GROOVY";
  
    retun 99.99;
  }
}
