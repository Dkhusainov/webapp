package webapp.jobtask.client;

import webapp.jobtask.shared.User;

import com.google.gwt.user.client.rpc.AsyncCallback;
/**
 * Async version of LoginService
 * @author user
 *
 */ 
public interface LoginServiceAsync {

	void logInUser(User user, AsyncCallback<User> callback);

	void signUpUser(User user, AsyncCallback<User> callback);


}
