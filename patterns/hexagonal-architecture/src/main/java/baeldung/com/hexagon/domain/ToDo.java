package baeldung.com.hexagon.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ToDo {
	public static final long INVALID_ID = -1l;
	@Id
	@GeneratedValue
	private Long id;
	private String what;
	public Long getId() {
		return id;
	}
	public String getWhat() {
		return what;
	}
	
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setWhat(String what) {
		this.what = what;
	}
	public ToDo() {
		super();
	}
	public ToDo(String what) {
		this.id = INVALID_ID;
		this.what = what;
	}
	
	
	
	
public ToDo(Long id, String what) {
		super();
		this.id = id;
		this.what = what;
	}

	@Override
	public String toString() {
		return "ToDo [id=" + id + ", what=" + what + "]";
	}
	
}
