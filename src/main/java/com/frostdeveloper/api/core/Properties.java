package com.frostdeveloper.api.core;

import com.frostdeveloper.api.FrostAPI;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * The Properties class is designed to add new methods not seen in the {@link java.util.Properties}
 * class, makes using properties files much easier and extends Javas properties capabilities.
 * <p/>
 *
 * @apiNote By default, this class does not order any properties added to the Hashtable.<br><br/>
 * To order, remember to add a boolean value to the class constructor.
 *
 * @author OMGitzFROST
 * @since 1.0.0
 */
public class Properties
{
	// CLASS INSTANCES
	private final FrostAPI api = FrostAPI.getInstance();
	
	// CLASS SPECIFIC OBJECTS
	private final java.util.Properties prop;
	private boolean ordered;
	
	/**
	 * Creates a new property list with no default values.
	 *
	 * @implNote Properties may be added in a random order as this constructor
	 * does not maintain its order inherently.
	 *
	 * @since 1.0.0
	 */
	public Properties()                      { prop = new java.util.Properties();  }
	
	/**
	 * Creates a new property list with no default values.
	 *
	 * @implNote If true, Properties are added in alphabetical order.
	 *
	 * @param ordered Determines whether the property list should be ordered alphabetically.
	 * @since 1.0.0
	 */
	public Properties(boolean ordered)
	{
		this.ordered = ordered;
		
		if (ordered) {
			this.prop = new java.util.Properties() {
				@Override
				public synchronized Set<Map.Entry<Object, Object>> entrySet() {
					return Collections.synchronizedSet(
							super.entrySet().stream().sorted(Comparator.comparing(e -> e.getKey().toString())).collect(Collectors.toCollection(LinkedHashSet::new)));
				}
			};
		}
		else {
			this.prop = new java.util.Properties();
		}
	}
	
	/**
	 * A method used to write our properties list (keys and element pairs), in a format
	 * suitable for property loading. This method will include your comment at the top of the
	 * file when printing.
	 *
	 * @see #store(File)
	 *
	 * @throws ClassCastException if this {@code Properties} object contains any keys or values that are not {@code Strings}.
	 * @throws NullPointerException if {@code out} is null.
	 *
	 * @param targetFile Storing location.
	 * @param comment A description of the property list.
	 * @since 1.0.0
	 */
	public void store(@NotNull File targetFile, String comment)
	{
		try(FileOutputStream outputStream = new FileOutputStream(targetFile)) {
			if (targetFile.getParentFile().exists() || targetFile.getParentFile().mkdirs()) {
				prop.store(outputStream, comment);
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * A method used to write our properties list (keys and element pairs), in a format
	 * suitable for property loading. This method will use Java's default comments
	 * when storing to file.
	 *
	 * @see #store(File, String)
	 *
	 * @throws ClassCastException if this {@code Properties} object contains any keys or values that are not {@code Strings}.
	 * @throws NullPointerException if {@code out} is null.
	 *
	 * @param targetFile Storing location.
	 * @since 1.0.0
	 */
	public void store(@NotNull File targetFile)
	{
		if (targetFile.getParentFile().exists() || targetFile.getParentFile().mkdirs()) {
			try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
				prop.store(outputStream, null);
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * A method used to read a property list (keys and element pairs). The input stream is
	 * closed by this method after it returns.
	 *
	 * @throws IllegalArgumentException if the input stream contains a malformed Unicode escape sequence.
	 * @throws NullPointerException if {@code inStream} is null.
	 *
	 * @param targetFile File being read.
	 * @since 1.0.0
	 */
	public void load(@NotNull File targetFile)
	{
		try {
			FileInputStream inputStream = new FileInputStream(targetFile);
			prop.load(inputStream);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * A method used to read a property list (keys and element pairs). The input stream is
	 * closed by this method after it returns.
	 *
	 * @throws IllegalArgumentException if the input stream contains a malformed Unicode escape sequence.
	 * @throws NullPointerException if {@code inStream} is null.
	 *
	 * @param inputStream The input stream
	 * @since 1.0.0
	 */
	public void load(InputStream inputStream)
	{
		try {
			prop.load(inputStream);
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * A method used to set a value to a property inside our property list if it
	 * does not already exist.
	 *
	 * @param key The target key.
	 * @param value The desired value.
	 * @since 1.0.0
	 */
	public void setProperty(String key, Object value) { prop.setProperty(key, api.toString(value)); }
	
	/**
	 * A method used to search for a specific property key inside our property list, If the
	 * key is not found, this method will return null
	 *
	 * @apiNote To avoid return the default value, remember to load a property file using {@link #load(File)}
	 * or load from an input stream using {@link #load(InputStream)}.
	 *
	 * @see #setProperty(String, Object)
	 *
	 * @param key The target key.
	 * @return The value in the property list.
	 * @since 1.0.0
	 */
	public String getProperty(String key)    { return prop.getProperty(key);       }
	
	/**
	 * A method used to search for a specific property key inside our property list, If the
	 * key is not found, this method will return the specified default property value.
	 *
	 * @apiNote To avoid return the default value, remember to load a property file using {@link #load(File)}
	 * or load from an input stream using {@link #load(InputStream)}.
	 *
	 * @see #setProperty(String, Object)
	 *
	 * @param key The target key.
	 * @param def A default value.
	 * @return The value in the property list.
	 * @since 1.0.0
	 */
	public String getProperty(String key, Object def) { return prop.getProperty(key, api.toString(def)); }
	
	/**
	 * Tests if the specified object is a key in this table.
	 *
	 * @param  key possible key
	 * @return {@code true} if and only if the specified object
	 *         is a key in this table, as determined by the
	 *         {@code equals} method; {@code false} otherwise
	 * @throws NullPointerException if the specified key is null
	 * @since 1.0.0
	 */
	public boolean containsKey(Object key)   { return prop.containsKey(key);       }
	
	/**
	 * A method used to return a set of string property names for a property map.
	 *
	 * @return Set of string property names
	 * @since 1.0.0
	 */
	public Set<String> stringPropertyNames() { return prop.stringPropertyNames();  }
	
	/**
	 * Returns {@code true} if this map contains no key-value mappings.
	 *
	 * @return {@code true} if this map contains no key-value mappings
	 * @since 1.0.0
	 */
	public boolean isEmpty()                 { return prop.isEmpty();              }
	
	/**
	 * A method used to return whether a properties are to be in alphabetical order.
	 *
	 * @return Whether in alphabetical order.
	 * @since 1.0.0
	 */
	public boolean isOrdered()               { return ordered;                     }
	
	/**
	 * Clears this hashtable so that it contains no keys.
	 *
	 * @since 1.0.0
	 */
	public void clear()                      { prop.clear();                       }
	
	/**
	 * Returns a {@link Set} view of the keys contained in this map.
	 * The set is backed by the map, so changes to the map are
	 * reflected in the set, and vice-versa.  If the map is modified
	 * while an iteration over the set is in progress (except through
	 * the iterators own <tt>remove</tt> operation), the results of
	 * the iteration are undefined.  The set supports element removal,
	 * which removes the corresponding mapping from the map, via the
	 * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
	 * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
	 * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
	 * operations.
	 *
	 * @since 1.3.0
	 */
	public Set<Object> keySet()              { return prop.keySet();               }
	
	/**
	 * Returns an enumeration of the keys in this hashtable.
	 *
	 * @return  an enumeration of the keys in this hashtable.
	 * @see     Enumeration
	 * @see     #keySet()
	 * @see     Map
	 * @since 1.3.0
	 */
	public Enumeration<Object> keys()        { return prop.keys();                 }
}