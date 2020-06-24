<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<jsp:useBean id="Const" class="com.epam.huntingService.util.constants.ParameterNamesConstants"/>

<html>
<style>
    body {
        background: url(img/darkTree.jpg) no-repeat fixed center;
        background-size: cover;
    }
</style>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="th.hunting.ground"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container text-center text-light">
    <div class="col-md-12">

        <h2 class="text-center"><fmt:message key="th.hunting.ground"/> ${sessionScope.huntingGround.name},
            <fmt:message key="th.assigned.to"/> ${sessionScope.huntingGround.organization.name}</h2>
        <p class="text-center">${sessionScope.huntingGround.description} </p>

        <h3><fmt:message key="head.wildfowl"/></h3>

        <table class="table table-striped table-dark text-center">
            <tr>
                <th><fmt:message key="th.animal"/></th>
                <th><fmt:message key="th.term.begin"/></th>
                <th><fmt:message key="th.term.end"/></th>
                <th><fmt:message key="th.quota.balance"/></th>
                <th><fmt:message key="th.animal.price"/><br><fmt:message key="alert.add"/></th>
                <c:if test="${sessionScope.roleID ne Const.adminRoleId}">
                    <th><fmt:message key="th.daily.permit.cost"/></th>
                    <th><fmt:message key="th.season.permit.cost"/></th>
                </c:if>
            </tr>

            <c:forEach var="animal" items="${sessionScope.huntingGround.animals}">
                <tr>
                    <th>
                            ${animal.name}
                    </th>
                    <td>
                        <fmt:formatDate value="${animal.animalLimitHistory.termBegin}" pattern="d MMMM yyyy"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${animal.animalLimitHistory.termEnd}" pattern="d MMMM yyyy"/>
                    </td>
                    <td>
                            ${animal.animalQuotaHistory.animalQuota}
                    </td>
                    <td>
                        <fmt:formatNumber value="${animal.animalLimitHistory.animalCost}" pattern="###.##"/>
                        <fmt:message key="label.money"/>
                    </td>
                    <c:if test="${sessionScope.roleID ne Const.adminRoleId}">
                        <td>
                            <form action="AddToCart" id="add_daily" method="post">
                                <input type="hidden" name="huntingGroundName" value="${sessionScope.huntingGround.name}">
                                <input type="hidden" name="huntingGroundID" value="${sessionScope.huntingGround.id}">
                                <input type="hidden" name="animalID" value="${animal.id}">
                                <input type="hidden" name="animalName" value="${animal.name}">
                                <input type="hidden" name="animalTermBegin"
                                       value="${animal.animalLimitHistory.termBegin}">
                                <input type="hidden" name="animalTermEnd" value="${animal.animalLimitHistory.termEnd}">
                                <input type="hidden" name="dailyPrice" value="${animal.animalQuotaHistory.dailyPrice}">
                                <input type="hidden" name="animalCost" value="${animal.animalLimitHistory.animalCost}">
                                <input type="hidden" name="huntingGroundQuota"
                                       value="${animal.animalQuotaHistory.animalQuota}">
                                <input type="hidden" name="permitType" value="Суточная">
                                <button type="submit" form="add_daily" class="btn btn-primary btn-lg btn-block">
                                    <fmt:formatNumber value="${animal.animalQuotaHistory.dailyPrice}"
                                                      pattern="###.##"/>
                                    <fmt:message key="label.money"/>
                                </button>
                            </form>
                        </td>
                        <td>
                            <form action="AddToCart" id="add_season" method="get">
                                <input type="hidden" name="huntingGroundName" value="${sessionScope.huntingGround.name}">
                                <input type="hidden" name="huntingGroundID" value="${sessionScope.huntingGround.id}">
                                <input type="hidden" name="animalID" value="${animal.id}">
                                <input type="hidden" name="animalName" value="${animal.name}">
                                <input type="hidden" name="animalTermBegin"
                                       value="${animal.animalLimitHistory.termBegin}">
                                <input type="hidden" name="animalTermEnd" value="${animal.animalLimitHistory.termEnd}">
                                <input type="hidden" name="seasonPrice"
                                       value="${animal.animalQuotaHistory.seasonPrice}">
                                <input type="hidden" name="animalCost" value="${animal.animalLimitHistory.animalCost}">
                                <input type="hidden" name="huntingGroundQuota"
                                       value="${animal.animalQuotaHistory.animalQuota}">
                                <input type="hidden" name="permitType" value="Сезонная">
                                <button type="submit" form="add_season" class="btn btn-primary btn-lg btn-block">
                                    <fmt:formatNumber value="${animal.animalQuotaHistory.seasonPrice}"
                                                      pattern="###.##"/> <fmt:message key="label.money"/>
                                </button>
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>

        <form action="Forward" method="get">
            <input type="hidden" name="direction" value="cart.jsp">
            <input type="submit" class="btn btn-primary btn-lg btn-block" value="<fmt:message key="button.cart"/>">
        </form>
    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
