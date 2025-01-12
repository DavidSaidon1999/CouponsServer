package com.david.coupons.dal;

import com.david.coupons.dto.CustomerDetails;
import com.david.coupons.entities.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomersDal extends JpaRepository<CustomerEntity, Integer> {
    @Query("SELECT NEW com.david.coupons.dto.CustomerDetails(c.id,c.name,c.user.userName,c.address,c.phone,c.amountOfKids)FROM CustomerEntity c  WHERE c.id= :id")
    CustomerDetails getCustomerDetailsById(@Param("id") int id);

    @Query("SELECT NEW com.david.coupons.dto.CustomerDetails(c.id, c.name, c.user.userName, c.address, c.phone, c.amountOfKids) FROM CustomerEntity c")
    Page<CustomerDetails> getCustomersDetails(Pageable pageable);

    @Query("SELECT count(c.id)>0 FROM CustomerEntity c WHERE c.phone= :phone")
    boolean IsPhoneExist(@Param("phone") String phone);


}
