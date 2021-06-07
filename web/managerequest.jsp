<%-- 
    Document   : managerequest
    Created on : May 23, 2021, 1:08:59 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Process Request</title>
        <link rel="stylesheet" href="./css/request.css"/>
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
                                <input class="back-to-search" type="submit" value="Back To Search Page" name="action" />
                            </form>
                        </td>
                    </tr>
                </tbody>
            </table>
            <h1 class="title-manage">MANAGE REQUEST</h1>
            <form action="DispatchServlet" method="GET">
                <table class="search-function" border="0">
                    <tbody>
                        <tr>
                            <td>
                                Find Request: 
                            </td>
                            <td>
                                <input type="text" name="txtManageItem" value="${param.txtManageItem}" placeholder="User ID..." />
                            </td>
                            <td>
                                Request Status:
                            </td>
                            <td>
                                <c:set value="${requestScope.FILL_DETAIL.currentStatusID}" var="checkCbo"/>
                                <select name="requestStatusCBO">
                                    <option <c:if test="${checkCbo eq '1'}">selected="true"</c:if> value="1">New</option>
                                    <option <c:if test="${checkCbo eq '2'}">selected="true"</c:if> value="2">Accept</option>
                                    <option <c:if test="${checkCbo eq '3'}">selected="true"</c:if> value="3">Delete</option>
                                    </select>
                                </td>
                                <td>
                                    <input type="submit" value="Search Request" name="action" />
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </div>

            <div class="paging">
            <c:url value="DispatchServlet" var="page">
                <c:param name="action" value="Search Request"/>
                <c:param name="requestStatusCBO" value="${requestScope.FILL_DETAIL.currentStatusID}"/>
                <c:param name="txtManageItem" value="${param.txtManageItem}"/>
            </c:url>

            <table border="0">
                <tbody>
                    <tr>
                        <td>
                            <button>
                                <a href="${page}&txtPage=<c:if test="${requestScope.FILL_DETAIL.currentPage > 1}">${requestScope.FILL_DETAIL.currentPage - 1}</c:if><c:if test="${requestScope.FILL_DETAIL.currentPage == 1}">1</c:if>">Previous Page</a>
                                </button>
                            </td>
                                <td>${requestScope.FILL_DETAIL.currentPage} / ${requestScope.FILL_DETAIL.totalPage}</td>
                        <td>
                            <button>
                                <a href="${page}&txtPage=<c:if test="${requestScope.FILL_DETAIL.currentPage < requestScope.FILL_DETAIL.totalPage}">${requestScope.FILL_DETAIL.currentPage + 1}</c:if><c:if test="${requestScope.FILL_DETAIL.currentPage == requestScope.FILL_DETAIL.totalPage}">${requestScope.FILL_DETAIL.totalPage}</c:if>">Next Page</a>
                                </button>
                            </td>
                        <c:if test="${requestScope.ALREADY_SENT != null}">
                            <td>
                                <font color="red">
                                ${requestScope.ALREADY_SENT}
                                </font>
                            </td>
                        </c:if>

                    </tr>
                </tbody>
            </table>
        </div>
        <c:if test="${requestScope.REQUEST_LIST.size() > 0}">
            <table class="item-list" border="0">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Order ID</th>
                        <th>Request Date</th>
                        <th>User Request</th>
                        <th>Status</th>
                        <th>Details</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.REQUEST_LIST}" var="requestList" varStatus="counter">
                        <tr>   
                            <td>${counter.count + ((requestScope.FILL_DETAIL.currentPage - 1) * 20)}</td>
                            <td>${requestList.orderID}</td>
                            <td>${requestList.requestDate}</td>
                            <td>${requestList.userID}</td>
                            <td>${requestList.statusName}</td>
                            <td>
                                <form action="DispatchServlet" method="POST">
                                    <input type="submit" value="View Request Details" name="action" />
                                    <input type="hidden" name="orderID" value="${requestList.orderID}" />
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${requestScope.REQUEST_LIST.size() == 0 || empty requestScope.REQUEST_LIST}">
            <font class="notfound-request" color="red">
            No Request Matches With This Condition!
            </font>
        </c:if>
    </body>
</html>
