<%-- 
    Document   : welcome
    Created on : 11/02/2014, 11:27:03 AM
    Author     : JGUTIERRGARC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
    </head>
    <body>
        <%

            if (request.getParameter("password")!= null) {
                if (request.getParameter("password").equals("123456")) {
                    HttpSession mySession = request.getSession();
                    String userName = request.getParameter("username")+ "-Session";
                    mySession.setAttribute("username", userName);
                    mySession.setMaxInactiveInterval(4);
                    out.println("Welcome "+request.getParameter("username") +"<HR>");
                    out.println("<a href='profile.jsp'> Visit your Profile </a>");  
                } else {
                    response.sendRedirect("index.jsp");
                }
            } else {
                    response.sendRedirect("index.jsp");
            }
        %>
        
        
    </body>
</html>
