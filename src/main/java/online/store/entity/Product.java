package online.store.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String imageFileName;
    private float price;
    private String category;

    public Product() {

    }

    public Product(String name, String description, String imageFileName, String category, float price) {

        this.name = name;
        this.description = description;
        this.imageFileName = imageFileName;
        this.category = category;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return Float.compare(product.price, this.price) == 0 && Objects.equals(this.id, product.id) &&
                Objects.equals(this.name, product.name) && Objects.equals(this.description, product.description) &&
                Objects.equals(this.imageFileName, product.imageFileName) &&
                Objects.equals(this.category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.description, this.imageFileName, this.price, this.category);
    }
}
