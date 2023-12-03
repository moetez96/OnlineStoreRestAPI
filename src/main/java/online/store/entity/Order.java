package online.store.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;
    private String lastName;
    private String email;
    private String shippingAddress;
    private long quantity;
    private String creditCard;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    public Order() {

    }

    public Order(String firstName, String lastName, String email, String shippingAddress, long quantity, String creditCard, Product product) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.shippingAddress = shippingAddress;
        this.quantity = quantity;
        this.creditCard = creditCard;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return this.quantity == order.quantity && Objects.equals(this.id, order.id) &&
                Objects.equals(this.firstName, order.firstName) && Objects.equals(this.lastName, order.lastName) &&
                Objects.equals(this.email, order.email) && Objects.equals(this.shippingAddress, order.shippingAddress) &&
                Objects.equals(this.creditCard, order.creditCard) && Objects.equals(this.product, order.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName, this.email, this.shippingAddress,
                this.quantity, this.creditCard, this.product);
    }
}
