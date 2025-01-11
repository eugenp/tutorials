package com.baeldung.category

@Category(Number)
class NumberCategory {

    Number cube() {
        return this**3
    }

    int divideWithRoundUp(BigDecimal divisor, boolean isRoundUp) {
        def mathRound = isRoundUp ? BigDecimal.ROUND_UP : BigDecimal.ROUND_DOWN
        
        return (int) new BigDecimal(this).divide(divisor, 0, mathRound)
    }
}
