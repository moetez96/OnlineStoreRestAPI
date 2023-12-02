package online.store.repository;

import online.store.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryIT {

    private static final String ELECTRONICS_CATEGORY = "electronics";
    private static final Product ELECTRONICS_PRODUCT1 = new Product(
            "APPLE Laptop",
            null,
            null,
            ELECTRONICS_CATEGORY,
            5.5f
    );
    private static final Product ELECTRONICS_PRODUCT2 = new Product(
            "Desktop Monitor",
            null,
            null,
            ELECTRONICS_CATEGORY,
            10.0f
    );
    private static final Product ART_PRODUCT = new Product(
            "Color Makers",
            "High quality color maker",
            "makers_640x426.jpeg",
            "art",
            14.99f
    );

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    public void findByCategory_withMultipleProducts_returnsMatchingProducts() {

        entityManager.persist(ELECTRONICS_PRODUCT1);
        entityManager.persist(ELECTRONICS_PRODUCT2);
        entityManager.persist(ART_PRODUCT);

        List<Product> products = this.productRepository.findByCategory(ELECTRONICS_CATEGORY);

        assertThat(products).containsExactlyInAnyOrder(ELECTRONICS_PRODUCT2, ELECTRONICS_PRODUCT1);
    }

}
