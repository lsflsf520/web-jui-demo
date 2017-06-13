package com.samsung.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class TenantPasswdToken extends UsernamePasswordToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int acId;

	public int getAcId() {
		return acId;
	}

	public TenantPasswdToken(int acId, String username, String password) {
		super(username, password);
		this.acId = acId;
	}
	
	

}
