package com.dudka.store.entity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Product(String nameOfProduct, Double price, Boolean isAvailable) {
        this.nameOfProduct = nameOfProduct;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    @Column(name = "name_of_product")
    private String nameOfProduct;


    @Column(name = "price")
    private Double price;

    @Column(name = "is_available")
    private Boolean isAvailable;

    public void setId(Long id) {
        this.id = id;
    }

    public void setNameOfItem(String nameOfItem) {
        this.nameOfProduct = nameOfItem;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setNameOfProduct(String nameOfProduct) {
        this.nameOfProduct = nameOfProduct;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getId().equals(product.getId());
    }

    @Override
    public int hashCode() {
        return (int) (37 * this.getId());
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", nameOfProduct='" + nameOfProduct + '\'' +
                ", price=" + price +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
