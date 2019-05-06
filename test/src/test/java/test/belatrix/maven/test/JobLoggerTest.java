package test.belatrix.maven.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import test.belatrix.maven.log.JobLogger;


@RunWith(Parameterized.class)
public class JobLoggerTest {

	JobLogger log;
	
	@Parameter(0)
	public boolean logToFileParam;
	@Parameter(1)
	public boolean logToConsoleParam ;
	@Parameter(2)
	public boolean logToDatabaseParam;
	@Parameter(3)
	public boolean logMessageParam ;
	@Parameter(4)
	public boolean logWarningParam ;
	@Parameter(5)
	public boolean logErrorParam ;
	@Parameter(6)
	public String messageText;
	@Parameter(7)
	public boolean message;
	@Parameter(8)
	public boolean warning;
	@Parameter(9)
	public boolean error;
	
	  @Parameters
	    public static Collection<Object[]> data() {
	        Object[][] data = new Object[][] 
	        { 
	        	//logToFileParam | logToConsoleParam | logToDatabaseParam | logMessageParam | logWarningParam | logErrorParam | messageText | message | warning | error
	        	{ false 		 , false			 , false			  , false			, false			  , false		  , "Prueba"	, false	  , false	, false}, 
	        	{ false 		 , false			 , false			  , false			, false			  , false		  , "Prueba"	, false	  , false	, true},
	        	{ false 		 , false			 , false			  , false			, false			  , false		  , "Prueba"	, false	  , true	, false},
	        	{ false 		 , false			 , false			  , false			, false			  , false		  , "Prueba"	, true	  , false	, false},
	        	{ false 		 , false			 , false			  , false			, false			  , true		  , "Prueba"	, false	  , false	, false},
	        	{ false 		 , false			 , false			  , false			, true			  , false		  , "Prueba"	, false	  , false	, false},
	        	{ false 		 , false			 , false			  , true			, false			  , false		  , "Prueba"	, false	  , false	, false},
	        	{ false 		 , false			 , true   			  , false			, false			  , false		  , "Prueba"	, false	  , false	, false},
	        	{ false 		 , true				 , false   			  , false			, false			  , false		  , "Prueba"	, false	  , false	, false},
	        	{ true 			 , false			 , false   			  , false			, false			  , false		  , "Prueba"	, false	  , false	, false},
	        	{ false			 , false			 , false   			  , false			, false			  , false		  , "Prueba"	, false	  , true	, true},
	        	{ false			 , false			 , false   			  , false			, false			  , false		  , "Prueba"	, true	  , false	, true},
	        	{ false			 , false			 , false   			  , false			, false			  , true		  , "Prueba"	, false	  , false	, true},
	        	{ false			 , false			 , false   			  , false			, true			  , false		  , "Prueba"	, false	  , false	, true},
	        	{ false			 , false			 , false   			  , true			, false			  , false		  , "Prueba"	, false	  , false	, true},
	        	{ false			 , false			 , true   			  , false			, false			  , false		  , "Prueba"	, false	  , false	, true},
	        	{ false			 , true				 , false   			  , false			, false			  , false		  , "Prueba"	, false	  , false	, true},
	        	{ false			 , false			 , false   			  , false			, false			  , false		  , "Prueba"	, false	  , false	, true},
	        	{ false			 , false			 , false   			  , false			, false			  , false		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , false			 , false   			  , false			, false			  , true		  , "Prueba"	, false	  , true	, true},
	        	{ false			 , false			 , false   			  , false			, true			  , false		  , "Prueba"	, false	  , true	, true},
	        	{ false			 , false			 , false   			  , true			, false			  , false		  , "Prueba"	, false	  , true	, true},
	        	{ false			 , false			 , true   			  , false			, false			  , false		  , "Prueba"	, false	  , true	, true},
	        	{ false			 , true				 , false   			  , false			, false			  , false		  , "Prueba"	, false	  , true	, true},
	        	{ true			 , false			 , false   			  , false			, false			  , false		  , "Prueba"	, false	  , true	, true},
	        	{ false			 , false			 , false   			  , false			, false			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , false			 , false   			  , false			, true			  , false		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , false			 , false   			  , true			, false			  , false		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , false			 , true   			  , false			, false			  , false		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , true				 , false   			  , false			, false			  , false		  , "Prueba"	, true	  , true	, true},
	        	{ true			 , false			 , false   			  , false			, false			  , false		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , false			 , false   			  , false			, true			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , false			 , false   			  , true			, false			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , false			 , true   			  , false			, false			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , true				 , false   			  , false			, false			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ true			 , false			 , false   			  , false			, false			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , false			 , false   			  , true			, true			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , false			 , true   			  , false			, true			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , true				 , false   			  , false			, true			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ true			 , false			 , false   			  , false			, true			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , false			 , true   			  , true			, true			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , true				 , false   			  , true			, true			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ true			 , false			 , false   			  , true			, true			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ false			 , true				 , true   			  , true			, true			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ true			 , false			 , true   			  , true			, true			  , true		  , "Prueba"	, true	  , true	, true},
	        	{ true 		 	 , true			 	 , true			      , true			, true			  , true		  , "Prueba"	, true	  , true	, true}
	        };
	        return Arrays.asList(data);
	    }
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testJobLogger() 
	{
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
			log = new JobLogger(logToFileParam, logToConsoleParam, logToDatabaseParam, logMessageParam, logWarningParam, logErrorParam, dbParamsMap);
			log.LogMessage(messageText,message,warning,error);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("Result",result, countRecords(dbParamsMap));
		
	}

	

	private  Map<String,String> connectionData()
	{
		Map<String,String> dbParamsMap = new HashMap<String,String>();
		dbParamsMap.put("userName", "BELATRIX");
		dbParamsMap.put("password", "ADMIN");
		dbParamsMap.put("dbms", "oracle");
		dbParamsMap.put("serverName", "localhost");
		dbParamsMap.put("portNumber", "1521");
		dbParamsMap.put("userName", "BELATRIX");
		dbParamsMap.put("logFileFolder", "D:\\Log\\");
		dbParamsMap.put("userName", "BELATRIX");
		return dbParamsMap;
	}
	
	private int countRecords(Map<String,String> datos)
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
