package test.belatrix.maven.facade;

import java.util.List;

public interface BaseDAOLocal <T>{
    public void create(T entity);

    public void edit(T entity);

    public void remove(T entity) ;

    public T find(Object id) ;

    public List<T> findAll();

    public List<T> findRange(int[] range) ;

    public int count() ;
}
