package com.david.coupons.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "coupons")
public class CouponEntity {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "title", unique = true, nullable = false, length = 45)
    private String title;
    @Column(name = "description", nullable = false, length = 200)
    private String description;
    @Column(name = "price", nullable = false)
    private float price;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date", nullable = false)
    private Date endDate;
    @Column(name = "amount", nullable = false)
    private int amount;
    @Column(name = "image_url", length = 100)
    private String imageURL;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<PurchasesEntity> purchasesEntityList;

    public CouponEntity() {
    }

    public CouponEntity(int id, String title,
                        String description,
                        float price, Integer companyId,
                        Integer categoryId,
                        Date startDate,
                        Date endDate,
                        int amount, String imageURL) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.company = new CompanyEntity();
        this.company.setId(companyId);
        this.category = new CategoryEntity();
        this.category.setId(categoryId);
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.imageURL = imageURL;
    }

    public CouponEntity(String title, String description, float price, Integer companyId, Integer categoryId, Date startDate, Date endDate, int amount, String imageURL) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.company = new CompanyEntity();
        this.company.setId(companyId);
        this.category = new CategoryEntity();
        this.category.setId(categoryId);
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.imageURL = imageURL;
    }

    public List<PurchasesEntity> getPurchasesEntityList() {
        return purchasesEntityList;
    }

    public void setPurchasesEntityList(List<PurchasesEntity> purchasesEntityList) {
        this.purchasesEntityList = purchasesEntityList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
}
