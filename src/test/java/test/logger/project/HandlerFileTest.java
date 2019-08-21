package test.logger.project;

import org.junit.Test;

import com.logger.project.ILogger;
import com.logger.project.LoggerFactory;
import com.logger.project.exception.LoggerException;
import com.logger.project.utils.Level;
import com.logger.project.utils.Message;
import com.logger.project.utils.enums.AppenderType;

public class HandlerFileTest {
	private static ILogger log = LoggerFactory.getLogger(AppenderType.FILE);

	@Test
	public void testLogMessageIsSent() throws LoggerException {
		
		log.print(new Message(Level.MESSAGE, "logging test in file"));
		log.print(new Message(Level.WARNING, "logging test in file"));
		log.print(new Message(Level.ERROR, "logging test in file"));
		
	}
}