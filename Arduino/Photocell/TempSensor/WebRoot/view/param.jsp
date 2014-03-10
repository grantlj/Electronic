<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'param.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <center>
      <h1>Set Sensor Parameter</h1><hr/>
      <form action="view/SetParam" method="post">
        ON/OFF:<select name="state">
                 <option value="1">Turn on</option>
                 <option value="0">Turn off</option>
               </select>
        Refresh Frequency:<input type="text" name="freq">sec<br>
        <input type="submit" value="submit">
        <input type="reset" value="reset">
        
      </form>
    </center>
  </body>
</html>
