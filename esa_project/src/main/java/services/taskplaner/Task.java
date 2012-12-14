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

	private String tasktext;

	private String username;

	private int yyyy;
	
	@Override
	public String toString() 
	{
		return "Task for "+username+", due "+yyyy+"-"+mm+"-"+dd+" "+(hh<10?"0":"")+hh+":"+(m<10?"0":"")+m+"\n"+tasktext;
	}

	public Task() {
	}
	
	public Task(String username, String tasktext, int yyyy, int mm, int dd,
			int hh, int m) {
		super();
		this.username = username;
		this.tasktext = tasktext;
		this.yyyy = yyyy;
		this.mm = mm;
		this.dd = dd;
		this.hh = hh;
		this.m = m;
	}



	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDd() {
		return this.dd;
	}

	public void setDd(int dd) {
		this.dd = dd;
	}

	public int getHh() {
		return this.hh;
	}

	public void setHh(int hh) {
		this.hh = hh;
	}

	public int getM() {
		return this.m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getMm() {
		return this.mm;
	}

	public void setMm(int mm) {
		this.mm = mm;
	}

	public String getTasktext() {
		return this.tasktext;
	}

	public void setTasktext(String tasktext) {
		this.tasktext = tasktext;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getYyyy() {
		return this.yyyy;
	}

	public void setYyyy(int yyyy) {
		this.yyyy = yyyy;
	}

}