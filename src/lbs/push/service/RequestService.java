package lbs.push.service;

import lbs.push.entity.Param;

/**
 * 请求业务类
 * 
 * @author Administrator
 * 
 */
public interface RequestService {
	/**
	 * 测试id返回数据
	 * 
	 * @param param
	 * @return
	 */
	public Object getTestResult(Param param);

	/**
	 * 广告开关的验证
	 * 
	 * @param param
	 * @return
	 */
	public boolean isSwitch(Param param);

	/**
	 * 正式id的请求
	 * 
	 * @param param
	 * @return
	 */
	public Object getResult(Param param);
	/**
	 * 黑名单id的请求
	 * 
	 * @param param
	 * @return
	 */
	public Object getBlacklistResult(Param param);

	/**
	 * 内测时，根据用户删除相对应用户的产品索引记录
	 * 
	 * @param param
	 * @return
	 */
	public boolean delReq(Param param);

}
