package lbs.push.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import lbs.dingwei.location.Geocoder;
import lbs.dingwei.location.Range;
import lbs.push.entity.Adv;
import lbs.push.entity.LBS;
import lbs.push.entity.Param;
import lbs.push.entity.Soft;
import lbs.push.mapper.ReqMapper;
import lbs.push.service.RequestService;
import lbs.push.tool.MemcacheUtil;
import lbs.push.tool.Utils;
import lbs.push.tool.Variable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("reqservice")
@Transactional
public class RequestServiceImpl implements RequestService {

	@Autowired
	private ReqMapper reqMapper;

	/**
	 * 测试id返回数据
	 */
	@Override
	public Object getTestResult(Param param) {
		// TODO Auto-generated method stub
		List<Soft> softs = null;// 产品广告
		List<Adv> advs = null;// 实体广告
		Object o = null;

		if (MemcacheUtil.mcc.get("Test_adv_flag" + param.getImei()) == null) {// 实体广告标识，为空出实体广告
			if (MemcacheUtil.mcc.get("Test_adv" + param.getImei()) != null) {// 判断mem中是否已经存储过了，存储过了直接提取
				advs = (List<Adv>) MemcacheUtil.mcc.get("Test_adv" + param.getImei());
			} else {
				advs = reqMapper.getTestadv(param);
			}
			if (!(advs == null || advs.isEmpty())) {// 获取品牌广告
				o = advs.get(0);
				advs.remove(0);
				MemcacheUtil.mcc.set("Test_adv" + param.getImei(), advs, getDefinedDateTime(23, 59, 59, 0));
				MemcacheUtil.mcc.set("Test_adv_flag" + param.getImei(), false, getDefinedDateTime(23, 59, 59, 59));
			} else {
				MemcacheUtil.mcc.delete("Test_adv" + param.getImei());
			}
		} else {
			if (MemcacheUtil.mcc.get("Test_soft" + param.getImei()) != null) {// 判断mem中是否已经存储过了，存储过了直接提取
				softs = (List<Soft>) MemcacheUtil.mcc.get("Test_soft" + param.getImei());
			} else {
				softs = reqMapper.getTestsoft(param);
			}
			if (!(softs == null || softs.isEmpty())) {// 获取品牌广告
				o = softs.get(0);
				softs.remove(0);
				MemcacheUtil.mcc.set("Test_soft" + param.getImei(), softs, getDefinedDateTime(23, 59, 59, 0));
				MemcacheUtil.mcc.delete("Test_adv_flag" + param.getImei());
			} else {
				MemcacheUtil.mcc.delete("Test_soft" + param.getImei());
			}
		}
		return o;
	}

	@Override
	public boolean isSwitch(Param param) {
		// TODO Auto-generated method stub
		Param p = reqMapper.isSwitch(param);
		boolean result = (p == null) ? false : true;
		return result;
	}

	@Override
	public Object getResult(Param param) {
		// TODO Auto-generated method stub
		Object obj;
		Utils.log.info("日志开始");
		Utils.log.info("【imei：" + param.getImei() + "；imsi：" + param.getImsi() + "；appid：" + param.getAppid() + "；sdkv："
				+ param.getVersion() + "】");
		// 1.初始化每天的请求次数
		int lbsptimes = initTimes(param);
		if (lbsptimes <= 0) {
			Utils.log.error("End【imei：" + param.getImei() + "；imsi：" + param.getImsi() + "；appid：" + param.getAppid()
					+ "；times：" + lbsptimes + "】");
			return null;
		}
		int hour = Integer.parseInt(Utils.DateTime("HH"));// 获取当前请求时间
		/* 时间范围设定 */
		if (hour >= 3 && hour < 6) {
			return null;
		} else {
			obj = push(param, lbsptimes);
		}
		return obj;
	}

	/**
	 * 查询广告，返回数据
	 * 
	 * @param param
	 *            用户参数
	 * @param lbsptimes
	 *            广告机会
	 * @return
	 */
	private Object push(Param param, int lbsptimes) {
		// TODO Auto-generated method stub
		param = Utils.analyzeImsi(param);
		Object o = null;
		int code = 0;
		if (MemcacheUtil.mcc.get("lbs_adv_flag" + param.getImei() + param.getImsi()) == null) {// 实体广告标识
			if (MemcacheUtil.mcc.get("lbs_adv_code" + param.getImei() + param.getImsi()) == null) {// 首次出现实体广告
				code = 0;
			} else {
				code = (int) MemcacheUtil.mcc.get("lbs_adv_code" + param.getImei() + param.getImsi());
			}
			param.setCode(code);
			Adv adv = reqMapper.getadvResult(param);
			if (adv != null) {// 查询到实体广告
				LBS lbs = adv;
				Utils.log.info("[result:ADV:" + lbs.getType() + ";aname:" + adv.getAdvname() + ";aindex:"
						+ adv.getAdvindex() + "]");
				if (matchLBSAdv(lbs, param) != null) {
					o = adv;
					Utils.log.info("【appid:" + param.getAppid() + ";imei:" + param.getImei() + ";imsi:"
							+ param.getImsi() + ";Times:" + (lbsptimes - 1) + ";】");
					MemcacheUtil.mcc.set("lbsp_req" + param.getImei() + param.getImsi(), lbsptimes - 1,
							getDefinedDateTime(23, 59, 59, 59));// 减少广告次数
					MemcacheUtil.mcc.set("lbs_adv_flag" + param.getImei() + param.getImsi(), false,
							getDefinedDateTime(23, 59, 59, 59));// 保证下次出现产品
				}
				MemcacheUtil.mcc.set("lbs_adv_code" + param.getImei() + param.getImsi(), adv.getPriority());// 设置优先级
				return o;
			} else {// 没有查询到，从0开始
				MemcacheUtil.mcc.set("lbs_adv_code" + param.getImei() + param.getImsi(), 0);// 设置优先级
			}
		} else {// 产品广告
			if (MemcacheUtil.mcc.get("lbs_soft_code" + param.getImei() + param.getImsi()) == null) {// 首次出现实体广告
				code = 0;
			} else {
				code = (int) MemcacheUtil.mcc.get("lbs_soft_code" + param.getImei() + param.getImsi());
			}
			param.setCode(code);
			Soft soft = reqMapper.getsoftResult(param);
			if (soft != null) {// 查询到符合的产品
				LBS lbs = soft;
				Utils.log.info("[result：Soft:" + lbs.getType() + ";sname:" + soft.getSname() + ";sindex:"
						+ soft.getSindex() + "]");
				if (matchLBSAdv(lbs, param) != null) {
					o = soft;
					Utils.log.info("【appid:" + param.getAppid() + ";imei:" + param.getImei() + ";imsi:"
							+ param.getImsi() + ";Times:" + (lbsptimes - 1) + ";】");
					MemcacheUtil.mcc.set("lbsp_req" + param.getImei() + param.getImsi(), lbsptimes - 1,
							getDefinedDateTime(23, 59, 59, 59));// 减少广告次数
					MemcacheUtil.mcc.delete("lbs_adv_flag" + param.getImei() + param.getImsi());// 保证下次出现实体

				}
				MemcacheUtil.mcc.set("lbs_soft_code" + param.getImei() + param.getImsi(), soft.getCode());// 设置优先级
				return o;
			} else {// 从0开始返回产品
				MemcacheUtil.mcc.set("lbs_soft_code" + param.getImei() + param.getImsi(), 0);// 设置优先级
			}
		}
		return null;
	}

	/**
	 * 匹配解析广告类型
	 * 
	 * @param lbs
	 *            广告对象
	 * @param param
	 *            用户参数
	 * @return
	 */

	private Object matchLBSAdv(LBS lbs, Param param) {
		// TODO Auto-generated method stub
		int type = lbs.getType();
		if (type == 0) {// 品牌广告，无需配置，直接返回
			return Variable.correntJson;
		} else {
			double longt = param.getLongt();// 获取经度
			double lat = param.getLat();// 获取纬度
			Utils.log.info("jing:" + longt + "；wei:" + lat);

			if (longt == 0.0 || lat == 0.0) {// 为传递经纬度坐标
				return null;
			}
			if (type == 1) {// 判断是大范围广告
				try {
					Map<String, Object> data = Geocoder.location(longt, lat);
					if (data == null) {// 没有解析出数据
						return null;
					} else {
						// 判断省份不为空，并且匹配成功
						if ((!Utils.isNULL(lbs.getProvince()))
								&& lbs.getProvince().contains(data.get("province").toString())) {
							// 市为空，说明是定位到省，返回数据
							if ((Utils.isNULL(lbs.getCity()))) {
								return Variable.correntJson;
							} else if ((!Utils.isNULL(lbs.getCity()))
									&& data.get("city").toString().contains(lbs.getCity())) {// 市不为空，并且匹配成功
								return Variable.correntJson;
							} else {// 其他条件，返回null
								return null;
							}
						} else {// 省匹配失败
							return null;
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			} else if (type == 2) {// 商业圈
				double range = Range.getDistance(longt, lat, lbs.getLongitude(), lbs.getLatitude());// 获取两点之间距离
				Utils.log.info("rang---" + range + "m;radius:" + lbs.getRadius());
				if (range <= lbs.getRadius()) {// 匹配是否在商业圈的范围内
					return Variable.correntJson;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
	}

	@Override
	public Object getBlacklistResult(Param param) {
		// TODO Auto-generated method stub
		Utils.log.info("黑名单数据");
		return null;
	}

	/**
	 * 内测时删除用户的产品索引记录
	 */
	@Override
	public boolean delReq(Param param) {
		// TODO Auto-generated method stub
		if (reqMapper.delReq(param) > 0)
			return true;
		else
			return false;
	}

	/**
	 * 获取截止时间
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 * @param milliSecond
	 * @return
	 */
	private Date getDefinedDateTime(int hour, int minute, int second, int milliSecond) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		cal.set(Calendar.MILLISECOND, milliSecond);
		Date date = new Date(cal.getTimeInMillis());
		return date;
	}

	/**
	 * 初始化用户每天的请求次数
	 * 
	 * @param param
	 * @return
	 */
	private int initTimes(Param param) {
		// TODO Auto-generated method stub
		int times = 0;
		if (MemcacheUtil.mcc.get("lbsp_req" + param.getImei() + param.getImsi()) == null) {
			/* 初始化广告次数 */
			times = 8;
			/** 额外加的测试活跃用户量 */
			int actuser = 0;
			if (!param.getImsi().equals("111111111111111")) {// 真有卡用户
				
				if (MemcacheUtil.mcc.get("lbsp_actout_" + param.getAppid()) == null) {
					actuser = 1;
				} else {
					actuser = Integer.parseInt(MemcacheUtil.mcc.get("lbsp_actout_" + param.getAppid()).toString()) + 1;// 获取用户数
				}
				MemcacheUtil.mcc.set("lbsp_actout_" + param.getAppid(), actuser, getDefinedDateTime(23, 59, 59, 0));
				Utils.log.info("[lbspTappid：" + param.getAppid() + "，Tactuser:" + actuser + "]");
			} else {
				if (MemcacheUtil.mcc.get("lbsp_factout_" + param.getAppid()) == null) {
					actuser = 1;
				} else {
					actuser = Integer.parseInt(MemcacheUtil.mcc.get("lbsp_factout_" + param.getAppid()).toString()) + 1;// 获取用户数
				}
				MemcacheUtil.mcc.set("lbsp_factout_" + param.getAppid(), actuser, getDefinedDateTime(23, 59, 59, 0));
				Utils.log.info("[lbspFappid：" + param.getAppid() + "，Factuser:" + actuser + "]");
			}
			/** 额外加的测试活跃用户量end */
			int flag = reqMapper.recordupdUser(param);
			if (flag == 0) {
				reqMapper.recordaddUser(param);
			}

		} else {
			times = (int) MemcacheUtil.mcc.get("lbsp_req" + param.getImei() + param.getImsi());
			Utils.log.info("lbsTimes：" + times + "】");
		}
		return times;
	}

}
