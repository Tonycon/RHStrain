/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/

package qxsso.pojo;

import java.util.ArrayList;
import java.util.List;

public class LoginResult {
    private List entry=new ArrayList();

    public List getEntry() {
        if (entry == null)
			entry = new ArrayList();
			return entry;
		}

	private int loginFlag;
	private String ticketGrantingTicket;
	private UserInfo userInfo=new UserInfo() ;

	public int getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(int value) {
		loginFlag = value;
	}

	public String getTicketGrantingTicket() {
		return ticketGrantingTicket;
	}

	public void setTicketGrantingTicket(String value) {
		ticketGrantingTicket = value;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo value) {
		userInfo = value;
	}

	public void setEntry(List entry) {
		this.entry = entry;
	}
	
	
}
