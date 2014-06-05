package webapp.jobtask.client;

import java.util.List;

import webapp.jobtask.shared.CustomTreeItemDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TreeServiceAsync {


	void get(AsyncCallback<List<CustomTreeItemDTO>> callback);

	void update(CustomTreeItemDTO item, AsyncCallback<Void> callback);

	void add(CustomTreeItemDTO item, AsyncCallback<CustomTreeItemDTO> callback);

	void delete(List<Long> list, AsyncCallback<Void> callback);

}
