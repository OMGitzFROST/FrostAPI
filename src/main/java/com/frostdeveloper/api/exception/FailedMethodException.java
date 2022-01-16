package com.frostdeveloper.api.exception;

import java.text.MessageFormat;

/**
 * An exception used when a method failed to execute its code correctly.
 *
 * @author OMGitzFROST
 * @since 1.0.0
 */
public class FailedMethodException extends RuntimeException
{
	/**
	 * An exception caught when a method failed to execute correctly.
	 *
	 * @since 1.0.0
	 */
	public FailedMethodException()                                 { super();                                     }
	
	/**
	 * An exception caught when a method failed to execute correctly. This method
	 * allows you to specify a message when caught.
	 *
	 * @param message Exception message
	 * @since 1.0.0
	 */
	public FailedMethodException(String message)                   { super(message);                              }
	
	/**
	 * An exception caught when a method failed to execute correctly.
	 *
	 * @param message Exception message
	 * @param thrown Original exception caught
	 * @since 1.0.0
	 */
	public FailedMethodException(String message, Throwable thrown) { super(message, thrown);                      }
	
	/**
	 * An exception caught when a method failed to execute correctly.
	 *
	 * @param message Exception message
	 * @param param Optional parameters
	 * @since 1.0.0
	 */
	public FailedMethodException(String message, Object... param)  { super(MessageFormat.format(message, param)); }
	
	/**
	 * An exception caught when a method failed to execute correctly.
	 *
	 * @param message Exception message
	 * @param thrown Original exception caught
	 * @param param Optional parameters
	 * @since 1.0.0
	 */
	public FailedMethodException(String message, Throwable thrown, Object... param)
	{
		super(MessageFormat.format(message, param), thrown);
	}
}
