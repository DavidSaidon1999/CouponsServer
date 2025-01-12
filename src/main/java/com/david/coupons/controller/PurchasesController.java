package com.david.coupons.controller;

import com.david.coupons.dto.Purchase;
import com.david.coupons.dto.PurchasesDetails;
import com.david.coupons.dto.UserLogin;
import com.david.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.david.coupons.logic.PurchasesLogic;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {

    private PurchasesLogic purchasesLogic;
    private UserLogin userLogin;

    @Autowired
    public PurchasesController(PurchasesLogic purchasesLogic) {
        this.purchasesLogic = purchasesLogic;
    }


    //   {
    //     "customerId":3,
    //     "couponId":4,
    //     "amount":1
    //    }
    //TODO עובד
    @PostMapping
    public void createPurchases(@RequestHeader("Authorization") String token, @RequestBody Purchase purchase) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        purchase.setCustomerId(userLogin.getUserId());
        purchase.setTimestamp(Timestamp.from(Instant.now()));
        this.purchasesLogic.createPurchases(purchase, userLogin.getUserType());
    }

    //TODO עובד
    @DeleteMapping("/MyPurchase/{id}")
    public void deleteMyPurchase(@RequestHeader("Authorization") String token, @PathVariable("id") int purchaseId) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.purchasesLogic.deletePurchase(purchaseId, userLogin.getUserId());
    }

    //TODO עובד
    @DeleteMapping("/ByAdmin/{id}")
    public void deletePurchaseByAdmin(@RequestHeader("Authorization") String token, @PathVariable("id") int purchaseId) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.purchasesLogic.deletePurchaseByAdmin(purchaseId, userLogin.getUserType());
    }

    //TODO עובד
    //http://localhost:8080/purchases?page=0&size=1
    @GetMapping
    public Page<PurchasesDetails> getPurchasesToAdmin(@RequestHeader("Authorization") String token,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        return purchasesLogic.getPurchases(userLogin.getUserType(),page,size);
    }

    //TODO עובד
    @GetMapping("/MyPurchases/{id}")
    public PurchasesDetails getMyPurchaseDetailsById(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        return this.purchasesLogic.getPurchaseDetailsById(id, userLogin.getUserId());
    }

    //TODO עובד
    @GetMapping("/Admin/{id}")
    public PurchasesDetails getPurchaseDetailsToAdminById(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        return this.purchasesLogic.getPurchaseDetailsByIdToUserAdmin(id, userLogin.getUserType());
    }


    //TODO עובד
    //http://localhost:8080/purchases/byCustomer?page=0&size=1
    @GetMapping("/byCustomer")
    public Page<PurchasesDetails> getPurchaseDetailsByCustomerId(@RequestHeader("Authorization") String token ,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        return this.purchasesLogic.getPurchaseDetailsByCustomerId(userLogin.getUserId(),page,size);
    }


//    @GetMapping("/byPrice/{price}")
//    public List<PurchaseDetails> getPurchasesDetailsByPrice(@RequestParam int customerId, int price) throws ServerException {
//        return purchasesLogic.getPurchasesDetailsByPrice(customerId, price);
//    }
}
