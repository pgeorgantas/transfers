package controllers;

import views.LoginView;

public class LoginController {
	
	private LoginView login;
	
	public LoginController(LoginView loginv) {
		this.login = loginv;
	}
	
	public String changePanel(){
		return login.getUserType();
	}
}
