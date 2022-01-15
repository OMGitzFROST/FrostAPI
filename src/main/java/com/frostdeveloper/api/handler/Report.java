package com.frostdeveloper.api.handler;

import com.frostdeveloper.api.FrostAPI;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

/**
 * A class used to handle exception reporting and logs our caught exception to a file.
 *
 * @author OMGitzFROST
 * @since 1.0
 */
public class Report
{
	// CLASS INSTANCES
	private final FrostAPI api = FrostAPI.getInstance();
	
	// CLASS SPECIFIC OBJECTS
	private final File output;

	/**
	 * A constructor used to establish our file output for our report log.
	 *
	 * @param output Output file
	 * @since 1.0
	 */
	public Report(File output)                    { this.output = output;           }

	/**
	 * A constructor used to establish our file output for our report log.
	 *
	 * @param path Output file path
	 * @since 1.0
	 */
	public Report(String path)                    { this.output = api.toFile(path); }

	/**
	 * A constructor used to establish our file output for our report log.
	 *
	 * @param path Output file path
	 * @since 1.0
	 */
	public Report(Path path)                      { this.output = api.toFile(path); }
	
	/**
	 * A method used to create our report with an option to specify if the stacktrace should
	 * be silent. A silent stacktrace simply means that the stack will not print the console
	 * but will still appear in the report file. And vice versa, if not silent the exception will
	 * print in both the report and the console.
	 *
	 * @param thrown Exception caught
	 * @param silent Whether the stacktrace should print silently.
	 * @since 1.0
	 *
	 * @see #create(Throwable)
	 */
	public void create(@NotNull Throwable thrown, boolean silent)
	{
		Validate.notNull(output, "Please specify an output file for the report");
		api.createParent(output);
		
		try {
			FileWriter writer = new FileWriter(output, true);
			PrintWriter printer = new PrintWriter(writer);
			
			printer.println("Date Created: " + api.getTodayAsString());
			printer.println("");
			
			if (thrown.getCause() != null) {
				printer.println("Message: " + thrown.getCause().getMessage());
			}
			else {
				printer.println("Message: " + thrown.getMessage());
			}
			
			printer.println("Location: " + thrown.getStackTrace()[0].getFileName());
			printer.println("Method: " + thrown.getStackTrace()[0].getMethodName());
			printer.println("At Line: " + thrown.getStackTrace()[0].getLineNumber());
			printer.println("");
			printer.println("Exception:");
			thrown.printStackTrace(printer);
			writer.close();
			printer.close();
			
			if (!silent) {
				thrown.printStackTrace();
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * A method used to create our report. By default, this method will not print the exception's stacktrace
	 * silently, to print silently; please see {@link #create(Throwable, boolean)}.
	 *
	 * @see #create(Throwable, boolean)
	 *
	 * @param thrown Exception caught
	 * @since 1.0
	 */
	public void create(@NotNull Throwable thrown) { create(thrown, false);          }
}
