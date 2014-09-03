package com.huatek.unicorn.base.dbaccess.exception;

public class DbaccessSyntaxException extends RuntimeException {

	private String errorStatement;
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4866599480147169658L;

	public DbaccessSyntaxException(String message) {
		super(message);
	}
	
	public DbaccessSyntaxException(String message, String errorStatement) {
		this(message);
		this.errorStatement = errorStatement;
	}

	public String getErrorStatement() {
		return errorStatement;
	}

	public void setErrorStatement(String errorStatement) {
		this.errorStatement = errorStatement;
	}
	
}
