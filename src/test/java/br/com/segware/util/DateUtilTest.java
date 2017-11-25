package br.com.segware.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilTest {

	@Test
	public void test() throws ParseException {
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		final String testDate1 = "25/11/2017 18:48:00";
		final String testDate2 = "25/11/2017 18:50:00";
		
		final Date date1 = sdf.parse(testDate1);
		final Date date2 = sdf.parse(testDate2);
		
		Assert.assertEquals(2, DateUtil.calculateDateDiff(date1, date2, TimeUnit.MINUTES));
	}

}
