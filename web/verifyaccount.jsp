<%-- 
    Document   : verifyaccount
    Created on : May 15, 2021, 4:10:46 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="./css/style.css"/>
    </head>
    <body>
        <h2>Verify Your Account</h2>
        <p>We have send code to your email: <span class="send-email">${requestScope.EMAIL_VERIFY}</span>, please check and click the link in the mailbox or type the code below to verify your account</p>
        <div class="verify-account-form">
            <form action="DispatchServlet">
            <table border="1">
                <tbody>
                    <tr>
                        <td>Your code: </td>
                        <td>
                            <input type="text" name="code" value="" required=""/>
                            <input type="hidden" name="email" value="${requestScope.EMAIL_VERIFY}" />
                            <input type="hidden" name="verifyType" value="submitCode" />
                            <input type="hidden" name="fullName" value="${requestScope.FULLNAME_VERIFY}" />
                        </td>
                        <td>
                            <input type="submit" value="Submit Code" name="action" />
                        </td>
                        <c:if test="${not empty requestScope.INVALID_CODE}">
                        <td>
                            <font color="red">
                            ${requestScope.INVALID_CODE}
                            </font>
                        </td>
                   
                        </c:if>
                    </tr>
                </tbody>
            </table>
            </form>
        </div>
           <p>Didn't see the mail yet? <button><a href="DispatchServlet?action=SendMail&txtID=${requestScope.EMAIL_VERIFY}&txtFullname=${requestScope.FULLNAME_VERIFY}">Resend</a></button></p>
           <button>
               <a href="DispatchServlet?action=BackToLogin"><-- Back To Login Page</a>  
           </button>
    </body>
</html>
