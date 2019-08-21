package test.logger.project;

import org.junit.Test;

import com.logger.project.ILogger;
import com.logger.project.LoggerFactory;
import com.logger.project.exception.LoggerException;
import com.logger.project.utils.Level;
import com.logger.project.utils.Message;
import com.logger.project.utils.enums.AppenderType;

public class HandlerConsoleTest {

	private static ILogger log = LoggerFactory.getLogger(AppenderType.CONSOLE);

	@Test
	public void testLogToConsoleSuccess() throws LoggerException {
		String msg = "log to console";
		log.print(new Message(Level.MESSAGE, msg));
		log.print(new Message(Level.ERROR, msg));
	}

}
