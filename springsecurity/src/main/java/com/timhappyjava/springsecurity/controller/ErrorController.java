package com.timhappyjava.springsecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.security.access.AccessDeniedException;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class ErrorController{

    private static Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler(value 
    	      = {Exception.class, IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exceptionUnknown(final Throwable throwable, final Model model) {
        logger.error("Exception 500", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Unknown error");
        model.addAttribute("errorMessage: ", errorMessage);
        return "deny";
    }
    
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value 
  	      = {EntityNotFoundException.class})
    public String exception404(final Throwable throwable, final Model model) {
        logger.error("Exception 404", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "404 Not Found");
        model.addAttribute("errorMessage: ", errorMessage);
        return "deny";
    }
    
    @ExceptionHandler(value = { AccessDeniedException.class, AuthenticationException.class })
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public String exceptionSecurity(final Throwable throwable, final Model model) {
        logger.error("Exception Security", throwable);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Access Deny");
        model.addAttribute("errorMessage: ", errorMessage);
        return "deny";
    }
    //@ResponseBody
    /*public RestError handleAuthenticationException(Exception ex) {

        int errorCode = AegisErrorCode.GenericAuthenticationError;
        if(ex instanceof AegisException) {
            errorCode = ((AegisException)ex).getCode();
        }

        RestError re = new RestError(
            HttpStatus.UNAUTHORIZED,
            errorCode, 
            "...",
            ex.getMessage());

        return re;
    }*/

}