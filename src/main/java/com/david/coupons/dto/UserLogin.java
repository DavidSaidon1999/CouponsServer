package com.david.coupons.dto;


public class UserLogin {
    private int userId;
    private Integer companyId;
    private String userType;

    public UserLogin() {
    }

    public UserLogin(int userId, Integer companyId, String userType) {
        this.userId = userId;
        this.companyId = companyId;
        this.userType = userType;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
