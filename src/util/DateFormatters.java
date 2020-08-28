package util;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateFormatters {
	public static final String dateTimeFormat = "dd/MM/yyyy HH:mm";

	public static SimpleDateFormat getDateTimeFormatter() {
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(dateTimeFormat);
		dateTimeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

		return dateTimeFormatter;
	}
	
	public static final String dateFormat = "dd/MM/yyyy";
	
	public static SimpleDateFormat getDateFormatter() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);
		dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		return dateFormatter;
	}
	
	public static final String timeFormat = "HH:mm";
	
	public static SimpleDateFormat getTimeFormatter() {
		SimpleDateFormat timeFormatter = new SimpleDateFormat(timeFormat);
		timeFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		return timeFormatter;
	}
}
