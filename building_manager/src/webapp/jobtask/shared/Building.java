package webapp.jobtask.shared;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Building entity for persisting.
 * @author user
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="BUILDING")
public class Building implements Serializable {
	
	@Id
	@GeneratedValue
	private Long id;
	private String address;
	@Column(name = "bdate")
	private Date date;
	private int floors;
	private int area;
	private String globalId;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getFloors() {
		return floors;
	}
	public void setFloors(int floors) {
		this.floors = floors;
	}
	public int getArea() {
		return area;
	}
	public void setArea(int area) {
		this.area = area;
	}
	public String getGlobalId() {
		return globalId;
	}
	public void setGlobalId(String globalId) {
		this.globalId = globalId;
	}
	@Override
	public String toString() {
		return "Building [id=" + id + ", address=" + address + ", date=" + date
				+ ", floors=" + floors + ", area=" + area + ", globalId="
				+ globalId + "]";
	}
	
}
