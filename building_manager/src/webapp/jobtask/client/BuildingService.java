package webapp.jobtask.client;

import java.util.List;

import webapp.jobtask.shared.Building;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("buildingService")
public interface BuildingService extends RemoteService{
	
	Building save(Building building);
	List<Building> getData();
	void update(Building building);
	void delete(Building building);
}
