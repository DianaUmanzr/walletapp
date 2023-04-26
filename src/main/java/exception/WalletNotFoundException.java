package exception;

public class WalletNotFoundException extends BusinessException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public WalletNotFoundException(BaseCommonError error) {
		super(error);

	}

}
