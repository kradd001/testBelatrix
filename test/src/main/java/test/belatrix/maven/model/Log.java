package test.belatrix.maven.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "LOG" database table.
 * 
 */
@Entity
@Table(name="\"LOG\"")
@NamedQuery(name="Log.findAll", query="SELECT l FROM Log l")
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	private String descrip;

	private String tipolog;

	public Log() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescrip() {
		return this.descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	public String getTipolog() {
		return this.tipolog;
	}

	public void setTipolog(String tipolog) {
		this.tipolog = tipolog;
	}

}