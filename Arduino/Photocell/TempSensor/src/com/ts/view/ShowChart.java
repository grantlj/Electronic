package com.ts.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ts.bean.TempData;
import com.ts.util.MySessionFactory;
import com.ts.util.SensorOperator;

public class ShowChart extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ShowChart() {
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
   // System.out.println("in show Charm"); 
	TempData td=null;
	String chartDir=null;
	
	 try
	 {
	
		
		 String str=SensorOperator.getLatestData();
        int runTime=Integer.parseInt(str.substring(0,str.indexOf(" ")));
        Double temperature=Double.parseDouble(str.substring(str.indexOf(" ")+1));
        td=new TempData();
        
        
        SessionFactory sf=MySessionFactory.getSessionFactory();
        Session sess=sf.openSession();
        Transaction trans=sess.beginTransaction();
        
        
        td.setDate(new Date());
        td.setRunTime(runTime);
        td.setTemperature(temperature);
        sess.save(td);
        trans.commit();
      
        sess.close();
        
        @SuppressWarnings("deprecation")
		String path=request.getRealPath("index.jsp");
        path=path.substring(0,path.lastIndexOf('\\')); //NO LAST /
       // System.out.println("real path is:"+path);
        chartDir=SensorOperator.generateChart(path);
		
     
	
	}
	 
	 catch (Exception e)
	 {
		 e.printStackTrace();
		 td=null;
	 }
   
     response.sendRedirect("show.jsp");
        
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
