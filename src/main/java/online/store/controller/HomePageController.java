package online.store.controller;

import online.store.entity.wrappers.ProductsWrapper;
import online.store.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class HomePageController {

    private final ProductService productService;

    public HomePageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/categories")
    public String getProductCategories() {
        return this.productService.getAllSupportedCategories()
                .stream()
                .collect(Collectors.joining(","));
    }

    @GetMapping(value = "/deals_of_the_day/{number_of_products}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductsWrapper getDealsOfTheDay(@PathVariable(name = "number_of_products") int numberOfProducts) {
        return new ProductsWrapper(this.productService.getDealOfTheDay(numberOfProducts));
    }

    @GetMapping("/products")
    public ProductsWrapper getProductsForCategory(@RequestParam(name = "category", required = false)
                                                      String category) {
        if (category != null && !category.isEmpty()) {
            return new ProductsWrapper(this.productService.getProductsByCategory(category));
        }
        return new ProductsWrapper(this.productService.getAllProducts());
    }
}
