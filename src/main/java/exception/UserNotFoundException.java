package exception;

public class UserNotFoundException extends BusinessException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(BaseCommonError error) {
		super(error);

	}

}
