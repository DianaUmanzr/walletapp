package exception;

import static exception.BaseCommonError.registerError;

public class CommonErrors {
	
	public static final BaseCommonError INF_001_CONNECTION_TIMEOUT = registerError("INF-001", "Could not connect to database");
	public static final BaseCommonError BLG_001_NOT_ENOUGH_BALANCE = registerError("BLG-001", "Not enough balance in your account");
	public static final BaseCommonError USER_001_NOT_FOUND = registerError("USER_001", "User not found");
	public static final BaseCommonError BANK_001_EXISTED = registerError("BANK_001", "Bank account already exists");
	public static final BaseCommonError WALLET_001_NOT_EXIST = registerError("WALLET_001", "Wallet does not exist for the user");
	public static final BaseCommonError CALL_WALLET_WD_001_ERROR = registerError("WALLET_WD_001", "Error to call /wallets/balance endpoint");
	public static final BaseCommonError CALL_WALLET_T_001_ERROR = registerError("CALL_WALLET_T_001", "Error to call /wallets/transactions endpoint");
	public static final BaseCommonError CALL_PAYMENT_T_001_ERROR = registerError("CALL_PAYMENT_T_001", "Error to call /api/v1/payments endpoint");

}
