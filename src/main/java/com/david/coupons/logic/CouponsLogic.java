package com.david.coupons.logic;
import com.david.coupons.dal.ICouponsDal;
import com.david.coupons.dto.*;
import com.david.coupons.entities.CouponEntity;
import com.david.coupons.enums.ErrorTypes;
import com.david.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class CouponsLogic {


    private CategoriesLogic categoriesLogic;

    private ICouponsDal couponsDal;

    @Autowired
    public CouponsLogic(ICouponsDal couponsDal, CategoriesLogic categoriesLogic) {
        this.couponsDal = couponsDal;
        this.categoriesLogic = categoriesLogic;
    }

    public void createCoupon(Coupon coupon, UserLogin userLogin) throws ServerException {
        validateCoupon(coupon, userLogin.getUserType());

        if (couponsDal.isCouponTitleNotUnique(coupon.getTitle())) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_TITLE_UNIQUE_IN_COMPANY, coupon.toString());
        }
        coupon.setCompanyId(userLogin.getCompanyId());
        CouponEntity couponEntity = convertCouponToCouponEntity(coupon);
        this.couponsDal.save(couponEntity);
    }

    private CouponEntity convertCouponToCouponEntity(Coupon coupon) {
        CouponEntity couponEntity = new CouponEntity(coupon.getId(), coupon.getTitle(),
                coupon.getDescription(), coupon.getPrice(),
                coupon.getCompanyId(), coupon.getCategoryId(),
                coupon.getStartDate(), coupon.getEndDate(), coupon.getAmount(), coupon.getImageURL());
        return couponEntity;
    }

    public void updateCoupon(Coupon coupon, UserLogin userLogin) throws ServerException {
        validateCoupon(coupon, userLogin.getUserType());

        CouponEntity couponEntityToCheck = this.couponsDal.findById(coupon.getId()).get();

        //you must a unique name
        if (!couponEntityToCheck.getTitle().equals(coupon.getTitle())) {
            if (couponsDal.isCouponTitleNotUnique(coupon.getTitle())) {
                throw new ServerException(ErrorTypes.INVALID_COUPON_TITLE_UNIQUE_IN_COMPANY, coupon.toString());
            }
        }

        coupon.setCompanyId(userLogin.getCompanyId());
        CouponEntity couponEntity = convertCouponToCouponEntity(coupon);
        this.couponsDal.save(couponEntity);
    }


    public Page<CouponsDetails> getCoupons(int page, int size) throws ServerException {
        Pageable pageable = PageRequest.of(page , size);

        return this.couponsDal.getCouponDetails(pageable);
    }

    private Coupon convertCouponEntityToCoupon(CouponEntity couponEntity) {
        Coupon coupon = new Coupon(couponEntity.getId(), couponEntity.getTitle(),
                couponEntity.getDescription(), couponEntity.getPrice(),
                couponEntity.getCompany().getId(), couponEntity.getCategory().getId(),
                couponEntity.getStartDate(), couponEntity.getEndDate(), couponEntity.getAmount(), couponEntity.getImageURL());
        return coupon;
    }

     Coupon getCoupon(int id) throws ServerException {
        CouponEntity couponEntity = this.couponsDal.findById(id).get();
        Coupon coupon = convertCouponEntityToCoupon(couponEntity);
        return coupon;
    }

    public CouponsDetails getCouponDetailsById(int couponId){
        return this.couponsDal.getCouponDetailsByCouponId(couponId);
    }

    public void deleteCouponByAdmin(int id, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorTypes.ADMIN_ONLY_ACCESS);
        }

        this.couponsDal.deleteById(id);
    }

    public void deleteCouponByCompany(int id,UserLogin userLogin) throws ServerException {
        if(!userLogin.getUserType().equals("Company")){
            throw new ServerException(ErrorTypes.INVALID_USER_TYPE);
        }
        CouponEntity couponEntity = couponsDal.findById(id).get();

        //checking that the user belongs to the same company whose coupon is being deleted
        if(couponEntity.getCompany().getId() != userLogin.getCompanyId()){
            throw new ServerException(ErrorTypes.GENERAL_ERROR,
                    "You are trying to delete a coupon that does not belong to your company");
        }

        this.couponsDal.deleteById(id);
    }

    public Page<CouponsDetails> getCouponsByCompanyId(int companyId,int page,int size) throws ServerException {
        Pageable pageable = PageRequest.of(page , size);

        return this.couponsDal.getCouponsByCompanyId(pageable,companyId);
    }

    public Page<CouponsDetails> getCouponsBelowPrice(float price,int page,int size) throws ServerException {
        Pageable pageable = PageRequest.of(page , size);

        return this.couponsDal.getCouponsBelowPrice(pageable,price);
    }

    public void deleteExpiredCoupons() throws ServerException {
        LocalDateTime localDateTime = LocalDateTime.now();
        Date currentDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        List<Integer> couponsIdToDelete =  this.couponsDal.getListIdFromExpiredCoupons(currentDate);

        for(Integer idToDelete:couponsIdToDelete){
            this.couponsDal.deleteById(idToDelete);
        }
    }

    private void validateCoupon(Coupon coupon, String userType) throws ServerException {

        if (!userType.equals("Company")) {
            throw new ServerException(ErrorTypes.INVALID_USER_TYPE);
        }

        if (coupon.getAmount() < 1) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_AMOUNT, coupon.toString());
        }

        if (coupon.getDescription() == null) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_DESCRIPTION, coupon.toString());
        }

        if (coupon.getDescription().length() > 200) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_DESCRIPTION, coupon.toString());
        }

        if (coupon.getPrice() <= 0) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_PRICE, coupon.toString());
        }

        if (coupon.getTitle() == null) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_TITLE, coupon.toString());
        }

        if (coupon.getTitle().length() > 45) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_TITLE, coupon.toString());
        }

        if (coupon.getStartDate() == null || coupon.getEndDate() == null) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_DATE, coupon.toString());
        }

        if (coupon.getEndDate().before(coupon.getStartDate())) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_START_END, coupon.toString());
        }

        if (coupon.getImageURL() != null && coupon.getImageURL().length() > 100) {
            throw new ServerException(ErrorTypes.INVALID_COUPON_IMAGE_URL_LENGTH, coupon.toString());
        }

        if (!categoriesLogic.isCategoryIdExists(coupon.getCategoryId())) {
            throw new ServerException(ErrorTypes.INVALID_CATEGORY_ID, coupon.toString());
        }
    }

    void decreaseCouponAmount(int purchaseAmount, int couponId) throws ServerException {
        Coupon coupon = getCoupon(couponId);
        int newAmount = coupon.getAmount() - purchaseAmount;

        coupon.setAmount(newAmount);
        CouponEntity couponEntity = convertCouponToCouponEntity(coupon);

        this.couponsDal.save(couponEntity);
    }

    void increaseCouponAmount(int purchaseAmount, int couponId) throws ServerException {
        Coupon coupon = getCoupon(couponId);
        int newAmount = coupon.getAmount() + purchaseAmount;
        coupon.setAmount(newAmount);
        CouponEntity couponEntity = convertCouponToCouponEntity(coupon);
        this.couponsDal.save(couponEntity);
    }

//    private List<Coupon> convertCouponEntityListToCouponList(List<CouponEntity> couponEntityList) {
//        List<Coupon> couponList = new ArrayList<>();
//
//        for (CouponEntity couponEntity : couponEntityList) {
//            Coupon coupon = convertCouponEntityToCoupon(couponEntity);
//            couponList.add(coupon);
//        }
//        return couponList;
//    }
}
