package lbs.push.entity;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 产品广告
 * @author Administrator
 *
 */

@JsonIgnoreProperties(value={"type","province","city","longitude","latitude","radius","code"})
public class Soft extends LBS implements Serializable{
	
	/**
	 */
	private static final long serialVersionUID = 1L;
	private String sid;// 产品id
	private String sname;// 产品名称
	private int advsoft=0;// 默认产品广告
	private String title;// 产品诱惑标题
	private String info1;// 产品短简介
	private String info2;// 产品长简介
	private String spck;// 产品包名
	private int code;// 产品优先级
	private int sindex;// 产品索引
	private String icon;// logo
	private String apk;// apk
	private String img01;//截图1
	private String img02;//截图2
	
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public int getAdvsoft() {
		return advsoft;
	}
	public void setAdvsoft(int advsoft) {
		this.advsoft = advsoft;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfo1() {
		return info1;
	}
	public void setInfo1(String info1) {
		this.info1 = info1;
	}
	
	public String getInfo2() {
		return info2;
	}
	public void setInfo2(String info2) {
		this.info2 = info2;
	}
	public String getSpck() {
		return spck;
	}
	public void setSpck(String spck) {
		this.spck = spck;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getSindex() {
		return sindex;
	}
	public void setSindex(int sindex) {
		this.sindex = sindex;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getApk() {
		return apk;
	}
	public void setApk(String apk) {
		this.apk = apk;
	}
	public String getImg01() {
		return img01;
	}
	public void setImg01(String img01) {
		this.img01 = img01;
	}
	
	public String getImg02() {
		return img02;
	}
	public void setImg02(String img02) {
		this.img02 = img02;
	}
	

}
