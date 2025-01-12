package com.david.coupons.entities;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username",unique = true,nullable = false,length = 45)
    private String userName;
    @Column(name = "password",nullable = false,length = 45)
    private String password;
    @Column(name = "user_type",nullable = false,length = 45)
    private String userType;
    @Column(name = "isVerify",nullable = false)
    private boolean isVerify;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @OneToOne(mappedBy = "user" , cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private CustomerEntity customer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private VerificationTokenEntity verificationTokenEntity;

    public UserEntity() {
    }

    public UserEntity(int id, String userName, String password, String userType, Integer companyId) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userType = userType;

        if (userType.equals("Company")) {

            this.company = new CompanyEntity();
            this.company.setId(companyId);
        }
    }

    public UserEntity(String userName, String password, String userType, Integer companyId) {
        this.id = id;
        this.userName = userName;
        this.password = password;

        if (userType.equals("Company")) {
            this.company = new CompanyEntity();
            this.company.setId(companyId);
        }
    }

    public VerificationTokenEntity getVerificationTokenEntity() {
        return verificationTokenEntity;
    }

    public void setVerificationTokenEntity(VerificationTokenEntity verificationTokenEntity) {
        this.verificationTokenEntity = verificationTokenEntity;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public boolean isVerify() {
        return isVerify;
    }

    public void setVerify(boolean verify) {
        isVerify = verify;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
