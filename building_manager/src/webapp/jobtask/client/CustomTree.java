package webapp.jobtask.client;


import webapp.jobtask.shared.CustomTreeItemDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CustomTree extends Tree {
	
	private TextBox textBox;
	private TextArea area;
	private TreeDialog dialog;
	
	private Long buildingId;
	
	public CustomTree(Long buildingId) {
		this.buildingId = buildingId;
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
			final CustomTreeItem selected = (CustomTreeItem) getSelectedItem();
			VerticalPanel panel = new VerticalPanel();
			area = new TextArea();
			textBox = new TextBox();
			Button button = new Button("Save");
			button.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent event) {
					selected.setText(textBox.getText());
					selected.setDescription(area.getText());
					
					CustomTreeItemDTO data = new CustomTreeItemDTO();
//					data.setBuildingId(CustomTree.this.buildingId);
					data.setDescription(area.getText());
					data.setName(textBox.getText());
					data.setBuildingId(buildingId);
					CustomTreeItem parent = (CustomTreeItem) selected.getParentItem();
					if (parent == null) {
						data.setParentId((long) 0);
					} else {
						data.setParentId(parent.getId());
					}
					data.setId(selected.getId());
					
					//RPC
					 TreeServiceAsync service = (TreeServiceAsync) GWT.create(TreeService.class); 
					 AsyncCallback<Void> callback = new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert(caught.getMessage());
						}

						@Override
						public void onSuccess(Void result) {
							
						}
						 
					 };
					 service.update(data, callback);
					 dialog.hide();
				}
			});
			panel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
			panel.add(textBox);
			panel.add(area);
			panel.add(button);
			
			dialog = new TreeDialog();
			
			area.setText(selected.getDescription());
			textBox.setText(selected.getText());
			dialog.add(panel);
			dialog.center();
			dialog.show();
			
		}
	}
}
