package com.david.coupons.dal;

import java.util.Date;
import java.util.List;

import com.david.coupons.dto.Coupon;
import com.david.coupons.dto.CouponsDetails;
import com.david.coupons.entities.CouponEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ICouponsDal extends JpaRepository<CouponEntity, Integer> {
    @Query("SELECT c FROM CouponEntity c WHERE c.category.id= :categoryId AND c.company.id = :companyId")
    List<Coupon> getCouponsByCategoryAndCompany(@Param("categoryId") int categoryId, @Param("companyId") int companyId);
    @Query("SELECT new com.david.coupons.dto.CouponsDetails(co.id,co.title,co.description,co.price,co.company.name,co.startDate,co.endDate,co.amount,co.imageURL) from CouponEntity co WHERE  co.company.id = :companyId")
    Page<CouponsDetails> getCouponsByCompanyId(Pageable pageable, @Param("companyId") int companyId);

    @Query("SELECT new com.david.coupons.dto.CouponsDetails(co.id,co.title,co.description,co.price,co.company.name,co.startDate,co.endDate,co.amount,co.imageURL) from CouponEntity co WHERE co.price <= :price")
    Page<CouponsDetails> getCouponsBelowPrice(Pageable pageable,@Param("price") float price);

    @Query("select count(c.id)>0 from CouponEntity c where title = :title")
    boolean isCouponTitleNotUnique(@Param("title") String title);

    @Query("SELECT c.id FROM CouponEntity c WHERE endDate < :currentDate")
    List<Integer> getListIdFromExpiredCoupons(@Param("currentDate") Date currentDate);

    @Query("SELECT new com.david.coupons.dto.CouponsDetails(co.id,co.title,co.description,co.price,co.company.name,co.startDate,co.endDate,co.amount,co.imageURL) from CouponEntity co")
    Page<CouponsDetails> getCouponDetails(Pageable pageable);
    @Query("SELECT new com.david.coupons.dto.CouponsDetails(co.id,co.title,co.description,co.price,co.company.name,co.startDate,co.endDate,co.amount,co.imageURL) from CouponEntity co WHERE co.id= :couponId")
    CouponsDetails getCouponDetailsByCouponId(@Param("couponId") int couponId);

}
