package webapp.jobtask.server.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import webapp.jobtask.server.dao.impl.BuildingGenericDaoImpl;
import webapp.jobtask.server.dao.impl.TreeItemDAO;
import webapp.jobtask.server.dao.impl.UserGenericDaoImpl;
import webapp.jobtask.shared.User;

/**
 * Server side controller.
 * @author user
 *
 */
public class Server {
	private static final Server instance = new Server();
	
	public static Server getInstance() {
		return instance;
		
	}
	
	private Server() {
		
	}
	
	private Map<String, LinkedList<Date>> blockedList = new HashMap<String, LinkedList<Date>>();
	
	private  UserGenericDaoImpl userDAO = new UserGenericDaoImpl();
	private BuildingGenericDaoImpl buildingDAO = new BuildingGenericDaoImpl();
	private TreeItemDAO treeItemDAO = new TreeItemDAO();
	

	public UserGenericDaoImpl getUserDAO() {
		return this.userDAO;
	}
	
	public TreeItemDAO getTreeItemDAO() {
		return this.treeItemDAO;
	}
	
	public BuildingGenericDaoImpl getBuildingDAO() {
		return this.buildingDAO;
	}
	
	public User loginUser(User user) {
		User user2 = Server.getInstance().getUserDAO().get(user.getName());
		if (user2 == null) {
			return user2;
		} else if (!user.getPassword().equals(user2.getPassword())) {
			return addForBlock(user2);
		}
		return user2;
	}
	
	private User addForBlock(User user){
		if (!blockedList.containsKey(user.getName())) {
			blockedList.put(user.getName(), new LinkedList<Date>());
		}
		List<Date> list = blockedList.get(user.getName());
		Date date = new Date();
		list.add(date);
		try {
			Date prevDate = list.get(list.indexOf(date) - 2);
			if ((date.getTime() - prevDate.getTime()) <= 1000*60*60*24) {
				user.setBlocked(true);
				return userDAO.update(user);
			}
			
		} catch (Exception e) {
			//do nothing
		}
		return user;
	}
	
	public User signUpUser(User user) {
		user.setBlocked(false);
		try {
			getUserDAO().create(user);
			return user;
		} catch (Exception e) {
			System.out.println("Hibernate:unique constraint violated hurrrrr");
//			e.printStackTrace();
			return null;
		}
	}
}
