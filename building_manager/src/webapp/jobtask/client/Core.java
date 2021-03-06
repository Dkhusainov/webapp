package webapp.jobtask.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

/**
 * Entry point of the application.
 */
public class Core implements EntryPoint {



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



