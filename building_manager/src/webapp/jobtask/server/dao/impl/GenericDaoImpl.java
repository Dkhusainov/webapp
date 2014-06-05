package webapp.jobtask.server.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import webapp.jobtask.server.dao.GenericDao;
import webapp.jobtask.server.dao.HibernateUtil;
/**
 * Implementation of a generic DAO.
 * @author user
 *
 * @param <T> entity class
 * @param <ID> primary key class
 */
public class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {
	
	protected  Class<T> entityClass;
	
	
	EntityManager em = HibernateUtil.getManager();
	
	@SuppressWarnings("unchecked")
	public GenericDaoImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

	public T create(T t) {
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
		return t;
		
	}

	public T get(ID id) {
		return em.find(entityClass, id);
		
	}

	public T update(T t) {
		em.getTransaction().begin();
		em.merge(t);
		em.getTransaction().commit();
		return t;
	}
	@SuppressWarnings("unchecked")
	public List<T> listAll() {
//		em.getTransaction().begin();
		return em.createQuery("select x from " + getEntityClass().getSimpleName() + " x").getResultList();
//		em.getTransaction().commit();
	}

	public void delete(T t) {
		em.getTransaction().begin();
		em.remove(em.contains(t) ? t : em.merge(t));
		em.getTransaction().commit();
	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}

	public  EntityManager getEntityManager() {
		return HibernateUtil.getManager();
	}
}
