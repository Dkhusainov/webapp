package webapp.jobtask.client;

import webapp.jobtask.shared.User;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side for the RPC service.
 */
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
	/**
	 * Ques db and returns User entity depending on username matching.
	 */
	User logInUser (User user);
	
}
