package com.david.coupons.controller;

import com.david.coupons.dto.User;
import com.david.coupons.dto.UserLogin;
import com.david.coupons.dto.UserLoginData;
import com.david.coupons.enums.ErrorTypes;
import com.david.coupons.exceptions.ServerException;
import com.david.coupons.logic.VerificationLogic;
import com.david.coupons.logic.UserLogic;
import com.david.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private UserLogic userLogic;
    private UserLogin userLogin;
    private VerificationLogic emailLogic;


    @Autowired
    public UsersController(UserLogic userLogic, VerificationLogic emailLogic) {
        this.userLogic = userLogic;
        this.emailLogic = emailLogic;
    }


    //   {
    //	"userName": "Dan@gmail.com",
    //    "password": "1234586"
    //    }
    //TODO עובד
    @PostMapping("/login")
    public String login(@RequestBody UserLoginData userLoginData) throws Exception {
        return userLogic.login(userLoginData);
    }


    //   {
    //     "userName":"avi@gmail.com",
    //     "password":"123456",
    //     "userType":"Company",
    //     "companyId":1
    //    }
    //TODO עובד
    @PostMapping("/createByAdmin")
    public void createUserFromAdmin(@RequestHeader("Authorization") String token, @RequestBody User user) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);

        if (!userLogin.getUserType().equals("Admin")) {
            throw new ServerException(ErrorTypes.INVALID_USER_TYPE, "Only an admin can create a user");
        }

        this.userLogic.createUser(user);
    }


    //   {
    //	"userName": "DanOren@gmail.com",
    //    "password": "1234586"
    //    }
    //TODO עובד
    @PutMapping
    public void updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        user.setId(userLogin.getUserId());
        user.setUserType(userLogin.getUserType());
        user.setCompanyId(userLogin.getCompanyId());
        this.userLogic.updateUser(user);
    }

    //TODO עובד
    @DeleteMapping("/DeleteMyUser")
    public void deleteMyUser(@RequestHeader("Authorization") String token) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.userLogic.deleteMyUser(userLogin.getUserId());
    }

    //TODO עובד
    @DeleteMapping("/AdminDelete/{id}")
    public void deleteUserByAdmin(@RequestHeader("Authorization") String token, @PathVariable("id") int userId) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        this.userLogic.deleteUserByAdmin(userId, userLogin.getUserType());
    }

    //TODO עובד
    //http://localhost:8080/users/usersList/?page=0&size=2
    @GetMapping("/usersList")
    public Page<User> getAllUsersToAdmin(@RequestHeader("Authorization") String token ,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        return this.userLogic.getUsers(userLogin.getUserType(),page,size);
    }

    //TODO עובד
    @GetMapping
    public User getMyUser(@RequestHeader("Authorization") String token) throws Exception {
        userLogin = JWTUtils.decodeJWT(token);
        return this.userLogic.getUser(userLogin.getUserId());
    }
}
