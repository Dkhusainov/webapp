package webapp.jobtask.server.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Basic generic DAO interface.
 * @author user
 *
 */
public interface GenericDao<T, ID extends Serializable> {
	T create(T t);
	/**
	 * Get entity by primary key.
	 */
	T get(ID id);
	/**
	 * Merge entitys.
	 * @param t
	 * @return
	 */
	T update(T t);
	List<T> listAll();
	void delete(T t);
	
}
