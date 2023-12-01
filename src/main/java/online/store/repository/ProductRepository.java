package online.store.repository;

import online.store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    default List<Product> findAtMostNumberOfProducts(int numberOfProducts) {

        Page<Product> page = findAll(PageRequest.of(0, numberOfProducts));
        return page.toList();
    }
}
