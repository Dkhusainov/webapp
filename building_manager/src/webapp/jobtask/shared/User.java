package webapp.jobtask.shared;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;


/**
 * User entity.
 *
 */
@Entity
@Table(name = "app_user")
public class User implements Serializable {
	private static final long serialVersionUID = 4964312034368502080L;

	@Id
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "BLOCKED")
	@Type(type="true_false")
	private boolean blocked;
	
	

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	

	public boolean isBlocked() {
		return blocked;
	}
	
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	


}
