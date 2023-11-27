package ch.bbzbl.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "GeneralEntity.findEntityById", query = "select e from GeneralEntity e where e.id = :entityId")
public class GeneralEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String FIND_ENTITY_BY_ID = "GeneralEntity.findEntityById";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GeneralEntity) {
			GeneralEntity generalEntity = (GeneralEntity) obj;
			return generalEntity.getId() == id;
		}

		return false;
	}
}