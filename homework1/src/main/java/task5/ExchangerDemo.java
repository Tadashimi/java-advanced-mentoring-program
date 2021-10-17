package task5;

import task5.model.Currency;
import task5.model.CurrencyType;
import task5.model.User;
import task5.service.UserService;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ExchangerDemo {
    private static final int THREAD_COUNT = 5;
    private static final Logger LOGGER = Logger.getLogger(ExchangerDemo.class.getSimpleName());

    public static void main(String[] args) {
        Set<Currency> user1InitialMoney = new HashSet<>(Arrays.asList(
                new Currency(CurrencyType.DOLLAR, new BigDecimal(100)),
                new Currency(CurrencyType.RUBLE, new BigDecimal(3000))));
        Set<Currency> user2InitialMoney = new HashSet<>(Arrays.asList(
                new Currency(CurrencyType.YEN, new BigDecimal(1_000_000)),
                new Currency(CurrencyType.RUBLE, new BigDecimal(50_000)),
                new Currency(CurrencyType.DOLLAR, new BigDecimal(100_000))));

        UserService userService = new UserService();
        User user1 = userService.createUser("User1", user1InitialMoney);
        User user2 = userService.createUser("User2", user2InitialMoney);

        ExecutorService currencyExchanger = Executors.newFixedThreadPool(THREAD_COUNT);
        for (int i = 0; i < 100; i++) {
            currencyExchanger.submit(() -> userService.convertCurrency(user2.getId(), CurrencyType.YEN, CurrencyType.RUBLE, new BigDecimal(100)));
            currencyExchanger.submit(() -> userService.convertCurrency(user2.getId(), CurrencyType.DOLLAR, CurrencyType.YEN, new BigDecimal(100)));
        }
        currencyExchanger.shutdown();
    }
}
