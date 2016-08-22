package lbs.dingwei.location;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import lbs.push.tool.Utils;

public class Geocoder {
	private static final String httpurl = "http://apis.map.qq.com/ws/geocoder/v1/?";
	private static final String[] key = new String[] {
			"ZDYBZ-KDWKV-67VPK-U7AP5-R2627-4DFH7",
			"K6KBZ-ZIHWF-CHBJ7-J4EOE-562FV-PYFCL",
			"TIABZ-XW73G-IJBQN-IBVLQ-L4K5Q-3ABEF",
			"OIFBZ-OL6WU-HVLVJ-4IUKC-JBNX6-QFFAG",
			"FJRBZ-YY5KW-RD6RV-RGLYH-64WWS-WZF3H",
			"XZ3BZ-LOKR4-N7XU3-DKY4W-JKBP6-V6BV4"};
	

	/**
	 * 根据传递的经纬度，调用腾讯地图，解析出
	 * 
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> location(double longitude, double latitude)
			throws Exception {
		// 拼接字符串路径
		Map<String, Object> locationinfo = null;
		String getURL = httpurl + "location=" + latitude + "," + longitude
				+ "&key=" + key[(int) Math.round(Math.random()*(key.length-1))] + "&get_poi=1";
		URL getUrl = null;
		HttpURLConnection connection = null;
		getUrl = new URL(getURL);
		Utils.log.info(getUrl);
		// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据
		// URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
		try {
			connection = (HttpURLConnection) getUrl.openConnection();
			// 建立与服务器的连接，并未发送数据
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(10 * 1000);// 連接超時
			connection.setUseCaches(false); // 设置缓存
			connection.setRequestProperty("Charset", "utf-8");
			// 发送数据到服务器并使用Reader读取返回的数据
			if (connection.getResponseCode() == 200) { // 200表示请求成功
				InputStream is = connection.getInputStream(); // 以输入流的形式返回
				// 将输入流转换成字符串

				InputStreamReader isr = new InputStreamReader(is, "utf-8");// 一定要在这个地方才不会乱码(utf-8,gb2312)
				BufferedReader br = new BufferedReader(isr);// 利用BufferedReader将流转为String
				String jsonString = "";
				String temp;
				while ((temp = br.readLine()) != null) {
					jsonString = jsonString + temp;
				}
				br.close();
				is.close();
				// 转换成json数据处理
				JSONObject jsono = new JSONObject(jsonString);
				if (jsono.getInt("status") == 0) {
					JSONObject result = jsono.getJSONObject("result");
					JSONObject component = result
							.getJSONObject("address_component");
					locationinfo = new HashMap<String, Object>();
					locationinfo.put("status", jsono.getInt("status"));
					locationinfo.put("province",
							component.getString("province"));
					locationinfo.put("city", component.getString("city"));
					locationinfo.put("district",
							component.getString("district"));
					Utils.log.info(component.getString("province") + ";"
							+ component.getString("city"));
				} else {
					Utils.log.error("error:" + jsono.getInt("message"));
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return locationinfo;
	}
}
