package com.david.coupons.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
public class CustomerEntity {

    @Id
    private int id;
    @Column(name = "name",nullable = false,length = 45)
    private String name;
    @Column(name = "address",length = 45)
    private String address;
    @Column(name = "amount_of_kids")
    private Integer amountOfKids;
    @Column(name = "phone",length = 15,nullable = false,unique = true)
    private String phone;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "id")
    private UserEntity user;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private List<PurchasesEntity> purchasesEntityList;

    public CustomerEntity() {
    }

    public CustomerEntity(int id, String name, String address, Integer amountOfKids, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.amountOfKids = amountOfKids;
        this.phone = phone;
        this.user=new UserEntity();
        this.user.setId(id);
    }

    public CustomerEntity(String name, String address, Integer amountOfKids, String phone) {
        this.name = name;
        this.address = address;
        this.amountOfKids = amountOfKids;
        this.phone = phone;
        this.user=new UserEntity();
        this.user.setId(id);
    }

    public List<PurchasesEntity> getPurchasesEntityList() {
        return purchasesEntityList;
    }

    public void setPurchasesEntityList(List<PurchasesEntity> purchasesEntityList) {
        this.purchasesEntityList = purchasesEntityList;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAmountOfKids() {
        return amountOfKids;
    }

    public void setAmountOfKids(Integer amountOfKids) {
        this.amountOfKids = amountOfKids;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
