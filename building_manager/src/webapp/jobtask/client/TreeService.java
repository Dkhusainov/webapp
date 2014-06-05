package webapp.jobtask.client;

import java.util.List;

import webapp.jobtask.shared.CustomTreeItemDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("treeService")
public interface TreeService extends RemoteService {
	List<CustomTreeItemDTO> get();
	void update(CustomTreeItemDTO item);
	CustomTreeItemDTO add(CustomTreeItemDTO item);
	void delete(List<Long> list);
}
