package online.store.controller;

import online.store.entity.Order;
import online.store.exception.CreditCardValidationException;
import online.store.request.CheckoutRequest;
import online.store.service.CreditCardValidationService;
import online.store.service.OrdersService;
import online.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class CheckoutController {

    private final OrdersService ordersService;
    private final ProductService productService;
    private final CreditCardValidationService creditCardValidationService;

    @Autowired
    public CheckoutController(OrdersService ordersService, ProductService productService,
                              CreditCardValidationService creditCardValidationService) {
        this.creditCardValidationService = creditCardValidationService;
        this.ordersService = ordersService;
        this.productService = productService;
    }

    private static boolean isNullOrBlank(String input) {
        return input == null || input.isEmpty() || input.trim().length() == 0;
    }

    @PostMapping("checkout")
    public ResponseEntity<String> checkout(@RequestBody CheckoutRequest checkoutRequest) {

        Set<Order> orders = new HashSet<>(checkoutRequest.getProducts().size());

        if (isNullOrBlank(checkoutRequest.getCreditCard())) {
            return new ResponseEntity<>("Credit card information is missing", HttpStatus.PAYMENT_REQUIRED);
        }
        if (isNullOrBlank(checkoutRequest.getFirstName())) {
            return new ResponseEntity<>("First name is missing", HttpStatus.BAD_REQUEST);
        }
        if (isNullOrBlank(checkoutRequest.getLastName())) {
            return new ResponseEntity<>("Last name is missing", HttpStatus.BAD_REQUEST);
        }

        this.creditCardValidationService.validate(checkoutRequest.getCreditCard());

        for (CheckoutRequest.ProductInfo productInfo: checkoutRequest.getProducts()) {
            Order order = new Order(checkoutRequest.getFirstName(),
                    checkoutRequest.getLastName(),
                    checkoutRequest.getEmail(),
                    checkoutRequest.getShippingAddress(),
                    productInfo.getQuantity(),
                    checkoutRequest.getCreditCard(),
                    this.productService.getProductById(productInfo.getProductId())
            );
            orders.add(order);
        }

        this.ordersService.placeOrders(orders);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @ExceptionHandler({CreditCardValidationException.class})
    public ResponseEntity<String> handleCreditCardError(Exception ex) {
        System.out.println("Request to /checkout path threw an exception " + ex.getMessage());
        return new ResponseEntity<>("Credit card is invalid, please use another form of payment. Reason: "
                + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
