<%-- 
    Document   : profile
    Created on : 11/02/2014, 11:40:32 AM
    Author     : JGUTIERRGARC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            HttpSession mySession = request.getSession();
            if (mySession.getAttribute("username")!=null) {
               out.println("Hi "+ mySession.getAttribute("username") +". This is your profile<HR>" );
               out.println("<a href='SignOut' > Sign out </a>");               
            } else {
               response.sendRedirect("index.jsp");
            }
        %>
    </body>
</html>
