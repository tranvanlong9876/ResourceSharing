<%-- 
    Document   : requestdetail
    Created on : May 25, 2021, 11:43:16 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Request</title>
        <link rel="stylesheet" href="./css/detail.css"/>
    </head>
    <body>
        <div class="header">
            <table class="welcome-user" border="1">
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
                                <input class="back-to-search" type="submit" value="Manage Request" name="action" />
                            </form>
                        </td>
                    </tr>
                </tbody>
            </table>
            <h1 class="title-detail">SHOW REQUEST DETAIL</h1>
            <table class="view-detail" border="0">
                <tbody>
                    <tr>
                        <td>
                            <h2>Request Details:</h2>
                        </td>
                        <td>
                            <h2>UserID: ${requestScope.ORDERDETAIL.statusDetail.userID} |</h2>
                        </td>
                        <td>
                            <h2>Request Status: ${requestScope.ORDERDETAIL.statusDetail.statusName}</h2>
                        </td>
                </tbody>
            </table>
        </div>
        <div class="cart-detail">
            <c:if test="${requestScope.ORDERDETAIL.listDetail.size() > 0}">
                <table border="0">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th class="long-name">Resource Name</th>
                            <th>Quantity</th>
                            <th>Using Date</th>
                            <th>Note</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.ORDERDETAIL.listDetail}" var="detail" varStatus="counter">
                            <tr>
                                <td>${counter.count}</td>
                                <td>${detail.resourceName}</td>
                                <td>${detail.quantity}</td>
                                <td>${detail.rentTime}</td>
                                <c:if test="${requestScope.ORDERDETAIL.statusDetail.statusName eq 'New'}">
                                    <td>
                                        <c:if test="${detail.checkQuantity == false}">
                                            <font color="red">
                                            Not Enough Resource, Can Not Accept
                                            </font>
                                        </c:if>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        <c:if test="${requestScope.ORDERDETAIL.statusDetail.statusName eq 'New'}">
                            <c:if test="${requestScope.ORDERDETAIL.canAccept == false}">
                                <tr>
                                    <td colspan="5">
                                        <font color="red">
                                        <h3>You Can Not Accept This Request Because One Or More Resource Are Not Enough Quantity.</h3>
                                        </font>
                                    </td>
                                </tr>
                            </c:if>
                        </c:if>
                        <c:if test="${requestScope.ORDERDETAIL.statusDetail.statusName eq 'Accept'}">
                            <tr>
                                <td colspan="5">
                                    <font color="green">
                                    <h3>You have approved this request.</h3>
                                    </font>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${requestScope.ORDERDETAIL.statusDetail.statusName eq 'Delete'}">
                            <tr>
                                <td colspan="5">
                                    <font color="red">
                                    <h3>You have deleted this request.</h3>
                                    </font>
                                </td>
                            </tr>
                        </c:if>
                        <c:if test="${requestScope.ORDERDETAIL.statusDetail.statusName eq 'New'}">
                            <tr>
                                <td colspan="5">
                                    <form action="DispatchServlet" method="POST">
                                        <c:if test="${requestScope.ORDERDETAIL.canAccept == true}">
                                            <input class="accept-btn" type="submit" value="Accept Request" name="action"/>
                                        </c:if>
                                        <input class="deny-btn" type="submit" value="Delete Request" name="action" />
                                        <input type="hidden" name="orderID" value="${requestScope.ORDERDETAIL.statusDetail.orderID}" />
                                    </form>
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </c:if>
        </div>
    </body>
</html>
