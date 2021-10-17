package task5.model;

import java.math.BigDecimal;

public enum CurrencyType {
    DOLLAR(new BigDecimal("1")),
    RUBLE(new BigDecimal("0.014")),
    EURO(new BigDecimal("1.16")),
    YEN(new BigDecimal("0.0088"));

    private final BigDecimal courseInDollars;

    CurrencyType(BigDecimal courseInDollars) {
        this.courseInDollars = courseInDollars;
    }

    public BigDecimal getCourseInDollars() {
        return courseInDollars;
    }
}
