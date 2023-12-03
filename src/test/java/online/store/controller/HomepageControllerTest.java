package online.store.controller;

import online.store.entity.Product;
import online.store.entity.wrappers.ProductsWrapper;
import online.store.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class HomepageControllerTest {

    private static final Product PRODUCT1 = new Product(
            "Color Makers",
            "High quality color maker",
            null,
            "art",
            14.99f);
    private static final Product PRODUCT2 = new Product(
            "Desktop Monitor",
            null,
            null,
            "electronics",
            10.0f
    );

    @Mock
    private ProductService productService;
    @InjectMocks
    private HomePageController homePageController;

    @Test
    public void getProductCategories_returnsString() {
        when(this.productService.getAllSupportedCategories())
                .thenReturn(Arrays.asList("category1", "category2"));

        String response = this.homePageController.getProductCategories();

        assertThat(response).isEqualTo("category1,category2");
    }

    @Test
    public void getDealsOfTheDay_success_returnsListOfOneProduct() {
        when(this.productService.getDealOfTheDay(any(Integer.class)))
                .thenReturn(Arrays.asList(PRODUCT1));

        ProductsWrapper productsWrapper =  this.homePageController.getDealsOfTheDay(1);

        assertThat(productsWrapper.getProducts()).containsExactly(PRODUCT1);
    }

    @Test
    public void getProductsForCategory_nullCategory_returnsList() {
        when(this.productService.getAllProducts()).thenReturn(Arrays.asList(PRODUCT1, PRODUCT2));

        ProductsWrapper productsWrapper = this.homePageController.getProductsForCategory(null);

        assertThat(productsWrapper.getProducts()).containsExactlyInAnyOrder(PRODUCT1, PRODUCT2);
    }

    @Test
    public void getProductsForCategory_withCategory_returnsList() {
        String category = "electronics";
        when(this.productService.getProductsByCategory(eq(category)))
                .thenReturn(Arrays.asList(PRODUCT2));

        ProductsWrapper productsWrapper = this.homePageController.getProductsForCategory("electronics");

        assertThat(productsWrapper.getProducts()).containsExactly(PRODUCT2);
    }
}
