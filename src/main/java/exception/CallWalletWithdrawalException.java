package exception;

public class CallWalletWithdrawalException extends BusinessException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CallWalletWithdrawalException(BaseCommonError error) {
		super(error);

	}

}
