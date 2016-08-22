package lbs.push.entity;

public class Param {
	
	private String appid;// 应用id
	private String imei;// 手机串码
	private String imsi;// 手机卡串码
	private String version;// sdk版本
	private String key;// 请求密钥
	
	private double longt;//经度
	private double lat;//纬度
	private int operation;//运营商
	
	private int code;//上次广告优先级
	
	
	//操作使用
	private int operate;//操作参数
	private String sid;//产品id
	private int wareindex;//产品索引
	private String time;
	
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
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
 
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public double getLongt() {
		return longt;
	}
	public void setLongt(double longt) {
		this.longt = longt;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
		this.operation = operation;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getOperate() {
		return operate;
	}
	public void setOperate(int operate) {
		this.operate = operate;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public int getWareindex() {
		return wareindex;
	}
	public void setWareindex(int wareindex) {
		this.wareindex = wareindex;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
 
	
}
