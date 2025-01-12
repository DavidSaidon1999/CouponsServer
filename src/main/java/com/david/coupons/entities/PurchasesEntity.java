package com.david.coupons.entities;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "purchases")
public class PurchasesEntity {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_id")
    private CouponEntity coupon;

    public PurchasesEntity() {
    }

    public PurchasesEntity(int id,Integer customerId, Integer couponId, int amount, Timestamp timestamp) {
        this.id = id;
        this.customer = new CustomerEntity();
        this.customer.setId(customerId);

        this.coupon = new CouponEntity();
        this.coupon.setId(couponId);

        this.amount = amount;
        this.timestamp = timestamp;
    }

    public PurchasesEntity(Integer customerId, Integer couponId, int amount, Timestamp timestamp) {
        this.customer = new CustomerEntity();
        this.customer.setId(customerId);

        this.coupon = new CouponEntity();
        this.coupon.setId(couponId);
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public CouponEntity getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponEntity coupon) {
        this.coupon = coupon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
