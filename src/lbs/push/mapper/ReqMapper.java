package lbs.push.mapper;

import java.util.List;

import lbs.push.entity.Adv;
import lbs.push.entity.Param;
import lbs.push.entity.Soft;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


/**
 * 请求映射接口
 * 
 * @author Administrator
 * 
 */
public interface ReqMapper {

	/**
	 * 返回测试id的产品信息
	 * 
	 * @param param
	 * @return
	 */
	List<Soft> getTestsoft(Param param);
	/**
	 * 返回测试id的实体信息
	 * 
	 * @param param
	 * @return
	 */
	List<Adv> getTestadv(Param param);

	
	
	/**
	 * 验证开关是否打开
	 * 
	 * @param param
	 * @return
	 */
	@Select("select id from zy_app where id=#{appid} and push_open_status=1")
	// push_open_status
	@Results({ @Result(column = "id", property = "appid") })
	public Param isSwitch(Param param);

	/**
	 * 正式id的实体广告请求
	 * 
	 * @param param
	 * @return
	 */
	Adv getadvResult(Param param);
	/**
	 * 正式id的产品广告请求
	 * 
	 * @param param
	 * @return
	 */
	Soft getsoftResult(Param param);

	/**
	 * 删除
	 * 
	 * @param param
	 * @return
	 */
	@Delete("DELETE FROM np_ori_req WHERE imei=#{imei} and imsi=#{imsi} ")
	public int delReq(Param param);
	
	/**
	 * 加入活跃用户记录
	 * 
	 * @param param
	 * @return
	 */
	@Insert("insert into np_ori_actuser(imeicount,gysdkv,time) values (1,#{version},#{time});")
	public int recordaddUser(Param param);

	/**
	 * 更新用户活跃记录
	 * 
	 * @param param
	 * @return
	 */
	@Update("update np_ori_actuser set imeicount=imeicount+1 where time=#{time} and gysdkv=#{version};")
	public int recordupdUser(Param param);
}
