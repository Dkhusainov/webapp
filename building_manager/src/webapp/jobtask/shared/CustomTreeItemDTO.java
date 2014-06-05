package webapp.jobtask.shared;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tree")
public class CustomTreeItemDTO implements Serializable {
	
	public CustomTreeItemDTO(Long parentId, Long buildingId, String name,
			String description) {
		super();
		this.parentId = parentId;
		this.buildingId = buildingId;
		this.name = name;
		this.description = description;
	}
	@Id
	@GeneratedValue
	private Long id;
	private Long parentId;
	private Long buildingId;
	private String name;
	private String description;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
