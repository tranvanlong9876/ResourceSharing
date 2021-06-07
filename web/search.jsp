<%-- 
    Document   : search
    Created on : May 11, 2021, 8:33:35 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search For Resource</title>
        <link rel="stylesheet" href="./css/style.css"/>
        <link rel="stylesheet" href="./css/search.css"/>
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
                        <c:if test="${sessionScope.ACCOUNTDETAIL.role eq 'Manager'}">
                            <td>
                                <form action="DispatchServlet" method="POST">
                                    <input class="manage-process" type="submit" value="Manage Request" name="action" />
                                </form>
                            </td>
                        </c:if>
                        <c:if test="${sessionScope.ACCOUNTDETAIL.role eq 'Employee' || sessionScope.ACCOUNTDETAIL.role eq 'Leader'}">
                            <td>
                                <form action="DispatchServlet" method="POST">
                                    <input class="manage-process" type="submit" value="View Request History" name="action" />
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
                            <h1>RENT RESOURCE</h1>
                        </td>
                    </tr>
                </tbody>
            </table>
            <form action="DispatchServlet" method="GET">
                <table class="search-function" border="0">
                    <tbody>
                        <tr>
                            <td>
                                Category:
                                <select name="categoryParam">
                                    <option value="0">All</option>
                                    <c:forEach items="${requestScope.SEARCH_DETAIL.listOfCategory}" var="dto">
                                        <option <c:if test="${requestScope.SEARCH_DETAIL.currentCategoryID == dto.categoryID}">selected="true"</c:if> value="${dto.categoryID}">${dto.categoryName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td>
                                Using Date:
                                <input type="date" name="txtRentTime" value="${requestScope.SEARCH_DETAIL.rentTime}" />
                            </td>
                            <td>
                                Find Item: 
                            </td>
                            <td>
                                <input type="text" name="txtSearchItem" value="${param.txtSearchItem}" placeholder="Item Name..." />
                            </td>
                            <td>
                                <input type="submit" value="Search" name="action" />
                            </td>

                        </tr>
                    </tbody>
                </table>
            </form>
        </div>

        <div class="paging">
            <c:url value="DispatchServlet" var="page">
                <c:param name="action" value="Search"/>
                <c:param name="categoryParam" value="${requestScope.SEARCH_DETAIL.currentCategoryID}"/>
                <c:param name="txtRentTime" value="${requestScope.SEARCH_DETAIL.rentTime}"/>
                <c:param name="txtSearchItem" value="${param.txtSearchItem}"/>
            </c:url>

            <table border="0">
                <tbody>
                    <tr>
                        <td>
                            <button>
                                <a href="${page}&txtPage=<c:if test="${requestScope.SEARCH_DETAIL.currentPage > 1}">${requestScope.SEARCH_DETAIL.currentPage - 1}</c:if><c:if test="${requestScope.SEARCH_DETAIL.currentPage == 1}">1</c:if>">Previous Page</a>
                                </button>
                            </td>
                                <td>${requestScope.SEARCH_DETAIL.currentPage} / ${requestScope.SEARCH_DETAIL.totalPage}</td>
                        <td>
                            <button>
                                <a href="${page}&txtPage=<c:if test="${requestScope.SEARCH_DETAIL.currentPage < requestScope.SEARCH_DETAIL.totalPage}">${requestScope.SEARCH_DETAIL.currentPage + 1}</c:if><c:if test="${requestScope.SEARCH_DETAIL.currentPage == requestScope.SEARCH_DETAIL.totalPage}">${requestScope.SEARCH_DETAIL.totalPage}</c:if>">Next Page</a>
                                </button>
                            </td>
                        <tr>
                        <c:if test="${requestScope.ALREADY_SENT != null}">
                    <font class="status-rent" color="red">
                    ${requestScope.ALREADY_SENT}
                    </font>
                </c:if>
                </tr>


                </tr>
                </tbody>
            </table>
            <c:if test="${sessionScope.ACCOUNTDETAIL.role eq 'Employee' || sessionScope.ACCOUNTDETAIL.role eq 'Leader'}">
                <a class="view-cart" href="DispatchServlet?action=viewCart">View Your Cart</a>
            </c:if>
        </div>

        <c:if test="${requestScope.RESOURCE_ITEM.size() > 0}">
            <table class="item-list" border="0">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Item Name</th>
                        <th>Color</th>
                        <th>Category</th>
                        <th>Quantity</th>
                            <c:if test="${sessionScope.ACCOUNTDETAIL.role eq 'Leader' || sessionScope.ACCOUNTDETAIL.role eq 'Employee'}">
                            <th>Rent Resource</th>
                            </c:if>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.RESOURCE_ITEM}" var="listResource" varStatus="counter">
                        <c:if test="${listResource.itemQuantity > 0}">
                            <tr>   
                                <td>${counter.count + ((requestScope.SEARCH_DETAIL.currentPage - 1) * 20)}</td>
                                <td>${listResource.itemName}</td>
                                <td>${listResource.itemColor}</td>
                                <td>${listResource.categoryName}</td>
                                <td>${listResource.itemQuantity}</td>
                                <c:if test="${sessionScope.ACCOUNTDETAIL.role eq 'Leader' || sessionScope.ACCOUNTDETAIL.role eq 'Employee'}">
                                    <td>
                                        <form action="DispatchServlet" method="GET">
                                            Quantity:
                                            <select name="cboRentQuantity">
                                                <c:forEach begin="1" end="${listResource.itemQuantity}" var="count">
                                                    <option value="${count}">${count}</option>
                                                </c:forEach>
                                            </select>
                                            <input type="submit" value="Add To Cart" name="action" />
                                            <input type="hidden" name="txtItemNo" value="${counter.count + ((requestScope.SEARCH_DETAIL.currentPage - 1) * 20)}" />
                                            <input type="hidden" name="txtItemID" value="${listResource.itemID}" />
                                            <input type="hidden" name="txtRentTime" value="${requestScope.SEARCH_DETAIL.rentTime}" />
                                            <input type="hidden" name="categoryParam" value="${requestScope.SEARCH_DETAIL.currentCategoryID}"/>
                                            <input type="hidden" name="txtSearchItem" value="${param.txtSearchItem}"/>
                                            <input type="hidden" name="txtPage" value="${requestScope.SEARCH_DETAIL.currentPage}" />
                                            <input type="hidden" name="txtItemName" value="${listResource.itemName}" />
                                        </form>
                                    </td>
                                </c:if>
                            </tr>
                        </c:if>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${requestScope.RESOURCE_ITEM.size() == 0 || empty requestScope.RESOURCE_ITEM}">
            <font class="notfound-item" color="red">
            No Item Matches With Name, Category Or Using Date!
            </font>
        </c:if>

    </body>
</html>
