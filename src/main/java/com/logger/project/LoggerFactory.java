package com.logger.project;

import com.logger.project.exception.LoggerException;
import com.logger.project.handler.HandlerConsole;
import com.logger.project.handler.HandlerDatabase;
import com.logger.project.handler.HandlerFile;
import com.logger.project.utils.enums.AppenderType;

public class LoggerFactory {

	public static ILogger getLogger(AppenderType appenderType) {

		try {

			ILogger logger = null;

			switch (appenderType) {
			case FILE:
				logger = new HandlerFile();
				break;

			case CONSOLE:
				logger = new HandlerConsole();
				break;

			case DATABASE:
				logger = new HandlerDatabase();
				break;

			default:
				throw new LoggerException("Invalid Parameter: " + appenderType);
			}

			return logger;

		} catch (LoggerException e) {
			System.err.println("Error in getLogger(): " + e);
		}

		return null;

	}
}