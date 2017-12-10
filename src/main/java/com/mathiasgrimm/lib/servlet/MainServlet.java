package com.mathiasgrimm.lib.servlet;

import com.mathiasgrimm.lib.Application;
import com.mathiasgrimm.lib.ApplicationFactory;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns={"/*"})
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Application app;

	public MainServlet() throws Exception {
        ApplicationFactory appFactory = new ApplicationFactory();
        this.app = appFactory.create();

        System.out.println("finished booting application");
    }
	
	public void service(HttpServletRequest request, HttpServletResponse response)
	{
	    try {
            this.app.handleHttp(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
