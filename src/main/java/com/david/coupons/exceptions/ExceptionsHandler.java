package com.david.coupons.exceptions;


import com.david.coupons.dto.ErrorBean;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.david.coupons.enums.ErrorTypes;

import javax.servlet.http.HttpServletResponse;

// Exception handler class

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler
    // Variable name is throwable in order to remember that it handles Exception and Error
    public ErrorBean toResponse(Throwable throwable , HttpServletResponse response) {

        //	ErrorBean errorBean;
        if(throwable instanceof ServerException) {
            ServerException serverException = (ServerException) throwable;

            ErrorTypes errorType = serverException.getErrorType();
            int errorNumber = errorType.getInternalError();
            int httpStatus = errorType.getHttpStatus();
            response.setStatus(httpStatus);
            String errorMessage = errorType.getClientErrorMessage();


            //ErrorTypes errorType, Exception e,String message)
            ErrorBean errorBean = new ErrorBean(errorNumber, errorMessage);
            if(serverException.getErrorType().isShowStackTrace()) {
                serverException.printStackTrace();
            }

            return errorBean;
        }

        String errorMessage = throwable.getMessage();
        ErrorBean errorBean = new ErrorBean(604, "General error");
        throwable.printStackTrace();
        response.setStatus(500);
        return errorBean;
    }

}
