package com.logger.project;

import com.logger.project.exception.LoggerException;
import com.logger.project.utils.Message;

public interface ILogger {

	void print(Message message) throws LoggerException;

}