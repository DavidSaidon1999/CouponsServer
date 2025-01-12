package com.david.coupons.dto;

import java.sql.Timestamp;
import java.time.Instant;

public class Purchase {

    private int id;
    private int customerId;
    private int couponId;
    private int amount;
    private Timestamp timestamp;

    public Purchase() {

    }
    public Purchase(int id, int customerId, int couponId, Integer amount) {
        this(customerId,couponId, amount);
        this.id = id;
    }

    public Purchase(int customerId,int couponId, Integer amount) {
        this.couponId = couponId;
        this.amount = amount;
        this.customerId=customerId;
    }

    public Purchase(int couponId, Integer amount) {
        this.amount = amount;
        this.customerId=customerId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getCouponId() {
        return couponId;
    }

    public int getAmount() {
        return amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", couponId=" + couponId +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }
}
