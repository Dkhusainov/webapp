package webapp.jobtask.client;

import webapp.jobtask.shared.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.KeyDownEvent;

/**
 * Login widget for composition.
 * @author user
 *
 */
public class LoginComposite extends Composite {

	private static final Binder binder = GWT.create(Binder.class);
	@UiField Label errorLabel;
	@UiField TextBox nameField;
	@UiField PasswordTextBox passwordField;
	@UiField Button logInButton;
	@UiField Button signUpButton;

	interface Binder extends UiBinder<Widget, LoginComposite> {
	}

	public LoginComposite() {
		initWidget(binder.createAndBindUi(this));
	}
	
	/**
	 * Checks for the empty fields and sends User entity on server for login.
	 * @return true if succeeded  
	 */
	@UiHandler("logInButton")
	void onLogInButtonClick(ClickEvent event) {
		errorLabel.setVisible(false);
		if (nameField.getText().length() == 0) {
			error("Username is empty");
			return;
		}
		if (passwordField.getText().length() == 0) {
			error("Password is empty");
			return;
		}
		
		
		User user = new User();
		user.setName(nameField.getText());
		user.setPassword(passwordField.getText());
		
		//RPC call
		LoginServiceAsync service = (LoginServiceAsync) GWT.create(LoginService.class);
		AsyncCallback<User> callback = new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("THis is onFailure method in callback");
				
			}

			@Override
			public void onSuccess(User result) {
				System.out.println("HIadsfgfhjk");
				if (result == null) {
					error("Incorrect login");
					return;
				}
				if(result.isBlocked()) {
					error("Account is blocked");
					return;
				}
				
				if (!passwordField.getText().equals(result.getPassword())) {
					error("Incorrect password");
					return;
				}
				showAccount();
			}
		};
		service.logInUser(user, callback);
	}
	
	private void showAccount(){
		RootPanel panel = RootPanel.get();
		panel.clear();
		DataTableComposite table = new DataTableComposite();
		HorizontalPanel hpanel = new HorizontalPanel();
		hpanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		hpanel.setVerticalAlignment(HorizontalPanel.ALIGN_TOP);
		table.setStyleName("rootPanel");
		panel.add(table);
	}
	
	private void error(String s) {
		errorLabel.setText(s);
		errorLabel.setVisible(true);
	}
	/**
	 * Sends user data on server for reg.
	 * @param event
	 */
	@UiHandler("signUpButton")
	void onSignUpButtonClick(ClickEvent event) {
		errorLabel.setVisible(false);
		if (nameField.getText().length() == 0) {
			error("Username is empty");
			return;
		}
		if (passwordField.getText().length() == 0) {
			error("Password is empty");
			return;
		}
		
		User user = new User();
		user.setName(nameField.getText());
		user.setPassword(passwordField.getText());
		
		//RPC call
		LoginServiceAsync service = (LoginServiceAsync) GWT.create(LoginService.class);
		AsyncCallback<User> callback = new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(User result) {
				if (result == null) {
					error("Username is not available");
					return;
				}
				error("You are signed up");
			}
		};
		service.signUpUser(user, callback);
		
	}
	

	
	@UiHandler("nameField")
	void onNameFieldMouseUp(MouseUpEvent event) {
		errorLabel.setVisible(false);
	}
	
	@UiHandler("passwordField")
	void onPasswordFieldMouseUp(MouseUpEvent event) {
		errorLabel.setVisible(false);
	}
	
	@UiHandler("nameField")
	void onNameFieldKeyDown(KeyDownEvent event) {
		errorLabel.setVisible(false);
	}
	@UiHandler("passwordField")
	void onPasswordFieldKeyDown(KeyDownEvent event) {
		errorLabel.setVisible(false);
	}
}
