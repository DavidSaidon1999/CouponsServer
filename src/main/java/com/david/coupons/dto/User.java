package com.david.coupons.dto;

public class User {
    private int id;
    private String userName;
    private String password;
    private String userType;
    private Integer companyId;

    public User() {

    }

    public User(int id, String userName, String userType, Integer companyId) {
        this.id = id;
        this.userName = userName;
        this.userType = userType;
        this.companyId = companyId;
    }

    public User(String userName, String password, String userType, Integer companyId) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.companyId = companyId;
    }

    public User(int id, String userName, String password, String userType, Integer companyId) {

        this(userName, password, userType, companyId);
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }

    public Integer getCompanyId() {
        return companyId;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userType='" + userType + '\'' +
                ", companyId=" + companyId +
                '}';
    }
}
