package com.david.coupons.dto;

public class Company {

    private int id;
    private String name;
    private String address;
    private String phone;

    public Company() {

    }
    public Company(int id, String name, String address, String phone) {
        this(name, address, phone);
        this.id = id;
    }
    public Company(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
