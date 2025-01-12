package com.david.coupons.controller;

import com.david.coupons.dto.UserLogin;
import com.david.coupons.exceptions.ServerException;
import com.david.coupons.logic.CategoriesLogic;
import com.david.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.david.coupons.dto.Category;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private CategoriesLogic categoriesLogic;
    private UserLogin userLogin;

    @Autowired
    public CategoriesController(CategoriesLogic categoriesLogic) {
        this.categoriesLogic = categoriesLogic;
    }

    //   {
    //     "name":"hi-tech"
    //    }
    //TODO עובד
    @PostMapping
    public void createCategory(@RequestHeader("Authorization") String token , @RequestBody Category category) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.categoriesLogic.createCategories(category,userLogin.getUserType());
    }

    //TODO עובד
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable("id") int id) throws ServerException {
        return this.categoriesLogic.getCategory(id);
    }

    //   {
    //     "id":2,
    //     "name":"holiday"
    //    }
   //TODO עובד
    @PutMapping
    public void updateCategories(@RequestHeader("Authorization") String token ,@RequestBody Category category) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.categoriesLogic.updateCategories(category,userLogin.getUserType());
    }

    //TODO עובד
    @DeleteMapping("{id}")
    public void deleteCategory(@RequestHeader("Authorization") String token ,@PathVariable("id") int id) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.categoriesLogic.deleteCategory(id,userLogin.getUserType());
    }

    //TODO עובד
    //http://localhost:8080/categories?page=2&size=2
    @GetMapping
    public Page<Category> getCategories(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) throws ServerException{
        return this.categoriesLogic.getCategories(page,size);
    }
}
