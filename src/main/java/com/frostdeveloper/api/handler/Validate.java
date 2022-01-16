package com.frostdeveloper.api.handler;

import com.frostdeveloper.api.FrostAPI;
import org.jetbrains.annotations.NotNull;

/**
 * A class used to house a variety of method that will validate objects throughout any project.
 *
 * @author OMGitzFROST
 * @since 1.0.0
 */
public class Validate
{
	// CLASS INSTANCES
	private static final FrostAPI api = FrostAPI.getInstance();
	
	/**
	 * A method used to validate that an object is not null.
	 *
	 * @param object Targeted object
	 * @since 1.0.0
	 */
	public static void notNull(Object object) { notNull(object, null);}
	
	/**
	 * A method used to validate that an object is not null.
	 *
	 * @throws IllegalArgumentException Thrown if the object does not equal null
	 *
	 * @param object Targeted object
	 * @param message The exception message
	 * @since 1.0.0
	 */
	public static void notNull(Object object, String message)
	{
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * A method used to validate that an object is equal to true, if not this method will throw
	 * an exception with the specified message.
	 *
	 * @throws IllegalArgumentException Thrown if tbe object is not true.
	 *
	 * @param object Targeted object
	 * @param message The exception message
	 * @since 1.0.0
	 */
	public static void isTrue(Object object, String message)
	{
		if (!api.toBoolean(object)) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * A method used to validate that an object is equal to true, if not this method will throw
	 * an exception.
	 *
	 * @throws IllegalArgumentException Thrown if tbe object is not true.
	 *
	 * @param object Targeted object
	 * @since 1.0.0
	 */
	public static void isTrue(Object object)
	{
		if (!api.toBoolean(object)) {
			throw new IllegalArgumentException("The object is not true!");
		}
	}
	
	
	/**
	 * A method used to validate that an object is equal to false, if not this method will throw
	 * an exception with the specified message.
	 *
	 * @throws IllegalArgumentException Thrown if tbe object is not false.
	 *
	 * @param object Targeted object
	 * @param message The exception message
	 * @since 1.0
	 */
	public static void isFalse(Object object, String message)
	{
		if (api.toBoolean(object)) {
			throw new IllegalArgumentException(message);
		}
	}
	
	/**
	 * A method used to validate that an object is equal to false, if not this method will throw
	 * an exception.
	 *
	 * @throws IllegalArgumentException Thrown if tbe object is not false.
	 *
	 * @param object Targeted object
	 * @since 1.0
	 */
	public static void isFalse(Object object)
	{
		if (api.toBoolean(object)) {
			throw new IllegalArgumentException("The object is true!");
		}
	}
}
