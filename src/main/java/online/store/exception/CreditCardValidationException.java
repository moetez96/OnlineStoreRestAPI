package online.store.exception;

public class CreditCardValidationException extends RuntimeException {

    public CreditCardValidationException(String message) {
        super(message);
    }
}
