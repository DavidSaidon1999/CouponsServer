package com.david.coupons.task;

import com.david.coupons.exceptions.ServerException;
import com.david.coupons.logic.CouponsLogic;
import com.david.coupons.logic.UserLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimerManager {

    private UserLogic userLogic;
    private CouponsLogic couponsLogic;


    @Autowired
    public TimerManager(UserLogic userLogic,CouponsLogic couponsLogic) {
        this.userLogic=userLogic;
        this.couponsLogic=couponsLogic;
    }

    // This method will run once every day at midnight (00:00)
    @Scheduled(cron = "0  0 0 * * ?")
    void executeDailyTask() throws ServerException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    userLogic.deleteUnVerifiedUsers();
                } catch (ServerException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            try {
                couponsLogic.deleteExpiredCoupons();
            } catch (ServerException e) {
                e.printStackTrace();
            }
        }).start();
    }
}