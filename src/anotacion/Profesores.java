package anotacion;


import java.util.HashSet;
import java.util.Set;
// ------------------------------------------
// - imports para mapeos


import javax.persistence.Entity;  
import javax.persistence.Id;  
import javax.persistence.Table;
import javax.persistence.Column;

// ------------------------
/**
 * Mapeo mediante anotaciones de la tabla profesore
 */
@Entity
@Table(name= "profesores") 
public class Profesores implements java.io.Serializable {
	
@Id
	private String idn;
@Column(name = "APENOM")
	private String apenom;
@Column(name = "DIRECC")
	private String direcc;
@Column(name = "PROV")
	private String prov;
@Column(name = "EMAIL")
	private String email;
	

	public Profesores() {
	}

	public Profesores(String idn) {
		this.idn = idn;
	}

	public Profesores(String idn, String apenom, String direcc, String prov, String email) {
		this.idn = idn;
		this.apenom = apenom;
		this.direcc = direcc;
		this.prov = prov;
		this.email = email;
		
	}

	public String getIdn() {
		return this.idn;
	}

	public void setIdn(String idn) {
		this.idn = idn;
	}

	public String getApenom() {
		return this.apenom;
	}

	public void setApenom(String apenom) {
		this.apenom = apenom;
	}

	public String getDirecc() {
		return this.direcc;
	}

	public void setDirecc(String direcc) {
		this.direcc = direcc;
	}

	public String getProv() {
		return this.prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}
