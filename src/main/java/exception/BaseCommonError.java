package exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseCommonError {
	
	private String code;
	private String message;
	
	private BaseCommonError() {
		super();
	}
	
	public static BaseCommonError registerError(String code,String message) {
		return new BaseCommonError(code, message);
	}	

	public BaseCommonError(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	@Override
	public String toString() {
		return "BaseCommonError [code=" + code + ", message=" + message + "]";
	}

}
