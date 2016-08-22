package lbs.push.entity;
/**
 * 用户请求记录
 * @author Administrator
 *
 */
public class Req {
	private String imei;
	private String imsi;
	private String nindex;//产品通知
	private String entindex;//实体广告通知
	private String setindex;//产品安装
	private String delindex;//实体广告查看详情
	private String time;

	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	 
	public String getNindex() {
		return nindex;
	}
	public void setNindex(String nindex) {
		this.nindex = nindex;
	}
	public String getEntindex() {
		return entindex;
	}
	public void setEntindex(String entindex) {
		this.entindex = entindex;
	}
	public String getSetindex() {
		return setindex;
	}
	public void setSetindex(String setindex) {
		this.setindex = setindex;
	}
	public String getDelindex() {
		return delindex;
	}
	public void setDelindex(String delindex) {
		this.delindex = delindex;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}
