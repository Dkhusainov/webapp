package webapp.jobtask.server;

import java.util.List;

import webapp.jobtask.client.BuildingService;
import webapp.jobtask.server.controller.Server;
import webapp.jobtask.shared.Building;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class BuildingServiceImpl extends RemoteServiceServlet implements BuildingService {


	@Override
	public void update(Building building) {
		Server.getInstance().getBuildingDAO().update(building);
		
	}

	@Override
	public Building save(Building building) {
		return Server.getInstance().getBuildingDAO().create(building);
	}

	@Override
	public List<Building> getData() {
		return Server.getInstance().getBuildingDAO().listAll();
	}

	@Override
	public void delete(Building building) {
		Server.getInstance().getBuildingDAO().delete(building);
		
	}

}
