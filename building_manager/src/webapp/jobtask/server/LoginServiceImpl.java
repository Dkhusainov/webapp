package webapp.jobtask.server;

import webapp.jobtask.client.LoginService;
import webapp.jobtask.shared.User;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
/**
 * Server side implementation of a LoginService.
 * @author user
 *
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {
	
	public User logInUser(User user) {
		return null;
	} 

}
