package webapp.jobtask.shared;

import java.io.Serializable;


/**
 * User bean for RPC
 *
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private boolean blocked;
	private String name;
	private String password;
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public User(String name) {
		super();
		this.name = name;
	}
}
