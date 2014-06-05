package webapp.jobtask.client;


import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CustomTree extends Tree {
	
	public CustomTree() {
		sinkEvents(Event.ONDBLCLICK);
	}

	static class TreeDialog extends DialogBox {

		private TreeDialog() {
			setAnimationEnabled(true);
			setText("Description");
			setGlassEnabled(true);
		}

		@Override
		protected void onPreviewNativeEvent(NativePreviewEvent event) {
			// TODO Auto-generated method stub
			super.onPreviewNativeEvent(event);
			switch (event.getTypeInt()) {
			case Event.ONKEYDOWN:
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE) {
					hide();
				}
				break;
			}
		}
	}

	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		if (DOM.eventGetType(event) == Event.ONDBLCLICK) {
			final TextArea area = new TextArea();
			Button button = new Button("Save");
			button.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					CustomTreeItem item = (CustomTreeItem) CustomTree.this.getSelectedItem();
					item.setDescription(area.getText());
				}
			});
			VerticalPanel panel = new VerticalPanel();
			panel.add(button);
			panel.add(area);
			
			TreeDialog dialog = new TreeDialog();
			dialog.add(panel);
			CustomTreeItem item = (CustomTreeItem) getSelectedItem();
			area.setText(item.getDescription());
			dialog.center();
			dialog.show();
			
		}
	}
}
