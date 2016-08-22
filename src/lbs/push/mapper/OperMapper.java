package lbs.push.mapper;

import lbs.push.entity.Param;
import lbs.push.entity.Req;

import org.apache.ibatis.annotations.Select;

/**
 * 操作映射接口
 * 
 * @author Administrator
 * 
 */
public interface OperMapper {

	/**
	 * 根据产品索引查出产品id
	 */
	@Select("SELECT id FROM zy_soft WHERE wareindex =#{wareindex}")
	public String getsid(int wareindex);
	/**
	 * 根据实体索引获取实体id
	 * @param wareindex
	 * @return
	 */
	@Select("SELECT id FROM zy_adv WHERE ADVINDEX =#{wareindex}")
	public String getaid(int wareindex);

	/**
	 * 根据用户查出相对应的操作索引记录
	 * 
	 * @param param
	 * @return
	 */
	@Select("SELECT imei,imsi, IFNULL(nindex,'') nindex,IFNULL(entindex,'') entindex,IFNULL(setindex,'') setindex,IFNULL(delindex,'') delindex,TIME FROM np_ori_req  WHERE imei =#{imei} and imsi=#{imsi} order by time limit 0,1")
	public Req getIndexs(Param param);

	/**
	 * 插入新用户通知索引记录
	 * 
	 * @param param
	 * @return
	 */
	public int insertIndex(Param param);


	/**
	 * 更新用户索引记录
	 * 
	 * @param req
	 * @return
	 */
	public int updateIndex(Req req);// 更新索引

	/**
	 * 更新产品操作
	 * @param param
	 * @return
	 */
	public int updateOper(Param param); 

	/**
	 * 添加产品操作
	 * @param param
	 * @return
	 */
	public int insertOper(Param param); 

	/**
	 * 插入通知安装表
	 * @param param
	 * @return
	 */
	public int insertNoticeSetup(Param param);

	/**
	 * 更新通知安装表
	 * @param param
	 * @return
	 */
	public int updateNoticeSetup(Param param);

	/**
	 * 更新实体广告操作
	 * @param param
	 * @return
	 */
	public int updateEntOper(Param param);

	/**
	 * 添加实体广告操作
	 * @param param
	 * @return
	 */
	public int insertEntOper(Param param);

	
}
