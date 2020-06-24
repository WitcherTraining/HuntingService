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
    <title><fmt:message key="head.quota"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:useBean id="Const" class="com.epam.huntingService.util.constants.ParameterNamesConstants"/>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="col-md-12">

        <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
            <jsp:include page="admin_panel_part.jsp"/>
        </c:if>

        <h2 class="text-center text-light"><fmt:message key="head.quota"/> ${sessionScope.huntingGround.name}</h2>

        <table class="table table-striped table-dark text-center">
            <tr>
                <th><fmt:message key="th.year"/></th>
                <th><fmt:message key="th.animal"/></th>
                <th><fmt:message key="th.hunting.ground"/></th>
                <th><fmt:message key="th.quota"/></th>
                <th><fmt:message key="th.daily.permit.cost"/></th>
                <th><fmt:message key="th.season.permit.cost"/></th>
            </tr>

            <c:forEach var="quota" items="${sessionScope.animalQuotas}">
                <tr>
                    <th>${quota.year} <fmt:message key="th.year"/></th>
                    <th>${quota.animal.name}</th>
                    <th>${quota.huntingGround.name}</th>
                    <th>${quota.animalQuota}</th>
                    <th>
                        <fmt:formatNumber value="${quota.dailyPrice}" pattern="###.##"/> <fmt:message
                            key="label.money"/>
                    </th>
                    <th>
                        <fmt:formatNumber value="${quota.seasonPrice}" pattern="###.##"/> <fmt:message
                            key="label.money"/>
                    </th>
                    <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
                        <c:if test="${quota.year == sessionScope.currentYear}">
                            <td>
                                <form action="PrepareQuotaEditing">
                                    <input type="hidden" name="quotaID" value="${quota.id}">
                                    <input type="submit" class="btn btn-primary btn-sm btn-block"
                                           value="<fmt:message key="button.edit"/>">
                                </form>
                            </td>
                        </c:if>
                    </c:if>
                </tr>
            </c:forEach>

            <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
                <form action="AddQuota">
                    <tr>
                        <td colspan="6">
                            <c:if test="${requestScope.emptyFields != null}">
                                <small class="text-danger"><fmt:message key="error.empty.fields"/></small>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <th>${sessionScope.currentYear}</th>
                        <th>
                            <label>
                                <select name="animalName">
                                    <c:forEach var="animal" items="${sessionScope.animals}">
                                        <option>${animal.name}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </th>
                        <th>${sessionScope.huntingGround.name}</th>
                        <th><label>
                            <input type="number" name="animalQuota" min="0" required>
                        </label></th>
                        <th><input type="number" name="dailyPrice" min="0" required></th>
                        <th><input type="number" name="seasonPrice" min="0" required></th>
                        <td>
                            <input type="hidden" name="huntingGroundID" value="${sessionScope.huntingGround.id}">
                            <input type="submit" class="btn btn-primary btn-sm btn-block"
                                   value="<fmt:message key="button.add"/>">
                        </td>
                    </tr>
                </form>
            </c:if>
        </table>

    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
