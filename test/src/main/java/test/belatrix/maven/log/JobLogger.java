package test.belatrix.maven.log;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
//import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class JobLogger {
	private static boolean logToFile;
	private static boolean logToConsole;
	private static boolean logMessage;
	private static boolean logWarning;
	private static boolean logError;
	private static boolean logToDatabase;
	//private boolean initialized;
	private static Map<String,String> dbParams;
	private static Logger logger;

	public JobLogger(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
			boolean logMessageParam, boolean logWarningParam, boolean logErrorParam, Map<String,String> dbParamsMap) {
		logger = Logger.getLogger("MyLog");  
		logError = logErrorParam;
		logMessage = logMessageParam;
		logWarning = logWarningParam;
		logToDatabase = logToDatabaseParam;
		logToFile = logToFileParam;
		logToConsole = logToConsoleParam;
		dbParams = dbParamsMap;
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

		Connection connection = null;
		Properties connectionProps = new Properties();
		connectionProps.put("user", dbParams.get("userName"));
		connectionProps.put("password", dbParams.get("password"));

		String url = "jdbc:" + dbParams.get("dbms") + ":thin:@" + dbParams.get("serverName")+ ":" + dbParams.get("portNumber")+":xe" ;
		System.out.println("url: " +url );
		connection = DriverManager.getConnection(url, connectionProps);
			
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

		//Statement stmt = connection.createStatement();

		String l = "";
		File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		
		FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt",true);
		ConsoleHandler ch = new ConsoleHandler();
		
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
			logger.log(level, l);
			logger.addHandler(fh);
		}
		
		if(logToConsole) {
			logger.log(level, l);
			logger.addHandler(ch);
		}
		
		if(logToDatabase) {
			String sql = "insert into Log_Values values('" + l + "', " + String.valueOf(t) + ")";
			System.out.println("sql: "+ sql);
			PreparedStatement  preparedStmt = connection.prepareStatement(sql);
			//stmt.executeUpdate("insert into Log_Values('" + message + "', " + String.valueOf(t) + ")");
			preparedStmt.execute();
		}
		if(connection != null)
		{
			//stmt.close();
			connection.close();			
		}
	/*	try {
			fh.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		try {
			ch.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}*/
	}

}
