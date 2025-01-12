package com.david.coupons.logic;
import com.david.coupons.dal.IUsersDal;
import com.david.coupons.dto.UserLogin;
import com.david.coupons.dto.UserLoginData;
import com.david.coupons.entities.CompanyEntity;
import com.david.coupons.entities.UserEntity;
import com.david.coupons.utils.JWTUtils;
import com.david.coupons.utils.StatisticsUtils;
import com.david.coupons.dto.User;
import com.david.coupons.enums.ErrorTypes;
import com.david.coupons.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserLogic {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,}|co\\.il)$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private IUsersDal usersDal;
    private CompaniesLogic companiesLogic;
    private VerificationLogic verificationLogic;

    @Autowired
    public UserLogic(IUsersDal usersDal, CompaniesLogic companiesLogic, VerificationLogic verificationLogic) {
        this.usersDal = usersDal;
        this.companiesLogic = companiesLogic;
        this.verificationLogic = verificationLogic;
    }

    @Transactional
    public UserEntity createUser(User user) throws ServerException, MessagingException {


        validateUser(user);
        if (usersDal.isUserNameNotUnique(user.getUserName())) {
            throw new ServerException(ErrorTypes.INVALID_USER_NAME, user.toString());
        }
        UserEntity userEntity = convertUserToUserEntity(user);

        userEntity = usersDal.save(userEntity);

        verificationLogic.createVerificationTokenForUser(userEntity);

        return userEntity;
    }

    private UserEntity convertUserToUserEntity(User user) {

        UserEntity userEntity = new UserEntity(user.getId(), user.getUserName(), user.getPassword(), user.getUserType(), user.getCompanyId());
        return userEntity;
    }

    private User convertUserEntityToUser(UserEntity userEntity) {

        User user = new User(userEntity.getUserName(),
                userEntity.getPassword(),
                userEntity.getUserType(),
                Optional.ofNullable(userEntity.getCompany())
                        .map(CompanyEntity::getId)
                        .orElse(null)); //

        return user;
    }

    public String login(UserLoginData userLoginData) throws Exception {
        UserEntity userEntity = this.usersDal.login(userLoginData.getUserName(), userLoginData.getPassword());

        if (userEntity == null) {
            throw new ServerException(ErrorTypes.UNAUTHORIZED);
        }
        if (!userEntity.isVerify()) {
            throw new ServerException(ErrorTypes.UNAUTHORIZED, "your account is not verify by email");
        }

        StatisticsUtils.sendStatistics(userLoginData.getUserName(), "login");

        // These lines are intended to avoid null pointer exceptions
        Integer companyOfTheUser = (userEntity.getCompany() != null) ? userEntity.getCompany().getId() : null;
        UserLogin userLogin = new UserLogin(userEntity.getId(), companyOfTheUser, userEntity.getUserType());
        String token = JWTUtils.createJWT(userLogin);
        return token;
    }

    public void updateUser(User user) throws ServerException {
        validateUser(user);
        UserEntity userNameBeforeUpdate = this.usersDal.findById(user.getId()).get();

        if (userNameBeforeUpdate == null) {
            throw new ServerException(ErrorTypes.CANT_FOUND_USER);
        }

        //If the username is different from what it was before, it still must remain unique.
        if (!userNameBeforeUpdate.getUserName().equals(user.getUserName())) {
            if (usersDal.isUserNameNotUnique(user.getUserName())) {
                throw new ServerException(ErrorTypes.INVALID_USER_NAME, user.toString());
            }
        }

        UserEntity userEntity = convertUserToUserEntity(user);
        userEntity.setVerify(userNameBeforeUpdate.isVerify());
        usersDal.save(userEntity);
    }


    public void deleteMyUser(int userId) throws ServerException {
        this.usersDal.deleteById(userId);
    }


    public void deleteUserByAdmin(int userId, String userType) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorTypes.ADMIN_ONLY_ACCESS);
        }
        this.usersDal.deleteById(userId);
    }

    public Page<User> getUsers(String userType , int page,int size) throws ServerException {
        if (!userType.equals("Admin")) {
            throw new ServerException(ErrorTypes.PERMISSION_DENIED);
        }

        Pageable pageable =PageRequest.of(page , size);

        // i return all users bat didnt whit password
        return this.usersDal.getAllUsers(pageable);
    }



    public User getUser(int id) throws ServerException {

        UserEntity userEntity = this.usersDal.findById(id).get();
        User user = convertUserEntityToUser(userEntity);
        return user;
    }


    private void validateUser(User user) throws ServerException {

        if (!isValidateEmail(user.getUserName())) {
            throw new ServerException(ErrorTypes.INVALID_USER_NAME, user.toString());
        }

        if (user.getUserType() == null || user.getUserType().length() > 45) {
            throw new ServerException(ErrorTypes.INVALID_USER_TYPE, user.toString());
        }

        if ((user.getUserType() == "Company") && (companiesLogic.getCompany(user.getCompanyId()) == null)) {
            throw new ServerException(ErrorTypes.INVALID_COMPANY_ID, user.toString());
        }

        if (!user.getUserType().equals("Customer") && !user.getUserType().equals("Admin") && !user.getUserType().equals("Company")) {
            throw new ServerException(ErrorTypes.INVALID_USER_TYPE, user.toString());
        }

        if (!user.getUserType().equals("Company") && user.getCompanyId() != null) {
            throw new ServerException(ErrorTypes.INVALID_USER_TYPE, user.toString());
        }

        if (user.getUserName() == null || user.getUserName().length() > 45) {
            throw new ServerException(ErrorTypes.INVALID_USER_NAME, user.toString());
        }

        if (user.getPassword() == null || user.getPassword().length() > 45) {
            throw new ServerException(ErrorTypes.INVALID_USER_PASSWORD, user.toString());
        }

        if (user.getCompanyId() != null && (this.companiesLogic.getCompany(user.getCompanyId()) == null)) {
            throw new ServerException(ErrorTypes.INVALID_COMPANY_ID, user.toString());
        }
    }

    private boolean isValidateEmail(String username) {

        Matcher matcher = UserLogic.EMAIL_PATTERN.matcher(username);
        return matcher.matches();
    }

    public void updateValidateStatusById(int userId) {
        this.usersDal.updateValidateStatusById(userId);
    }

   public void deleteUnVerifiedUsers() throws ServerException{

        LocalDateTime currentDate = LocalDateTime.now();
        List<Integer> userIdTheNeedDelete =  this.usersDal.getUsersIdExpiredVerification(currentDate);

        for(Integer idToDelete:userIdTheNeedDelete){
            this.usersDal.deleteById(idToDelete);
        }
    }

}
