package com.example.hopeshop.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tbl_Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User buyer;
    private Date buyDate;
    public Cart() {
        super();
    }
    public Cart(int id, User buyer, Date buyDate) {
        super();
        this.id = id;
        this.buyer = buyer;
        this.buyDate = buyDate;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public User getBuyer() {
        return buyer;
    }
    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
    public Date getBuyDate() {
        return buyDate;
    }
    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }
}
