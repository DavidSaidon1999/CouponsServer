package com.david.coupons.controller;

import com.david.coupons.dto.Company;
import com.david.coupons.dto.UserLogin;
import com.david.coupons.exceptions.ServerException;
import com.david.coupons.logic.CompaniesLogic;
import com.david.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    private CompaniesLogic companiesLogic;
    private UserLogin userLogin;

    @Autowired
    public CompaniesController(CompaniesLogic companiesLogic) {
        this.companiesLogic = companiesLogic;
    }

    //TODO עובד
    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable("id") int id) throws ServerException {
        return this.companiesLogic.getCompany(id);
    }


    //   {
    //     "name":"Hp",
    //     "address":"Rehovot",
    //     "phone":"0548875695"
    //    }
    //TODO עובד
    @PostMapping
    public void crateCompany(@RequestHeader("Authorization") String token ,@RequestBody Company company) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.companiesLogic.createCompanies(company,userLogin.getUserType());
    }

    //    {
    //      	"id":2,
    //        "name":"AIA",
    //        "address":"Lod",
    //        "phone":"089494522"
    //    }
    //TODO עובד
    @PutMapping
    public void updateCompany(@RequestHeader("Authorization") String token , @RequestBody Company company) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.companiesLogic.updateCompany(company,userLogin.getUserType());
    }

    //TODO עובד
    //http://localhost:8080/companies?page=0&size=1
    @GetMapping
    public Page<Company> getCompanies(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) throws ServerException {
        return this.companiesLogic.getCompanies(page,size);
    }

    //TODO עובד
    @DeleteMapping("/{id}")
    public void deleteCompany(@RequestHeader("Authorization") String token  , @PathVariable("id") int id) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.companiesLogic.deleteCompany(id,userLogin.getUserType());
    }
}
