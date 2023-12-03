package online.store.controller;

import online.store.request.CheckoutRequest;
import online.store.service.CreditCardValidationService;
import online.store.service.OrdersService;
import online.store.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CheckoutControllerTest {

    private static final List<CheckoutRequest.ProductInfo> PRODUCT_INFOS =

            List.of(new CheckoutRequest.ProductInfo(1, 1));
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Smith";
    private static final String EMAIL = "abc@company.com";
    private static final String ADDRESS = "130 Green Olive Dr. San Francisco";
    private static final String CREDIT_CARD = "1234123412341234";
    @Mock
    private OrdersService ordersService;
    @Mock
    private ProductService productsService;
    @Mock
    private CreditCardValidationService creditCardValidationService;

    @InjectMocks
    private CheckoutController checkoutController;

    public CheckoutControllerTest() {
    }

    @Test
    public void checkout_withValidRequest_returnsSuccess() {
        CheckoutRequest checkoutRequest = new CheckoutRequest(FIRST_NAME, LAST_NAME,
                EMAIL, ADDRESS, PRODUCT_INFOS, CREDIT_CARD);

        ResponseEntity<String> responseEntity = this.checkoutController.checkout(checkoutRequest);

        assertThat(responseEntity.getBody()).isEqualTo("success");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void checkout_withLastNameNull_returnsError() {
        CheckoutRequest requestWithoutLastName = new CheckoutRequest(FIRST_NAME, null,
                EMAIL, ADDRESS, PRODUCT_INFOS, CREDIT_CARD);

        ResponseEntity<String> responseEntity = this.checkoutController.checkout(requestWithoutLastName);

        assertThat(responseEntity.getBody()).isEqualTo("Last name is missing");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void checkout_firstNameNull_returnsError() {
        CheckoutRequest requestWithoutFirstName = new CheckoutRequest(null, LAST_NAME,
                EMAIL, ADDRESS, PRODUCT_INFOS, CREDIT_CARD);

        ResponseEntity<String> responseEntity =
                this.checkoutController.checkout(requestWithoutFirstName);

        assertThat(responseEntity.getBody()).isEqualTo("First name is missing");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void checkout_creditCardNull_returnsError() {
        CheckoutRequest requestWithoutCreditCard = new CheckoutRequest(FIRST_NAME, LAST_NAME,
                EMAIL, ADDRESS, PRODUCT_INFOS, null);

        ResponseEntity<String> responseEntity =
                this.checkoutController.checkout(requestWithoutCreditCard);

        assertThat(responseEntity.getBody()).isEqualTo("Credit card information is missing");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.PAYMENT_REQUIRED);
    }

    @Test
    public void handleCreditCardError_creditCardInvalid_returnsError() {
        String reason = "card expired";

        ResponseEntity<String> responseEntity =
                this.checkoutController.handleCreditCardError(new Exception(
                        reason));

        assertThat(responseEntity.getBody()).isEqualTo("Credit card is invalid, please use " +
                "another form of payment. Reason: " + reason);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}