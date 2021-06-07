<%-- 
    Document   : signup
    Created on : May 12, 2021, 7:10:30 AM
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
        <h1>Register Account</h1>
        <form action="DispatchServlet" method="POST">
            <div class="register-form">
                <table border="1">
                    <tbody>
                        <tr>
                            <td>Email (ID):</td>
                            <td><input type="text" name="txtID" value="${param.txtID}" /></td>
                            <td>
                                <c:if test="${requestScope.INVALID_CREATEACCOUNT.invalidEmail == null}">
                                    <font color="red">
                                    We will send code to your email to verify your account
                                    </font>
                                </c:if>
                                <c:if test="${requestScope.INVALID_CREATEACCOUNT.invalidEmail != null}">
                                    <font color="red">
                                    ${requestScope.INVALID_CREATEACCOUNT.invalidEmail}
                                    </font>
                                </c:if>    
                            </td>
                        </tr>
                        <tr>
                            <td>Phone:</td>
                            <td><input type="text" name="txtPhone" value="${param.txtPhone}" /></td>
                            <c:if test="${requestScope.INVALID_CREATEACCOUNT.invalidPhone != null}">
                            <td>
                                <font color="red">
                                    ${requestScope.INVALID_CREATEACCOUNT.invalidPhone}
                                </font>
                            </td>       
                            </c:if>  
                        </tr>
                        <tr>
                            <td>Fullname:</td>
                            <td><input type="text" name="txtFullname" value="${param.txtFullname}" /></td>
                            <c:if test="${requestScope.INVALID_CREATEACCOUNT.invalidFullname != null}">
                            <td>
                                <font color="red">
                                    ${requestScope.INVALID_CREATEACCOUNT.invalidFullname}
                                </font>
                            </td>       
                            </c:if>
                        </tr>
                        <tr>
                            <td>Password:</td>
                            <td><input type="password" name="txtPassword" value="" /></td>
                            <c:if test="${requestScope.INVALID_CREATEACCOUNT.invalidPassword != null}">
                            <td>
                                <font color="red">
                                    ${requestScope.INVALID_CREATEACCOUNT.invalidPassword}
                                </font>
                            </td>       
                            </c:if>
                        </tr>
                        <tr>
                            <td>Confirm Password:</td>
                            <td><input type="password" name="txtConfirm" value="" /></td>
                            <c:if test="${requestScope.INVALID_CREATEACCOUNT.invalidConfirmPassword != null}">
                            <td>
                                <font color="red">
                                    ${requestScope.INVALID_CREATEACCOUNT.invalidConfirmPassword}
                                </font>
                            </td>       
                            </c:if>
                        </tr>
                        <tr>
                            <td>Address:</td>
                            <td>
                                <textarea name="txtAddress" type="text" cols="20" rows="5"
                                          maxlength="200">${param.txtAddress}</textarea>
                            </td>
                            <c:if test="${requestScope.INVALID_CREATEACCOUNT.invalidAddress != null}">
                            <td>
                                <font color="red">
                                    ${requestScope.INVALID_CREATEACCOUNT.invalidAddress}
                                </font>
                            </td>       
                            </c:if>
                        </tr>
                        <tr>
                            <td>
                                <input type="submit" value="Create Account" name="action" />
                            </td>
                            <td>
                                <button>
                                    <a href="DispatchServlet?action=BackToLogin">Back To Login Page</a>
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </form>
    </body>
</html>
