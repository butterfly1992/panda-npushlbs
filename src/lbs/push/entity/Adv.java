package lbs.push.entity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 实体广告
 * @author Administrator
 *
 */
@JsonIgnoreProperties(value={"type","province","city","longitude","latitude","radius","priority"})
public class Adv extends LBS implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String aid;
	private String advname;
	private String info;
	private int advsoft=1;
	private String icon;
	private String linkurl;
	private int advindex;
	private int priority;
	public String getAid() {
		return aid;
	}
	public void setAid(String aid) {
		this.aid = aid;
	}
	public String getAdvname() {
		return advname;
	}
	public void setAdvname(String advname) {
		this.advname = advname;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getAdvsoft() {
		return advsoft;
	}
	public void setAdvsoft(int advsoft) {
		this.advsoft = advsoft;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getLinkurl() {
		return linkurl;
	}
	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}
	public int getAdvindex() {
		return advindex;
	}
	public void setAdvindex(int advindex) {
		this.advindex = advindex;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
}
