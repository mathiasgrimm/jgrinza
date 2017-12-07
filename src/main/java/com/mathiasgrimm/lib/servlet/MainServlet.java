package com.mathiasgrimm.lib.servlet;

import com.mathiasgrimm.lib.Application;
import com.mathiasgrimm.lib.ApplicationFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns={"/*"})
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Application app;

	public MainServlet() {
        ApplicationFactory appFactory = new ApplicationFactory();
        this.app = appFactory.create();
    }
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		this.app.handleHttp(request, response);
	}
}
