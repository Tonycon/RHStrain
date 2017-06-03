package qxsso.customer.ws;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;



@WebService
public interface ISSOWebService {


	/**
	 * 登录
	 * @param param {"loginname":"qiaoxin","logintype":"100","password":"*********"}
	 * logintype:
	 * -1:模糊登录
	 * 0:username登录
	 * 1:GBID登录
	 * 2:身份证登录
	 * 3:手机登录
	 * 4:邮箱登陆
	 * 5:微信公众号接入 loginname=appid , password=code
	 * 6:微信网页登录 loginname=appid , password=code
	 * 7:微信移动登录 loginname=appid , password=code
	 * 8:QQ网站登录 
	 * 9:QQ移动登录 loginname=appid , password=accessToken
	 * 10:新浪微博网站登录
	 * 11:新浪微博移动登录 loginname=appid , password=accessToken
	 * 12:深大云伴接入
	 * 100:华大AD企业登录
	 * @return result
	 * {"data":"*******","flag":0,"map":{"gbid":"118246","guid":"FFF53F927D4F6997E040A8C057E01EE8","logintype":"100"}}
	 * data:登录凭据
	 * flag:登录结果 , 0成功；非0失败
	 * map:用户属性
	 */
	@WebResult(name="result")String login(@WebParam(name="param")String param);
	
	/**
	 * 发送随即密码
	 * @param {"loginname":"*******","logintype":"-1"}
	 * @return
	 * flag:
	 * 0，成功
	 * 1，参数错误
	 * 2，未知账户
	 * 3，账户未激活
	 * 4，账户不可用
	 * 5，账号被锁
	 * 6，多条账号
	 * 7，账户没有联系方式
	 * 8，随机密码获取失败
	 * 9，未知异常
	 */
	@WebResult(name="result")String sendRandomPassword(@WebParam(name="param")String param);
	
	/**
	 * 登出
	 * @param {"ticket":"*******"}
	 * @return {"flag":0}
	 * flag：登出结果
	 * 0:成功；非0：失败
	 */
	@WebResult(name="result")String logout(@WebParam(name="param")String param);
	/**
	 * 登录校验
	 * @param {"ticket":"*******"}
	 * @return {"flag":0}
	 * flag：校验结果
	 * 0:成功；非0：失败
	 */
	@WebResult(name="result")String validate(@WebParam(name="param")String param);
	
	/**
	 * 根据ticket获取用户基本信息
	 * * @param {"ticket":"*******"}
	 * @return  {"data":{"birthday":"1986-02-24","displayName":"Jonathan1111","gender":"1","groupName":"100","groupUsername":"qiaoxin","id":"FFF53F927D4F6997E040A8C057E01EE8","imageCenter":[1,2,3,4],"imageHeader":[1,2,3,4],"loginId":"118246","nickName":"Jonathan1111","username":"qiaoxin"},"flag":0}
	 * data：用户属性
	 * flag:无效参数
	 */
	@WebResult(name="result")String getUserInfo(@WebParam(name="param")String param);
	/**
	 * 根据ticket获取用户联系信息
	 * @param {"ticket":"*******"}
	 * @return
	 */
	@WebResult(name="result")String getContactInfo(@WebParam(name="param")String param);
	
	
}
