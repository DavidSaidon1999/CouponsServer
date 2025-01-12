package com.david.coupons.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class TokenEmailUtil {

    private static final int expiryTimeInDay = 1;

    public static LocalDateTime calculateExpiryDate() {
        return LocalDateTime.now().plus(expiryTimeInDay, ChronoUnit.DAYS);
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}