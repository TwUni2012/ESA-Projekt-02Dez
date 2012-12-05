package services.taskplaner;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the TASK database table.
 * 
 */
@Entity
@Table(name="Task")
public class Task implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int dd;
	private int hh;
	private int m;
	private int mm;
	private int yyyy;
	
	private String tasktext;
	private String username;
	
	public Task() {
	}
	
	public Task(String username, String tasktext, int yyyy, int mm, int dd,
			int hh, int m) {
//		super(); // wird automatisch eingefuegt; zeile kann geloescht werden
		this.username = username;
		this.tasktext = tasktext;
		this.yyyy = yyyy;
		this.mm = mm;
		this.dd = dd;
		this.hh = hh;
		this.m = m;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDd() {
		return dd;
	}

	public void setDd(int dd) {
		this.dd = dd;
	}

	public int getHh() {
		return hh;
	}

	public void setHh(int hh) {
		this.hh = hh;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getMm() {
		return mm;
	}

	public void setMm(int mm) {
		this.mm = mm;
	}

	public String getTasktext() {
		return tasktext;
	}

	public void setTasktext(String tasktext) {
		this.tasktext = tasktext;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getYyyy() {
		return yyyy;
	}

	public void setYyyy(int yyyy) {
		this.yyyy = yyyy;
	}
	
	@Override
	public String toString() {
		return "Task for " +username + ", due " + yyyy +
				"-" + mm + "-" + dd + " " + (hh < 10 ? "0" : "") + 
				hh + ":" + (m < 10 ? "0" : "") + m + "\n" + tasktext;
	}
}