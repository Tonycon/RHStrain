package com.bgi.bgicafe.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import qxsso.customer.ws.ISSOWebService;

import com.bgi.bgicafe.dao.impl.CafeInfoDaoImpl;
import com.bgi.bgicafe.service.CafeInfoService;
import com.bgi.bgicafe.vo.CafeInfoVO;

@Transactional
@Service("cafeInfoServiceImpl")
public class CafeInfoServiceImpl implements CafeInfoService {

	@Autowired
	private HttpServletRequest request;

	@Resource(name = "cafeInfoDaoImpl")
	private CafeInfoDaoImpl cafeInfoDaoImpl;

	@Resource
	private ISSOWebService iSSOWebService;

	private Log logger = LogFactory.getLog(CafeInfoServiceImpl.class);

	/**
	 * 获取用户是否已报名
	 */
	@Override
	public boolean getIsSignupCafe4activityByCustomerId() {
		try {
			List<CafeInfoVO> info = cafeInfoDaoImpl
					.getIsSignupCafe4activityByCustomerId(getCustomerId());
			return !CollectionUtils.isEmpty(info);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 报名
	 */
	@Override
	public boolean signup() {
		CafeInfoVO info = null;
		List<CafeInfoVO> infoList = cafeInfoDaoImpl
				.getUserInfoByGroupUsername(getGroupUsername());
		if (CollectionUtils.isEmpty(infoList)) {
			logger.info("获取用户信息失败！");
			return false;
		}
		info = infoList.get(0);
		// 报名写入记录
		return cafeInfoDaoImpl.signup(getCustomerId(), info.getUsername(),
				info.getIdnumber(), info.getEmailaddress(),
				info.getPhonenumber());

	}

	/*
	 * 获取用户是否已缴费
	 * 
	 * @see com.bgi.bgicafe.service.CafeInfoService#getIsPay4activityByemail()
	 */
	@Override
	public String getPlanType4activityByCustomerId() {
		try {
			List<CafeInfoVO> info = cafeInfoDaoImpl
					.getPlanType4activityByCustomerId(getCustomerId());
			if (!CollectionUtils.isEmpty(info)) {
				return info.get(0).getPlanType();
			}
		} catch (Exception e) {

		}
		return "";
	}

	@SuppressWarnings("rawtypes")
	public String getGroupUsername() {
		Map map = (Map) request.getAttribute("userInfo");
		if (null == map) {
			logger.info("获取用户groupUsername失败！");
			return "";
		}
		Object groupUsernameObj = map.get("groupUsername");
		if (null == groupUsernameObj) {
			logger.info("获取用户groupUsername失败！");
			return "";
		}
		return groupUsernameObj.toString();
	}

	@SuppressWarnings("rawtypes")
	public String getCustomerId() {
		Map map = (Map) request.getAttribute("userInfo");
		if (null == map) {
			logger.info("获取用户id失败！");
			return "";
		}
		Object idObj = map.get("id");
		if (null == idObj) {
			logger.info("获取用户id失败！");
			return "";
		}
		return idObj.toString();
	}

	/**
	 * 根据登录的用户获取领取记录
	 */
	@Override
	public List<CafeInfoVO> getUserDrawalHistoryByCustomerId() {
		return cafeInfoDaoImpl
				.getUserDrawalHistoryByCustomerId(getCustomerId());
	}

	/**
	 * 获取下次领取日期
	 */
	@Override
	public String getNextDrawalDateByCustomerId() {
		return cafeInfoDaoImpl.getNextDrawalDateByCustomerId(getCustomerId());
	}

	/**
	 * 获取未领取日期
	 */
	@Override
	public List<CafeInfoVO> getNotReceiveDateByCustomerId() {
		return cafeInfoDaoImpl.getNotReceiveDateByCustomerId(getCustomerId());
	}

	/**
	 * 获取待领取日期
	 */
	@Override
	public List<CafeInfoVO> getStayReceiveDateByCustomerId() {
		return cafeInfoDaoImpl.getStayReceiveDateByCustomerId(getCustomerId());
	}

	/**
	 * 获取当月开始日期
	 */
	@Override
	public String getPlanStartDateByCustomerId() {
		try {
			List<CafeInfoVO> info = cafeInfoDaoImpl
					.getPlanStartDateByCustomerId(getCustomerId());
			if (!CollectionUtils.isEmpty(info)) {
				return info.get(0).getExpectTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取已领取、领取总数
	 */
	@Override
	public CafeInfoVO getJuiceSumByCustomerId() {
		List<CafeInfoVO> list = cafeInfoDaoImpl
				.getJuiceSumByCustomerId(getCustomerId());
		return !CollectionUtils.isEmpty(list) ? list.get(0) : null;
	}

	/**
	 * 获取最后领取结束日期
	 */
	@Override
	public boolean getEndIsGreaterThanByCustomerId() {
		List<CafeInfoVO> infoVOList = cafeInfoDaoImpl
				.getEndIsGreaterThanByCustomerId(getCustomerId());
		return !CollectionUtils.isEmpty(infoVOList);
	}

}
