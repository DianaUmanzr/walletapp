package exception;

public class BusinessNotEnoughBalanceException extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessNotEnoughBalanceException(BaseCommonError error) {
		super(error);

	}

}
