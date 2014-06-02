package webapp.jobtask.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

/**
 * Entry point of the application.
 */
public class Core implements EntryPoint {

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * This is the entry point method.  
	 */ 
	public void onModuleLoad() { 

		RootPanel rootPanel = RootPanel.get();
		rootPanel.setStyleName("rootPanel");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		rootPanel.add(verticalPanel);
		verticalPanel.setSize("100%", "100%");
		
		LayoutPanel layoutPanel = new LayoutPanel();
		verticalPanel.add(layoutPanel);
		layoutPanel.setHeight("87px");
		
		LoginComposite loginComposite = new LoginComposite();
		loginComposite.setStyleName("align");
		verticalPanel.add(loginComposite);

	}
}



