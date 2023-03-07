package springboot.mssql.demo.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ResponseAPI {
	private Object content;
	private List<String> messages = new ArrayList<String>();;
	private boolean status;
	private String error;
	
	public static ResponseAPI fail(String error) {
		return new ResponseAPI(false, error, null);
	}

	public static ResponseAPI fail(List<String> error) {
		return new ResponseAPI(false, error, null);
	}

	public static ResponseAPI success() {
		return new ResponseAPI(true, "SUCCESS", null);
	}

	public static ResponseAPI successMsg(String message) {
		return new ResponseAPI(true, message, null);
	}
	
	public static ResponseAPI success(Object content) {
		return new ResponseAPI(true, "SUCCESS_MSG", content);
	}
	
	public ResponseAPI(boolean status, String message, Object content) {
		this.status = status;
		this.messages.add(message);
		this.content = content;
	}
	
	public ResponseAPI(boolean status, List<String> messages, Object content) {
		this.status = status;
		this.messages = messages;
		this.content = content;
	}
	
	public ResponseAPI(boolean status, String messages) {
		this.status = status;
		this.messages.add(messages);
	}
	
	public ResponseAPI(boolean status, List<String> messages) {
		this.status = status;
		this.messages = messages;
	}
}
