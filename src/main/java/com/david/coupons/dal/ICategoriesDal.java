package com.david.coupons.dal;

import com.david.coupons.dto.Category;
import com.david.coupons.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoriesDal extends JpaRepository<CategoryEntity, Integer> {
    @Query("select count(c.id)>0 from CategoryEntity c where name = :name")
    boolean isCategoriesNameNotUnique(@Param("name") String name);
    @Query("SELECT new com.david.coupons.dto.Category(c.id,c.name) FROM CategoryEntity c")
    Page<Category> allCategory(Pageable pageable);
}
