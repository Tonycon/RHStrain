package com.bgi.bgicafe.dao.impl;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.bgi.bgicafe.vo.CafeInfoVO;
import com.genebook.vip.base.BaseDao;

@Repository
public class CafeInfoDaoImpl extends BaseDao{
	
	public List<CafeInfoVO> getIsSignupCafe4activityByCustomerId(String id) {
		String sql = "select t.emp_email emailaddress from t_bgicafe_member t  where t.customer_id = ?";
		List<CafeInfoVO> info =  getJdbcTemplate().query(sql,
				new Object[] { id },
				BeanPropertyRowMapper.newInstance(CafeInfoVO.class));
		return info;
	}

	public List<CafeInfoVO>  getPlanType4activityByCustomerId(String customerid) {
		String sql = "select t.plan_type planType from t_bgicafe_member t where  t.customer_id = ? and t.SIGN_STATUS='HAS_BEEN_PAID' ";
		List<CafeInfoVO> infoVOList = getJdbcTemplate().query(sql,
				new Object[] { customerid },
				BeanPropertyRowMapper.newInstance(CafeInfoVO.class));
		return infoVOList;
	}
	public boolean signup(String id, String username, String cardid, String email, String phoneNum) {
		String sql = "insert into t_bgicafe_member(id,customer_id, emp_email, emp_name, emp_no, phone_no, signup_date, sign_status)"
				+ "values(SYS_GUID(),?, ?, ?, ?, ?, sysdate, 'WAIT_PAYMENT')";
		int result = getJdbcTemplate().update(sql,
				new Object[] {  id, email, username, cardid, phoneNum });
		return result >= 1;
	}
	
	
	public List<CafeInfoVO> getUserInfoByGroupUsername(String groupUsername) {
		String sql = "select t.emailaddress,t.lastname username,t.customstring1 idnumber,t.phonenumber　from T_CUSTOMER_DEPARTMENT t where t.userid= ?";
		List<CafeInfoVO> infoVOList = getJdbcTemplate().query(sql,
				new Object[] { groupUsername },
				BeanPropertyRowMapper.newInstance(CafeInfoVO.class));
		return infoVOList;
	}
	
	public List<CafeInfoVO> getUserDrawalHistoryByCustomerId(String id) {
		String sql = "select to_char(t.drawal_time,'yyyy-mm-dd') drawalTime from t_bgicafe_drawal t where t.customer_id=? and t.drawal_time is not null";
		List<CafeInfoVO> infoVOList = getJdbcTemplate().query(sql,
				new Object[] { id },
				BeanPropertyRowMapper.newInstance(CafeInfoVO.class));
		return infoVOList;
	}
	
	public String getNextDrawalDateByCustomerId(String id) {
		String sql = "select min(to_char(w.expect_time, 'yyyy-mm-dd')) expectTime from t_bgicafe_drawal w where w.drawal_time IS NULL and w.customer_id = ? and to_char(w.expect_time, 'yyyy-mm-dd') >= to_char(sysdate, 'yyyy-mm-dd')";
		String 	date = getJdbcTemplate().queryForObject(sql,
				new Object[] { id }, String.class);
		return date;
	}
	
	public List<CafeInfoVO> getNotReceiveDateByCustomerId(String id) {
		String sql = "select to_char(t.expect_time,'yyyy-mm-dd') expectTime from t_bgicafe_drawal t where t.customer_id= ? and t.drawal_time is null and to_char(t.expect_time, 'yyyy-mm-dd') < to_char(sysdate, 'yyyy-mm-dd')";
		List<CafeInfoVO> infoVOList = getJdbcTemplate().query(sql,
				new Object[] { id },
				BeanPropertyRowMapper.newInstance(CafeInfoVO.class));
		return infoVOList;
	}

	public List<CafeInfoVO> getStayReceiveDateByCustomerId(String id) {
		String sql = "select to_char(t.expect_time,'yyyy-mm-dd') expectTime from t_bgicafe_drawal t where t.customer_id= ? and t.drawal_time is null and to_char(t.expect_time, 'yyyy-mm-dd') >= to_char(sysdate, 'yyyy-mm-dd')";
		List<CafeInfoVO> infoVOList = getJdbcTemplate().query(sql,
				new Object[] { id },
				BeanPropertyRowMapper.newInstance(CafeInfoVO.class));
		return infoVOList;
	}
	
	public List<CafeInfoVO> getPlanStartDateByCustomerId(String id) {
		String sql = "select to_char(min(t.expect_time),'yyyy-mm-dd') expectTime from t_bgicafe_drawal t where t.customer_id= ?";
		List<CafeInfoVO> infoVOList = getJdbcTemplate().query(sql,
				new Object[] { id },
				BeanPropertyRowMapper.newInstance(CafeInfoVO.class));
		return infoVOList;
	}
	public List<CafeInfoVO> getJuiceSumByCustomerId(String id) {
		String sql = "select sum(case when t.customer_id is not null then 1 else 0 end) as juiceCount, sum(case when t.drawal_time is not null then 1 else 0 end) as drawalCount from t_bgicafe_drawal t where t.customer_id = ?";
		List<CafeInfoVO> infoVOList = getJdbcTemplate().query(sql,
				new Object[] { id },
				BeanPropertyRowMapper.newInstance(CafeInfoVO.class));
		return infoVOList;
	}
	 
	public List<CafeInfoVO> getEndIsGreaterThanByCustomerId(String id) {
		String sql = "select t.emp_name username from t_bgicafe_member t where to_char(sysdate, 'yyyy-mm-dd') <= to_char(t.end_date, 'yyyy-mm-dd') and t.customer_id = ? and (select max(w.expect_time) from t_bgicafe_drawal w where w.drawal_time is null and w.customer_id = ?) is not null";
		List<CafeInfoVO> infoVOList = getJdbcTemplate().query(sql,
				new Object[] { id, id },
				BeanPropertyRowMapper.newInstance(CafeInfoVO.class));
		return infoVOList;
	}
}


