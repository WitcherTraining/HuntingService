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
    <title><fmt:message key="th.organization"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">

    <div class="text-center text-light">
        <p><img src="data:image/jpg;base64,${organization.logo}" class="rounded-circle"
                alt="logo" height="100" width="100"/></p>
        <h2><fmt:message key="head.welcome"/> ${sessionScope.organization.name}</h2>
        <p>${sessionScope.organization.description} </p>

        <h3><fmt:message key="head.assigned.hunting.grounds"/></h3>
    </div>

    <table class="table table-striped table-dark text-center">
        <tr>
            <th colspan="4"><fmt:message key="th.hunting.ground"/></th>
        </tr>
        <tr>
            <th><fmt:message key="th.animal"/></th>
            <th><fmt:message key="th.quota.balance"/></th>
            <th><fmt:message key="th.daily.permit.cost"/></th>
            <th><fmt:message key="th.season.permit.cost"/></th>
        </tr>

        <c:forEach var="huntingGround" items="${sessionScope.organization.huntingGrounds}">
            <tr>
                <td colspan="4">
                    <form action="ShowHuntingGround" method="get">
                        <input type="hidden" name="huntingGroundID" value="${huntingGround.id}">
                        <input type="submit" class="btn btn-link" value="${huntingGround.name}">
                    </form>
                </td>
            </tr>
            <c:forEach var="animal" items="${huntingGround.animals}">
                <tr>
                    <td>${animal.name}</td>
                    <td>${animal.animalQuotaHistory.animalQuota} <fmt:message key="label.heads"/></td>
                    <td>
                        <fmt:formatNumber value="${animal.animalQuotaHistory.dailyPrice}"
                                          pattern="###.##"/> <fmt:message key="label.money"/>
                    </td>
                    <td>
                        <fmt:formatNumber value="${animal.animalQuotaHistory.seasonPrice}"
                                          pattern="###.##"/> <fmt:message key="label.money"/>
                    </td>
                </tr>
            </c:forEach>
        </c:forEach>
    </table>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
