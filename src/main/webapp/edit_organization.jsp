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
    <title><fmt:message key="head.edit.organization"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="col-md-12">

        <jsp:include page="admin_panel_part.jsp"/>

        <h2 class="text-center text-light"><fmt:message key="head.edit.organization"/></h2>

        <table class="table table-striped table-dark text-center">
            <tr>
                <th><fmt:message key="th.organization"/></th>
                <th><fmt:message key="th.description"/></th>
                <th><fmt:message key="th.logo"/></th>
            </tr>
            <form action="EditOrganization" method="post" enctype="multipart/form-data">
                <tr>
                    <th colspan="3"><input type="hidden" name="langIDForAdding" value="${Const.russianId}">На
                        русском
                    </th>
                </tr>
                <tr>
                    <th colspan="3" class="text-danger">
                        <c:if test="${requestScope.emptyFields != null}">
                            <small class="text-danger"><fmt:message key="error.empty.fields"/></small>
                        </c:if>
                        <c:if test="${requestScope.incorrectFile != null}">
                            <small class="text-danger"><fmt:message key="error.file"/></small>
                        </c:if>
                    </th>
                </tr>
                <tr>
                    <td>
                        <c:forEach var="organization" items="${sessionScope.localizedOrgs}">
                            <c:if test="${organization.languageID eq Const.russianId}">
                                <input type="hidden" name="organizationID" value="${organization.id}">
                                <input type="text" name="orgName" placeholder="Название организации"
                                       value="${organization.name}" required>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach var="organization" items="${sessionScope.localizedOrgs}">
                            <c:if test="${organization.languageID eq Const.russianId}">
                            <textarea rows="3" cols="60" name="orgDescription" placeholder="Описание организации"
                                      required>${organization.description}</textarea>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <input type="file" name="logo" required>
                        <fmt:message key="alert.file.type"/>
                    </td>
                </tr>

                <tr>
                    <th colspan="3"><input type="hidden" name="langIDForAdding" value="${Const.englishId}">In
                        English
                    </th>
                </tr>
                <tr>
                    <td>
                        <c:forEach var="organization" items="${sessionScope.localizedOrgs}">
                            <c:if test="${organization.languageID eq Const.englishId}">
                                <input type="hidden" name="organizationID" value="${organization.id}">
                                <input type="text" name="orgName" placeholder="Название организации"
                                       value="${organization.name}" required>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach var="organization" items="${sessionScope.localizedOrgs}">
                            <c:if test="${organization.languageID eq Const.englishId}">
                            <textarea rows="3" cols="60" name="orgDescription" placeholder="Описание организации"
                                      required>${organization.description}</textarea>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <input type="file" name="logo" required>
                        <fmt:message key="alert.file.type"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="3"><input type="submit" class="btn btn-primary btn-sm btn-block"
                                           value="<fmt:message key="button.update"/>"></td>
                </tr>
            </form>
        </table>

    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
