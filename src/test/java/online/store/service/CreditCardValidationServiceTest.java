package online.store.service;

import online.store.exception.CreditCardValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CreditCardValidationServiceTest {

    private final CreditCardValidationService creditCardValidationService =
            new CreditCardValidationService();

    @Test
    public void validate_validCard_noExceptionThrown() {
        this.creditCardValidationService.validate("1234123412341234");
    }

    @Test
    public void validate_invalidNumberOfDigits_thrownException() {
        assertThrows(CreditCardValidationException.class,
                () -> this.creditCardValidationService.validate("1234"));
    }

    @Test
    public void valid_withInvalidCardFormat_throwsException() {
        assertThrows(CreditCardValidationException.class,
                () -> this.creditCardValidationService.validate("abcdabcdabcdabcd"));
    }

    @Test
    public void validate_stolenCreditCard_throwsException() {
        assertThrows(CreditCardValidationException.class,
                () -> this.creditCardValidationService.validate("1111111111111111"));
    }

}
