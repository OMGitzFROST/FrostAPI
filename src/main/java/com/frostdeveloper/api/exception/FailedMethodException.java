package com.frostdeveloper.api.exception;

public class FailedMethodException extends RuntimeException
{
	public FailedMethodException() { super(); }
	
	public FailedMethodException(String message) { super(message); }
	
	public FailedMethodException(String message, Throwable thrown) {super(message, thrown); }
}
