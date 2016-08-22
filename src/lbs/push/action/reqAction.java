package lbs.push.action;

import lbs.push.entity.Param;
import lbs.push.service.RequestService;
import lbs.push.tool.MemcacheUtil;
import lbs.push.tool.Utils;
import lbs.push.tool.Variable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 请求action入口
 * 
 * @author Administrator
 * 
 */
@Controller
public class reqAction {

	private RequestService reqservice;

	@RequestMapping(value = "/request", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	Object validate(Param param) {// 请求返回数据方法
		Object obejct = null;
		// 一系列参数验证后
		if (Utils.isNULL(param.getAppid()) || Utils.isNULL(param.getImei())
				|| Utils.isNULL(param.getVersion()) || (param.getKey() == null)) {
			Utils.log.error("[appid为" + param.getAppid() + "；imei为"
					+ param.getImei() + "；版本为" + param.getVersion() + "；key： "
					+ param.getKey() + ";]");
			return Variable.errorJson;
		}
		/**
		 * 验证key
		 */
		// if (!Utils.getMD5(param.getImei()).equals(param.getKey())) {
		// Utils.log.error("Key Error|key:" + param.getImei() + ";key:"
		// + param.getKey() + ";md:" + Utils.getMD5(param.getImei()));
		// return Variable.errorJson;
		// }
		Utils.log
				.info("-----------------------------lbsstart-------------------------------------");
		if (Utils.isNULL(param.getImsi())) {
			param.setImsi("111111111111111");// 如果没sim卡，用临时的号
			Utils.log.error("[imei为" + param.getImei() + "；imei为kong");
		}
		param.setTime(Utils.DateTime());
		/**
		 * 测试id请求数据
		 */
		if (Variable.testId.contains(param.getAppid())) {
			obejct = reqservice.getTestResult(param);
			if (obejct == null) {
				return Variable.errorJson;
			} else
				return obejct;
		}
		/**
		 * 黑名单id请求数据
		 */
		if (Variable.blacklistId.contains(param.getAppid())) {
			obejct = reqservice.getBlacklistResult(param);
			if (obejct == null) {
				return Variable.errorJson;
			} else
				return obejct;
		}
		/******************* 正式id请求开始 ******************************************************************/
		// 正式用户开始请求
		try {
			if (reqservice.isSwitch(param)) {// 判断开关是否打开
				obejct = reqservice.getResult(param);
				if (obejct == null) {
					Utils.log.info("result:『" + null + "』");
					return Variable.errorJson;
				} else
					Utils.log.info("result:『" + obejct + "』");
				return obejct;
			} else {
				Utils.log
						.error("『appid:" + param.getAppid() + ";switch:close』");
				return Variable.switcherror;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		}
		return obejct;

	}

	@Autowired
	public void setReqservice(RequestService reqservice) {
		this.reqservice = reqservice;
	}

	@RequestMapping(value = "/clearmem", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody
	Object deleteme(Param param) {// 删除memcached用户数据
		if (Utils.isNULL(param.getImei())) {
			Utils.log.error(" imei为null");
			return Variable.errorJson;
		}
		if (Utils.isNULL(param.getImsi())) {
			param.setImsi("111111111111111");
		}
		boolean res = false;
		if (MemcacheUtil.mcc.keyExists("lbsp_req" + param.getImei()
				+ param.getImsi())) {// 清空使用过的机会
			res = MemcacheUtil.mcc.delete("lbsp_req" + param.getImei()
					+ param.getImsi());
		}
		if (!Utils.isNULL(param.getKey())) {
			String key = param.getKey();
			if (key.equals("clear")) {
				res=reqservice.delReq(param);
			} else if (key.equals("head")) {
				res=MemcacheUtil.mcc.delete("lbs_soft_code" + param.getImei()
						+ param.getImsi());
				res=MemcacheUtil.mcc.delete("lbs_adv_code" + param.getImei()
						+ param.getImsi());
			}
		}
		if (res) {
			return Variable.correntJson+param.getKey();
		} else
			return Variable.errorJson+param.getKey();
	}

	@RequestMapping(value = "/testclear", method = { RequestMethod.GET })
	public @ResponseBody
	Object testclear(Param param) {// 删除memcached用户数据
		boolean res = false;
		if (MemcacheUtil.mcc.keyExists("lbsp_balcklist")) {// 清空使用过的机会
			res = MemcacheUtil.mcc.delete("lbsp_balcklist");
		}
		if (MemcacheUtil.mcc.keyExists("Test_adv" + param.getImei())) {// 清空使用过的机会
			res = MemcacheUtil.mcc.delete("Test_adv" + param.getImei());
		}
		if (MemcacheUtil.mcc.keyExists("Test_soft" + param.getImei())) {// 清空使用过的机会
			res = MemcacheUtil.mcc.delete("Test_soft" + param.getImei());
		}
		if (res) {
			return Variable.correntJson;
		} else
			return Variable.errorJson;
	}
}
