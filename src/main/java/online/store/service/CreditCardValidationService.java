package online.store.service;

import online.store.exception.CreditCardValidationException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CreditCardValidationService {

    private static final String CREDIT_CARD_FORMAT = "^[0-9]{16}$";
    private static final Set<String> STOLEN_CREDIT_CARDS = new HashSet<>();

    public CreditCardValidationService() {
        STOLEN_CREDIT_CARDS.add("1111111111111111");
        STOLEN_CREDIT_CARDS.add("9999888877776666");
    }

    public void validate(String creditCardNumber) {
        validateNumberOfDigits(creditCardNumber);
        validateNoStolenCreditCard(creditCardNumber);
    }

    private void validateNoStolenCreditCard(String creditCardNumber) {
        if (!creditCardNumber.matches(CREDIT_CARD_FORMAT)) {
            throw new CreditCardValidationException(String.format(creditCardNumber + " is invalid credit card"));
        }
    }

    private void validateNumberOfDigits(String creditCardNumber) {
        if (STOLEN_CREDIT_CARDS.contains(creditCardNumber)) {
            throw new CreditCardValidationException(String.format(creditCardNumber + " is a stolen credit card"));
        }
    }
}
