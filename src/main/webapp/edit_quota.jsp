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
    <title><fmt:message key="head.edit.quota"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="col-md-12">

        <jsp:include page="admin_panel_part.jsp"/>

        <h2 class="text-center text-light"><fmt:message key="head.edit.quota"/> ${sessionScope.quota.animal.name}</h2>

        <table class="table table-striped table-dark text-center">
            <tr>
                <th><fmt:message key="th.animal"/></th>
                <th><fmt:message key="th.hunting.ground"/></th>
                <th><fmt:message key="th.quota"/></th>
                <th><fmt:message key="th.daily.permit.cost"/></th>
                <th><fmt:message key="th.season.permit.cost"/></th>
            </tr>

            <form action="EditQuota">
                <tr>
                    <td colspan="5">
                        <c:if test="${requestScope.emptyFields != null}">
                            <small class="text-danger"><fmt:message key="error.empty.fields"/></small>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td><input type="hidden" name="animalID" value="${sessionScope.quota.animal.id}">${sessionScope.quota.animal.name}</td>
                    <td><input type="hidden" name="huntingGroundID"
                               value="${sessionScope.quota.huntingGround.id}">${sessionScope.quota.huntingGround.name}</td>
                    <td><label>
                        <input type="number" name="animalQuota" value="${sessionScope.quota.animalQuota}" min="0" required>
                    </label></td>
                    <td><label>
                        <input type="number" name="dailyPrice" min="0" value="${sessionScope.quota.dailyPrice}" required>
                    </label></td>
                    <td><label>
                        <input type="number" name="seasonPrice" min="0" value="${sessionScope.quota.seasonPrice}" required>
                    </label></td>
                    <td>
                        <input type="hidden" name="quotaID" value="${sessionScope.quota.id}">
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
