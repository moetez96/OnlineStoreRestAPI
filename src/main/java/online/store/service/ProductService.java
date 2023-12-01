package online.store.service;

import online.store.entity.Product;
import online.store.entity.ProductCategory;
import online.store.repository.ProductCategoryRepository;
import online.store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
    }

    public List<String> getAllSupportedCategories() {
        return this.productCategoryRepository.findAll().stream()
                .map(productCategory -> productCategory.getCategory())
                .collect(Collectors.toList());
    }

    public List<Product> getDealOfTheDay(int numberOfProducts) {
        return this.productRepository.findAtMostNumberOfProducts(numberOfProducts);
    }

    public List<Product> getProductsByCategory(String productCategories) {
        return this.productRepository.findByCategory(productCategories);
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }
}
