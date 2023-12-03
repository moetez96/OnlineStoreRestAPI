package online.store.controller;

import online.store.entity.Order;
import online.store.entity.Product;
import online.store.exception.CreditCardValidationException;
import online.store.service.CreditCardValidationService;
import online.store.service.OrdersService;
import online.store.service.ProductService;
import org.assertj.core.util.Sets;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CheckoutControllerIT {
    private static final String VALID_CHECKOUT_REQUEST = "{\n" +
            "  \"firstName\": \"John\",\n" +
            "  \"lastName\": \"Smith\",\n" +
            "  \"email\": \"abc@gmail.com\",\n" +
            "  \"shippingAddress\": \"City Location 8100\",\n" +
            "  \"products\": [{\"productId\":123, \"quantity\" : 5}],\n" +
            "  \"creditCard\" : \"1234123412341234\"\n" +
            "}";
    private static final String CHECKOUT_REQUEST_WITH_STOLEN_CREDIT_CARD = "{\n" +
            "  \"firstName\": \"John\",\n" +
            "  \"lastName\": \"Smith\",\n" +
            "  \"email\": \"abc@gmail.com\",\n" +
            "  \"shippingAddress\": \"City Location 8100\",\n" +
            "  \"products\": [{\"productId\":123, \"quantity\" : 5}],\n" +
            "  \"creditCard\" : \"1111111111111111\"\n" +
            "}";
    private static final Product PRODUCT = new Product("Audio Speakers",
            "Stereo speakers for listening to music at home",
            "speakers.jpeg","electronics", 89f
            );

    @MockBean
    private OrdersService ordersService;
    @MockBean
    private ProductService productsService;
    @MockBean
    private CreditCardValidationService creditCardValidationService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void checkout_withCorrectRequest_success() throws Exception {
        when(this.productsService.getProductById(123)).thenReturn(PRODUCT);
        Set<Order> expectedOrders =
                Sets.set(
                        new Order("John", "Smith", "abc@gmail.com",
                                "City Location 8100", 5,
                                "1234123412341234",PRODUCT));

        this.mockMvc.perform(post("/checkout")
                .content(VALID_CHECKOUT_REQUEST)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(content().string("success"))
                .andExpect(status().isOk());

        Mockito.verify(this.ordersService).placeOrders(expectedOrders);
    }

    @Test
    public void checkout_invalidCreditCard_returnsBadRequestStatus() throws Exception {
        doThrow(new CreditCardValidationException("Invalid Credit Card"))
                .when(creditCardValidationService)
                .validate(eq("1111111111111111"));


        this.mockMvc.perform(post("/checkout").content(CHECKOUT_REQUEST_WITH_STOLEN_CREDIT_CARD)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(content().string("Credit card is invalid, please use another form of payment. " +
                        "Reason: Invalid Credit Card"))
                .andExpect(status().isBadRequest());
    }

}
