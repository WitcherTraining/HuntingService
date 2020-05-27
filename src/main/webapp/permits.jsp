<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>

<html>
<style>
    body {
        background: url(img/darkTree.jpg) no-repeat fixed center;
        background-size: cover;
    }
</style>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="button.permits"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="col-md-12">

        <jsp:include page="admin_panel_part.jsp"/>

        <h2 class="text-center text-light"><fmt:message key="head.permits"/></h2>

        <table class="table table-striped table-dark text-center">
            <tr>
                <th><fmt:message key="th.hunter"/></th>
                <th><fmt:message key="th.order.date"/></th>
                <th><fmt:message key="th.organization"/></th>
                <th><fmt:message key="th.hunting.ground"/></th>
                <th><fmt:message key="th.animal"/></th>
                <th><fmt:message key="th.animals.count"/></th>
                <th><fmt:message key="th.permit.type"/></th>
                <th><fmt:message key="th.total.cost"/></th>
            </tr>

            <c:forEach var="permit" items="${sessionScope.permits}">
                <tr>
                    <td>${permit.user.login}</td>
                    <td><fmt:formatDate value="${permit.orderDate}" pattern="d MMMM yyyy"/></td>
                    <td>${permit.organization.name}</td>
                    <td>${permit.huntingGround.name}</td>
                    <td>${permit.animal.name}</td>
                    <td>${permit.countOrderedAnimals}</td>
                    <td>${permit.permitType}</td>
                    <td>
                        <fmt:formatNumber value="${permit.totalCost}" pattern="###.##"/> <fmt:message
                            key="label.money"/>
                    </td>
                </tr>
            </c:forEach>
        </table>

    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
