package com.david.coupons.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_verification")
public class VerificationTokenEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column (name = "email_Token",nullable = false,unique = true)
    private String emailToken;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;


    public VerificationTokenEntity() {
    }

    public VerificationTokenEntity(String emailToken, int id,int userId,LocalDateTime expiryDate) {
        this.emailToken = emailToken;
        this.id = id;
        this.user = new UserEntity();
        this.user.setId(userId);
        this.expiryDate=expiryDate;
    }

    public VerificationTokenEntity(String emailToken,int userId,LocalDateTime expiryDate) {
        this.emailToken = emailToken;
        this.user = new UserEntity();
        this.user.setId(userId);
        this.expiryDate=expiryDate;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }
}