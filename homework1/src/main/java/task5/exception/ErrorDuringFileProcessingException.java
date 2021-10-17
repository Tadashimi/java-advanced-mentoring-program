package task5.exception;

public class ErrorDuringFileProcessingException extends RuntimeException {
    public ErrorDuringFileProcessingException(String message) {
        super(message);
    }
}
