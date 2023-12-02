package online.store.service;

import online.store.entity.Product;
import online.store.entity.ProductCategory;
import online.store.repository.ProductCategoryRepository;
import online.store.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest {

    private static final Product PRODUCT1 = new Product("Color Makers",
            "High quality color maker",
            null,
            "art",
            14.99f);
    private static final Product PRODUCT2 = new Product("Desktop Monitor",
            null,
            null,
            "electronics",
            10.0f);

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductCategoryRepository productCategoryRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    public void getAllSupportedCategories_returnsExpectedCategories() {
        String category1 = "electronics";
        String category2 = "art";
        List<ProductCategory> categories = Arrays.asList(new ProductCategory(category1),
                new ProductCategory(category2));
        when(this.productCategoryRepository.findAll()).thenReturn(categories);

        List<String> supportedCategories = this.productService.getAllSupportedCategories();
        assertThat(supportedCategories).containsExactly(category1, category2);
    }

    @Test
    public void getDealsOfTheDay_withLimit_returnsExpectedProducts() {
        when(this.productRepository.findAtMostNumberOfProducts(eq(2)))
                .thenReturn(Arrays.asList(PRODUCT1,PRODUCT2));

        List<Product> dealsOfTheDay = this.productService.getDealOfTheDay(2);

        assertThat(dealsOfTheDay).containsExactly(PRODUCT1, PRODUCT2);
    }

    @Test
    public void getProductByCategory_withExistingCategoryProducts_returnsExpectedProducts() {
        when(this.productRepository.findAll()).thenReturn(Arrays.asList(PRODUCT1, PRODUCT2));

        List<Product> products = this.productService.getAllProducts();

        assertThat(products).containsExactly(PRODUCT1, PRODUCT2);
    }

    @Test
    public void getAllProducts_returnsAllProducts() {
        when(this.productRepository.findAll()).thenReturn(Arrays.asList(PRODUCT1, PRODUCT2));

        List<Product> products = this.productService.getAllProducts();

        assertThat(products).containsExactly(PRODUCT1, PRODUCT2);
    }

    @Test
    public void getProductsById_success_returnList() {
        long id = 123;
        when(this.productRepository.findById(id)).thenReturn(Optional.of(PRODUCT1));

        Product product = this.productService.getProductById(id);

        assertThat(product).isEqualTo(PRODUCT1);
    }

    @Test
    public void getProductById_nonExistingProduct_throwException() {
        long id = 123;
        when(this.productRepository.findById(id)).thenReturn(Optional.empty());

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                this.productService.getProductById(id));

        assertThat(exception).hasMessage("Product with id 123 doesn't exist");
    }

}
