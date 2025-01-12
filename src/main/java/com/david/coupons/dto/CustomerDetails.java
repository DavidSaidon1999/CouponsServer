package com.david.coupons.dto;

public class CustomerDetails {

    private int id;
    private String name;
    private String userName;
    private String address;
    private String phoneNumber;
    private int amountOfKids;

    public CustomerDetails() {

    }

    public CustomerDetails(String name, String userName, String address, String phoneNumber, int amountOfKids) {
        this.name = name;
        this.userName = userName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.amountOfKids = amountOfKids;
    }

    public CustomerDetails(int id, String name, String userName, String address, String phoneNumber, int amountOfKids) {
        this.name = name;
        this.userName = userName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.amountOfKids = amountOfKids;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAmountOfKids() {
        return amountOfKids;
    }

    public void setAmountOfKids(int amountOfKids) {
        this.amountOfKids = amountOfKids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CustomerDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userName='" + userName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", amountOfKids=" + amountOfKids +
                '}';
    }
}
