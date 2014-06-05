package webapp.jobtask.server.dao.impl;

import java.util.List;

import webapp.jobtask.shared.CustomTreeItemDTO;

public class TreeItemDAO extends GenericDaoImpl<CustomTreeItemDTO, Long> {

	@Override
	public CustomTreeItemDTO update(CustomTreeItemDTO t) {
		em.getTransaction().begin();
		em.find(getEntityClass(), t.getId());
		em.merge(t);
		em.getTransaction().commit();
		return t;
	}

	public void delete(List<Long> t) {
		em.getTransaction().begin();
		for (Long id : t) {
			CustomTreeItemDTO entity = em.find(getEntityClass(), id);
			em.remove(entity);
		}
		em.getTransaction().commit();
	}
	
}
