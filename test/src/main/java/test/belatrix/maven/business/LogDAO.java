package test.belatrix.maven.business;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import test.belatrix.maven.facade.AbstractDAO;
import test.belatrix.maven.facade.LogDAOLocal;
import test.belatrix.maven.model.Log;

/**
 * Session Bean implementation class LogDAO
 */
@LocalBean
@Stateless(mappedName = "LogDAO")
public class LogDAO extends AbstractDAO<Log> implements LogDAOLocal{
	@PersistenceContext(unitName = "BelatrixDS")
	private EntityManager em;
	
	public LogDAO(Class<Log> entityClass) {
		super(entityClass);
	}

    public void createLogDB(String message , String type) {
      	       
        try {
        	
        	Log log = new Log();
        	log.setDescrip(message);
        	log.setTipolog(type);
        	create(log);
        	
        }catch(Exception e)
        {
        	e.printStackTrace();
        }
    }
    
    public int getLogDB() {
                    	
        return count();
        
    }
    
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
