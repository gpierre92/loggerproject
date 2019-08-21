package test.logger.project.init;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import es.logger.project.init.JobLogger;

public class JobLoggerTest {

	@Test
	public void jobLoggertest() {

		boolean logToConsoleParam = true;
		boolean logToFileParam = true;
		boolean logToDatabaseParam = false;

		boolean logWarningParam = true;
		boolean logErrorParam = true;
		boolean logMessageParam = true;

		Map<String, String> dbParamsMap = new HashMap<>();
		dbParamsMap.put("logFileFolder", "D:\\");

		JobLogger jobLogger = new JobLogger(logToFileParam, logToConsoleParam, logToDatabaseParam,
				logMessageParam, logWarningParam, logErrorParam, dbParamsMap);

		try {
			
			jobLogger.logMessage("Mi info", true, true, false);
			jobLogger.logMessage("Mi warning", false, true, false);
			jobLogger.logMessage("Mi error", false, false, true);
			
		} catch (Exception e) {
			System.out.println("Error in jobLoggerNewtest(): " + e);
		}

	}

}
