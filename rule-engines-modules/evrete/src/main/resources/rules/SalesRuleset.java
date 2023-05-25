package org.abc.author1;

import com.baeldung.evrete.introduction.model.Customer;
import com.baeldung.evrete.introduction.model.Invoice;
import org.evrete.dsl.annotation.Rule;
import org.evrete.dsl.annotation.Where;

public class SalesRuleset {

    @Rule
    public void rule1(Customer $c) {
        $c.setTotal(0.0);
    }

    @Rule
    @Where("$i.customer == $c")
    public void rule2(Customer $c, Invoice $i) {
        $c.addToTotal($i.getAmount());
    }
}