package webapp.jobtask.shared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import webapp.jobtask.client.CustomTree;
import webapp.jobtask.client.CustomTreeItem;
import webapp.jobtask.client.DataTableComposite;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.server.Base64Utils;

public class TreeUtil {
	
	static private CustomTree tree;
	
	static private Map<Long, CustomTreeItem> addedItems;
	
	static private Map<Long, CustomTreeItemDTO> idMap;
	
	public static Tree getTree(List<CustomTreeItemDTO> list, DataTableComposite table) {
		
		idMap = new HashMap<Long, CustomTreeItemDTO>();
		for (CustomTreeItemDTO item : list) {
			idMap.put(item.getId(), item);
		}
		
		addedItems = new HashMap<Long, CustomTreeItem>();
		
		tree = new CustomTree();
		
		for (CustomTreeItemDTO data : list) {
			add(data);
		}
		return tree;
	}
	
	static private void add(CustomTreeItemDTO data) {
		CustomTreeItem item = null;
		// rec exit
		if (data.getParentId() == 0) {
			item = new CustomTreeItem(data.getName());
			item.setDescription(data.getDescription());
			tree.addItem(item);
			addedItems.put(data.getId(), item);
		}
		
		if (!addedItems.containsKey(data.getParentId())) {
			add(idMap.get(data.getParentId()));
		}
			item = new CustomTreeItem(data.getName());
			item.setDescription(data.getDescription());
			addedItems.get(data.getParentId()).addItem(item);
			addedItems.put(data.getId(), item);
	}
	
	static private Image getImageData(byte[] b){
	      String base64 = Base64Utils.toBase64(b); 
	      base64 = "data:image/png;base64,"+base64;
	      Image image = new Image(base64);     
	      return image;
	}
}
