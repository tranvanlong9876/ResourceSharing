<%-- 
    Document   : index
    Created on : May 11, 2021, 7:23:49 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resource Sharing</title>
        <script src='https://www.google.com/recaptcha/api.js?hl=en'></script>
        <link rel="stylesheet" href="./css/style.css" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css"/>
        <link rel="stylesheet" href="./css/index.css"/>
    </head>
    <body>
        <c:if test="${sessionScope.ACCOUNTDETAIL != null}">
            <c:redirect url="DispatchServlet?action=Back To Search Page"/>
        </c:if>
        <h1>LOGIN TO RESOURCE SHARING SYSTEM</h1>
        <div class="login-page">
            <form action="DispatchServlet" method="POST">
                <table border="0">
                    <tbody>
                        <tr>
                            <td>UserID</td>
                            <td><input type="text" name="txtID" value="${param.txtID}" placeholder="Email (ex): @gmail.com "/></td>
                        </tr>
                        <tr>
                            <td>Password</td>
                            <td><input type="password" name="txtPassword" /></td>
                    </tbody>
                </table>
                <div class="g-recaptcha" data-sitekey="6LfrR88aAAAAAG-Tm1tdnjfGXR_HL8fiDGqj1a1W"></div><br/>
                <c:if test="${requestScope.LOGINSTATUS != null}">
                    <font color="red">
                    ${requestScope.LOGINSTATUS}
                    </font> <br/> <br/>
                </c:if>
                <div class="login-register"><input class="btn btn-default" type="submit" name="action" value="Login" />
                    <a class="btn btn-primary" style="color: white;" href="DispatchServlet?action=Register Account">Register Account</a>
                </div>
            </form>

            <a style="position: absolute; margin-left: 40px; margin-top: 25px; border-radius: 3px; border: 1px solid grey; padding: 7px 5px 5px 5px; background-color: red; color: white;"  href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/ResourceSharing/DispatchServlet?action=loginGoogle&response_type=code&client_id=1009035370439-jhih7tec412cs537k313giuph9jst9s9.apps.googleusercontent.com&approval_prompt=force">Login With Google Account</a>
        </div>
    </body>
</html>
