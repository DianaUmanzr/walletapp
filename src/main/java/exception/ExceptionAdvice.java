package exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse<Object> processUserNotFound(UserNotFoundException ex) {
        log.error("processUserNotFound",ex);
        return new ErrorResponse<>(
                CommonErrors.USER_001_NOT_FOUND.getCode(),
                CommonErrors.USER_001_NOT_FOUND.getMessage());
    }

    /*@ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse<Object> processCannotGetJdbcConnectionException(CannotGetJdbcConnectionException ex) {
    	log.error("processCannotGetJdbcConnectionException",ex);
        ErrorResponse<Object> response = new ErrorResponse<>(
        		CommonErrors.INF_001_CONNECTION_TIMEOUT.getCode(), 
        		CommonErrors.INF_001_CONNECTION_TIMEOUT.getMessage());
        return response;
    }*/
    
    @ExceptionHandler(BusinessNotEnoughBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse<Object> processNotEnoughBalance(BusinessNotEnoughBalanceException ex) {
    	log.error("processNotEnoughBalance",ex);
        ErrorResponse<Object> response = new ErrorResponse<>(
        		CommonErrors.BLG_001_NOT_ENOUGH_BALANCE.getCode(), 
        		CommonErrors.BLG_001_NOT_ENOUGH_BALANCE.getMessage());
        return response;
    }

    @ExceptionHandler(BankAccountExistedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse<Object> processBankAccountAlreadyExisted(BankAccountExistedException ex) {
        log.error("processNotEnoughBalance",ex);
        ErrorResponse<Object> response = new ErrorResponse<>(
                CommonErrors.BANK_001_EXISTED.getCode(),
                CommonErrors.BANK_001_EXISTED.getMessage());
        return response;
    }
}