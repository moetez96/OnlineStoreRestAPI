package online.store.controller;

import online.store.entity.wrappers.ProductsWrapper;
import online.store.service.ProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}
