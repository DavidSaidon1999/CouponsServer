package com.david.coupons.dto;

public class Customer {

    private int id;
    private String name;
    private String address;
    private Integer amountOfKids;
    private String phone;
    private User user;

    public Customer() {

    }

    public Customer(int id, String name, String address, int amountOfKids, String phone, User user) {
        this.name = name;
        this.address = address;
        this.amountOfKids = amountOfKids;
        this.phone = phone;
        this.user = user;
    }

    public Customer(int id, String name, String address, int amountOfKids, String phone) {
        this.name = name;
        this.address = address;
        this.amountOfKids = amountOfKids;
        this.phone = phone;
    }

    public Customer(String name, String address, int amountOfKids, String phone, User user) {
        this.name = name;
        this.address = address;
        this.amountOfKids = amountOfKids;
        this.phone = phone;
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAmountOfKids(Integer amountOfKids) {
        this.amountOfKids = amountOfKids;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Integer getAmountOfKids() {
        return amountOfKids;
    }

    public String getPhone() {
        return phone;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", amountOfKids=" + amountOfKids +
                ", phone='" + phone + '\'' +
                '}';
    }

}
