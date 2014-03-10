package com.ts.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import org.hibernate.Session;

import com.ts.bean.TempData;



import gnu.io.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class SensorOperator {
  @SuppressWarnings("rawtypes")
  
  
private static SerialPort sPort=null;
private static OutputStream os=null;
private static InputStream is=null;



public static boolean isPortAvailable() throws NoSuchPortException, PortInUseException, IOException
  {
	  Enumeration portList=CommPortIdentifier.getPortIdentifiers();
	//  System.out.println(portList.toString());
	  if (portList.hasMoreElements()) getFirstComName();
	  return portList.hasMoreElements();
  }

public static void setSensorState(int state, int freq) throws NoSuchPortException, PortInUseException, IOException {
	// TODO Auto-generated method stub
	
	
	getFirstComName();
	
	//byte[] bt=(String.valueOf(state)+" "+String.valueOf(freq)).getBytes();
	//for (int i=0;i<bt.length;i++)
    //System.out.print(bt[i]);
	//System.out.println("***");
    int bag=freq*10+state;
    System.out.println("bag is:"+bag);
	os.write(bag/10+'0');
	os.write(bag%10+'0');

   // System.out.println(freq*10+state);
    os.flush();
   // os.close();
   // sPort.close();
}

private static String getFirstComName() throws NoSuchPortException, PortInUseException, IOException {
	// TODO Auto-generated method stub
	 Enumeration<?> portList=CommPortIdentifier.getPortIdentifiers(); 
	 String portId=((CommPortIdentifier) portList.nextElement()).getName();
	
	 if (sPort==null || os==null || is==null)
	 {
        CommPortIdentifier tmp=CommPortIdentifier.getPortIdentifier(portId);
        sPort=(SerialPort) tmp.open("Arduino",30);
        os=sPort.getOutputStream();
        is=sPort.getInputStream();
	    
	 }
	 return portId;
}

public static String getLatestData() throws PortInUseException, NoSuchPortException, IOException {
	// TODO Auto-generated method stub
    
	getFirstComName();
	
	int s=0;
    do
    {
    	char c=(char) is.read();
    	if (c=='a')
    		s++;
    	else 
    		s=0;
    }
    while (s!=5);
    
    String t="";
    s=0;
    do
    {
    	char c=(char) is.read();
    	if (c=='b')
    		s++;
    	else
    		s=0;
    	if (c!='b')
    		t+=c;
    	//System.out.println("in loop bbb");
    }
    while (s!=5);
    
	//is.close();
	//sPort.close();
	return t;
}

@SuppressWarnings("unchecked")
public static String generateChart(String path) {
	

	List<TempData> list=null;
	Session sess=MySessionFactory.getSessionFactory().openSession();
	list=(List<TempData>) sess.createQuery("from TempData").list();
	sess.close();
	
	int beginPos,endPos;
	endPos=list.size();
	if (list.size()>20)
	   beginPos=list.size()-20;
	else
		beginPos=0;
	
	
    
	//Generate Chart.
	XYSeries xyseries=new XYSeries("Curve1");
	
	for (int i=beginPos;i<endPos;i++)
	{
	   TempData t=list.get(i);
	   xyseries.add((Integer)t.getRunTime(), t.getTemperature());
	   
	}
	
   XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
   xySeriesCollection.addSeries(xyseries);
   
   
	JFreeChart jfreechart=ChartFactory.createXYLineChart(
			"Temperature Sensor Monitor",
			"RunTime",
			"Value(C)",
			xySeriesCollection,
			PlotOrientation.VERTICAL,
			true,
			false,
			false);
	XYPlot plot=(XYPlot) jfreechart.getPlot();
	plot.setBackgroundAlpha(0.5f);
	
	plot.setForegroundAlpha(0.5f);
	
	//Generate Chart finished.
	
	//Save chart to file.
	saveAsFile(jfreechart, path+"\\"+"lineXY.png", 720, 480); 
	//System.out.println("saveOK!");
	
	return null;
	
}

private static void saveAsFile(JFreeChart chart, String outputPath, int weight,
		int height) {
	  FileOutputStream out = null;      

      try {      

          File outFile = new File(outputPath);      

         // if (!outFile.getParentFile().exists()) {     

           //   outFile.getParentFile().mkdirs();      

          //}      

          out = new FileOutputStream(outputPath);      

        

          ChartUtilities.writeChartAsPNG(out, chart, weight, height);      
  

          out.flush();      

      } catch (FileNotFoundException e) {      

          e.printStackTrace();      

      } catch (IOException e) {      

          e.printStackTrace();      

      } finally {      

          if (out != null) {      

              try {      

                  out.close();      
              } catch (IOException e) {      
                  // do nothing      
              }      
          }      
      }      
	
}

public static void stopSensor() {
	// TODO Auto-generated method stub
	try
	{
		os.write('0');
		os.write('0');
		is.close();
		os.close();
		sPort.close();
	}
	catch (Exception e)
	{
		
	}
	finally
	{
		is=null;
		os=null;
		sPort=null;
	}
	
}
}

