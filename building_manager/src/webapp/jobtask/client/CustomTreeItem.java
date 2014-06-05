package webapp.jobtask.client;

import java.io.Serializable;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TreeItem;

public class CustomTreeItem extends TreeItem implements Serializable{
	
	public CustomTreeItem(String text, String description, Long id) {
		setText(text);
		this.description = description;
		this.id = id;
	}
	
	private Long id;
	
	private String description;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
}
