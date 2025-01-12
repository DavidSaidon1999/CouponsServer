package com.david.coupons.dto;
import java.util.Date;

public class Coupon {
    private int  id;
    private String title;
    private String description;
    private float price;
    private int companyId;
    private int categoryId;
    private Date startDate;
    private Date endDate;
    private int amount;
    private String imageURL;

    public Coupon() {

    }

    public Coupon(int id, String title, String description, float price, int companyId
            , int categoryId, Date startDate, Date endDate, int amount, String imageURL) {

        this(title, description, price, companyId, categoryId, startDate, endDate, amount, imageURL);
        this.id = id;
    }

    public Coupon(String title, String description, float price, int companyId
            , int categoryId, Date startDate, Date endDate, int amount, String imageURL) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.companyId = companyId;
        this.categoryId = categoryId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.imageURL = imageURL;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int getCompanyId() {
        return companyId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getAmount() {
        return amount;
    }

    public String getImageURL() {
        return imageURL;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", companyId=" + companyId +
                ", categoryId=" + categoryId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
