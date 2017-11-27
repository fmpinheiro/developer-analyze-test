package br.com.segware.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {

	public static long calculateDateDiff(final Date date1, final Date date2, final TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();
		return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

}
