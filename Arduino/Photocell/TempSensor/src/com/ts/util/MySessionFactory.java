package com.ts.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

final public class MySessionFactory {
  private static SessionFactory sessionFactory=null;
  private MySessionFactory(){
	  
  }
  
  static
  {
	 sessionFactory=new Configuration().configure().buildSessionFactory();
  }
  
  public static SessionFactory getSessionFactory()
  {
	  return sessionFactory;
  }
}
