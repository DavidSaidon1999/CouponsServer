package com.david.coupons.dal;

import com.david.coupons.dto.PurchasesDetails;
import com.david.coupons.entities.PurchasesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IPurchasesDal extends JpaRepository<PurchasesEntity, Integer> {

    @Query("SELECT new com.david.coupons.dto.PurchasesDetails(p.id, p.coupon.id, p.amount, (p.amount * p.coupon.price) AS totalPrice, p.timestamp, " +
            "p.coupon.title, p.coupon.description, p.coupon.imageURL, p.coupon.endDate) " +
            "FROM PurchasesEntity p " +
            "WHERE p.id = :id")
    PurchasesDetails getPurchaseDetailsById(@Param("id") int id);


    @Query("SELECT new com.david.coupons.dto.PurchasesDetails(p.id, p.coupon.id, p.amount, (p.amount * p.coupon.price) AS totalPrice, p.timestamp, " +
            "p.coupon.title, p.coupon.description, p.coupon.imageURL, p.coupon.endDate) " +
            "FROM PurchasesEntity p ")
    Page<PurchasesDetails> getPurchases(Pageable pageable);

    @Query("SELECT new com.david.coupons.dto.PurchasesDetails(p.id, p.coupon.id, p.amount, (p.amount * p.coupon.price) AS totalPrice, p.timestamp, " +
            "p.coupon.title, p.coupon.description, p.coupon.imageURL, p.coupon.endDate) " +
            "FROM PurchasesEntity p " +
            "WHERE p.customer.id = :customerId")
    Page<PurchasesDetails> getPurchasesByCustomerId(Pageable pageable,@Param("customerId") int customerId);

}
