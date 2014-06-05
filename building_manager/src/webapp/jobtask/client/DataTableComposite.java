package webapp.jobtask.client;

import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import webapp.jobtask.shared.Building;
import webapp.jobtask.shared.CustomTreeItemDTO;

import com.google.gwt.cell.client.DateCell;
import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.view.client.ListDataProvider;

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
	
	CellTable<Building> cellTable;
	private TextBox textBox;
	private CustomTree tree;

	public DataTableComposite() {
				
		requestUpdate();
		LayoutPanel verticalPanel = new LayoutPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("100%", "1287px");

		cellTable = new CellTable<Building>();
		cellTable.setPageSize(10);
		cellTable.setStyleName("dataTableCSS");
		cellTable.setVisible(true);
		verticalPanel.add(cellTable);

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
		
		Button btnDelete = new Button("Delete");
		btnDelete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				tree.getSelectedItem().remove();
			}
		});
		verticalPanel.add(btnDelete);
		verticalPanel.setWidgetLeftWidth(btnDelete, 163.0, Unit.PX, 53.0, Unit.PX);
		verticalPanel.setWidgetTopHeight(btnDelete, 349.0, Unit.PX, 30.0, Unit.PX);
		
		Button btnCreate = new Button("Create");
		btnCreate.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (textBox.getText().trim().equals("")) {
					Window.alert("Name is empty");
					return;
				}
				if (tree.getItemCount() == 0) {
					tree.addTextItem(textBox.getText());
					return;
				}
				
//				CustomTreeItemDTO data = new CustomTreeItemDTO();
				tree.getSelectedItem().addTextItem(textBox.getText());
				tree.getSelectedItem().setState(true);
				
			}
		});
		verticalPanel.add(btnCreate);
		verticalPanel.setWidgetLeftWidth(btnCreate, 0.0, Unit.PX, 48.0, Unit.PX);
		verticalPanel.setWidgetTopHeight(btnCreate, 349.0, Unit.PX, 30.0, Unit.PX);
		btnCreate.setWidth("48px");
		
		textBox = new TextBox();
		textBox.setVisibleLength(10);
		verticalPanel.add(textBox);
		textBox.setSize("", "");
		verticalPanel.setWidgetLeftWidth(textBox, 50.0, Unit.PX, 107.0, Unit.PX);
		verticalPanel.setWidgetTopHeight(textBox, 349.0, Unit.PX, 30.0, Unit.PX);
		
		
		tree = new CustomTree();
		tree.setWidth("202px");
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		ScrollPanel sp = new ScrollPanel(tree);
		sp.setAlwaysShowScrollBars(true);
		decoratorPanel.add(sp);
		sp.setSize("305px", "307px");
		verticalPanel.add(decoratorPanel);
		verticalPanel.setWidgetLeftWidth(decoratorPanel, 10.0, Unit.PX, 487.0, Unit.PX);
		verticalPanel.setWidgetTopHeight(decoratorPanel, 385.0, Unit.PX, 454.0, Unit.PX);
		
	}

	List<Building> testList(int count) {
		Random rn = new Random(12342);
		List<Building> list = new LinkedList<Building>();
		for (int i = 0; i < count; i++) {
			Building b = new Building();
			b.setArea(rn.nextInt(100));
			b.setDate(new Date());
			b.setFloors(rn.nextInt(10));
			b.setGlobalId(Long.toString(rn.nextLong()));
			b.setAddress(Long.toString(rn.nextLong()));
			list.add(b);
		}
		return list;
	}

	void requestUpdate() {
		BuildingServiceAsync service = (BuildingServiceAsync) GWT
				.create(BuildingService.class);
		AsyncCallback<List<Building>> callback = new AsyncCallback<List<Building>>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("fail");
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
