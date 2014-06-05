package webapp.jobtask.client;

import java.util.List;

import webapp.jobtask.shared.CustomTreeItemDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TreeServiceAsync {

	void add(CustomTreeItemDTO item, AsyncCallback<Void> callback);

	void get(AsyncCallback<List<CustomTreeItemDTO>> callback);

	void update(CustomTreeItemDTO item, AsyncCallback<Void> callback);

}
