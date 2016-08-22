package lbs.push.service.impl;

import lbs.push.entity.Param;
import lbs.push.entity.Req;
import lbs.push.mapper.OperMapper;
import lbs.push.service.OperService;
import lbs.push.tool.Utils;
import lbs.push.tool.Variable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作实现类
 * 
 * @author Administrator
 * 
 */
@Repository(value = "operservice")
@Transactional
public class OperServiceImpl implements OperService {

	private OperMapper operMapper;

	/* 0：产品通知，1：产品全屏，2：产品点击 3：产品下载 4：产品安装 */
	/* 5：实体通知，6：实体查看详情 */
	@Override
	public Object oper(Param param) {// 产品操作
		// TODO Auto-generated method stub
		String time = Utils.DateTime();
		int oper = param.getOperate();
		param.setTime(time);
		int flag = 0;
		if (oper == 0) {// 产品通知
			Req req = operMapper.getIndexs(param);// 获取之前通知记录
			if (req == null) {// 未存在记录，插入新数据
				flag = operMapper.insertIndex(param);
			} else {// 存在通知记录
				String nindex = req.getNindex();// 获取通知索引
				if (isContain(nindex, param.getWareindex() + "")) {// 已经通知过，不记录
					// 在有效期内的已经展示过
					return Variable.errorJson;
				} else {// 未通知过，记录
					req.setNindex(nindex.concat("," + param.getWareindex()));
					flag = operMapper.updateIndex(req);
				}
			}
			if (flag > 0) {// 更新操作
				flag = operMapper.updateNoticeSetup(param);
				if (flag == 0) {
					flag = operMapper.insertNoticeSetup(param);
				}
				flag = operMapper.updateOper(param);
				if (flag == 0) {// 插入操作
					flag = operMapper.insertOper(param);
				}
			} else {
				return Variable.errorJson;
			}

		} else if (oper == 1) {// 全屏
			flag = operMapper.updateOper(param);
			if (flag != 1) {
				flag = operMapper.insertOper(param);
			}
		} else if (oper == 2) {// 点击
			flag = operMapper.updateOper(param);
			if (flag != 1) {
				flag = operMapper.insertOper(param);
			}
		} else if (oper == 3) {// 下载
			flag = operMapper.updateOper(param);
			if (flag != 1) {
				flag = operMapper.insertOper(param);
			}
		} else if (oper == 4) {// 安装
			Req req = operMapper.getIndexs(param);
			if (req == null) {
				// 没有安装过
				flag = operMapper.insertIndex(param);
			} else {
				String sindex = req.getSetindex();
				if (isContain(sindex, param.getWareindex() + "")) {
					// 已经安装过,
					return Variable.errorJson;
				}
				// 未安装过，拼接更新数据库
				req.setSetindex(sindex.concat("," + param.getWareindex()));
				flag = operMapper.updateIndex(req);
			}
			if (flag == 1) {
				flag = operMapper.updateNoticeSetup(param);
				if (flag == 0) {
					flag = operMapper.insertNoticeSetup(param);
				}
				flag = operMapper.updateOper(param);
				if (flag == 0) {
					flag = operMapper.insertOper(param);
				}
			} else {
				return Variable.errorJson;
			}
		} else if (oper == 5) {// 实体广告通知
			Req req = operMapper.getIndexs(param);
			if (req == null) {
				// 实体广告通知过
				flag = operMapper.insertIndex(param);
			} else {
				String entindex = req.getEntindex();
				if (isContain(entindex, param.getWareindex() + "")) {
					// 已经安装过,
					return Variable.errorJson;
				}
				// 未安装过，拼接更新数据库
				req.setEntindex(entindex.concat("," + param.getWareindex()));
				flag = operMapper.updateIndex(req);
			}
			if (flag == 1) {
				flag = operMapper.updateEntOper(param);
				if (flag == 0) {
					flag = operMapper.insertEntOper(param);
				}
			} else {
				return Variable.errorJson;
			}
		} else if (oper == 6) {// 实体广告查看详情
			Req req = operMapper.getIndexs(param);
			if (req == null) {
				// 没有查看过详情
				flag = operMapper.insertIndex(param);
			} else {
				String delindex = req.getDelindex();
				if (isContain(delindex, param.getWareindex() + "")) {
					// 已经查看过
					return Variable.errorJson;
				}
				// 未安装过，拼接更新数据库
				req.setDelindex(delindex.concat("," + param.getWareindex()));
				flag = operMapper.updateIndex(req);
			}
			if (flag == 1) {
				flag = operMapper.updateEntOper(param);
				if (flag == 0) {
					flag = operMapper.insertEntOper(param);
				}
			} else {
				return Variable.errorJson;
			}
		} else {
			return Variable.errorJson;
		}
		if (flag > 0) {
			return Variable.correntJson;
		} else
			return Variable.errorJson;
	}

	/**
	 * 根据广告索引获取到广告id
	 */
	@Override
	public String getsid(int wareindex, int operate) {
		// TODO Auto-generated method stub
		String id = null;
		if (operate <= 4) {
			id = operMapper.getsid(wareindex);
		} else {
			id = operMapper.getaid(wareindex);
		}
		return id;
	}

	@Autowired
	public void setOperMapper(OperMapper operMapper) {
		this.operMapper = operMapper;
	}

	public boolean isContain(String s, String c) {
		String a[] = s.split(",");
		for (int i = 0; i < a.length; i++) {
			if (a[i].equals(c)) {
				return true;
			}
		}
		return false;
	}
}
