package task5.model;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class User {
    private int id;
    private String fio;
    private Set<Currency> userMoney = new CopyOnWriteArraySet<>();

    public User(int id, String fio) {
        this.id = id;
        this.fio = fio;
    }

    public User(int id, String fio, Set<Currency> userMoney) {
        this(id, fio);
        this.userMoney = userMoney;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Set<Currency> getUserMoney() {
        return new CopyOnWriteArraySet<>(userMoney);
    }

    public Currency getUserCurrency(CurrencyType type) {
        Optional<Currency> optionalCurrency = userMoney.stream()
                .filter(currency -> Objects.equals(type, currency.getType()))
                .findAny();
        return optionalCurrency.map(currency -> new Currency(currency.getType(), currency.getAmount())).orElse(null);
    }

    public void setUserMoney(Set<Currency> userMoney) {
        this.userMoney = userMoney;
    }

    public int getId() {
        return id;
    }

    public void addCurrency(Currency currency) {
        userMoney.add(currency);
    }

    public void removeCurrency(CurrencyType currencyType) {
        userMoney.removeIf(currency -> currencyType.equals(currency.getType()));
    }

    public void setCurrencyAmount(CurrencyType currencyType, BigDecimal amount) {
        Optional<Currency> currencyOptional = userMoney.stream()
                .filter(currency -> currencyType.equals(currency.getType()))
                .findAny();
        if (currencyOptional.isPresent()) {
            synchronized (userMoney) {
                currencyOptional.get().setAmount(amount);
            }
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "fio='" + fio + '\'' +
                ", userMoney=" + userMoney +
                '}';
    }

    public String toStringForFile() {
        final StringBuffer sb = new StringBuffer(id + '\n');
        sb.append(fio + '\n');
        userMoney.forEach(currency -> sb.append(currency.toStringForFile() + '\n'));
        return sb.toString();
    }
}
