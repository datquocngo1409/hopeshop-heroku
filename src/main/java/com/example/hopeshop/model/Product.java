package com.example.hopeshop.model;

import javax.persistence.*;

@Entity
@Table(name = "tbl_Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private long price;
    private String image;
    private String des;
    private String miniDetail;
    private String superDetail;
    @ManyToOne
    private Category category;//cate_id
    @ManyToOne
    private Brand brand;


    public Product() {
        super();
    }

    public Product(int id, String name, long price, String image, String des, String miniDetail, String superDetail, Category category, Brand brand) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.des = des;
        this.category = category;
        this.brand = brand;
        this.miniDetail = miniDetail;
        this.superDetail = superDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getMiniDetail() {
        return miniDetail;
    }

    public void setMiniDetail(String miniDetail) {
        this.miniDetail = miniDetail;
    }

    public String getSuperDetail() {
        return superDetail;
    }

    public void setSuperDetail(String superDetail) {
        this.superDetail = superDetail;
    }
}
