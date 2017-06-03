package qxsso.pojo;
public class RegisterActiveResult {

	private int flag;
	private LoginResult loginResult=new LoginResult();

	public int getFlag() {
		return flag;
	}

	public void setFlag(int value) {
		flag = value;
	}

	public LoginResult getLoginResult() {
		return loginResult;
	}

	public void setLoginResult(LoginResult loginResult) {
		this.loginResult = loginResult;
	}

}
