package lbs.push.entity;



/**
 * 定位广告类型
 * 
 * @author Administrator
 * 
 */
public class LBS {
	private int type;//广告类型
	private String province;//省
	private String city;//市
	private double longitude;//经度
	private double latitude;//纬度
	private double radius;//半径范围

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

}
