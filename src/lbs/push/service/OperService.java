package lbs.push.service;

import lbs.push.entity.Param;


public interface OperService {
	/**
	 * 产品操作
	 * 
	 * @param param
	 * @return
	 */
	public Object oper(Param param);


	/**
	 * 查询产品id
	 * @param operate 
	 * 
	 * @param wareindex
	 * @return
	 */
	public String getsid(int wareindex, int operate);  

}
