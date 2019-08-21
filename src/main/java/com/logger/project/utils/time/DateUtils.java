package com.logger.project.utils.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

	private DateUtils() {
	}

	public static String getCurrentTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date now = new Date();
		String instant = sdf.format(now);
		
		return instant;
	}

}