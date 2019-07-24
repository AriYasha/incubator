<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@page import="com.home.servlets.ControllerServlet" %>
<%--
  Created by IntelliJ IDEA.
  User: irina
  date: 18.06.2019
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show some information</title>
</head>
<body>
<form role="form" method="POST" action="controller?action=/getUserIdByMaxSum">
    <input type="text" id="userName" name="userName" value="<%=session.getAttribute("userName")%>">
    <button type="submit"> name User</button>
</form>
<br/>
<form role="form" method="POST" action="controller?action=/getAccountByMaxSum">
    <input type="text" id="account" name="account" value="<%=session.getAttribute("account")%>">
    <button type="submit"> sum accounts</button>
</form>
</body>
</html>
