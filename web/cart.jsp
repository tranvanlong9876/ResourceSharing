<%-- 
    Document   : cart
    Created on : May 25, 2021, 3:47:03 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Your Cart</title>
        <link rel="stylesheet" href="./css/cart.css"/>
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
                                <input class="back-to-search" type="submit" value="Back To Search Page" name="action" />
                            </form>
                        </td>
                    </tr>
                </tbody>
            </table>
            <table border="0">
                <tbody>
                    <tr>
                        <td>
                            <h2>${sessionScope.ACCOUNTDETAIL.name}'s Cart Details:</h2>
                        </td>
                        <c:if test="${requestScope.CARTSTATUS != null}">
                            <td>
                                <font color="red">
                                ${requestScope.CARTSTATUS}
                                </font>
                            </td>
                        </c:if>
                    </tr>
                </tbody>
            </table>

        </div>
        <div class="cart-detail">
            <c:if test="${sessionScope.CARTDETAIL.cart.size() > 0}">
                <table border="0">
                    <thead>
                        <tr>
                            <th>No.</th>
                            <th class="long-name">Resource Name</th>
                            <th>Quantity</th>
                            <th>Using Date</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${sessionScope.CARTDETAIL.cart}" var="cart" varStatus="counter">
                            <c:url value="DispatchServlet" var="DeleteCartLink">
                                <c:param name="action" value="Delete Cart"/>
                                <c:param name="idResource" value="${cart.key}"/>
                            </c:url>
                            <tr>
                                <td>${counter.count}</td>
                                <td>${cart.value.itemName}</td>
                                <td>${cart.value.itemQuantity}</td>
                                <td>${cart.value.rentDate}</td>
                                <td>
                                    <button>
                                        <a style="text-decoration: none;" href="${DeleteCartLink}">Delete Item</a>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${sessionScope.CARTDETAIL.cart.size() == 0 || empty sessionScope.CARTDETAIL}">
                <font color="red">
                <h2 style="margin-left: 22%;">There's No Item In Your Cart Yet, <a href="DispatchServlet?action=Back To Search Page">Rent Now!</a></h2>
                </font>
            </c:if>
        </div>
        <c:if test="${sessionScope.CARTDETAIL.cart.size() > 0}">
            <form action="DispatchServlet" method="GET">
                <input class="book-btn" type="submit" value="Send Request" name="action" />
            </form>
        </c:if>


    </body>
</html>
