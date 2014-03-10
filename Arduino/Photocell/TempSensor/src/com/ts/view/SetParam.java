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

public class SetParam extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public SetParam() {
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
    
	 int state=Integer.parseInt((String) request.getParameter("state"));
     //int freq=Integer.parseInt((String) request.getParameter("freq"));
     String freqstr=(String) request.getParameter("freq");
     int freq;
     if (freqstr==null)
       freq=0;
     else
    	 freq=Integer.parseInt(freqstr);
	 try {
		SensorOperator.setSensorState(state,freq);
		request.getRequestDispatcher("ShowChart").forward(request, response);
		
     }
     
     catch (Exception e)
     {
    	  PrintWriter out = response.getWriter(); 
    	  e.printStackTrace();
    	  
          out.println("<html>");  
          out.println("<head>");  
          out.println("<title>Sensor Checker</title>");
          out.println("<meta http-equiv=\"refresh\" content=5;url=../index.jsp");
          out.println("</head>");  
          out.println("<body>");  
         
          out.println("Error occurs when set parameter!<br>");
          out.println("Jump to homepage in 5 sec.");
    
          out.println("</body>");  
          out.println("</html>");
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
