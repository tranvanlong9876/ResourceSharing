<%-- 
    Document   : error
    Created on : May 11, 2021, 8:40:31 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error Page</title>
    </head>
    <body>
        <font color="red">
            <h2>Error At ${requestScope.ERROR_PAGE.errorServlet}</h2>
            <p>Detail: ${requestScope.ERROR_PAGE.errorDetail}</p>
        </font> <br/>
        <a href="DispatchServlet?action=BackToLogin"><-- Back To Login Page</a>
    </body>
</html>
