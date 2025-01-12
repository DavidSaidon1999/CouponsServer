package com.david.coupons.controller;
import com.david.coupons.dto.Customer;
import com.david.coupons.dto.CustomerDetails;
import com.david.coupons.dto.UserLogin;
import com.david.coupons.exceptions.ServerException;
import com.david.coupons.logic.CustomerLogic;
import com.david.coupons.logic.UserLogic;
import com.david.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerLogic customerLogic;
    private UserLogic userLogic;
    private UserLogin userLogin;


    @Autowired
    public CustomerController(CustomerLogic customerLogic,UserLogic userLogic) {
        this.customerLogic = customerLogic;
        this.userLogic = userLogic;
    }


    //TODO עובד
    @GetMapping("/MyCustomer")
    public CustomerDetails getMyCustomer(@RequestHeader("Authorization") String token) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        return this.customerLogic.getCustomer(userLogin.getUserId());
    }

    //TODO עובד
    @GetMapping("{id}")
    public CustomerDetails getCustomerByAdmin(@RequestHeader("Authorization") String token,@PathVariable("id") int id) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        return this.customerLogic.getCustomerByAdmin(id,userLogin.getUserType());
    }

//   {
//    "name": " nir",
//   "address": "Rehovot",
//    "phone": "+97258547789",
//    "amountOfKids": 5,
//      "user":{
//     "userName": "nir@gmail.com",
//    "password": "1234586",
//    "userType": "Customer",
//    "companyId": null}
//    }
    //TODO עובד
    @PostMapping
    public void createCustomer(@RequestBody Customer customer) throws ServerException, MessagingException {
        this.customerLogic.createCustomer(customer);
    }

//    {
//      "id":52,
//    "name": " dan48385",
//    "address": "hifaa",
//    "phone": "+972504655985",
//    "amountOfKids": 5,
//      "user":{
//     "userName": "danoren3151848855@gmail.com",
//    "password": "1234586",
//    "userType": "Customer",
//    "companyId": null}
//    }
    //TODO עובד
    @PutMapping
    public void updateCustomer(@RequestHeader("Authorization") String token , @RequestBody Customer customer) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        customer.setId(userLogin.getUserId());
        customer.getUser().setId(userLogin.getUserId());
        customer.getUser().setUserType("Customer");
        customer.getUser().setCompanyId(null);
        this.customerLogic.updateCustomer(customer);
        this.userLogic.updateUser(customer.getUser());
    }

    //TODO עובד
    //http://localhost:8080/customers?page=0&size=1
    @GetMapping
    public Page<CustomerDetails> getCustomers(@RequestHeader("Authorization") String token,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        return this.customerLogic.getCustomers(userLogin.getUserType(),page,size);
    }

    //TODO עובד
    @DeleteMapping()
    public void deleteMyCustomer(@RequestHeader("Authorization") String token) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);

        this.customerLogic.deleteCustomer(userLogin.getUserId());
    }

    //TODO עובד
    @DeleteMapping("/deleteByAdmin/{id}")
    public void deleteCustomerByAdmin(@RequestHeader("Authorization") String token, @PathVariable("id") int id) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.customerLogic.deleteCustomerByAdmin(id,userLogin.getUserType());
    }
}
