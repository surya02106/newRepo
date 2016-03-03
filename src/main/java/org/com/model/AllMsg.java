package org.com.model;

import javax.persistence.*;


@Entity
@Table(name="allmsg")
public class AllMsg extends EntityObject{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column
	private int id;
	
	@Column
	private String frommsg;
	
	@Column
	private String tomsg;
	
	@Column
	private String flag;
	
	@Column
	private String timedate;
	
	@Column
	private String msgs;
	
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFrommsg() {
		return frommsg;
	}

	public void setFrommsg(String frommsg) {
		this.frommsg = frommsg;
	}

	public String getTomsg() {
		return tomsg;
	}

	public void setTomsg(String tomsg) {
		this.tomsg = tomsg;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTimedate() {
		return timedate;
	}

	public void setTimedate(String timedate) {
		this.timedate = timedate;
	}

	public String getMsgs() {
		return msgs;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}

	public void setMsgs(String msgs) {
		this.msgs = msgs;
	}
	public String toString(){
		return "{id : "+id+", frommsg : "+frommsg+", tomsg : "+tomsg+", flag : "+flag+", timedate : "+timedate+", msgs : "+msgs+", status : "+status+"}";
	}
}
