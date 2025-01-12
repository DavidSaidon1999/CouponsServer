package com.david.coupons.utils;

import com.david.coupons.dto.SendStatisticsThread;

public class StatisticsUtils {

    public static void sendStatistics(String userName, String actionType) {

        SendStatisticsThread sendStatisticsThread = new SendStatisticsThread(userName,actionType);
        sendStatisticsThread.start();
    }
}
