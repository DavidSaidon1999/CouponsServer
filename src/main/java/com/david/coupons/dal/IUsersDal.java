package com.david.coupons.dal;

import com.david.coupons.dto.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.david.coupons.entities.UserEntity;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface IUsersDal extends JpaRepository<UserEntity, Integer> {
    @Query("select u  from UserEntity u where u.userName = :userName AND u.password = :password")
    UserEntity login(@Param("userName") String userName, @Param("password") String password);

    @Query("select count(u.id)>0 from UserEntity u where u.userName = :userName")
    boolean isUserNameNotUnique(@Param("userName") String userName);
    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.isVerify = true WHERE u.id = :userId")
    void updateValidateStatusById(@Param("userId") int userId);

    @Query("select new com.david.coupons.dto.User(u.id,u.userName,u.userType,u.company.id) from UserEntity u")
    Page<User> getAllUsers(Pageable pageable);


    @Query("SELECT u.id FROM UserEntity u WHERE u.id IN (SELECT v.user.id FROM VerificationTokenEntity v WHERE v.expiryDate < :currentDate) AND u.isVerify = false")
    List<Integer> getUsersIdExpiredVerification(@Param("currentDate") LocalDateTime currentDate);
}
