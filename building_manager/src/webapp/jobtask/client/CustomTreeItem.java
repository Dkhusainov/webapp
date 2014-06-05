package webapp.jobtask.client;

import java.io.Serializable;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TreeItem;

public class CustomTreeItem extends TreeItem implements Serializable{
	
	public CustomTreeItem(String text) {
		setText(text);
	}
	
	private Image image;
	private String description;
	
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
