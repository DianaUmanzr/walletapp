package exception;

import com.api.wallet.entity.Wallet;
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
        return new ErrorResponse<>(
                CommonErrors.USER_001_NOT_FOUND.getCode(),
                CommonErrors.USER_001_NOT_FOUND.getMessage());
    }

    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse<Object> processWalletNotFound(WalletNotFoundException ex) {
        return new ErrorResponse<>(
                CommonErrors.WALLET_001_NOT_EXIST.getCode(),
                CommonErrors.WALLET_001_NOT_EXIST.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse<Object> processCannotGetJdbcConnectionException(CannotGetJdbcConnectionException ex) {
        return new ErrorResponse<>(
        		CommonErrors.INF_001_CONNECTION_TIMEOUT.getCode(), 
        		CommonErrors.INF_001_CONNECTION_TIMEOUT.getMessage());
    }
    
    @ExceptionHandler(BusinessNotEnoughBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse<Object> processNotEnoughBalance(BusinessNotEnoughBalanceException ex) {
        return new ErrorResponse<>(
        		CommonErrors.BLG_001_NOT_ENOUGH_BALANCE.getCode(), 
        		CommonErrors.BLG_001_NOT_ENOUGH_BALANCE.getMessage());
    }

    @ExceptionHandler(BankAccountExistedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse<Object> processBankAccountAlreadyExisted(BankAccountExistedException ex) {
        return new ErrorResponse<>(
                CommonErrors.BANK_001_EXISTED.getCode(),
                CommonErrors.BANK_001_EXISTED.getMessage());
    }
}