package exception;

public class ExceptionUtils {
	
	private ExceptionUtils() {
		super();
	}

	public static void throwBusinessException(BaseCommonError baseCommonError) {
		throw new BusinessException(baseCommonError);
	}
	
	public static void throwBusinessBusinessNotEnoughBalanceException(BaseCommonError baseCommonError) {
		throw new BusinessNotEnoughBalanceException(baseCommonError);
	}

	public static void throwUserNotFoundException(BaseCommonError baseCommonError) {
		throw new UserNotFoundException(baseCommonError);
	}

	public static void throwBankAccountExistedException(BaseCommonError baseCommonError) {
		throw new BankAccountExistedException(baseCommonError);
	}

	public static void throwWalletNotFoundException(BaseCommonError baseCommonError) {
		throw new WalletNotFoundException(baseCommonError);
	}
}
