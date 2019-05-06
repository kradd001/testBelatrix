package test.belatrix.maven.facade;

import javax.ejb.Local;

import test.belatrix.maven.model.Log;



@Local
public interface LogDAOLocal extends BaseDAOLocal<Log>{

	
	public void createLogDB(String message , String type) ;
	
	public int getLogDB();
}