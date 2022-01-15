package com.frostdeveloper.api.utility;

import org.jetbrains.annotations.NotNull;

/**
 * A class used to create methods to get instance of console colors.
 *
 * @author OMGitzFROST
 * @since 1.0
 */
public enum ConsoleColor {
	
	// COLOR END STRING, COLOR RESET
	RESET("\033[0m"),                           // RESET
	
	// REGULAR COLORS. NORMAL COLOR, NO BOLD, BACKGROUND COLOR ETC.
	BLACK("\033[0;30m"),                        // BLACK
	RED("\033[0;31m"),                          // RED
	GREEN("\033[0;32m"),                        // GREEN
	YELLOW("\033[0;33m"),                       // YELLOW
	BLUE("\033[0;34m"),                         // BLUE
	MAGENTA("\033[0;35m"),                      // MAGENTA
	CYAN("\033[0;36m"),                         // CYAN
	WHITE("\033[0;37m"),                        // WHITE
	
	// BOLD
	BLACK_BOLD("\033[1;30m"),                   // BLACK
	RED_BOLD("\033[1;31m"),                     // RED
	GREEN_BOLD("\033[1;32m"),                   // GREEN
	YELLOW_BOLD("\033[1;33m"),                  // YELLOW
	BLUE_BOLD("\033[1;34m"),                    // BLUE
	MAGENTA_BOLD("\033[1;35m"),                 // MAGENTA
	CYAN_BOLD("\033[1;36m"),                    // CYAN
	WHITE_BOLD("\033[1;37m"),                   // WHITE
	
	// UNDERLINE
	BLACK_UNDERLINED("\033[4;30m"),             // BLACK
	RED_UNDERLINED("\033[4;31m"),               // RED
	GREEN_UNDERLINED("\033[4;32m"),             // GREEN
	YELLOW_UNDERLINED("\033[4;33m"),            // YELLOW
	BLUE_UNDERLINED("\033[4;34m"),              // BLUE
	MAGENTA_UNDERLINED("\033[4;35m"),           // MAGENTA
	CYAN_UNDERLINED("\033[4;36m"),              // CYAN
	WHITE_UNDERLINED("\033[4;37m"),             // WHITE
	
	// BACKGROUND
	BLACK_BACKGROUND("\033[40m"),               // BLACK
	RED_BACKGROUND("\033[41m"),                 // RED
	GREEN_BACKGROUND("\033[42m"),               // GREEN
	YELLOW_BACKGROUND("\033[43m"),              // YELLOW
	BLUE_BACKGROUND("\033[44m"),                // BLUE
	MAGENTA_BACKGROUND("\033[45m"),             // MAGENTA
	CYAN_BACKGROUND("\033[46m"),                // CYAN
	WHITE_BACKGROUND("\033[47m"),               // WHITE
	
	// HIGH INTENSITY
	BLACK_BRIGHT("\033[0;90m"),                 // BLACK
	RED_BRIGHT("\033[0;91m"),                   // RED
	GREEN_BRIGHT("\033[0;92m"),                 // GREEN
	YELLOW_BRIGHT("\033[0;93m"),                // YELLOW
	BLUE_BRIGHT("\033[0;94m"),                  // BLUE
	MAGENTA_BRIGHT("\033[0;95m"),               // MAGENTA
	CYAN_BRIGHT("\033[0;96m"),                  // CYAN
	WHITE_BRIGHT("\033[0;97m"),                 // WHITE
	
	// BOLD HIGH INTENSITY
	BLACK_BOLD_BRIGHT("\033[1;90m"),            // BLACK
	RED_BOLD_BRIGHT("\033[1;91m"),              // RED
	GREEN_BOLD_BRIGHT("\033[1;92m"),            // GREEN
	YELLOW_BOLD_BRIGHT("\033[1;93m"),           // YELLOW
	BLUE_BOLD_BRIGHT("\033[1;94m"),             // BLUE
	MAGENTA_BOLD_BRIGHT("\033[1;95m"),          // MAGENTA
	CYAN_BOLD_BRIGHT("\033[1;96m"),             // CYAN
	WHITE_BOLD_BRIGHT("\033[1;97m"),            // WHITE
	
	// HIGH INTENSITY BACKGROUNDS
	BLACK_BACKGROUND_BRIGHT("\033[0;100m"),     // BLACK
	RED_BACKGROUND_BRIGHT("\033[0;101m"),       // RED
	GREEN_BACKGROUND_BRIGHT("\033[0;102m"),     // GREEN
	YELLOW_BACKGROUND_BRIGHT("\033[0;103m"),    // YELLOW
	BLUE_BACKGROUND_BRIGHT("\033[0;104m"),      // BLUE
	MAGENTA_BACKGROUND_BRIGHT("\033[0;105m"),   // MAGENTA
	CYAN_BACKGROUND_BRIGHT("\033[0;106m"),      // CYAN
	WHITE_BACKGROUND_BRIGHT("\033[0;107m");     // WHITE
	
	// CLASS SPECIFIC OBJECTS
	private final String code;
	
	/**
	 * A constructor used to define the required parameters for our Color enums
	 *
	 * @param code Color code
	 * @since 1.0
	 */
	ConsoleColor(String code)                                                { this.code = code;             }
	
	/**
	 * A method used to get the color code for a console color
	 *
	 * @return Console color value
	 * @since 1.0
	 */
	@Override
	public String toString()                                                 { return code;                  }
	
	/**
	 * A method used to add a color to a console message, this method will always automatically include
	 * a reset at the end of the string.
	 *
	 * @param color Target Color
	 * @param input Target input
	 * @return Color formatted string
	 * @since 1.1.0
	 */
	public static @NotNull String add(ConsoleColor color, String input) { return color + input + RESET; }
	
	/**
	 * A method used to remove an enum color from an input, if not color was found this method
	 * will return the unchanged original string.
	 *
	 * @param input Target input
	 * @return Stripped output
	 * @since 1.1.0
	 */
	public static @NotNull String remove(String input)
	{
		for (ConsoleColor current : ConsoleColor.values()) {
			if (input.contains(current.toString())) {
				input = input.replace(current.toString(), "");
			}
		}
		return input;
	}
}