<%-- 
    Document   : historydetail
    Created on : May 28, 2021, 10:14:09 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Detail</title>
        <link rel="stylesheet" href="./css/historydetail.css"/>
    </head>
    <body>
        <div class="header">
            <table class="welcome-user" border="0">
                <tbody>
                    <tr>
                        <td>
                            <h4>Welcome, ${sessionScope.ACCOUNTDETAIL.name} (Your role is: ${sessionScope.ACCOUNTDETAIL.role})</h4>
                        </td>
                        <td>
                            <form action="DispatchServlet" method="POST">
                                <input class="log-out-btn" type="submit" value="Log Out" name="action" />
                            </form>
                        </td>
                        <td>
                            <form action="DispatchServlet" method="POST">
                                <input class="back-to-search" type="submit" value="Get All Request" name="action" />
                            </form>
                        </td>
                    </tr>
                </tbody>
            </table>
            <h1 class="title-detail">HISTORY DETAIL</h1>
            <table class="view-detail" border="0">
                <tbody>
                    <tr>
                        <td>
                            <h2>Your Request Details:</h2>
                        </td>
                        <td>
                            <h2>Request Date: ${requestScope.HISTORY_DETAIL.requestStatus.requestTime} |</h2>
                        </td>
                        <td>
                            <h2>Request Status: ${requestScope.HISTORY_DETAIL.requestStatus.status}</h2>
                        </td>
                </tbody>
            </table>
        </div>
        <div class="cart-detail">
            <c:if test="${requestScope.HISTORY_DETAIL.itemDetail.size() > 0}">
                <table border="0">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th class="long-name">Resource Name</th>
                            <th>Quantity</th>
                            <th>Using Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.HISTORY_DETAIL.itemDetail}" var="detail" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${detail.resourceName}</td>
                                <td>${detail.quantity}</td>
                                <td>${detail.rentTime}</td>
                            </tr>
                        </c:forEach>
                        <c:if test="${requestScope.HISTORY_DETAIL.requestStatus.status eq 'New'}">
                                <tr>
                                    <td colspan="4">
                                        <font color="red">
                                        <h3>Your Request Is Waiting For Manager Response...</h3>
                                        </font>
                                    </td>
                                </tr>
                        </c:if>
                        <c:if test="${requestScope.HISTORY_DETAIL.requestStatus.status eq 'Approved'}">
                            <tr>
                                <td colspan="4">
                                    <font color="green">
                                    <h3>Manager Had Approved This Request, Follow The Using Date To Get Our Resource.</h3>
                                    </font>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${requestScope.HISTORY_DETAIL.requestStatus.status eq 'Denied'}">
                            <tr>
                                <td colspan="4">
                                    <font color="red">
                                    <h3>Your Request Is Denied Due To Not Enough Quantity.</h3>
                                    </font>
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </c:if>
        </div>
    </body>
</html>
