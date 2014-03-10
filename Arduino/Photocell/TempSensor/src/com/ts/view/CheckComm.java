package com.ts.view;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.ts.util.SensorOperator;

public class CheckComm extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CheckComm() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
      this.doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	try {
		if (!SensorOperator.isPortAvailable())
		{
  
		    PrintWriter out = response.getWriter();  
  
		    out.println("<html>");  
		    out.println("<head>");  
		    out.println("<title>Sensor Checker</title>");
		    out.println("<meta http-equiv=\"refresh\" content=5;url=../index.jsp");
		    out.println("</head>");  
		    out.println("<body>");  
		   
		    out.println("No Sensor available!<br>");
		    out.println("Jump to homepage in 5 sec.");
  
		    out.println("</body>");  
		    out.println("</html>");
		}
		
		else
			if (SensorOperator.isMonitoring())
				response.sendRedirect("show.jsp");
			else
			request.getRequestDispatcher("param.jsp").forward(request, response);
	} catch (NoSuchPortException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (PortInUseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
