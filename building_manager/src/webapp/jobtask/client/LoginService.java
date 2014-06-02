package webapp.jobtask.client;

import webapp.jobtask.shared.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side for the RPC service.
 */
@RemoteServiceRelativePath("loginService")
public interface LoginService extends RemoteService {
	/**
	 * Sends a login request on the server.
	 * @return returns null if wrong username, otherwise User entity with.
	 */
	User logInUser (User user);
	User signUpUser (User user);
	
}
