package qxsso.customer.ws;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
@WebService
public interface IRegisterWebService {

	/**
	 * 
	 * {"address":"qiaoxin@genomics.cn","password":"e10adc3949ba59abbe56e057f20f883e"}
	 * @return
	 * flag:
	 * 0,成功;
	 * 1,参数错误(参数格式错误，address和password相同)
	 * 2,手机或邮箱已存在(在激活账号中已存在)
	 * 3,注册失败
	 * 4,验证码获取失败(验证码生成失败或发送失败)
	 */
	@WebResult(name="result")String register(@WebParam(name="param")String param);
	
	/**
	 * {"guid":"23C2BBD7557B61B8E050A8C057E052BC","activecode":"18235"}
	 * @return
	 * flag:
	 * 0,成功
	 * 1,参数错误
	 * 2,验证码失效或错误
	 * 3,手机或邮箱已存在(在激活账号中已存在)
	 * 4,激活失败
	 */
	@WebResult(name="result")String activeRegister(@WebParam(name="param")String param);
	
	/**
	 * {"loginname":"qiaoxin@genomics.cn","address":"qiaoxin@genomics.cn","logintype":"4"}
	 * @param param
	 * @return
	 * flag:
	 * 0,成功
	 * 1,参数错误(参数格式错误，address和password相同)
	 * 2,未找到该账户或者手机邮箱错误
	 * 3,找到多条该账户，无法匹配，提示用户选用其它账户类型
	 * 4,该账户被禁用
	 * 5,该账户未激活
	 * 6,生成验证码失败
	 * 7,发送验证码失败
	 */
	@WebResult(name="result")String sendResetPasswordActiveCode(@WebParam(name="param")String param);
	
	
	/**
	 * {"guid":"************","activecode":"*******","password":"**************"}
	 * @return
	 * flag:
	 * 0,成功
	 * 1,参数错误
	 * 2,验证码失效或错误
	 * 3,该账户被禁用
	 * 4,该账户未激活
	 * 5,修改密码失败
	 */
	@WebResult(name="result")String resetPassword(@WebParam(name="param")String param);
	
	/**
	 * {"mainticket":"***********","ticket":"********************"}
	 * @return
	 * flag:
	 * 0,成功
	 * 1,参数错误
	 * 2,ticket验证失败
	 * 3,绑定账号不能是同一账号
	 * 4,绑定失败
	 */
	@WebResult(name="result")String bind(@WebParam(name="param")String param);
	
	/**
	 * @return
	 */
	@WebResult(name="result")String unbind(@WebParam(name="param")String param);
	
	/**
	 * {"address":"qiaoxin@genomics.cn","ticket":"***********************"}
	 * @return
	 * flag:
	 * 0,成功
	 * 1,参数错误
	 * 2,ticket验证失败
	 * 3,该手机或邮箱存在于多条账户信息中
	 * 4,获取验证码失败
	 */
	@WebResult(name="result")String sendUpdateContactOrBindActiveCode(@WebParam(name="param")String param);
	
	/**
	 * {"ticket":"************","activecode":"18235"}
	 * @return
	 * flag:
	 * 0,成功
	 * 1,参数错误
	 * 2,ticket验证失败
	 * 3,验证码失效或错误
	 * 4,该手机或邮箱存在于多条账户信息中
	 * 5,操作失败(补填手机邮箱失败或绑定失败)
	 */
	@WebResult(name="result")String updateContactOrBind(@WebParam(name="param")String param);
}
