package lbs.dingwei.location;


/**
 * 范围圈
 * @author Administrator
 *
 */
public class Range {
	private final static double PI = 3.14159265358979323; // 圆周率
	private final static double R = 6371229; // 地球直径

	/**
	 * 获取用户与本次广告的距离
	 * @param longt1 用户经度
	 * @param lat1 用户纬度
	 * @param longt2 本次广告经度
	 * @param lat2 本次广告纬度
	 * @return 两点坐标间的距离
	 */
	public static double getDistance(double longt1, double lat1, double longt2,
			double lat2) {
		double x, y, distance;
		x = (longt2 - longt1) * PI * R
				* Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
		y = (lat2 - lat1) * PI * R / 180;
		distance = Math.hypot(x, y);
		return distance;
	}
}
