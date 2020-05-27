<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<jsp:useBean id="Const" class="com.epam.huntingService.util.ParameterNamesConstants"/>

<html>
<style>
    body {
        background: url(img/darkTree.jpg) no-repeat fixed center;
        background-size: cover;
    }
</style>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="button.organizations"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container-fluid">
    <div class="col-md-12">

        <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
            <jsp:include page="admin_panel_part.jsp"/>
        </c:if>

        <h2 class="text-center text-light"><fmt:message key="head.organizations"/></h2>

        <table class="table table-striped table-dark text-center">
            <tr>
                <th><fmt:message key="th.logo"/></th>
                <th><fmt:message key="th.organization"/></th>
                <th><fmt:message key="th.description"/></th>
                <th><fmt:message key="th.hunting.ground"/></th>
            </tr>

            <c:forEach var="organization" items="${sessionScope.organizations}">
                <tr>
                    <td><img src="data:image/jpg;base64,${organization.logo}" class="rounded-circle"
                             alt="logo" height="100" width="100"/></td>
                    <td>
                        <form action="ShowOrganization" method="get">
                            <input type="hidden" name="organizationID" value="${organization.id}">
                            <input type="submit" class="btn btn-link" value="${organization.name}">
                        </form>
                    </td>
                    <td>${organization.description}</td>
                    <td>
                        <c:forEach var="huntingGround" items="${organization.huntingGrounds}">
                            ${huntingGround.name}<br>
                        </c:forEach>
                    </td>
                    <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
                        <td>
                            <form action="PrepareOrganizationEditing">
                                <input type="hidden" name="organizationID" value="${organization.id}">
                                <input type="submit" class="btn btn-primary btn-sm btn-block"
                                       value="<fmt:message key="button.edit"/>">
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>

            <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
                <form action="AddOrganization" method="post" enctype="multipart/form-data">
                    <tr>
                        <th colspan="7">
                            <c:if test="${requestScope.emptyFields != null}">
                                <small class="text-danger"><fmt:message key="error.empty.fields"/></small>
                            </c:if>
                            <c:if test="${requestScope.incorrectFile != null}">
                                <small class="text-danger"><fmt:message key="error.file"/></small>
                            </c:if>
                            <c:if test="${requestScope.organizationExistAlready != null}">
                                <small class="text-danger"><fmt:message key="error.org.exists.already"/></small>
                            </c:if>
                        </th>
                    </tr>
                    <tr>
                        <td><input type="hidden" name="langIDForAdding" value="${Const.russianId}">На русском</td>
                        <td><input type="file" name="logo" required><fmt:message key="alert.file.type"/></td>
                        <td><input type="text" name="orgName" placeholder="Название организации" required></td>
                        <td colspan="2"><textarea rows="3" cols="60" name="orgDescription"
                                                  placeholder="Описание организации"
                                                  required></textarea></td>
                    </tr>
                    <tr>
                        <td><input type="hidden" name="langIDForAdding" value="${Const.englishId}">In English</td>
                        <td><input type="file" name="logo" required><fmt:message key="alert.file.type"/></td>
                        <td><input type="text" name="orgName" placeholder="Название организации" required></td>
                        <td colspan="3"><textarea rows="3" cols="60" name="orgDescription"
                                                  placeholder="Описание организации"
                                                  required></textarea></td>
                    </tr>
                    <tr>
                        <td colspan="6"><input type="submit" class="btn btn-primary btn-sm btn-block"
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
