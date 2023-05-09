<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %> <%=request.getAttribute("test1")%></h1>
<h2><%= request.getAttribute("test2") %></h2>
<br/>
<a href="tests?name=ok&&ok=ok">Hello Servlet</a>
</body>
</html>