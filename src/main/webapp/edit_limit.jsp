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
    <title><fmt:message key="head.edit.limit"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="col-md-12">

        <jsp:include page="admin_panel_part.jsp"/>

        <h2 class="text-center text-light"><fmt:message key="head.edit.limit"/> ${sessionScope.animalName}</h2>

        <table class="table table-striped table-dark text-center">
            <tr>
                <td><fmt:message key="th.limit"/></td>
                <td><fmt:message key="th.MCI"/></td>
                <td><fmt:message key="th.cost.in.MCI"/></td>
                <td><fmt:message key="th.term.begin"/></td>
                <td><fmt:message key="th.term.end"/></td>
            </tr>

            <form action="EditAnimalLimit">
                <tr>
                    <th colspan="5" class="text-danger">
                        <c:if test="${requestScope.emptyFields != null}">
                            <small class="text-danger"><fmt:message key="error.empty.fields"/></small>
                        </c:if>
                    </th>
                </tr>
                <tr>
                    <td><label>
                        <input type="number" min="0" name="allLimit" value="${sessionScope.animalLimitHistory.allLimit}"
                               required>
                    </label>
                    </td>
                    <td><label>
                        <input type="number" min="0" name="mci"
                               value="${sessionScope.animalLimitHistory.monthlyCalculationIndex}"
                               required>
                    </label></td>
                    <td><label>
                        <input type="number" name="costInMCI" min="0" max="16" step="0.001"
                               value="${sessionScope.animalLimitHistory.animalCostInMCI}" required>
                    </label>
                    </td>
                    <td><label>
                        <input type="date" class="calendar-grid" name="termBegin" min="${sessionScope.today}"
                               value="${sessionScope.animalLimitHistory.termBegin}" required>
                    </label></td>
                    <td><label>
                        <input type="date" class="calendar-grid" name="termEnd" min="${sessionScope.today}"
                               value="${sessionScope.animalLimitHistory.termEnd}" required>
                    </label></td>
                    <td>
                        <input type="hidden" name="limitID" value="${sessionScope.animalLimitHistory.id}">
                        <input type="hidden" name="chosenYear" value="${sessionScope.animalLimitHistory.year}">
                        <input type="submit" class="btn btn-primary btn-sm btn-block"
                               value="<fmt:message key="button.update"/>">
                    </td>
                </tr>
            </form>
        </table>

    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>