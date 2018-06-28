<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Menu</title>
    </head>
    <body>
        <h1>Menu!</h1>
        <!--<p><%=request.getAttribute("usuario") %><p>-->
        <%
           ArrayList<String> list = (ArrayList<String>) request.getAttribute("contas");
           for (String s : list) {
               out.println(s);
           }
        %>
    </body>
</html>
