package br.com.segware;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Utils {
	
	public static DateTime toDate(String date) throws ParseException{
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
		DateTime data = format.parseDateTime(date);
		return data;
	}

}
