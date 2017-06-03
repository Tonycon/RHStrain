package com.bgi.bgicafe.controller;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.common.util.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.bgi.bgicafe.service.CafeInfoService;
import com.bgi.bgicafe.vo.CafeInfoVO;
import com.bgi.rank.util.PropertiesUtils;
import com.bgi.rank.util.StringUtils;

@Controller
@RequestMapping("/cafeLogin")
public class CafeLoginController {

	@Resource
	private CafeInfoService cafeInfoService;

	@RequestMapping("/cafeLoginPage")
	public ModelAndView cafeLoginPage(HttpServletRequest request)
			throws ParseException {
		ModelAndView mav = new ModelAndView();
		Object _st = request.getAttribute("_st");
		if (null == _st || !StringUtils.isNotEmpty(_st.toString())) {
			// 1：表示没有用健客APP扫描
			mav.setViewName("/cafe/cafe");
			mav.addObject("isGenomics", "1");
			return mav;
		}
		Object isHuadaUser = request.getAttribute("isHuadaUser");
		// 判断是否为华大员工
		if (null == isHuadaUser || "false".equals(isHuadaUser.toString())) {
			mav.addObject("jump", false);
			// 获取版本号
			Object objVersion = request.getParameter("app_version_code");
			if (null != objVersion && !"".equals(objVersion)) {
				int version = Integer.parseInt(String.valueOf(objVersion));
				// 版本号必须大于当前版本号才可以跳转
				if (version >= 603019) {
					mav.addObject("jump", true);
				}
			}
			// 2：表示没有用非华大员工扫描
			mav.setViewName("/cafe/cafe");
			mav.addObject("isGenomics", "2");
			return mav;
		}

		// 表示用健客APP扫描二维码，并且带有登录状态
		boolean isSignup = cafeInfoService
				.getIsSignupCafe4activityByCustomerId();
		boolean isPlanStartDate = StringUtils
				.isNotEmpty(getPlanStartDateByCustomerId());
		String plan = cafeInfoService.getPlanType4activityByCustomerId();
		// 报名且缴费且有计划开始日期
		if (isSignup && StringUtils.isNotEmpty(plan) && isPlanStartDate) {
			Map<String, String> map = new HashMap<String, String>();
			// 设置领取时间
			setDrawalDate(map);
			// 设置漏领取时间
			setNotReceiveDate(map);
			// 设置待领取时间
			setStayReceiveDate(map);

			mav.addObject("dataMap", map);
			// 获取计划开始日期
			map.put("startDate", getPlanStartDateByCustomerId());
			boolean isEndDate = cafeInfoService
					.getEndIsGreaterThanByCustomerId();
			if (isEndDate) {
				// 获取下次领取日期
				String nextDrawalDate = cafeInfoService
						.getNextDrawalDateByCustomerId();
				if (StringUtils.isNotEmpty(nextDrawalDate)) {
					// 设置下次领取月份
					mav.addObject("nextDrawalMonth",
							nextDrawalDate.substring(5, 7));
					// 设置下次领取天数
					mav.addObject("nextDrawalDay",
							nextDrawalDate.substring(8, 10));
				}
			}
			// 获取已领取、可领取总数
			CafeInfoVO cafeInfoVO = cafeInfoService.getJuiceSumByCustomerId();
			// 设置已领取总数
			mav.addObject("drawalCount", cafeInfoVO.getDrawalCount());
			// 设置可领取总数
			mav.addObject("juiceCount", cafeInfoVO.getJuiceCount());
			// 设置果汁计划类型
			mav.addObject("plantype", plan);
			// 跳转个人首页
			mav.setViewName("/cafe/cafejuiceplan");
			return mav;
		}
		Properties pro = PropertiesUtils.getProperty("cafe.properties");
		String money = pro.getProperty("money");
		String original = pro.getProperty("original");
		// 只报名未缴费/报名交费但未开始
		mav.addObject("isSignup", isSignup);
		mav.addObject("money", money);
		mav.addObject("original", original);
		mav.addObject("isPlanStartDate", true);
		// 报名且缴费为计划日期未开始
		if (!isPlanStartDate && StringUtils.isNotEmpty(plan)) {
			mav.addObject("isPlanStartDate", false);
		}
		mav.setViewName("/cafe/cafeindex");
		return mav;

	}

	/**
	 * 报名
	 * 
	 * @return
	 */
	@RequestMapping("/signup")
	@ResponseBody
	public boolean signup() {
		return cafeInfoService.signup();
	}

	private void setDrawalDate(Map<String, String> map) {
		List<CafeInfoVO> dataList = cafeInfoService
				.getUserDrawalHistoryByCustomerId();
		// 获取已领取日期
		if (!CollectionUtils.isEmpty(dataList) && dataList.size() > 0) {
			String drawalDate = "";
			for (CafeInfoVO info : dataList) {
				drawalDate += info.getDrawalTime() + ",";
			}
			if ("" != drawalDate) {
				drawalDate = drawalDate.substring(0, drawalDate.length() - 1);
			}
			map.put("drawalDate", drawalDate);
		}
	}

	private void setNotReceiveDate(Map<String, String> map) {
		// 获取忘记领取日期
		List<CafeInfoVO> notReceiveDateList = cafeInfoService
				.getNotReceiveDateByCustomerId();
		if (!CollectionUtils.isEmpty(notReceiveDateList)
				&& notReceiveDateList.size() > 0) {
			String notReceiveDate = "";
			for (CafeInfoVO info : notReceiveDateList) {
				notReceiveDate += info.getExpectTime() + ",";
			}
			if ("" != notReceiveDate) {
				notReceiveDate = notReceiveDate.substring(0,
						notReceiveDate.length() - 1);
			}
			map.put("notReceiveDate", notReceiveDate);
		}
	}

	private void setStayReceiveDate(Map<String, String> map) {
		// 获取日历待领取日期
		List<CafeInfoVO> stayReceiveDateList = cafeInfoService
				.getStayReceiveDateByCustomerId();
		if (!CollectionUtils.isEmpty(stayReceiveDateList)
				&& stayReceiveDateList.size() > 0) {
			String stayReceiveDate = "";
			for (CafeInfoVO info : stayReceiveDateList) {
				stayReceiveDate += info.getExpectTime() + ",";
			}
			if ("" != stayReceiveDate) {
				stayReceiveDate = stayReceiveDate.substring(0,
						stayReceiveDate.length() - 1);
			}
			map.put("stayReceiveDate", stayReceiveDate);
		}
	}

	private String getPlanStartDateByCustomerId() {
		// 获取计划开始日期
		String startDate = cafeInfoService.getPlanStartDateByCustomerId();
		return StringUtils.isNotEmpty(startDate) ? startDate : "";
	}
}
