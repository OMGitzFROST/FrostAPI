package com.frostdeveloper.api.exception;

import com.frostdeveloper.api.FrostAPI;

import java.text.MessageFormat;

/**
 * An exception caught when a file is not defined.
 *
 * @author OMGitzFROST
 * @since 1.0
 */
public class UndefinedFileException extends IllegalArgumentException
{
	private final FrostAPI api = FrostAPI.getInstance();
	
	/**
	 * An exception caught when a file is not defined.
	 *
	 * @since 1.0
	 */
	public UndefinedFileException()                                { super();                                     }
	
	/**
	 * An exception caught when a file is not defined.
	 *
	 * @param message Exception message
	 *
	 * @since 1.0
	 */
	public UndefinedFileException(String message)                  { super(message);                              }
	
	/**
	 * An exception caught when a file is not defined.
	 *
	 * @param message Exception message
	 * @param param Optional parameters
	 *
	 * @since 1.0
	 */
	public UndefinedFileException(String message, Object... param) { super(MessageFormat.format(message, param)); }
}
