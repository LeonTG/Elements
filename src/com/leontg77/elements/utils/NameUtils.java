package com.leontg77.elements.utils;


/**
 * Name utilities class.
 * <p>
 * Contains methods for capitalizing strings and getting potion real names.
 * 
 * @author LeonTG77
 */
public class NameUtils {
	
	/**
	 * Fix the given text with making the first letter captializsed and the rest not.
	 * 
	 * @param text the text fixing.
	 * @param replaceUnderscore True to replace all _ with a space, false otherwise.
	 * @return The new fixed text.
	 */
	public static String capitalizeString(final String text, final boolean replaceUnderscore) {
		if (text.isEmpty()) {
			return text;
		}
		
		if (text.length() == 1) {
			return text.toUpperCase();
		}
		
		String toReturn = text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
		
		if (replaceUnderscore) {
			toReturn = toReturn.replace("_", " ");
		} 
		
		return toReturn;
	}
}