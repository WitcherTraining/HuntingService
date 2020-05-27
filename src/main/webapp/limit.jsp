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
    <title><fmt:message key="th.limit"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:useBean id="Const" class="com.epam.huntingService.util.ParameterNamesConstants"/>
<jsp:include page="header.jsp"/>
<div class="container-fluid">

    <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
        <jsp:include page="admin_panel_part.jsp"/>
    </c:if>

    <h2 class="text-center text-light"><fmt:message key="head.limit"/></h2>

    <table class="table table-striped table-dark text-center">
        <tr>
            <td><fmt:message key="th.animal"/></td>
            <td><fmt:message key="th.limit"/></td>
            <td><fmt:message key="th.MCI"/></td>
            <td><fmt:message key="th.cost.in.MCI"/></td>
            <td><fmt:message key="th.animal.price"/></td>
            <td><fmt:message key="th.term.begin"/></td>
            <td><fmt:message key="th.term.end"/></td>
        </tr>

        <c:forEach var="limit" items="${sessionScope.animalLimitHistories}">
            <tr>
                <td>${limit.animal.name}</td>
                <td>${limit.allLimit}</td>
                <td>
                    <fmt:formatNumber value="${limit.monthlyCalculationIndex}"
                                      pattern="###.##"/> <fmt:message key="label.money"/>
                </td>
                <td>${limit.animalCostInMCI}</td>
                <td>
                    <fmt:formatNumber value="${limit.animalCost}"
                                      pattern="###.##"/> <fmt:message key="label.money"/>
                </td>
                <td><fmt:formatDate value="${limit.termBegin}" pattern="d MMMM yyyy"/></td>
                <td><fmt:formatDate value="${limit.termEnd}" pattern="d MMMM yyyy"/></td>

                <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
                    <td>
                        <form action="PrepareAnimalLimitEditing">
                            <input type="hidden" name="animalName" value="${limit.animal.name}">
                            <input type="hidden" name="animalLimitID" value="${limit.id}">
                            <input type="hidden" name="year" value="${limit.year}">
                            <input type="submit" class="btn btn-primary btn-sm btn-block"
                                   value="<fmt:message key="button.edit"/>">
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
            <c:if test="${sessionScope.chosenYear == sessionScope.currentYear}">
                <form action="AddAnimalLimit">
                    <tr>
                        <th colspan="9">
                            <c:if test="${requestScope.emptyFields != null}">
                                <small class="text-danger"><fmt:message key="error.empty.fields"/></small>
                            </c:if>
                            <c:if test="${requestScope.animalIsNotExist != null}">
                                <small class="text-danger"><fmt:message key="error.animal.not.exist"/></small>
                            </c:if>
                            <c:if test="${requestScope.animalLimitIsExist != null}">
                                <small class="text-danger"><fmt:message key="error.limit.set.already"/></small>
                            </c:if>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <label>
                                <select name="animalName">
                                    <c:forEach items="${sessionScope.animalsForLimit}" var="animal">
                                        <option>${animal.name}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </td>
                        <td><input type="number" min="0" name="allLimit" required></td>
                        <td><input type="number" min="0" name="mci" required></td>
                        <td><input type="number" value="0" min="0" max="16" step="0.001" name="costInMCI"
                                   required>
                        </td>
                        <td><input type="hidden" name="chosenYear" value="${sessionScope.chosenYear}"><fmt:message
                                key="alert.calculated.auto"/></td>
                        <td><input type="date" class="calendar-grid" min="${sessionScope.today}" name="termBegin"
                                   placeholder="Дата начала охоты" required></td>
                        <td><input type="date" class="calendar-grid" min="${sessionScope.today}" name="termEnd"
                                   placeholder="Дата окончания охоты" required></td>
                        <td><input type="submit" class="btn btn-primary btn-sm btn-block"
                                   value="<fmt:message key="button.add"/>"></td>
                    </tr>
                </form>
            </c:if>
        </c:if>
    </table>

</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
