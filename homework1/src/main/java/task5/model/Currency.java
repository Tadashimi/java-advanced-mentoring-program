package task5.model;

import java.math.BigDecimal;

public class Currency {
    private CurrencyType type;
    private BigDecimal amount;

    public Currency(CurrencyType type, BigDecimal amount) {
        this.type = type;
        this.amount = amount;
    }

    public CurrencyType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        synchronized (this.amount) {
            this.amount = amount;
        }
    }

    @Override
    public String toString() {
        return "Currency{" +
                "type=" + type +
                ", amount=" + amount +
                '}';
    }

    public String toStringForFile() {
        return type + " = " + amount + '\n';
    }
}
