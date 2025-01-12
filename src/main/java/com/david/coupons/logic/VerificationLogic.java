package com.david.coupons.logic;

import com.david.coupons.dal.IVerificationDal;
import com.david.coupons.dto.User;
import com.david.coupons.entities.UserEntity;
import com.david.coupons.entities.VerificationTokenEntity;
import com.david.coupons.enums.ErrorTypes;
import com.david.coupons.exceptions.ServerException;
import com.david.coupons.utils.TokenEmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;

@Service
public class VerificationLogic {

    private JavaMailSender mailSender;
    private IVerificationDal verificationDal;
    private UserLogic userLogic;

    @Autowired
    public VerificationLogic(JavaMailSender mailSender, IVerificationDal verificationDal) {
        this.mailSender = mailSender;
        this.verificationDal = verificationDal;
    }

    @Lazy
    @Autowired
    public void setUserLogic(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    public void sendVerificationEmail(String userEmail, String emailToken) throws MessagingException {
        //todo:
        String verificationUrl = "http://localhost:8080/verification?emailToken=" + emailToken;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(userEmail);
        helper.setSubject("Email Verification");
        helper.setText("Please click the link below to verify your email:\n" + verificationUrl);
        helper.setFrom("davidsaidon5@gmail.com");
        mailSender.send(message);
    }

    @Transactional
    public void createVerificationTokenForUser(UserEntity user) throws MessagingException {

        String emailToken = TokenEmailUtil.generateToken();
        LocalDateTime expiryDate = TokenEmailUtil.calculateExpiryDate();
        VerificationTokenEntity verificationToken = new VerificationTokenEntity(emailToken, user.getId(), expiryDate);

        verificationDal.save(verificationToken);

        // Send email with the token
        this.sendVerificationEmail(user.getUserName(), emailToken);
    }

    @Transactional
    public void verifyEmailToken(String emailToken) throws ServerException {
        if (!verificationDal.isEmailTokenExists(emailToken)) {
            throw new ServerException(ErrorTypes.INVALID_EMAIL_TOKEN);
        }
        int userId = verificationDal.getByUserIdByEmailToken(emailToken);
        this.userLogic.updateValidateStatusById(userId);
        verificationDal.deleteByUserId(userId);
    }
}