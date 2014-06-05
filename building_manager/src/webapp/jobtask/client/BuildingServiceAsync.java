package webapp.jobtask.client;

import java.util.List;

import webapp.jobtask.shared.Building;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface BuildingServiceAsync {

	void save(Building building, AsyncCallback<Building> callback);

	void getData(AsyncCallback<List<Building>> callback);

	void update(Building building, AsyncCallback<Void> callback);

	void delete(Building building, AsyncCallback<Void> callback);
	

}
