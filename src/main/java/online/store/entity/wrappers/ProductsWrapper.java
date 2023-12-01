package online.store.entity.wrappers;

import online.store.entity.Product;

import java.util.Collections;
import java.util.List;

public class ProductsWrapper {

    private List<Product> products = Collections.EMPTY_LIST;

    public ProductsWrapper(List<Product> products) {
        this.products = Collections.unmodifiableList(products);
    }

    public List<Product> getProducts() {
        return this.products;
    }
}
