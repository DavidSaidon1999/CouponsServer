package com.david.coupons.enums;

public enum ErrorTypes {
    GENERAL_ERROR(1000, "A general error has occurred please try again later", false, 500),
    UNAUTHORIZED(1001, "Invalid user name or password", false, 401),
    INVALID_COUPON_DESCRIPTION(1002, "Invalid coupon description", false, 400),
    INVALID_COUPON_AMOUNT(1003, "You have to create at least one coupon", false, 400),
    INVALID_COUPON_PRICE(1004, "The price must be greater than 0", false, 400),
    INVALID_COMPANY_ID(1005, "Invalid company id", false, 400),
    INVALID_COUPON_TITLE(1006, "Invalid coupon title", false, 400),
    INVALID_COUPON_DATE(1007, "Invalid coupon date", false, 400),
    INVALID_COUPON_START_END(1008, "Start date must be earlier than the End date", false, 400),
    INVALID_COUPON_IMAGE_URL_LENGTH(1009, "Invalid Image URL. The URL must contain less than 100 characters", false, 400),
    INVALID_USER_NAME(1010, "Username must contain between 1-45 characters, be unique, and be a Gmail address", false, 400),
    INVALID_USER_PASSWORD(1011, "Password must contain between 1-45 characters", false, 400),
    INVALID_USER_TYPE(1012, "invalid user type", false, 400),
    INVALID_CATEGORY_NAME(1013, "The category name must contain between 1-45 characters", false, 400),
    INVALID_NAME(1014, "The name must contain between 1-45 characters", false, 400),
    INVALID_PHONE(1015, "Invalid phone number. Phone number must contain between 9-45 characters", false, 400),
    INVALID_ADDRESS(1016, "The address must contain between 1-45 characters", false, 400),
    INVALID_PURCHASES_AMOUNT(1017, "You must choose at least one coupon", false, 400),
    INVALID_COUPON_AMOUNT_AFTER_PURCHASE(1018, "Cannot purchase a quantity of coupons that does not exist", false, 400),
    EXPIRED_DATE_PURCHASE(1019, "The coupon is expired", false, 400),
    INVALID_USER_ID(1020, "The user ID is null", false, 400),
    INVALID_PURCHASES_ID(1021, "The purchase ID is null", false, 400),
    INVALID_CATEGORY_ID(1022, "Category ID does not exist", false, 400),
    INVALID_COMPANY_NAME(1023, "The company name must be unique and no more than 45 characters", false, 400),
    INVALID_COUPON_TITLE_UNIQUE_IN_COMPANY(1024, "The coupon title is not unique within the company", false, 400),
    INVALID_CUSTOMER(1025, "The user type is not Customer", false, 400),
    PERMISSION_DENIED(1026, "Permission denied! This view is only for admins", false, 403),
    CANT_FOUND_USER(1027, "User not found", false, 404),
    INVALID_EMAIL_TOKEN(1028, "Invalid email token", false, 400),
    APPLICATION_EXCEPTION(444, "Something went wrong. Please try again later", true, 500),
    ADMIN_ONLY_ACCESS(1029, "Sorry, no access. You must be an admin", false, 403),
    PHONE_ALREADY_EXISTS(1030,"The phone number already exists in the system",false,400);;

    private int internalError;
    private String clientErrorMessage;
    private boolean isShowStackTrace;
    private int httpStatus;

    ErrorTypes(int internalError, String clientErrorMessage, boolean isShowStackTrace, int httpStatus) {
        this.internalError = internalError;
        this.clientErrorMessage = clientErrorMessage;
        this.isShowStackTrace = isShowStackTrace;
        this.httpStatus = httpStatus;
    }

    public int getInternalError() {
        return internalError;
    }

    public String getClientErrorMessage() {
        return clientErrorMessage;
    }

    public boolean isShowStackTrace() {
        return isShowStackTrace;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
