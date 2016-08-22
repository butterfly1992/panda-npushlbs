package lbs.push.action;

import javax.annotation.Resource;

import lbs.push.entity.Param;
import lbs.push.service.OperService;
import lbs.push.tool.Utils;
import lbs.push.tool.Variable;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 操作控制类
 * 
 * @author Administrator
 * 
 */
@Controller
public class OperAction {

	@Resource(name = "operservice")
	private OperService operservice;

	@RequestMapping(value = "/oper", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody
	Object oper(Param param) {// 操作统计
		if (Utils.isNULL(param.getAppid()) || Utils.isNULL(param.getImei())
				|| Utils.isNULL(param.getImsi()) || param.getWareindex() <= 0
				|| param.getOperate() < 0 || Utils.isNULL(param.getVersion())
				|| Utils.isNULL(param.getKey())) {
			Utils.log.error("[appid为" + param.getAppid() + "；imei为"
					+ param.getImei() + "；imsi为" + param.getImsi() + ";产品索引："
					+ param.getWareindex() + ";操作：" + param.getOperate()
					+ "；版本为" + param.getVersion() + "；key为" + param.getKey()
					+ ";]");
			return Variable.errorJson;
		}
		/**
		 * 验证key
		 */
		if (!Utils.getMD5(param.getImei()).equals(param.getKey())) {
			Utils.log.error("Key Error|key:" + param.getImei() + ";key:"
					+ param.getKey() + ";md:" + Utils.getMD5(param.getImei()));
			return Variable.errorJson;
		}
		if (Utils.isNULL(param.getSid())) {// 查询产品id
			String sid = operservice.getsid(param.getWareindex(),
					param.getOperate());
			Utils.log.info("other：" + sid + ";apid:" + param.getAppid() + ";");
			if (sid == null) {// 根据索引没查询出id
				return Variable.errorJson;
			}
			param.setSid(sid);
		}
		if (Variable.testId.contains(param.getAppid())) {// 测试id不统计数据
			return Variable.errorJson;
		}
		Utils.log.info("『操作：" + param.getOperate() + "；appid:"
				+ param.getAppid() + "；imei为" + param.getImei() + "；imsi为"
				+ param.getImsi() + "；sid:" + param.getSid() + ";』" + ";产品索引："
				+ param.getWareindex() + "；版本：" + param.getVersion());
		return operservice.oper(param);
	}
}
