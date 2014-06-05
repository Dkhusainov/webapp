package webapp.jobtask.shared;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import webapp.jobtask.client.CustomTree;
import webapp.jobtask.client.CustomTreeItem;

/**
 * Builds a tree map from a given list of nodes.
 * @author user
 *
 */
public abstract class TreeUtil {
	
	static private CustomTree tree;
	
	static private Map<Long, CustomTreeItem> addedItems;
	
	static private Map<Long, CustomTreeItemDTO> idMap;
	
	public synchronized static Map<Long, CustomTree> getList(List<CustomTreeItemDTO> list) {
		Set<Long> ids = new HashSet<Long>();
		Map<Long, CustomTree> result = new HashMap<Long, CustomTree>();
		for (CustomTreeItemDTO data : list) {
			ids.add(data.getBuildingId());
		}
		for (Long id : ids) {
			List list2 = new LinkedList<CustomTreeItemDTO>();
			for (CustomTreeItemDTO data : list) {
				if (data.getBuildingId().equals(id)) {
					list2.add(data);
				}
			}
			result.put(id, getTree(list2));
		}
		return result;
	}
	
	public static CustomTree getTree(List<CustomTreeItemDTO> list) {
		
		idMap = new HashMap<Long, CustomTreeItemDTO>();
		for (CustomTreeItemDTO item : list) {
			idMap.put(item.getId(), item);
		}
		
		addedItems = new HashMap<Long, CustomTreeItem>();
		
		tree = new CustomTree(list.get(0).getBuildingId());
		tree.setWidth("202px");
		for (CustomTreeItemDTO data : list) {
			add(data);
		}
		return tree;
	}
	
	static private void add(CustomTreeItemDTO data) {
		CustomTreeItem item = null;
		// rec exit
		if (data.getParentId() == 0) {
			item = new CustomTreeItem(data.getName(), data.getDescription(), data.getId());
			tree.addItem(item);
			addedItems.put(data.getId(), item);
			return;
		}
		
		if (!addedItems.containsKey(data.getParentId())) {
			add(idMap.get(data.getParentId()));
		}
			item = new CustomTreeItem(data.getName(), data.getDescription(), data.getId());
			addedItems.get(data.getParentId()).addItem(item);
			addedItems.put(data.getId(), item);
	}
	
	public synchronized static List<Long> getChildsIds(CustomTreeItem parent) {
		List<Long> ids = new LinkedList<Long>();
		ids.add(parent.getId());
		if (parent.getChildCount() == 0) {
			return ids;
		}
		for (int i = 0;i < parent.getChildCount();i++) {
			CustomTreeItem child = (CustomTreeItem) parent.getChild(i);
			ids.addAll(getChildsIds(child));
		}
		return ids;
	}
}
