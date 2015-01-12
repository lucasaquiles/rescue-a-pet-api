package com.rescueapet.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Actionable{
	public Object execute( HttpServletRequest request , HttpServletResponse response );
}