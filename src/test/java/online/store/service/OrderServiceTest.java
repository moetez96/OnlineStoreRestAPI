package online.store.service;

import online.store.entity.Order;
import online.store.entity.Product;
import online.store.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    private static final Product PRODUCT = new Product("Apple Laptop",
            null, null, "electronics", 5.5f);
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Smith";
    private static final String EMAIL = "abc@gmail.com";
    private static final String ADDRESS = "City Location 8100";
    private static final String CREDIT_CARD  = "1234123412341234";
    private static final int MAX_NUMBER_OF_ITEMS = 5;

    @Mock
    private OrderRepository orderRepository;
    private OrdersService ordersService;

    @BeforeEach
    public void setupTest() {
        this.ordersService = new OrdersService(this.orderRepository, MAX_NUMBER_OF_ITEMS);
    }

    @Test
    public void placeOrders_success_returnsNothing() {
        Order order = new Order(FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, 2, CREDIT_CARD,PRODUCT);
        List<Order> orders = List.of(order);

        this.ordersService.placeOrders(orders);
        verify(this.orderRepository).saveAll(orders);
    }

    @Test
    public void placeOrders_numberOfItemsExceeded_throwException() {
        Order order = new Order(FIRST_NAME, LAST_NAME, EMAIL, ADDRESS, 20, CREDIT_CARD,PRODUCT);

        Assertions.assertThrows(IllegalStateException.class, () -> {
            this.ordersService.placeOrders(List.of(order));
        });
    }
}
