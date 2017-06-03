package com.bgi.bgicafe.service;

import java.util.List;

import com.bgi.bgicafe.vo.CafeInfoVO;


public interface CafeInfoService {

	boolean getIsSignupCafe4activityByCustomerId();

	boolean signup();
	
	String getPlanType4activityByCustomerId();
	
	List<CafeInfoVO> getUserDrawalHistoryByCustomerId();
	
	String getNextDrawalDateByCustomerId();
	
	List<CafeInfoVO> getNotReceiveDateByCustomerId();
	
	List<CafeInfoVO> getStayReceiveDateByCustomerId();

	String getPlanStartDateByCustomerId();
	
	CafeInfoVO getJuiceSumByCustomerId();
	
	boolean getEndIsGreaterThanByCustomerId();
}
