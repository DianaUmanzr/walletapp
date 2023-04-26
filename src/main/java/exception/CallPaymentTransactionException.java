package exception;

public class CallPaymentTransactionException extends BusinessException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public CallPaymentTransactionException(BaseCommonError error) {
		super(error);

	}

}
