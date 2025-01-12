package com.david.coupons.dal;

import com.david.coupons.entities.VerificationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface IVerificationDal extends JpaRepository<VerificationTokenEntity,Integer> {

    @Query("select v.user.id from VerificationTokenEntity v where v.emailToken= :emailToken")
    int getByUserIdByEmailToken(@Param("emailToken") String emailToken);

    @Query("select count(v.id) > 0 from VerificationTokenEntity v where v.emailToken= :emailToken")
    boolean isEmailTokenExists(@Param("emailToken") String emailToken);

    @Modifying
    @Transactional
    @Query("delete from VerificationTokenEntity v where v.user.id= :userId ")
    void deleteByUserId(@Param("userId") int userId);
}
