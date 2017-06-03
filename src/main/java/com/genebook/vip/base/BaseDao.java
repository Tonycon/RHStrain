package com.genebook.vip.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

/**
 * 
 * @author yanghui
 *
 */
public class BaseDao {
	
    public BaseDao(){}  
    
    @Resource(name="jdbcTemplate")
    private JdbcTemplate jdbcTemplate ;  
      
    public SimpleJdbcCall createSimpleJdbcCall() {  
        return new SimpleJdbcCall(this.jdbcTemplate);  
    }  
  
    public JdbcTemplate getJdbcTemplate() {  
        return jdbcTemplate;  
    }  
  
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {  
        this.jdbcTemplate = jdbcTemplate;  
    }  
    
    /**
     * 判断用户是否存在
     * @param customerId
     * @return
     */
    public boolean isExistCustomer(String customerId) {
    	String sql = "select id from t_customer where id = ?";
    	List<String> customerIds = this.jdbcTemplate.query(sql, new Object[]{customerId}, new RowMapper<String>(){
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("id");
			}
    		
    	});
    	if(customerIds != null && customerIds.size() > 0) {
    		return true;
    	}
    	return false;
    }
    
    
    public <T> Object getObjById(String tableName,String id, Class<T> c)
    {
    	String sql = "select * from " + tableName + " t where t.id = ?  and t.ENABLE = '1'";
    	List<T> eList = getJdbcTemplate().query(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance(c));
    	if (eList != null && eList.size() > 0) {
    		return eList.get(0);
		}
    	return null;
    }
    
    public <T> Object getFirstData(String customerId, String tableName, Class<T> c) {
		String sql = "select * from (select * from " + tableName + " t where t.customer_id = ? and t.ENABLE = '1' order by create_time desc,add_time desc) where rownum=1";
		List<T> list = getJdbcTemplate().query(sql, new Object[]{customerId}, BeanPropertyRowMapper.newInstance(c));
		if (list != null && list.size() > 0) {
    		return list.get(0);
		}
		return null;
	}
    
    public <T> Object getFirstDataOrderByCreateTime(String customerId, String tableName, Class<T> c) {
		String sql = "select * from (select * from " + tableName + " t where t.customer_id = ? and t.ENABLE = '1' order by sleep_date desc, create_time desc) where rownum=1";
		List<T> list = getJdbcTemplate().query(sql, new Object[]{customerId}, BeanPropertyRowMapper.newInstance(c));
		if (list != null && list.size() > 0) {
    		return list.get(0);
		}
		return null;
	}
    
    public boolean deleteObjById(String tableName,String id)
    {
    	String sql = "delete from " + tableName + " t where t.id = ?";
    	int count = this.getJdbcTemplate().update(sql, new Object[]{id});
		return count > 0 ? true : false;
    }
    
    public boolean updateEnableById(String tableName,String id)
    {
    	String sql = "update " + tableName + " t set t.ENABLE = '2' where t.id = ? ";
    	int count = this.getJdbcTemplate().update(sql, new Object[]{id});
		return count > 0 ? true : false;
    }
    
    public boolean updateRecordUnread(String tableName) {
		String sql = " update " + tableName + " t set t.UNREAD = '1' where t.UNREAD = '0' ";
		int count = this.getJdbcTemplate().update(sql);
		return count > 0 ? true : false;
	}
    
    public boolean updateRecordRemark(String tableName, String reportId, String remarkStr) {
		String sql = " update " + tableName + " t set t.remark = ? where t.id = ? ";
		Object [] parmters = {remarkStr, reportId };
		int count = this.getJdbcTemplate().update(sql, parmters);
		return count > 0 ? true : false;
	}
    
    public String getDataType(String talbeName,String id){
    	String sql="select data_type from "+talbeName+" t where t.id=?  and t.ENABLE = '1'";
    	List<Map<String,Object>> list=this.getJdbcTemplate().queryForList(sql,id);
    	if(ArrayUtils.isNotEmpty(list)){
    		return ArrayUtils.objectToString(list.get(0).get("DATA_TYPE"));
    	}
    	return null;
    }
}  