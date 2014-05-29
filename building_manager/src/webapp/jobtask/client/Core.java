package webapp.jobtask.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Core implements EntryPoint {

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * This is the entry point method.  
	 */ 
	public void onModuleLoad() {  

		RootPanel rootPanel = RootPanel.get("nameFieldContainer"); 

		LoginComposite loginComposite = new LoginComposite();
		rootPanel.add(loginComposite, 415, 181);

	}
}
