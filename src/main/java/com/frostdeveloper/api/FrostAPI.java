package com.frostdeveloper.api;

import com.frostdeveloper.api.exception.FailedMethodException;
import com.frostdeveloper.api.handler.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A class used to house redundant methods used across multiple projects, this plugin
 * is designed to make development easier for all current and future projects.
 *
 * @author OMGitzFROST
 * @version 1.0
 */
public class FrostAPI
{
	/**
	 * A method used to return an instance of this api class
	 *
	 * @return API instance
	 * @since 1.0
	 */
	@Contract (value = " -> new", pure = true)
	public static @NotNull FrostAPI getInstance() { return new FrostAPI();                                        }
	
	/*
	 * FILE METHODS
	 */
	
	// GET RESOURCES
	
	/**
	 * Returns an input stream for reading the specified resource.
	 *
	 * @param  name
	 *         The resource name
	 *
	 * @return  An input stream for reading the resource; {@code null} if the
	 *          resource could not be found, the resource is in a package that
	 *          is not opened unconditionally, or access to the resource is
	 *          denied by the security manager.
	 *
	 * @throws  NullPointerException If {@code name} is {@code null}
	 *
	 * @since  1.0
	 */
	public InputStream getResource(String name) { return getClass().getClassLoader().getResourceAsStream(name);   }
	
	/**
	 * A method used to return the url for a resource located in this .jar's resources file.
	 *
	 * @param name Target resource file name
	 *
	 * @return Resource URL
	 *
	 * @throws  NullPointerException If {@code name} is {@code null}
	 *
	 * @since 1.0
	 */
	public URL getResourceURL(String name)      { return getClass().getClassLoader().getResource(name);           }
	
	/**
	 * A method used to save a resource from our resource file to a defined location.
	 *
	 * @param location Target location
	 * @param name Resource name
	 *
	 * @since 1.0
	 */
	public void saveResource(@NotNull File location, String name) { saveResource(location, name, false); }
	
	/**
	 * A method used to save a resource from our resource file to a defined location. This method also
	 * allows you to define whether we should replace an existing copy of this resource.
	 *
	 * @param location Target location
	 * @param name Resource name
	 * @param replace Whether an existing resource should be replaced.
	 *
	 * @since 1.0
	 */
	public void saveResource(@NotNull File location, String name, boolean replace)
	{
		try {
			if (replace) {
				Files.copy(getResource(name), location.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}
			else {
				Files.copy(getResource(name), location.toPath());
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * A method used to create all parent directories for a targeted file.
	 *
	 * @param target The file path
	 * @since 1.0
	 */
	public void createParent(@NotNull File target)
	{
		if (!target.getParentFile().exists() && !target.getParentFile().mkdirs()) {
			throw new FailedMethodException("Failed to create the parent directories for this file.");
		}
	}
	
	/* CREATE FILE OBJECTS */
	
	/**
	 * A method used to create a file object from a string path.
	 *
	 * @param path Target path
	 * @return File object from a string path
	 *
	 * @since 1.0
	 */
	public File toFile(String path)             { return toFile(null, path);                              }
	
	/**
	 * A method used to create a file object from a path.
	 *
	 * @param path Target path
	 * @return File object from a string path
	 *
	 * @since 1.0
	 */
	public File toFile(Path path)               { return toFile(null, toString(path));                    }
	
	/**
	 * A method used to create a file object from a string path. This method also allows
	 * you to define the starting point of the path, from a parent directory
	 *
	 * @param parent The starting point to the path.
	 * @param path Target path
	 * @return File object from a string path
	 *
	 * @since 1.0
	 */
	public File toFile(File parent, @NotNull String path)
	{
		if (path.startsWith("/")) {
			path = path.replaceFirst("/", "");
		}
		
		if (parent != null) {
			return new File(parent + File.separator + path);
		}
		return new File(File.separator + path);
	}
	
	/**
	 * A method used to return an object as a string
	 *
	 * @param target Target object
	 * @return An object as a string
	 * @since 1.0
	 */
	public String toString(Object target)   { return String.valueOf(target);                                      }
	
	/**
	 * A method used to return an object as a boolean
	 *
	 * @param target Target object
	 * @return An object as a boolean
	 * @since 1.0
	 */
	public boolean toBoolean(Object target) { return Boolean.parseBoolean(toString(target));                      }
	
	/**
	 * A method used to return an object as an integer
	 *
	 * @param target Target object
	 * @return An object as an integer
	 * @since 1.0
	 */
	public int toInteger(Object target)     { return Integer.parseInt(toString(target));                          }
	
	/**
	 * A method used to return an object as a double
	 *
	 * @param target Target object
	 * @return An object as a double
	 * @since 1.0
	 */
	public double toDouble(Object target)   { return Double.parseDouble(toString(target));                        }
	
	/**
	 * A method used to convert a string into a usable locale, this method pairs with the
	 * Apache Commons library to convert the string. This method
	 * will correct
	 *
	 * @param target Targeted input
	 * @return A valid locale type
	 * @since 1.0
	 */
	public Locale toLocale(@NotNull String target)
	{
		String correction1 = target.replaceAll("-", "_");
		String[] split = correction1.split("_");
		
		Locale locale = null;
		
		if (split.length == 1) {
			locale = new Locale(split[0].toLowerCase());
		}
		if (split.length == 2) {
			locale = new Locale(split[0].toLowerCase(), split[1].toUpperCase());
		}
		if (split.length == 3) {
			locale = new Locale(split[0].toLowerCase(), split[1].toUpperCase(), split[2].toUpperCase());
		}
		
		for (Locale current : Locale.getAvailableLocales()) {
			if (current.equals(locale)) {
				return locale;
			}
		}
		throw new IllegalArgumentException("Failed to convert string to locale, is it a valid locale?");
	}
	
	/*
	 * EXTENSION GETTERS
	 */
	
	/**
	 * A method used to get the file extension from a string representation of a file path
	 *
	 * @param path String representation of file path
	 * @return File extension
	 * @since 1.0
	 */
	public String getExtension(String path)     { return getExtension(path, false);                      }
	
	/**
	 * A method used to get the file extension from a file path
	 *
	 * @param target File path
	 * @return File extension
	 * @since 1.0
	 */
	public String getExtension(File target)     { return getExtension(target, false);                    }
	
	/**
	 * A method used to get the file extension from a file path
	 *
	 * @param path File path
	 * @return File extension
	 * @since 1.0
	 */
	public String getExtension(Path path)       { return getExtension(path, false);                      }
	
	/**
	 * A method used to get the file extension from a string representation of a file path.
	 * Additionally define whether the extension should contain its '.' character.
	 *
	 * @param path String representation of file path
	 * @param dotless Whether our return value should contain a '.' character
	 * @return File extension
	 * @since 1.0
	 */
	public String getExtension(String path, boolean dotless)
	{
		if (dotless) {
			return path.substring(path.lastIndexOf(".")).replace(".", "");
		}
		return path.substring(path.lastIndexOf("."));
	}
	
	/**
	 * A method used to get the file extension from a file path, Additionally define whether
	 * the extension should contain its '.' character.
	 *
	 *
	 * @param target File path
	 * @param dotless Whether our return value should contain a '.' character
	 * @return File extension
	 * @since 1.0
	 */
	public String getExtension(@NotNull File target, boolean dotless)
	{
		String targetPath = target.getPath();
		
		if (dotless) {
			return targetPath.substring(targetPath.lastIndexOf(".")).replace(".", "");
		}
		return targetPath.substring(targetPath.lastIndexOf("."));
	}
	
	/**
	 * A method used to get the file extension from a file path, Additionally define whether
	 * the extension should contain its '.' character.
	 *
	 *
	 * @param path File path
	 * @param dotless Whether our return value should contain a '.' character
	 * @return File extension
	 * @since 1.0
	 */
	public String getExtension(@NotNull Path path, boolean dotless)
	{
		String stringPath = path.toString();
		
		if (dotless) {
			return stringPath.substring(stringPath.lastIndexOf(".")).replace(".", "");
		}
		return stringPath.substring(stringPath.lastIndexOf("."));
	}
	
	/*
	 * GET FILE ATTRIBUTES
	 */
	
	/**
	 * A method used to get the attributes to a file.
	 *
	 * @param target Target file
	 * @return File attributes
	 * @since 1.0
	 */
	public BasicFileAttributes getFileAttribute(@NotNull File target)
	{
		Validate.notNull(target, "Please create the file first.");
		
		try {
			return Files.readAttributes(target.toPath(), BasicFileAttributes.class);
		}
		catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method is used to get the date a file was created.
	 *
	 * @param target The target file
	 * @return The creation date of a file.
	 * @since 1.0
	 */
	public String getCreatedDate(@NotNull File target)
	{
		FileTime time = getFileAttribute(target).creationTime();
		return format(new Date(time.toMillis()));
	}
	
	/**
	 * This method is used to get the date a file was last modified.
	 *
	 * @param target The target file
	 * @return The creation date of a file.
	 * @since 1.0
	 */
	public String getLastModified(@NotNull File target)
	{
		FileTime time = getFileAttribute(target).lastModifiedTime();
		return format(new Date(time.toMillis()));
	}
	
	/**
	 * This method is used to get the date a file was last accessed.
	 *
	 * @param target The target file
	 * @return The creation date of a file.
	 * @since 1.0
	 */
	public String getLastAccessed(@NotNull File target)
	{
		FileTime time = getFileAttribute(target).lastAccessTime();
		return format(new Date(time.toMillis()));
	}
	
	/*
	 * INDEX VERIFIERS
	 */
	
	/**
	 * A method used to return whether an index is a file or not.
	 *
	 * @param target Targeted index
	 * @return Whether our index is a file.
	 * @since 1.0
	 */
	public boolean isFile(@NotNull File target)      { return target.isFile();                                    }
	
	/**
	 * A method used to return whether an index path is a file or not.
	 *
	 * @param path Targeted index
	 * @return Whether our index is a file.
	 * @since 1.0
	 */
	public boolean isFile(String path)               { return toFile(path).isFile();                              }
	
	/**
	 * A method used to return whether an index path is a file or not.
	 *
	 * @param path Targeted index
	 * @return Whether our index is a file.
	 * @since 1.0
	 */
	public boolean isFile(@NotNull Path path)        { return path.toFile().isFile();                             }
	
	/**
	 * A method used to return whether an index is a directory or not.
	 *
	 * @param target Targeted index
	 * @return Whether our index is a file.
	 * @since 1.0
	 */
	public boolean isDirectory(@NotNull File target) { return target.isDirectory();                               }
	
	/**
	 * A method used to return whether an index path is a directory or not.
	 *
	 * @param path Targeted index
	 * @return Whether our index is a file.
	 * @since 1.0
	 */
	public boolean isDirectory(String path)          { return toFile(path).isDirectory();                         }
	
	/**
	 * A method used to return whether an index path is a directory or not.
	 *
	 * @param path Targeted index
	 * @return Whether our index is a file.
	 * @since 1.0
	 */
	public boolean isDirectory(@NotNull Path path)   { return path.toFile().isDirectory();                        }
	
	/*
	 * RENAMING FILES
	 */
	
	/**
	 * A method used to rename the parent directory of an index.
	 *
	 * @param target Target file
	 * @param name New parent name
	 * @since 1.0
	 */
	public void renameParent(@NotNull File target, String name) { renameIndex(target.getParentFile(), name);      }
	
	/**
	 * A method used to rename an index
	 *
	 * @param target Target file
	 * @param name New name
	 * @since 1.0
	 */
	public void renameIndex(File target, String name)
	{
		Validate.notNull(target, "The target file cannot be null!");
		Validate.notNull(name, "The new file name cannot be null");
		
		if (!target.exists()) {
			return;
		}
		
		if (!target.getName().equals(name) && !target.renameTo(toFile(target.getParentFile(), name))) {
			if (!target.exists()) {
				throw new FailedMethodException("Failed to rename file");
			}
		}
	}
	
	/*
	 * TIME METHODS
	 */
	
	/**
	 * A method used to return the current time
	 *
	 * @return Current Time
	 * @since 1.0
	 */
	public Date getToday() { return new Date(); }
	
	/**
	 * A method used to return the current date and time as a string
	 *
	 * @return A string representing the current date.
	 * @since 1.0
	 */
	public String getTodayAsString() { return toString(getToday()); }
	
	/**
	 * A method used to return the current date and time as a string
	 *
	 * @param pattern Date pattern
	 *
	 * @return A string representing the current date.
	 * @since 1.0
	 */
	public String getTodayAsString(String pattern)
	{
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(getToday());
	}
	
	/**
	 * A method used in a runnable. It's used to return the amount of time required to complete the delay in seconds
	 *
	 * @param amount Amount of seconds.
	 * @return Delay in seconds
	 * @since 1.0
	 */
	public int toSecond(Object amount) { return toInteger(amount);                                                }
	
	/**
	 * A method used in a runnable. It's used to return the amount of time required to complete the delay in minutes
	 *
	 * @param amount Amount of minutes.
	 * @return Delay in minutes
	 * @since 1.0
	 */
	public int toMinute(Object amount) { return toSecond(60) * toInteger(amount);                        }
	
	/**
	 * A method used in a runnable. It's used to return the amount of time required to complete the delay in hours
	 *
	 * @param amount Amount of hours.
	 * @return Delay in hours
	 * @since 1.0
	 */
	public int toHour(Object amount)   { return toMinute(60) * toInteger(amount);                        }
	
	/**
	 * A method used in a runnable. It's used to return the amount of time required to complete the delay in days
	 *
	 * @param amount Amount of days.
	 * @return Delay in days
	 * @since 1.0
	 */
	public int toDay(Object amount)    { return toHour(24) * toInteger(amount);                          }
	
	/**
	 * A method used to turn a string into a valid time, and calculate the amount required.
	 *
	 * @param input String input
	 * @return Requested duration
	 * @since 1.0
	 */
	public int toTime(String input)
	{
		input = input.toLowerCase();
		String[] description = {"s","m","h","d","y","second","minute","hour","day","year","seconds","minutes","hours","days","years"};
		String identifier = null;
		int time = -1;
		
		for (String s : description) {
			if (input.contains(s)) {
				identifier = s;
				time = toInteger(input.replace(" ", "").split(identifier)[0]);
			}
		}
		
		switch (Objects.requireNonNull(identifier)){
			case "s":
			case "second":
			case "seconds":
				return toSecond(time);
			case "m":
			case "minute":
			case "minutes":
				return toMinute(time);
			case "h":
			case "hour":
			case "hours":
				return toHour(time);
			case "d":
			case "day":
			case "days":
				return toDay(time);
		}
		throw new IllegalArgumentException("Invalid time: " + input);
	}
	
	/*
	 * FORMATTERS
	 */
	
	/**
	 * A method used to format a date to a pre-defined pattern
	 *
	 * @param date Target date
	 * @return Formatted date
	 * @since 1.0
	 */
	public String format(Date date)
	{
		String pattern = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}
	
	/**
	 * A method used to format a date to a specific pattern
	 *
	 * @param pattern Desired pattern
	 * @param date Target date
	 * @return Formatted date
	 * @since 1.0
	 */
	public String format(String pattern, Date date)
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}
	
	/**
	 * A method used to format a method to include an array of parameters in the message
	 * using a number format variable (i.e {0}).
	 *
	 * @param input Target message
	 * @param param Optional params
	 * @return Formatted message
	 * @since 1.0
	 */
	public @NotNull String format(String input, Object... param) { return MessageFormat.format(input, param);    }
	
	/**
	 * A method used to format a string and translate Bukkit color codes
	 *
	 * @param stripColor Condition to strip color codes
	 * @param input Target message
	 * @param param Optional params
	 * @return Formatted message
	 * @since 1.0
	 */
	public @NotNull String format(boolean stripColor, String input, Object... param)
	{
		if (stripColor) {
			input = stripColor(input);
		}
		return format(input, param);
	}
	
	/*
	 * STRIP COLOR
	 */
	
	/**
	 * A method used to strip color codes from an input, it will remove any color codes present.
	 *
	 * @param input Target input
	 * @return Colorless output.
	 * @since 1.0
	 */
	public @NotNull String stripColor(String input)
	{
		input = input.replaceAll("§", "&");
		
		if (input.contains("&")) {
			input = input.replace("&a", "");
			input = input.replace("&b", "");
			input = input.replace("&c", "");
			input = input.replace("&d", "");
			input = input.replace("&e", "");
			input = input.replace("&f", "");
			
			input = input.replace("&0", "");
			input = input.replace("&1", "");
			input = input.replace("&2", "");
			input = input.replace("&3", "");
			input = input.replace("&4", "");
			input = input.replace("&5", "");
			input = input.replace("&6", "");
			input = input.replace("&7", "");
			input = input.replace("&8", "");
			input = input.replace("&9", "");
			
			input = input.replace("&r", "");
		}
		return input;
	}
	
	/**
	 * A method used to remove color codes from any given string list this method
	 * will iterate through the list and individually remove color codes.
	 *
	 * @param list Target list
	 * @return Colorless list.
	 * @since 1.0
	 */
	public @NotNull List<String> stripColor(@NotNull List<String> list)
	{
		return list.stream().map(this::stripColor).collect(Collectors.toList());
	}
}