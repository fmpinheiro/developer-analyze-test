package br.com.segware;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	public static Date toDate(String date) throws ParseException{
		DateFormat format = new SimpleDateFormat("YYYY-mm-dd kk:mm:ss");
		Date data = format.parse(date);
		return data;
	}

}
