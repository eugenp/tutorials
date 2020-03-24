package com.baeldung.category;

import groovy.lang.Category

@Category(Number)
class NumberCategory {

    public Number cube() {
        return this*this*this
    }

    public int divideWithRoundUp(BigDecimal divisor, boolean isRoundUp) {
        def mathRound = isRoundUp ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN
        return (int)new BigDecimal(this).divide(divisor, 0, mathRound)
    }

}
