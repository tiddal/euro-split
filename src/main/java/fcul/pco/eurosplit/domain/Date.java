package fcul.pco.eurosplit.domain;

import java.time.LocalDateTime;

/**
 * 
 * @author fc45764, fc49049
 *
 */

public class Date {
	private int day;
	private int month;
	private int year;
	private int hour;
	private int minutes;
	
	/**
	 * This constructor method creates a Date instance with the given parameters.
	 * @param day is an integer.
	 * @param month is an integer.
	 * @param year is an integer.
	 * @param hour is an integer.
	 * @param minutes is an integer.
	 */
	public Date(int day, int month, int year, int hour, int minutes) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.hour = hour;
		this.minutes = minutes;
	}
	
	/**
	 * This method creates a new Date instance with the current local date and time.
	 * @return a new Date instance.
	 */
	public static Date now() {
		LocalDateTime now = LocalDateTime.now();
		return new Date(now.getDayOfMonth(), now.getMonthValue(), now.getYear(), now.getHour(), now.getMinute());
	}

	/**
	 * This method overrides the default toString() method from Javadoc. 
	 * @return String with the Date in the format "dd-mm-yyyy hh:mm".
	 */
	@Override
	public String toString() {
		return day + "-" + month + "-" + year + " " + hour + ":" + minutes;
	}
	
	/**
	 * This method takes a string in the format "dd-mm-yyyy hh:mm".
	 * @param dateString is a String.
	 * @return a new Date instance.
	 */
	public static Date fromString(String dateString) {

		String stringDate = dateString.split(" ")[0];
		String stringTime = dateString.split(" ")[1];
		
		// Date
		int day = Integer.parseInt(stringDate.split("-")[0]);
		int month = Integer.parseInt(stringDate.split("-")[1]);
		int year = Integer.parseInt(stringDate.split("-")[2]);
		// Time
		int hour = Integer.parseInt(stringTime.split(":")[0]);
		int minutes = Integer.parseInt(stringTime.split(":")[1]);	
		
		return new Date(day, month, year, hour, minutes);
	}
}
