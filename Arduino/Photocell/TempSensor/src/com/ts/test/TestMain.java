package com.ts.test;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ts.bean.TempData;
import com.ts.util.MySessionFactory;

public class TestMain {
  public static void main(String[] args)
  {
	  Session sess=MySessionFactory.getSessionFactory().openSession();
	  Transaction ts=sess.beginTransaction();
	  TempData td=new TempData();
	  td.setDate(new Date());
	  td.setRunTime(100);
	  td.setTemperature((double) 20);
	  sess.save(td);
	  ts.commit();
	  sess.close();
  }
}
