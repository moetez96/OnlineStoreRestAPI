package online.store.controller;

import online.store.entity.Product;
import online.store.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(HomePageController.class)
public class HomeControllerTestIT {

    private static final Product ELECTRONIC_PRODUCT1 = new Product("Apple Laptop",
            null, null, "electronics", 5.5f );
    private static final Product ELECTRONIC_PRODUCT2 = new Product("Desktop Monitor",
            null, null, "electronics", 10.0f);
    private static final Product ART_PRODUCT = new Product("Color Makers",
            "High quality color maker", "makers_640x426.jpeg",
            "art", 14.99f);

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void getCategories_returnsListOfCategories() throws Exception {
        List<String> expectedCategories = Arrays.asList("apparel", "electronics");
        when(this.productService.getAllSupportedCategories()).thenReturn(expectedCategories);

        this.mockMvc.perform(get("/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("apparel,electronics"));
    }

    @Test
    public void getDealsOfTheDay_withPathParameter_returnsJsonResponse() throws Exception {
        List<Product> products = Arrays.asList(ELECTRONIC_PRODUCT1, ELECTRONIC_PRODUCT2);
        when(this.productService.getDealOfTheDay(2)).thenReturn(products);

        this.mockMvc.perform(get("/deals_of_the_day/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.products").isNotEmpty())
                .andExpect(jsonPath("$.products[0].name").value("Apple Laptop"))
                .andExpect(jsonPath("$.products[0].price").value(equalTo(5.5f), Float.class))
                .andExpect(jsonPath("$.products[1].name").value("Desktop Monitor"))
                .andExpect(jsonPath("$.products[1].price").value(equalTo(10.0f), Float.class));
    }

    @Test
    public void getProducts_withoutCategories_returnAllProducts() throws Exception {
        List<Product> products = Arrays.asList(ELECTRONIC_PRODUCT1, ELECTRONIC_PRODUCT2, ART_PRODUCT);
        when(this.productService.getAllProducts()).thenReturn(products);

        this.mockMvc.perform(get("/products")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.products.length()").value(3));
    }
}
