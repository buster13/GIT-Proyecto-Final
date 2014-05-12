<%-- 
    Document   : index
    Created on : 11/02/2014, 11:14:07 AM
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
        <form action="welcome.jsp" method="POST">
            <p>User name: <input type="text" name="username" value="" /></p>
            <p>Password: <input type="text" name="password" value="" /></p>        
            <BR> <input type="submit" value="Send" />
        </form>
    </body>
</html>
