package test.belatrix.maven.log;

import java.text.DateFormat;
import java.util.Date;
import java.util.logging.Level;

import javax.ejb.EJB;

import test.belatrix.maven.facade.LogDAOLocal;
import test.belatrix.maven.utilities.archiveUtilities;

public class JobLoggerNew {
	private static boolean logToFile;
	private static boolean logToConsole;
	private static boolean logMessage;
	private static boolean logWarning;
	private static boolean logError;
	private static boolean logToDatabase;
	archiveUtilities archive = new archiveUtilities();
	@EJB
	LogDAOLocal log;
	
	public JobLoggerNew(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
			boolean logMessageParam, boolean logWarningParam, boolean logErrorParam) {
		logError = logErrorParam;
		logMessage = logMessageParam;
		logWarning = logWarningParam;
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
	}

	public void LogMessage(String messageText, boolean message, boolean warning, boolean error) throws Exception {
		messageText.trim();
		if (messageText == null || messageText.length() == 0) {
			return;
		}	
		if (!logToConsole && !logToFile && !logToDatabase) {
			throw new Exception("Invalid configuration");
		}
		if ((!logError && !logMessage && !logWarning) || (!message && !warning && !error)) {
			throw new Exception("Error or Warning or Message must be specified");
		}
			
		int t = 0;
		Level level =Level.INFO;
		if (message && logMessage) {
			t = 1;
			level = Level.INFO;
		}

		if (error && logError) {
			t = 2;
			level = Level.SEVERE;
		}

		if (warning && logWarning) {
			t = 3;
			level = Level.WARNING;
		}

		String l = "";

		archive.createArchive();
		archive.createFileHandler();
		archive.createConsoleHandler();
		
		if (error && logError) {
			l = l + " error " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) +" "+ messageText;
		}

		if (warning && logWarning) {
			l = l + " warning " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date())+" " + messageText;
		}

		if (message && logMessage) {
			l = l + " message " +DateFormat.getDateInstance(DateFormat.LONG).format(new Date())+" " + messageText;
		}
		
		if(logToFile) {
			archive.insertDataHandler(level, l);
		}
		
		if(logToConsole) {
			archive.insertDataConsole(level, l);
		}
		
		if(logToDatabase) {
			log.createLogDB(l, String.valueOf(t));
		}
		
	}

}
