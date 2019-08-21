package es.logger.project.init;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class JobLogger {

	private static final Logger logger = Logger.getLogger("MyLog");

	private boolean logToFile;
	private boolean logToConsole;
	private boolean logToDatabase;
	private boolean logMessage;
	private boolean logWarning;
	private boolean logError;
	private Map<String, String> dbParams;

	public JobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
			boolean logMessageParam, boolean logWarningParam, boolean logErrorParam, Map<String, String> dbParamsMap) {
		logError = logErrorParam;
		logMessage = logMessageParam;
		logWarning = logWarningParam;
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
		dbParams = dbParamsMap;
	}

	public void logMessage(String messageText, boolean message, boolean warning, boolean error) throws MyException {
		this.validateParameters(messageText);
		Level loggerLevel = this.getLoggerConfig();
		Level userLevel = this.getUserConfig(message, warning, error);
		String messageFormatted = getMessageFormated(message, warning, error, messageText);

		if (logToDatabase) {
			this.saveToDatabase(userLevel, messageFormatted);
		}

		if (logToFile) {
			this.saveToFile(loggerLevel, userLevel, messageFormatted);
		}

		if (logToConsole) {
			this.printToConsole(loggerLevel, userLevel, messageFormatted);
		}

	}

	private void printToConsole(Level loggerLevel, Level userLevel, String messageFormatted) {
		Handler ch = new ConsoleHandler();
		ch.setLevel(loggerLevel);
		logger.addHandler(ch);
		logger.setUseParentHandlers(false);
		logger.log(userLevel, messageFormatted.toString());
		logger.removeHandler(ch);
	}

	private void saveToFile(Level loggerLevel, Level userLevel, String messageFormatted) {
		SimpleFormatter formatter = new SimpleFormatter();
		String path;
		try {
			path = this.setupFilePath();
			Handler fh = new FileHandler(path, true);
			fh.setFormatter(formatter);
			fh.setLevel(loggerLevel);

			logger.addHandler(fh);
			logger.setUseParentHandlers(false);
			logger.log(userLevel, messageFormatted.toString());
			logger.removeHandler(fh);
		} catch (IOException e) {
			System.err.println("Error in saveToFile(): " + e);
		}
	}

	private void saveToDatabase(Level userLevel, String messageFormatted) {
		Connection connection = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", dbParams.get("userName"));
		connectionProps.put("password", dbParams.get("password"));
		try {

			connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" + dbParams.get("serverName")
					+ ":" + dbParams.get("portNumber") + "/", connectionProps);
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("insert into Log_Values('" + messageFormatted + "', " + String.valueOf(userLevel) + ")");

		} catch (SQLException e) {
			System.err.println("Error in saveToDatabase(): " + e);
		}
	}

	private void validateParameters(String messageText) throws MyException {
		if (messageText == null || messageText.length() == 0) {
			throw new MyException("Message is empty");
		}

		if (!logToConsole && !logToFile && !logToDatabase) {
			throw new MyException("Invalid configuration");
		}

		if ((!logError && !logMessage && !logWarning)) {
			throw new MyException("Error or Warning or Message must be specified");
		}
	}

	private String setupFilePath() throws IOException {
		String path = dbParams.get("logFileFolder") + "/logFile.txt";
		File logFile = new File(path);
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		return path;
	}

	private Level getLoggerConfig() {

		Level loggerLevel = Level.ALL;
		// Assume message = info
		// I choose the highest level
		if (logMessage) {
			loggerLevel = Level.INFO;
		} else if (logWarning) {
			loggerLevel = Level.WARNING;
		} else if (logError) {
			loggerLevel = Level.SEVERE;
		}

		return loggerLevel;
	}

	private boolean isMessageActived(boolean message, boolean logMessage) {
		return message && logMessage;
	}

	private boolean isErrorActived(boolean error, boolean logError) {
		return error && logError;
	}

	private boolean isWarningActived(boolean warning, boolean logWarning) {
		return warning && logWarning;
	}

	private Level getUserConfig(boolean message, boolean warning, boolean error) {
		Level userLevel = Level.ALL;
		if (this.isMessageActived(message, logMessage)) {
			userLevel = Level.INFO;
		}

		if (this.isErrorActived(error, logError)) {
			userLevel = Level.SEVERE;
		}

		if (this.isWarningActived(warning, logWarning)) {
			userLevel = Level.WARNING;
		}

		return userLevel;
	}

	private String getMessageFormated(boolean message, boolean warning, boolean error, String messageText) {

		StringBuilder messageFormatted = new StringBuilder();

		if (this.isMessageActived(message, logMessage)) {
			messageFormatted.append("Message ").append(DateFormat.getDateInstance(DateFormat.LONG).format(new Date()))
					.append(" - ").append(messageText).append("\n");
		}

		if (this.isErrorActived(error, logError)) {
			messageFormatted.append("Error ").append(DateFormat.getDateInstance(DateFormat.LONG).format(new Date()))
					.append(" - ").append(messageText).append("\n");
		}

		if (this.isWarningActived(warning, logWarning)) {
			messageFormatted.append("Warning ").append(DateFormat.getDateInstance(DateFormat.LONG).format(new Date()))
					.append(" - ").append(messageText).append("\n");
		}

		return messageFormatted.toString();

	}

	class MyException extends Exception {

		private static final long serialVersionUID = 1L;

		public MyException(String message) {
			super(message);
		}

	}

}
