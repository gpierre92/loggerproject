package test.logger.project;

import org.junit.Test;

import com.logger.project.ILogger;
import com.logger.project.LoggerFactory;
import com.logger.project.exception.LoggerException;
import com.logger.project.utils.Level;
import com.logger.project.utils.Message;
import com.logger.project.utils.enums.AppenderType;

public class HandlerDatabaseTest {
	private static ILogger log = LoggerFactory.getLogger(AppenderType.DATABASE);

	@Test
	public void testLogMessageIsSent() throws LoggerException {
		log.print(new Message(Level.MESSAGE, "logging to database"));
		log.print(new Message(Level.WARNING, "logging to database"));
		log.print(new Message(Level.ERROR, "logging to database"));
	}
}