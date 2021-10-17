package task5.service;

import task5.exception.DontHaveEnoughMoneyException;
import task5.exception.EntityNotFoundException;
import task5.model.Currency;
import task5.model.CurrencyType;
import task5.model.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

import static task5.utility.ValidationUtility.isObjectExist;
import static task5.utility.ValidationUtility.validateInputInteger;
import static task5.utility.ValidationUtility.validateInputObject;
import static task5.utility.ValidationUtility.validateInputSet;
import static task5.utility.ValidationUtility.validateInputString;

public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getSimpleName());

    private final ReentrantLock exchangeCurrencyLock = new ReentrantLock();

    private List<User> users = new ArrayList<>();
    private AtomicInteger idGenerator = new AtomicInteger(0);

    public User createUser(String fio) {
        validateInputString(fio);
        int id = idGenerator.incrementAndGet();
        User user = new User(id, fio);
        users.add(user);
        return user;
    }

    public User createUser(String fio, Set<Currency> userMoney) {
        validateInputString(fio);
        validateInputSet(userMoney);
        int id = idGenerator.incrementAndGet();
        User user = new User(id, fio, userMoney);
        users.add(user);
        LOGGER.info("User was created: " + user);
        return user;
    }

    public User getUser(Integer id) {
        validateInputInteger(id);
        Optional<User> optionalUser = getOptionalUser(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new EntityNotFoundException("User with id = " + id + " not found");
        }
    }

    public List<User> getAll() {
        return users;
    }

    public void updateUser(Integer id, User user) {
        validateInputInteger(id);
        validateInputObject(user);
        Optional<User> optionalUser = getOptionalUser(id);
        if (optionalUser.isPresent()) {
            User updatedUser = optionalUser.get();
            updatedUser.setFio(user.getFio());
            updatedUser.setUserMoney(user.getUserMoney());
            LOGGER.info("User was created: " + updatedUser);
        } else {
            throw new EntityNotFoundException("User with id = " + id + " not found");
        }
    }

    public void removeUser(Integer id) {
        validateInputInteger(id);
        users.removeIf(entity -> Objects.equals(id, entity.getId()));
    }

    public void addCurrency(Integer userId, CurrencyType type, BigDecimal amount) {
        validateInputInteger(userId);
        validateInputObject(type);
        validateInputObject(amount);
        Optional<User> optionalUser = getOptionalUser(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.addCurrency(new Currency(type, amount));
        } else {
            throw new EntityNotFoundException("User with id = " + userId + " not found");
        }
    }

    public void convertCurrency(Integer userId, CurrencyType fromType, CurrencyType toType, BigDecimal toAmount) {
        BigDecimal rate = toType.getCourseInDollars().divide(fromType.getCourseInDollars(), RoundingMode.UNNECESSARY);
        BigDecimal fromAmount = toAmount.multiply(rate);
        exchangeCurrencyLock.lock();
        minusCurrencyMoney(userId, fromType, fromAmount);
        plusCurrencyMoney(userId, toType, toAmount);
        exchangeCurrencyLock.unlock();
    }

    private void minusCurrencyMoney(Integer userId, CurrencyType fromType, BigDecimal amount) {
        validateInputInteger(userId);
        validateInputObject(fromType);
        validateInputObject(amount);
        Optional<User> optionalUser = getOptionalUser(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Currency fromUserCurrency = user.getUserCurrency(fromType);
            validateInputObject(fromUserCurrency);
            if (fromUserCurrency.getAmount().compareTo(amount) < 0) {
                throw new DontHaveEnoughMoneyException();
            } else {
                BigDecimal oldValue = fromUserCurrency.getAmount();
                BigDecimal newAmount = oldValue.min(amount);
                fromUserCurrency.setAmount(newAmount);
                LOGGER.info(fromType + " - minus operation was performed: old value = " + oldValue + " new value = " + newAmount);
            }
        } else {
            throw new EntityNotFoundException("User with id = " + userId + " not found");
        }
    }

    private void plusCurrencyMoney(Integer userId, CurrencyType toType, BigDecimal amount) {
        validateInputInteger(userId);
        validateInputObject(toType);
        validateInputObject(amount);
        Optional<User> optionalUser = getOptionalUser(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Currency fromUserCurrency = user.getUserCurrency(toType);
            if (isObjectExist(fromUserCurrency)) {
                BigDecimal oldValue = fromUserCurrency.getAmount();
                BigDecimal newAmount = oldValue.add(amount);
                fromUserCurrency.setAmount(newAmount);
                LOGGER.info(toType + " plus operation was performed: old value = " + oldValue + " new value = " + newAmount);
            } else {
                user.addCurrency(new Currency(toType, amount));
                LOGGER.info("Currency was added: new type " + toType + " new amount = " + amount);
            }
        } else {
            throw new EntityNotFoundException("User with id = " + userId + " not found");
        }
    }

    private Optional<User> getOptionalUser(Integer id) {
        validateInputInteger(id);
        return users.stream()
                .filter(entity -> Objects.equals(id, entity.getId()))
                .findAny();
    }
}
