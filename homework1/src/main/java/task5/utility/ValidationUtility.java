package task5.utility;

import task5.exception.ParameterIsNullException;
import task5.model.Currency;

import java.util.Set;

import static java.util.Objects.isNull;

public final class ValidationUtility {

    public static void validateInputString(String string) {
        validateInputObject(string);
        if (string.isEmpty()) {
            throw new ParameterIsNullException("String is null or empty");
        }
    }

    public static void validateInputSet(Set<Currency> set) {
        validateInputObject(set);
        if (set.isEmpty()) {
            throw new ParameterIsNullException("Set is null or empty");
        }
    }

    public static void validateInputInteger(Integer value) {
        validateInputObject(value);
        if (value < 0) {
            throw new ParameterIsNullException("Integer is null or less that zero");
        }
    }

    public static void validateInputObject(Object object) {
        if (isNull(object)) {
            throw new ParameterIsNullException("Object is null");
        }
    }

    public static boolean isObjectExist(Object object) {
        if (isNull(object)) {
            return false;
        }
        return true;
    }
}
