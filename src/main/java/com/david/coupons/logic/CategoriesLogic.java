package com.david.coupons.logic;

import com.david.coupons.dal.ICategoriesDal;
import com.david.coupons.dto.Category;
import com.david.coupons.entities.CategoryEntity;
import com.david.coupons.enums.ErrorTypes;
import com.david.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriesLogic {

    private ICategoriesDal categoriesDal;

    @Autowired
    public CategoriesLogic(ICategoriesDal categoriesDal) {
        this.categoriesDal = categoriesDal;
    }

    public void createCategories(Category category, String userType) throws ServerException {

        validateCategories(category, userType);

        CategoryEntity categoryEntity = convertCotegoryToCategoryEntity(category);
        this.categoriesDal.save(categoryEntity);
    }

    private CategoryEntity convertCotegoryToCategoryEntity(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity(category.getId(), category.getName());
        return categoryEntity;
    }

    public void updateCategories(Category category, String userType) throws ServerException {

        validateCategories(category, userType);
        CategoryEntity categoryEntity = convertCotegoryToCategoryEntity(category);
        this.categoriesDal.save(categoryEntity);
    }

    public void deleteCategory(int id, String userType) throws ServerException {

        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorTypes.ADMIN_ONLY_ACCESS);
        }
        this.categoriesDal.deleteById(id);
    }

    public Page<Category> getCategories(int page, int size) throws ServerException {
        Pageable pageable = PageRequest.of(page , size);

        return this.categoriesDal.allCategory(pageable);
    }

    private List<Category> convertListCategoryEntityToListCategory(List<CategoryEntity> categoryEntityList) {
        List<Category> categoryList = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryEntityList) {
            Category category = convertCategoryEntityToCategory(categoryEntity);
            categoryList.add(category);
        }
        return categoryList;
    }

    private Category convertCategoryEntityToCategory(CategoryEntity categoryEntity) {
        Category category = new Category(categoryEntity.getId(), categoryEntity.getName());
        return category;
    }

    public Category getCategory(int id) throws ServerException {
        CategoryEntity categoryEntity = this.categoriesDal.findById(id).get();
        Category category = convertCategoryEntityToCategory(categoryEntity);
        return category;
    }

    boolean isCategoryIdExists(int id) throws ServerException {
        return this.categoriesDal.existsById(id);
    }

    private void validateCategories(Category category, String userType) throws ServerException {
        if (category.getName() == null) {
            throw new ServerException(ErrorTypes.INVALID_CATEGORY_NAME, category.toString());
        }

        if(category.getName().length() > 45){
            throw new ServerException(ErrorTypes.INVALID_CATEGORY_NAME, category.toString());
        }

        if (categoriesDal.isCategoriesNameNotUnique(category.getName())) {
            throw new ServerException(ErrorTypes.INVALID_CATEGORY_NAME, category.toString());
        }
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorTypes.ADMIN_ONLY_ACCESS);
        }
    }
}
