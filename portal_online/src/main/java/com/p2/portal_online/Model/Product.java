package com.p2.portal_online.Model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Product;

    @NonNull
    private String name;
    @NonNull
    private String image;
    private int price;

    public Product(){}

    public Product(@NonNull String name, @NonNull String image, int price) {
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public Long getId_Product() {
        return id_Product;
    }

    public void setId_Product(Long id_Product) {
        this.id_Product = id_Product;
    }

    @NonNull
    public String getName() {
        return name;
    }
    @NonNull
    public void setName(String name) {
        this.name = name;
    }
    @NonNull
    public String getImage() {
        return image;
    }
    @NonNull
    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
