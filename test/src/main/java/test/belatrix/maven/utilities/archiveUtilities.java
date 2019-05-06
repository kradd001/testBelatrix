package test.belatrix.maven.utilities;

import java.io.File;
import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;


public class archiveUtilities {

	private File logFile = null;
	private FileHandler fh = null;
	private ConsoleHandler ch = null;
	private static Logger logger;
	
	public archiveUtilities()
	{
		logger = Logger.getLogger(constants.LOG_NAME);	
	}
	
	public void createArchive()
	{
		
		logFile = new File(constants.ARCHIVE_LOCATION);
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createFileHandler()
	{
		if(fh ==null)
		{
			try {
				fh = new FileHandler(constants.ARCHIVE_LOCATION + constants.ARCHIVE_NAME,true);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createConsoleHandler()
	{
		if(ch == null)
		{
			ch = new ConsoleHandler();	
		}
	
	}
	
	public void insertDataHandler(Level level, String menssage)
	{
		logger.log(level, menssage);
		logger.addHandler(fh);
	}
	
	public void insertDataConsole(Level level, String menssage)
	{
		logger.log(level, menssage);
		logger.addHandler(ch);
	}
	
	
}
