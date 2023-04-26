package exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{

	private BaseCommonError error;

	public BaseException(BaseCommonError error) {
		super(error.toString());
		this.error = error;
	}

}
