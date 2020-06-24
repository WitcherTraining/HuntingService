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
    <title><fmt:message key="button.hunting.grounds"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:include page="header.jsp"/><div class="container-fluid">
    <div class="col-md-12">

        <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
            <jsp:include page="admin_panel_part.jsp"/>
        </c:if>

        <h2 class="text-center text-light"><fmt:message key="head.hunting.grounds"/></h2>

        <table class="table table-striped table-dark text-center">
            <tr>
                <th><fmt:message key="th.hunting.ground"/></th>
                <th><fmt:message key="th.assigned.to"/></th>
                <th><fmt:message key="th.description"/></th>
                <th><fmt:message key="th.district"/></th>
                <th><fmt:message key="th.animal"/></th>
            </tr>

            <c:forEach var="huntingGround" items="${sessionScope.huntingGrounds}">
                <tr>
                    <td>
                        <form action="ShowHuntingGround" method="get">
                            <input type="hidden" name="huntingGroundID" value="${huntingGround.id}">
                            <input type="submit" class="btn btn-link" value="${huntingGround.name}">
                        </form>
                    </td>
                    <td>${huntingGround.organization.name}</td>
                    <td>${huntingGround.description}</td>
                    <td>${huntingGround.district}</td>
                    <td>
                        <c:forEach var="animal" items="${huntingGround.animals}">
                            ${animal.name}<br>
                        </c:forEach>
                    </td>
                    <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
                        <td>
                            <form action="PrepareHuntingGroundEditing">
                                <input type="hidden" name="huntingGroundID" value="${huntingGround.id}">
                                <input type="submit" class="btn btn-primary btn-sm btn-block"
                                       value="<fmt:message key="button.edit"/>">
                            </form>
                            <form action="ShowQuota" method="get">
                                <input type="hidden" name="huntingGroundID" value="${huntingGround.id}">
                                <input type="submit" class="btn btn-primary btn-sm btn-block"
                                       value="<fmt:message key="button.show.quota"/>">
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>

            <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
                <form action="AddHuntingGround">
                    <tr>
                        <th colspan="8">
                            <c:if test="${requestScope.emptyFields != null}">
                                <small class="text-danger"><fmt:message key="error.empty.fields"/></small>
                            </c:if>
                            <c:if test="${requestScope.notEqualOrganizations != null}">
                                <small class="text-danger"><fmt:message key="error.org.equality"/></small>
                            </c:if>
                            <c:if test="${requestScope.huntingGroundExistAlready != null}">
                                <small class="text-danger"><fmt:message key="error.hunting.ground.name"/></small>
                            </c:if>
                        </th>
                    </tr>
                    <tr>
                        <td><input type="hidden" name="langIDForAdding" value="${Const.russianId}">На русском</td>
                        <td><input type="text" name="huntingGroundName" placeholder="Название хозяйства" required></td>
                        <td>
                            <label>
                                <select class="form-control form-control-sm" name="orgName">
                                    <c:forEach var="organization" items="${sessionScope.localizedOrgs}">
                                        <c:if test="${organization.languageID eq Const.russianId}">
                                            <option>
                                                    ${organization.name}
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </label>
                        </td>
                        <td colspan="2"><textarea rows="3" cols="40" name="description"
                                                  placeholder="Описание хозяйства"
                                                  required></textarea></td>
                        <td><input type="text" name="district" placeholder="Админитративный район" required></td>
                    </tr>
                    <tr>
                        <td><input type="hidden" name="langIDForAdding" value="${Const.englishId}">На английском
                        </td>
                        <td><input type="text" name="huntingGroundName" placeholder="Название хозяйства" required></td>
                        <td>
                            <label>
                                <select class="form-control form-control-sm" name="orgName">
                                    <c:forEach var="organization" items="${sessionScope.localizedOrgs}">
                                        <c:if test="${organization.languageID eq Const.englishId}">
                                            <option>
                                                    ${organization.name}
                                            </option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                            </label>
                        </td>
                        <td colspan="2"><textarea rows="3" cols="40" name="description"
                                                  placeholder="Описание хозяйства"
                                                  required></textarea></td>
                        <td><input type="text" name="district" placeholder="Админитративный район" required></td>
                    </tr>
                    <tr>
                        <td colspan="8"><input type="submit" class="btn btn-primary btn-sm btn-block"
                                               value="<fmt:message key="button.add"/>"></td>
                    </tr>
                </form>
            </c:if>
        </table>

    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
