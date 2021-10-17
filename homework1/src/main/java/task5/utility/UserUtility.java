package task5.utility;

import org.apache.commons.io.FileUtils;
import task5.exception.ErrorDuringFileProcessingException;
import task5.model.Currency;
import task5.model.CurrencyType;
import task5.model.User;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class UserUtility {
    public static void writeUserToFile(User user) {
        File file = new File(user.getFio());
        try {
            FileUtils.writeStringToFile(file, user.toStringForFile(), Charset.defaultCharset());
        } catch (Exception e) {
            throw new ErrorDuringFileProcessingException("Error during writing file " + file.getAbsolutePath());
        }
    }

    public static User readUserFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        List<String> userInfo = FileUtils.readLines(file, Charset.defaultCharset());
        if (userInfo.isEmpty()) {
            throw new ErrorDuringFileProcessingException("Error during reading file " + fileName);
        }
        Iterator<String> iterator = userInfo.iterator();
        String id = iterator.next();
        String fio = iterator.next();
        Set<Currency> userMoney = new HashSet<>();
        while (iterator.hasNext()) {
            String currencyString = iterator.next();
            String[] strings = currencyString.split(" = ");
            userMoney.add(new Currency(CurrencyType.valueOf(strings[0]), new BigDecimal(strings[1])));
        }
        return new User(Integer.valueOf(id), fio, userMoney);
    }
}
