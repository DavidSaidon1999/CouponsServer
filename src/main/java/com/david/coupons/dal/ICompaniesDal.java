package com.david.coupons.dal;

import com.david.coupons.dto.Company;
import com.david.coupons.entities.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompaniesDal extends JpaRepository<CompanyEntity, Integer> {
    @Query("select count(c.id)>0 from CompanyEntity c where name = :name")
    boolean isCompanyNameDontUnique(@Param("name") String name);

    @Query("SELECT new com.david.coupons.dto.Company(c.id,c.name,c.address,c.phone) FROM CompanyEntity c")
    Page<Company> allCompany(Pageable pageable);
}
