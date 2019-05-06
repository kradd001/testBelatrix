package test.belatrix.maven.log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ejb.EJB;

import test.belatrix.maven.facade.LogDAOLocal;


public class PRUEBA {
	//static JobLogger log;
	static JobLoggerNew log;
	
	@EJB
	public static LogDAOLocal logdao;
	
	public static void main(String[] args) {
		
		//case 1
		/*boolean logToFileParam =true;
		boolean logToConsoleParam = true;
		boolean logToDatabaseParam 	= true;
		boolean logMessageParam =false;
		boolean logWarningParam =false;
		boolean logErrorParam =true;
		String messageText ="prueba";
		boolean message = false;
		boolean warning =false;
		boolean error = true;
		System.out.println("INICIA LA PRUEBA");
		System.out.println("CONSULTA DATOS DE CONEXION");
		Map<String,String> dbParamsMap = connectionData();
		System.out.println("GENERA DATOS DE CONSULTA");
		int result = countRecords(dbParamsMap);
		System.out.println("result: " + result);
		try {
			System.out.println("logToFileParam: " + logToFileParam);
			System.out.println("logToConsoleParam: " + logToConsoleParam);
			System.out.println("logToDatabaseParam: "+logToDatabaseParam);
			System.out.println("logMessageParam: " +logMessageParam);
			System.out.println("logWarningParam: " +logWarningParam);
			System.out.println("logErrorParam: " +logErrorParam);
			System.out.println("messageText: "+messageText);
			System.out.println("message: "+message);
			System.out.println("warning: "+warning);
			System.out.println("error: "+error);
			System.out.println("CREA INSTANCIA LOG");
			log = new JobLogger(logToFileParam, logToConsoleParam, logToDatabaseParam, logMessageParam, logWarningParam, logErrorParam, dbParamsMap);
			System.out.println("ALMACENA LOG");
			log.LogMessage(messageText,message,warning,error);
			System.out.println("FIN");
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		//case 2
		boolean logToFileParam =false;
		boolean logToConsoleParam = false;
		boolean logToDatabaseParam 	= true;
		boolean logMessageParam =false;
		boolean logWarningParam =false;
		boolean logErrorParam =true;
		String messageText ="prueba";
		boolean message = false;
		boolean warning =false;
		boolean error = true;
		System.out.println("INICIA LA PRUEBA");
		System.out.println("CONSULTA DATOS DE CONEXION");
		
		try {
			int result = logdao.getLogDB();
			System.out.println("result: " + result);
			System.out.println("logToFileParam: " + logToFileParam);
			System.out.println("logToConsoleParam: " + logToConsoleParam);
			System.out.println("logToDatabaseParam: "+logToDatabaseParam);
			System.out.println("logMessageParam: " +logMessageParam);
			System.out.println("logWarningParam: " +logWarningParam);
			System.out.println("logErrorParam: " +logErrorParam);
			System.out.println("messageText: "+messageText);
			System.out.println("message: "+message);
			System.out.println("warning: "+warning);
			System.out.println("error: "+error);
			System.out.println("CREA INSTANCIA LOG");
			log = new JobLoggerNew(logToFileParam, logToConsoleParam, logToDatabaseParam, logMessageParam, logWarningParam, logErrorParam);
			System.out.println("ALMACENA LOG");
			log.LogMessage(messageText,message,warning,error);
			System.out.println("FIN");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static  Map<String,String> connectionData()
	{
		Map<String,String> dbParamsMap = new HashMap<String,String>();
		dbParamsMap.put("userName", "BELATRIX");
		dbParamsMap.put("password", "ADMIN");
		dbParamsMap.put("dbms", "oracle");
		dbParamsMap.put("serverName", "localhost");
		dbParamsMap.put("portNumber", "1521");
		dbParamsMap.put("logFileFolder", "D:\\Log\\");
		return dbParamsMap;
	}
	
	private static int countRecords( Map<String,String> datos)
	{
		int result =-1; 
	
		try {
			Connection connection = null;
			Properties connectionProps = new Properties();
			connectionProps.put("user", datos.get("userName"));
			connectionProps.put("password", datos.get("password"));
			String url = "jdbc:" + datos.get("dbms") + ":thin:@" + datos.get("serverName")
			+ ":" + datos.get("portNumber")+":xe" ;
			System.out.println("url: " +url );
			connection = DriverManager.getConnection(url, connectionProps);
			Statement stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery("select count(1) from Log_Values");
			res.next();
			result = res.getInt(1);
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
