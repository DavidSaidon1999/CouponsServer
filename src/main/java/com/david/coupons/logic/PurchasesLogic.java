package com.david.coupons.logic;

import com.david.coupons.dal.IPurchasesDal;
import com.david.coupons.dto.Coupon;
import com.david.coupons.dto.Purchase;
import com.david.coupons.dto.PurchasesDetails;
import com.david.coupons.entities.PurchasesEntity;
import com.david.coupons.enums.ErrorTypes;
import com.david.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PurchasesLogic {
    private IPurchasesDal purchasesDal;
    private CouponsLogic couponsLogic;

    @Autowired
    public PurchasesLogic(IPurchasesDal purchasesDal, CouponsLogic couponsLogic) {
        this.purchasesDal = purchasesDal;
        this.couponsLogic = couponsLogic;
    }

    @Transactional
    public void createPurchases(Purchase purchase, String userType) throws ServerException {
        if (!userType.equals("Customer")) {
            throw new ServerException(ErrorTypes.INVALID_CUSTOMER);
        }

        validatePurchases(purchase);
        this.couponsLogic.decreaseCouponAmount(purchase.getAmount(), purchase.getCouponId());

        PurchasesEntity purchasesEntity = convertPurchaseToPurchasesEntity(purchase);
        this.purchasesDal.save(purchasesEntity);
    }

    private PurchasesEntity convertPurchaseToPurchasesEntity(Purchase purchase) {
        PurchasesEntity purchasesEntity = new PurchasesEntity(purchase.getId(), purchase.getCustomerId(),
                purchase.getCouponId(), purchase.getAmount(),
                purchase.getTimestamp());
        return purchasesEntity;
    }

    private Purchase convertPurchasesEntityToPurchase(PurchasesEntity purchasesEntity) {
        Purchase purchase = new Purchase(purchasesEntity.getId(), purchasesEntity.getCustomer().getId(),
                purchasesEntity.getCoupon().getId(), purchasesEntity.getAmount());
        return purchase;
    }

    private Purchase getPurchase(int purchaseId) throws ServerException {
        PurchasesEntity purchasesEntity = this.purchasesDal.findById(purchaseId).get();
        Purchase purchase = convertPurchasesEntityToPurchase(purchasesEntity);
        return purchase;
    }

    @Transactional
    public void deletePurchase(int purchaseId, int userId) throws ServerException {
        Purchase purchase = getPurchase(purchaseId);

        if (purchase.getCustomerId() != userId) {
            throw new ServerException(ErrorTypes.GENERAL_ERROR);
        }

        this.couponsLogic.increaseCouponAmount(purchase.getAmount(), purchase.getCouponId());
        this.purchasesDal.deleteById(purchaseId);
    }

    @Transactional
    public void deletePurchaseByAdmin(int purchaseId, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorTypes.ADMIN_ONLY_ACCESS);
        }

        Purchase purchase = getPurchase(purchaseId);
        this.couponsLogic.increaseCouponAmount(purchase.getAmount(), purchase.getCouponId());
        this.purchasesDal.deleteById(purchaseId);
    }

    private void validatePurchases(Purchase purchase) throws ServerException {

        Coupon coupon = couponsLogic.getCoupon(purchase.getCouponId());

        if (purchase.getTimestamp().after(coupon.getEndDate())) {
            throw new ServerException(ErrorTypes.EXPIRED_DATE_PURCHASE, purchase.toString());
        }

        if (purchase.getAmount() < 1) {
            throw new ServerException(ErrorTypes.INVALID_PURCHASES_AMOUNT, purchase.toString());
        }

        if (!isCouponAmountSufficient(purchase)) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_AMOUNT_AFTER_PURCHASE, purchase.toString());
        }
    }


    private boolean isCouponAmountSufficient(Purchase purchase) throws ServerException {

        Coupon coupon = this.couponsLogic.getCoupon(purchase.getCouponId());

        if (coupon == null || coupon.getAmount() < purchase.getAmount()) {
            return false;
        }
        return true;
    }

    public PurchasesDetails getPurchaseDetailsById(int purchaseId, int userId) throws ServerException {
        Purchase purchase = getPurchase(purchaseId);
        if (purchase.getCustomerId() != userId) {
            throw new ServerException(ErrorTypes.GENERAL_ERROR);
        }
        return this.purchasesDal.getPurchaseDetailsById(purchaseId);
    }

    public Page<PurchasesDetails> getPurchases(String userType, int page, int size) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorTypes.PERMISSION_DENIED);
        }

        Pageable pageable = PageRequest.of(page, size);
        return purchasesDal.getPurchases(pageable);
    }

    public PurchasesDetails getPurchaseDetailsByIdToUserAdmin(int purchaseId, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorTypes.PERMISSION_DENIED);
        }

        return this.purchasesDal.getPurchaseDetailsById(purchaseId);
    }


    public Page<PurchasesDetails> getPurchaseDetailsByCustomerId(int customerId, int page, int size) throws ServerException {
        Pageable pageable = PageRequest.of(page, size);

        return this.purchasesDal.getPurchasesByCustomerId(pageable, customerId);
    }

}


