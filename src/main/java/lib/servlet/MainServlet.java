package lib.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;


@WebServlet(urlPatterns={"/*"})
public class MainServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static Application app;

	public MainServlet()
    {
        MainServlet.app = new Application();
        System.out.println("construct");
    }
	
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	    PrintWriter w = response.getWriter();

		w.write("test 4");

		try {
//			File file = new File(this.getClass().getResource("/route.txt").toURI());
//			FileInputStream fis = new FileInputStream(file);
//			Properties prop = new Properties();
//			prop.load(fis);
			
			// System.out.println(prop.getProperty("/test"));
			// w.write(prop.getProperty("/test"));
	        
			
		} catch (Exception e) {
			response.getWriter().write(e.getMessage());
		}
		
	}

}

class Application
{
	Application()
	{
		System.out.println("initialising application");
	}
	
}