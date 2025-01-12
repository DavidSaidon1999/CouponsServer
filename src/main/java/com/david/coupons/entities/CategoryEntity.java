package com.david.coupons.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name",nullable = false,unique = true,length = 45)
    private String name;

    @OneToMany(mappedBy = "category" ,cascade = CascadeType.REMOVE ,fetch = FetchType.LAZY)
    private List<CouponEntity> couponEntityList;

    public CategoryEntity() {
    }

    public CategoryEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryEntity(String name) {
        this.name = name;
    }

    public List<CouponEntity> getCouponEntityList() {
        return couponEntityList;
    }

    public void setCouponEntityList(List<CouponEntity> couponEntityList) {
        this.couponEntityList = couponEntityList;
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
}
