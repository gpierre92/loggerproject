package com.logger.project.handler;

import org.apache.log4j.Logger;

import com.logger.project.AbstractLogger;

public class HandlerDatabase extends AbstractLogger {
	private static final Logger logger = Logger.getLogger(HandlerDatabase.class);
	
	@Override
	protected void logMessage(String msg) {
		logger.error(msg);
		
	}
	
}