package webapp.jobtask.client;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import webapp.jobtask.shared.Building;
import webapp.jobtask.shared.CustomTreeItemDTO;
import webapp.jobtask.shared.TreeUtil;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.user.client.ui.TextArea;


/**
 * Grid for representing list of buildings.
 * 
 * @author user
 * 
 */
public class DataTableComposite extends Composite {
	
	
	static class InfoDialog extends DialogBox {

		public InfoDialog(Building building, Composite parent) {

			// Set the dialog box's caption.
			setText("hi");

			// Enable animation.
			setAnimationEnabled(true);

			// Enable glass background.
			setGlassEnabled(true);

			Composite comp = new BuildingInfo(building, parent);
			setWidget(comp);
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
	
	InfoDialog inf;

	ListDataProvider<Building> dataProvider = new ListDataProvider<Building>(new LinkedList<Building>());
	
	List<Building> items = dataProvider.getList();
	
	Map<Long, CustomTree> trees;
	
	CellTable<Building> cellTable;
	private TextBox textBox;
	private CustomTree tree;
	private ScrollPanel sp;

	public DataTableComposite() {
		
				
		requestUpdate();
		buildTrees();
		LayoutPanel verticalPanel = new LayoutPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("100%", "1287px");

		cellTable = new CellTable<Building>();
		cellTable.setPageSize(10);
		cellTable.setStyleName("dataTableCSS");
		cellTable.setVisible(true);
		verticalPanel.add(cellTable);
		
		final SingleSelectionModel<Building> selectionModel = new SingleSelectionModel<Building>();
	    cellTable.setSelectionModel(selectionModel);
	    selectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
	      public void onSelectionChange(SelectionChangeEvent event) {
	        Building selected = selectionModel.getSelectedObject();
	        if (selected != null) {
		        	if (trees.containsKey(selected.getId())) {
		        		tree = trees.get(selected.getId());
		        		sp.clear();
		        		sp.add(tree);
		        	} else {
		        		tree = new CustomTree(selected.getId());
		        		tree.setWidth("202px");
		        		sp.clear();
		        		sp.add(tree);
		        	}
	        }
	      }
	    });

		SimplePager simplePager = new SimplePager();
		simplePager.setPageSize(10);
		simplePager.setDisplay(cellTable);
		verticalPanel.add(simplePager);
		verticalPanel.setWidgetLeftWidth(simplePager, 298.0, Unit.PX, 161.0, Unit.PX);
		verticalPanel.setWidgetTopHeight(simplePager, 295.0, Unit.PX, 44.0, Unit.PX);
		simplePager.setSize("161px", "43px");

		cellTable.addDomHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				Building b = cellTable.getVisibleItem(cellTable
						.getKeyboardSelectedRow());
				inf = new InfoDialog(b, DataTableComposite.this);
				inf.center();
				inf.show();
			}

		}, DoubleClickEvent.getType());
		cellTable.sinkEvents(Event.ONCLICK);
		initColumns();
		dataProvider.addDataDisplay(cellTable);

		
		Button btnCreate = new Button("Create");
		btnCreate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (textBox.getText().trim().equals("")) {
					Window.alert("Name is empty");
					return;
				}
				Building sl = cellTable.getVisibleItem(cellTable.getKeyboardSelectedRow());
				CustomTreeItemDTO data = new CustomTreeItemDTO();
				data.setBuildingId(sl.getId());
				data.setName(textBox.getText());
				data.setDescription("");
				CustomTreeItem selected = (CustomTreeItem) tree.getSelectedItem();
//				
				if (tree.getItemCount() == 0) {
					data.setParentId((long)0);
				} else {
					data.setParentId(selected.getId());
				}
				
				//RPC
				 TreeServiceAsync service = (TreeServiceAsync) GWT.create(TreeService.class); 
				 AsyncCallback<CustomTreeItemDTO> callback = new AsyncCallback<CustomTreeItemDTO>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert(caught.getMessage());
					}

					@Override
					public void onSuccess(CustomTreeItemDTO result) {
						try {
							CustomTreeItem item = new CustomTreeItem(result.getName(), result.getDescription(), result.getId());
							if (tree.getItemCount() == 0) {
								tree.addItem(item);
							} else {
								tree.getSelectedItem().addItem(item);
								tree.getSelectedItem().setState(true);
							}
						} catch (Exception e) {
							Window.alert(e.getMessage() + "/n failed to add node");
						}
					}
				 };
				 service.add(data, callback);
			}
		});
		verticalPanel.add(btnCreate);
		verticalPanel.setWidgetLeftWidth(btnCreate, 10.0, Unit.PX, 48.0, Unit.PX);
		verticalPanel.setWidgetTopHeight(btnCreate, 349.0, Unit.PX, 30.0, Unit.PX);
		btnCreate.setWidth("48px");
		
		textBox = new TextBox();
		textBox.setVisibleLength(10);
		verticalPanel.add(textBox);
		textBox.setSize("", "");
		verticalPanel.setWidgetLeftWidth(textBox, 64.0, Unit.PX, 107.0, Unit.PX);
		verticalPanel.setWidgetTopHeight(textBox, 349.0, Unit.PX, 30.0, Unit.PX);
		
		

		DecoratorPanel decoratorPanel = new DecoratorPanel();
		sp = new ScrollPanel(tree);
		sp.setAlwaysShowScrollBars(true);
		decoratorPanel.add(sp);
		sp.setSize("305px", "307px");
		verticalPanel.add(decoratorPanel);
		verticalPanel.setWidgetLeftWidth(decoratorPanel, 10.0, Unit.PX, 487.0, Unit.PX);
		verticalPanel.setWidgetTopHeight(decoratorPanel, 385.0, Unit.PX, 454.0, Unit.PX);
		
		Button deleteButton = new Button("Delete");
		deleteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				List<Long> ids = TreeUtil.getChildsIds((CustomTreeItem) tree.getSelectedItem());
				tree.getSelectedItem().removeItems();
				tree.getSelectedItem().remove();
				//RPC
				 TreeServiceAsync service = (TreeServiceAsync) GWT.create(TreeService.class); 
				 AsyncCallback<Void> callback = new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						
					}

					 
				 };
				 service.delete(ids, callback);
			}
		});
		verticalPanel.add(deleteButton);
		verticalPanel.setWidgetLeftWidth(deleteButton, 177.0, Unit.PX, 81.0, Unit.PX);
		verticalPanel.setWidgetTopHeight(deleteButton, 349.0, Unit.PX, 30.0, Unit.PX);
		
	}

	private void buildTrees() {
		//RPC
		 TreeServiceAsync service = (TreeServiceAsync) GWT.create(TreeService.class); 
		 AsyncCallback<List<CustomTreeItemDTO>> callback = new AsyncCallback<List<CustomTreeItemDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage() + "/nFailed to build trees");
			}

			@Override
			public void onSuccess(List<CustomTreeItemDTO> result) {
					trees = TreeUtil.getList(result);
			}
			 
		 };
		 service.get(callback);
	}
	
	void requestUpdate() {
		BuildingServiceAsync service = (BuildingServiceAsync) GWT
				.create(BuildingService.class);
		AsyncCallback<List<Building>> callback = new AsyncCallback<List<Building>>() {

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(List<Building> result) {
				items.clear();
				items.addAll(result);
				cellTable.setRowCount(items.size(), true);
			}
		};
		service.getData(callback);
	}

	void updateItem(Building building) {
		items.add(building);
		dataProvider.refresh();
	}
	

	/**
	 * Initializes columns.
	 */
	private void initColumns() {

		
		ListHandler<Building> sortHandler = new ListHandler<Building>(items);
		cellTable.addColumnSortHandler(sortHandler);

		// Add a text column to show the name.
		TextColumn<Building> nameColumn = new TextColumn<Building>() {

			@Override
			public String getValue(Building object) {
				return object.getAddress();
			}
		};
		nameColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		nameColumn.setSortable(true);
		sortHandler.setComparator(nameColumn, new Comparator<Building>() {

			@Override
			public int compare(Building o1, Building o2) {
				return o1.getAddress().compareTo(o2.getAddress());
			}
		});
		cellTable.addColumn(nameColumn, "Address");
		cellTable.setColumnWidth(nameColumn, 177.0, Unit.PX);

		// Add a date column to show date
		DateCell dateCell = new DateCell();
		Column<Building, Date> dateColumn = new Column<Building, Date>(dateCell) {

			@Override
			public Date getValue(Building object) {
				return object.getDate();
			}
		};
		dateColumn.setSortable(true);
		sortHandler.setComparator(dateColumn, new Comparator<Building>() {

			@Override
			public int compare(Building o1, Building o2) {
				return o1.getDate().compareTo(o2.getDate());
			}
		});
		cellTable.addColumn(dateColumn, "Date");
		cellTable.setColumnWidth(dateColumn, 217.0, Unit.PX);

		// Add a text column to show number of floors
		TextColumn<Building> floorColumn = new TextColumn<Building>() {

			@Override
			public String getValue(Building object) {
				return Integer.toString(object.getFloors());
			}
		};
		floorColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		floorColumn.setSortable(true);
		cellTable.addColumn(floorColumn, "Floors");
		cellTable.setColumnWidth(floorColumn, "68px");

		// Add a text column to show area.
		TextColumn<Building> areaColumn = new TextColumn<Building>() {

			@Override
			public String getValue(Building object) {
				return Integer.toString(object.getArea()) + " m.";
			}
		};
		areaColumn.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		areaColumn.setSortable(true);
		cellTable.addColumn(areaColumn, "Area");
		cellTable.setColumnWidth(areaColumn, "104px");

		// Add a text column to show global id.
		TextColumn<Building> idColumn = new TextColumn<Building>() {

			@Override
			public String getValue(Building object) {
				return object.getGlobalId();
			}
		};
		cellTable.addColumn(idColumn, "Global number");
		cellTable.setColumnWidth(idColumn, "150px");
	}
}
