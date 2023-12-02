package online.store.service;

import online.store.entity.Order;
import online.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OrdersService {

    private final OrderRepository orderRepository;
    private final long maxNumberOfItems;

    @Autowired
    public OrdersService(OrderRepository orderRepository,
                         @Value("${products.service.max-number-of-items:25}")
                         long maxNumberOfItems) {
        this.orderRepository = orderRepository;
        this.maxNumberOfItems = maxNumberOfItems;
    }

    public void placeOrders(Iterable<Order> orders) {
        validateNumberOfItemsOrdered(orders);
        this.orderRepository.saveAll(orders);
    }

    public void validateNumberOfItemsOrdered(Iterable<Order> orders) {
        long totalNumberOfItems = 0;
        for (Order order: orders) {
            totalNumberOfItems += order.getQuantity();
        }

        if (totalNumberOfItems > this.maxNumberOfItems) {
            throw new IllegalStateException(String.format("Number of products " +
                    totalNumberOfItems + " exceeded the limit of " + this.maxNumberOfItems));
        }
    }
}
