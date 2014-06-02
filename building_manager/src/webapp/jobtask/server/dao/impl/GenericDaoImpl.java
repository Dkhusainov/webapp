package webapp.jobtask.server.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import webapp.jobtask.server.dao.GenericDao;
import webapp.jobtask.server.dao.HibernateUtil;
/**
 * Implementation of a generic DAO.
 * @author user
 *
 * @param <T> entity class
 * @param <ID> PK class
 */
public class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {
	
	protected  Class<T> entityClass;
	
	
	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

	public void create(T t) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		em.persist(t);
		em.flush();
		em.getTransaction().commit();
	}

	public T get(ID id) {
		return getEntityManager().find(entityClass, id);
	}

	public T update(T t) {
		return getEntityManager().merge(t);
	}

	@SuppressWarnings("unchecked")
	public List<T> listAll(T item) {
		return (LinkedList<T>) getEntityManager().createQuery("select x from " + getEntityClass().getSimpleName() + " x");
	}

	public void delete(T t) {
		getEntityManager().remove(t);

	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}

	public  EntityManager getEntityManager() {
		return HibernateUtil.getManager();
	}
}
