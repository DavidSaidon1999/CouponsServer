package com.david.coupons.dto;

import java.util.Date;

public class PurchasesDetails {
    private int id;
    private int couponId;
    private int amount;
    private float totalPrice;
    private Date timestamp;
    private String title;
    private String description;
    private Date endDate;
    private String imageURL;

    public PurchasesDetails(int id, int couponId, int amount,  float totalPrice , Date timestamp, String title, String description, String imageURL , Date endDate) {
        this(couponId , amount ,  totalPrice, timestamp,title , description ,  imageURL  , endDate);
        this.id = id;

    }

    public PurchasesDetails(int couponId, int amount, float totalPrice,Date timestamp, String title, String description, String imageURL, Date endDate) {
        this.couponId = couponId;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.timestamp = timestamp;
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.imageURL = imageURL;
    }

    public PurchasesDetails() {
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCouponId() {
        return couponId;
    }

    public int getAmount() {
        return amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return "PurchasesDetails{" +
                "id=" + id +
                ", couponId=" + couponId +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", totalPrice=" + totalPrice +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}