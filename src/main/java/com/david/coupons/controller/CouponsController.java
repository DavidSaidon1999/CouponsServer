package com.david.coupons.controller;
import com.david.coupons.dto.Coupon;
import com.david.coupons.dto.CouponsDetails;
import com.david.coupons.dto.UserLogin;
import com.david.coupons.exceptions.ServerException;
import com.david.coupons.logic.CouponsLogic;
import com.david.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponsController {

    private CouponsLogic couponsLogic;
    private UserLogin userLogin;

    @Autowired
    public CouponsController(CouponsLogic couponsLogic) {
        this.couponsLogic = couponsLogic;
    }

    //       {
    //         "title":"check6",
    //         "description":"fdssghdfg",
    //         "price":25,
    //         "companyId":1,
    //         "categoryId":2,
    //         "startDate":"2024-09-24",
    //         "endDate":"2024-09-25",
    //         "amount":30,
    //         "imageURL":"image"
    //        }
    //TODO עובד
    @PostMapping
    public void createCouponByUserCompany(@RequestHeader("Authorization") String token , @RequestBody Coupon coupon) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.couponsLogic.createCoupon(coupon,userLogin);
    }

    //TODO עובד
    @GetMapping("{id}")
    public CouponsDetails getCouponDetailsById(@PathVariable("id") int id) throws ServerException {
        return this.couponsLogic.getCouponDetailsById(id);
    }

    // {
    //         "id":7,
    //         "title":"check7",
    //         "description":"fdssghdfg",
    //         "price":25,
    //         "companyId":1,
    //         "categoryId":2,
    //         "startDate":"2024-09-24",
    //         "endDate":"2024-09-25",
    //         "amount":20,
    //         "imageURL":"image"
    //        }
    //TODO עובד
    @PutMapping
    public void updateCouponByUserCompany(@RequestHeader("Authorization") String token , @RequestBody Coupon coupon) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.couponsLogic.updateCoupon(coupon,userLogin);
    }

    //TODO עובד
    //http://localhost:8080/coupons?page=0&size=5
    @GetMapping
    public Page<CouponsDetails> getCoupons( @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) throws ServerException {
        return this.couponsLogic.getCoupons(page,size);
    }

    @DeleteMapping("/Admin/{id}")
    public void deleteCouponByAdmin(@RequestHeader("Authorization") String token , @PathVariable("id") int id) throws Exception {
        userLogin=JWTUtils.decodeJWT(token);
        this.couponsLogic.deleteCouponByAdmin(id,userLogin.getUserType());
    }

    //TODO עובד
    @DeleteMapping("/{id}")
    public void deleteCouponByUserCompany(@RequestHeader("Authorization") String token , @PathVariable("id") int id) throws Exception {
        userLogin=JWTUtils.decodeJWT(token);
        this.couponsLogic.deleteCouponByCompany(id,userLogin);
    }

    //TODO עובד
    //http://localhost:8080/coupons/byCompanyId?companyId=1
    //http://localhost:8080/coupons/byCompanyId?companyId=1&page=0&size=5
    @GetMapping("/byCompanyId")
    public Page<CouponsDetails> getCouponsByCompanyId(@RequestParam("companyId") int companyId,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) throws ServerException {
        return this.couponsLogic.getCouponsByCompanyId(companyId,page,size);
    }

    //http://localhost:8080/coupons/byMaxPrice?price=100
    //http://localhost:8080/coupons/byMaxPrice?price=100&page=0&size=5
    //TODO עובד
    @GetMapping("/byMaxPrice")
    public Page<CouponsDetails> getCouponsBelowPrice(@RequestParam("price") float price,
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) throws ServerException {
        return this.couponsLogic.getCouponsBelowPrice(price,page,size);
    }
}
