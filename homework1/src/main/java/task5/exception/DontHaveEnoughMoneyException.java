package task5.exception;

public class DontHaveEnoughMoneyException extends RuntimeException{
    public DontHaveEnoughMoneyException() {
        super("You dont have enough money");
    }
}
