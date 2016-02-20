package com.leontg77.elements.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Number utilities class.
 * <p>
 * Contains methods for formatting doubles and ints, make a health into percent, 
 * get the amount of ticks #### and to get a random int between two given ints.
 * 
 * @author LeonTG77
 */
public class NumberUtils {
	public static final int TICKS_PER_SECOND = 20;
	public static final int TICKS_PER_MIN = TICKS_PER_SECOND * 60;
	public static final int TICKS_PER_HOUR = TICKS_PER_MIN * 60;
	public static final int TICKS_PER_DAY = TICKS_PER_HOUR * 24;
	public static final int TICKS_IN_999_DAYS = TICKS_PER_DAY * 999;
	
	/**
	 * Format the given double to a less lengthed one.
	 * 
	 * @param number the double to format.
	 * @return The formated double.
	 */
	public static String formatDouble(final double number) {
		final NumberFormat formater = new DecimalFormat("##.##");
		
		return formater.format(number);
	}

	/**
	 * Format the given int to a int with ,'s in it.
	 * 
	 * @param number the int to format.
	 * @return The formated integer.
	 */
	public static String formatInt(final int number) {
		final NumberFormat formater = NumberFormat.getInstance(Locale.UK);
		
		return formater.format(number);
	}
}