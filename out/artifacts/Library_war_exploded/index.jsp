<%--
  Created by IntelliJ IDEA.
  User: VM
  Date: 02-Jul-18
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Online Library :: Sign in</title>
    <link href="css/style_index.css" rel="stylesheet" type="text/css">
  </head>
  <body>
  <div class="main">
    <div class="content">
      <p class="title"><span class="text"><img src="images/lib.png" width="76" height="77" hspace="10" vspace="10" align="middle"> </span> </p>
      <p class="title">Online library</p>
      <p class="text">Welcome to the online library where you can find any book. The search, browsing, sorting and other functions are available.</p>
      <p class="text">The project is still in development, so the design ad functionality will be constantly updated.</p>
    </div>
    <div class="login_div">
      <form class="login_form" name="username" action="pages/main.jsp" method="post">
        <p class="title">Login Form</p>
        <input class="login_form_input" type="text" name="username" placeholder="username" size="20" style="margin-bottom: 10px;"/><br/>
        <input class="login_form_input" type="password" name="password" placeholder="password" size="20" style="margin-bottom: 10px;"/><br/>
        <input type="submit" class="form_button" value="LOGIN"/>
      </form>
    </div>

  </div>
  <div class="footer"> Developer: Vitalii M., 2018 </div>
  </body>
</html>
