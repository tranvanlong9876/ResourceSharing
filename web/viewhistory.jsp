<%-- 
    Document   : viewhistory
    Created on : May 27, 2021, 5:07:42 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Request History</title>
        <link rel="stylesheet" href="./css/history.css"/>
        <script src="./js/script.js" type="text/javascript"></script>
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
                        <c:if test="${sessionScope.ACCOUNTDETAIL.role eq 'Employee' || sessionScope.ACCOUNTDETAIL.role eq 'Leader'}">
                            <td>
                                <form action="DispatchServlet" method="POST">
                                    <input class="manage-process" type="submit" value="Rent Resource" name="action" />
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </tbody>
            </table>
            <table class="table-title" border="0">
                <tbody>
                    <tr>
                        <td>
                            <h1>REQUEST HISTORY</h1>
                        </td>
                    </tr>
                </tbody>
            </table>
            <form action="DispatchServlet" method="GET" onsubmit="return checkDate();">
                <table class="search-function" border="0">
                    <tbody>
                        <tr>
                            <td>
                                Date From: 
                            </td>
                            <td>
                                <input id="txt-dateFrom" type="date" name="txtDateFrom" value="${param.txtDateFrom}" placeholder="User ID..." />
                            </td>
                            <td>
                                To: 
                            </td>
                            <td>
                                <input id="txt-dateTo" type="date" name="txtDateTo" value="${param.txtDateTo}" placeholder="User ID..." />
                            </td>
                            <td>
                                <input type="submit" value="Fill Request" name="action" />
                            </td>
                            <td>
                                <button onclick="resetDate();" name="action" value="Get All Request">Get All Request</button>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6">
                                <p class="invalid-date-1" id="invalid-date"></p>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </form>
            <c:if test="${not empty requestScope.STATUS_HISTORY}">
                <font class="history-notify">
                ${requestScope.STATUS_HISTORY}
                </font>
            </c:if>

        </div>
        <c:if test="${requestScope.HISTORYHEADER.size() > 0}">
            <table class="item-list" border="0">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Order ID</th>
                        <th>Request Date</th>
                        <th>Status</th>
                        <th>Details</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.HISTORYHEADER}" var="historyList" varStatus="counter">
                        <tr>   
                            <td>${counter.count}</td>
                            <td>${historyList.orderID}</td>
                            <td>${historyList.requestTime}</td>
                            <td>${historyList.status}</td>
                            <td>
                                <form action="DispatchServlet" method="POST">
                                    <button name="action" value="View History Detail">Show Order Details</button>
                                    <input type="hidden" name="orderID" value="${historyList.orderID}" />
                                </form>
                            </td>
                            <td>
                                <c:if test="${historyList.status eq 'New' || historyList.status eq 'Denied'}">
                                    <form action="DispatchServlet" method="POST">
                                        <button name="action" value="Delete History Request">Delete Request</button>
                                        <input type="hidden" name="orderID" value="${historyList.orderID}" />
                                        <input type="hidden" name="statusName" value="${historyList.status}" />
                                        <input type="hidden" name="txtDateFrom" value="${param.txtDateFrom}" placeholder="User ID..." />
                                        <input type="hidden" name="txtDateTo" value="${param.txtDateTo}" placeholder="User ID..." />
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${requestScope.HISTORYHEADER.size() == 0 || empty requestScope.HISTORYHEADER}">
            <font class="notfound-request" color="red">
            No Request Matches With This Condition!
            </font>
        </c:if>
    </body>
</html>
