package webapp.jobtask.client;

import java.util.List;

import webapp.jobtask.shared.Building;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;

public class BuildingInfo extends Composite {
	private TextBox addressText;
	private ListBox floorBox;
	private TextBox areaText;
	private TextBox numberText;
	private DateBox dateBox;
	
	private DataTableComposite parent;
	private Building building;
	
	private boolean checkFields() {
		String error = "";
		if (addressText.getText().trim().length() == 0) {
			error = error + "Address is empty \n";
		}
		if (areaText.getText().trim().length() == 0) {
			error = error + "Area is empty \n";
		}
		if (dateBox.getValue().toString().trim().length() == 0) {
			error = error + "Date is empty \n";
		}
		if (numberText.getValue().trim().length() == 0) {
			error = error + "Number is empty \n";
		}
		try {
			Integer.parseInt(areaText.getText());
		} catch (NumberFormatException e) {
			error = error + "Area  is  inappropriate";
		}
		if (!error.equals("")) {
			Window.confirm(error);
			return false;
		}
		return true;
	}

	public BuildingInfo(Building building, Composite parent) {
		
		this.parent = (DataTableComposite) parent;
		this.building = building;
		
		LayoutPanel layoutPanel = new LayoutPanel();
		initWidget(layoutPanel);
		layoutPanel.setSize("287px", "405px");
		
		Label titleLabel = new Label("Building information");
		layoutPanel.add(titleLabel);
		layoutPanel.setWidgetLeftWidth(titleLabel, 86.0, Unit.PX, 151.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(titleLabel, 14.0, Unit.PX, 18.0, Unit.PX);
		
		Label lblAddress = new Label("Address");
		layoutPanel.add(lblAddress);
		layoutPanel.setWidgetLeftWidth(lblAddress, 23.0, Unit.PX, 56.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblAddress, 61.0, Unit.PX, 18.0, Unit.PX);
		
		Label lblFloors = new Label("Floors");
		layoutPanel.add(lblFloors);
		layoutPanel.setWidgetLeftWidth(lblFloors, 23.0, Unit.PX, 56.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblFloors, 117.0, Unit.PX, 18.0, Unit.PX);
		
		Label lblArea = new Label("Area");
		layoutPanel.add(lblArea);
		layoutPanel.setWidgetLeftWidth(lblArea, 23.0, Unit.PX, 56.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblArea, 173.0, Unit.PX, 18.0, Unit.PX);
		
		Label lblDate = new Label("Date");
		layoutPanel.add(lblDate);
		layoutPanel.setWidgetLeftWidth(lblDate, 23.0, Unit.PX, 56.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblDate, 285.0, Unit.PX, 18.0, Unit.PX);
		Label lblNumber = new Label("Number");
		layoutPanel.add(lblNumber);
		layoutPanel.setWidgetLeftWidth(lblNumber, 23.0, Unit.PX, 56.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(lblNumber, 229.0, Unit.PX, 18.0, Unit.PX);
		
		addressText = new TextBox();
		addressText.setText(building.getAddress());
		layoutPanel.add(addressText);
		layoutPanel.setWidgetLeftWidth(addressText, 85.0, Unit.PX, 173.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(addressText, 61.0, Unit.PX, 34.0, Unit.PX);
		
		areaText = new TextBox();
		areaText.setText(Integer.toString(building.getArea()));
		layoutPanel.add(areaText);
		layoutPanel.setWidgetLeftWidth(areaText, 85.0, Unit.PX, 173.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(areaText, 173.0, Unit.PX, 34.0, Unit.PX);
		
		numberText = new TextBox();
		numberText.setText(building.getGlobalId());
		layoutPanel.add(numberText);
		layoutPanel.setWidgetLeftWidth(numberText, 85.0, Unit.PX, 173.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(numberText, 229.0, Unit.PX, 34.0, Unit.PX);
		
		Button btnCreate = new Button("Create");
		btnCreate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				 if (!checkFields()) {
					 return;
				 }
				 Building b = new Building();
				 b.setAddress(addressText.getText());
				 b.setArea(Integer.parseInt(areaText.getText()));
				 b.setDate(dateBox.getValue());
				 b.setFloors(Integer.parseInt(floorBox.getItemText(floorBox.getSelectedIndex())));
				 b.setGlobalId(numberText.getText());
				 
				 
				 //RPC call
				 BuildingServiceAsync service = (BuildingServiceAsync) GWT.create(BuildingService.class); 
				 AsyncCallback<Building> callback = new AsyncCallback<Building>() {

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("this is a failure method");
					}

					@Override
					public void onSuccess(Building result) {
						BuildingInfo.this.parent.updateItem(result);
					}

					 
				 };
				 
				 service.save(b, callback);
				 BuildingInfo.this.parent.inf.hide();
			}
		});
		layoutPanel.add(btnCreate);
		layoutPanel.setWidgetLeftWidth(btnCreate, 15.0, Unit.PX, 81.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(btnCreate, 353.0, Unit.PX, 30.0, Unit.PX);
		
		Button btnUpdate = new Button("Update");
		btnUpdate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!checkFields()) {
					return;
				}
				Building building = BuildingInfo.this.building;
				building.setAddress(addressText.getText());
				building.setArea(Integer.parseInt(areaText.getText()));
				building.setDate(dateBox.getValue());
				building.setFloors(Integer.parseInt(floorBox.getItemText(floorBox.getSelectedIndex())));
				building.setGlobalId(numberText.getText());
				
				 //RPC call
				 BuildingServiceAsync service = (BuildingServiceAsync) GWT.create(BuildingService.class); 
				 AsyncCallback<Void> callback = new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Void result) {
					}
				 };
				 List<Building> list = BuildingInfo.this.parent.dataProvider.getList();
				 int i = -1;
				 for (Building b : list) {
					 i++;
					 if (b.getId() == building.getId()) {
					 	list.set(i, building);
					}
						
				 }
				 service.update(building, callback);
				 BuildingInfo.this.parent.inf.hide();
			}
		});
		layoutPanel.add(btnUpdate);
		layoutPanel.setWidgetLeftWidth(btnUpdate, 102.0, Unit.PX, 81.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(btnUpdate, 353.0, Unit.PX, 30.0, Unit.PX);
		
		dateBox = new DateBox();
		dateBox.setValue(building.getDate());
		layoutPanel.add(dateBox);
		layoutPanel.setWidgetLeftWidth(dateBox, 86.0, Unit.PX, 172.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(dateBox, 285.0, Unit.PX, 32.0, Unit.PX);
		
		floorBox = new ListBox();
		// good code
		floorBox.addItem("1");
		floorBox.addItem("2");
		floorBox.addItem("3");
		floorBox.addItem("4");
		floorBox.addItem("5");
		floorBox.addItem("6");
		floorBox.addItem("7");
		floorBox.addItem("8");
		floorBox.addItem("9");
		floorBox.addItem("10");
		floorBox.addItem("11");
		floorBox.addItem("12");
		floorBox.addItem("13");
		floorBox.addItem("14");
		floorBox.addItem("15");
		floorBox.addItem("16");
		floorBox.addItem("17");
		floorBox.addItem("18");
		floorBox.addItem("19");
		floorBox.addItem("20");
		
		floorBox.setSelectedIndex((building.getFloors()));
		layoutPanel.add(floorBox);
		layoutPanel.setWidgetRightWidth(floorBox, 29.0, Unit.PX, 172.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(floorBox, 117.0, Unit.PX, 34.0, Unit.PX);
		
		Button deleteBtn = new Button("Delete");
		deleteBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//RPC call
				 BuildingServiceAsync service = (BuildingServiceAsync) GWT.create(BuildingService.class); 
				 AsyncCallback<Void> callback = new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(Void result) {
					}
				 };
				 List<Building> list = BuildingInfo.this.parent.items;
				 int i = -1;
				 for (Building b : list) {
					 i++;
					 if (b.getId() == BuildingInfo.this.building.getId()) {
					 	list.remove(i);
					}
				 }
				 service.delete(BuildingInfo.this.building, callback);
				 BuildingInfo.this.parent.inf.hide();
			}
		});
		deleteBtn.setText("Delete");
		layoutPanel.add(deleteBtn);
		layoutPanel.setWidgetLeftWidth(deleteBtn, 189.0, Unit.PX, 81.0, Unit.PX);
		layoutPanel.setWidgetTopHeight(deleteBtn, 353.0, Unit.PX, 30.0, Unit.PX);
	}
}
