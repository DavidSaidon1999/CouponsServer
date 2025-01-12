package com.david.coupons.dto;

import java.util.Date;

public class CouponsDetails {

    private Integer id;
    private String title;
    private String description;
    private float price;
    private String companyName;
    private Date startDate;
    private Date endDate;
    private int amount;
    private String imageURL;

    public CouponsDetails() {
    }

    public CouponsDetails(Integer id, String title, String description, float price,
                          String companyName, Date startDate, Date endDate,
                          int amount, String imageURL) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.imageURL = imageURL;
    }

    public CouponsDetails(String title, String description, float price, String companyName,
                          Date startDate, Date endDate, int amount, String imageURL) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.imageURL = imageURL;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "CouponsDetails{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", companyName='" + companyName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
