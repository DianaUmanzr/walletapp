package exception;

public class BankAccountExistedException extends BusinessException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BankAccountExistedException(BaseCommonError error) {
        super(error);
    }
}
