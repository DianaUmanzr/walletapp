package exception;

public class CallWalletTransactionException extends BusinessException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CallWalletTransactionException(BaseCommonError error) {
		super(error);

	}

}
