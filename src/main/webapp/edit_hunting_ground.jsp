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
    <title><fmt:message key="head.edit.hunting.ground"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="col-md-12">

        <jsp:include page="admin_panel_part.jsp"/>

        <h2 class="text-center text-light"><fmt:message key="head.edit.hunting.ground"/></h2>

        <table class="table table-striped table-dark text-center">
            <tr>
                <th><fmt:message key="th.hunting.ground"/></th>
                <th><fmt:message key="th.assigned.to"/></th>
                <th><fmt:message key="th.description"/></th>
                <th><fmt:message key="th.district"/></th>
            </tr>

            <form action="EditHuntingGround">
                <tr>
                    <th colspan="4"><input type="hidden" name="langIDForAdding" value="${Const.russianId}">На
                        русском
                    </th>
                </tr>
                <tr>
                    <th colspan="4" class="text-danger">
                        <c:if test="${requestScope.emptyFields != null}">
                            <small class="text-danger"><fmt:message key="error.empty.fields"/></small>
                        </c:if>
                        <c:if test="${requestScope.wrongRuOrganizationName != null}">
                            <small class="text-danger"><fmt:message key="error.org.ru.name"/></small>
                        </c:if>
                        <c:if test="${requestScope.wrongEnOrganizationName != null}">
                            <small class="text-danger"><fmt:message key="error.org.en.name"/></small>
                        </c:if>
                    </th>
                </tr>
                <tr>
                    <td>
                        <c:forEach var="huntingGround" items="${sessionScope.localizedHuntingGrounds}">
                            <c:if test="${huntingGround.languageID eq Const.russianId}">
                                <input type="hidden" name="huntingGroundID" value="${huntingGround.id}">
                                <input type="text" name="huntingGroundName" placeholder="Название хозяйства"
                                       value="${huntingGround.name}" required>
                            </c:if>
                        </c:forEach>
                    </td>
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
                    <td>
                        <c:forEach var="huntingGround" items="${sessionScope.localizedHuntingGrounds}">
                            <c:if test="${huntingGround.languageID eq Const.russianId}">
                            <textarea rows="3" cols="60" name="description" placeholder="Описание хозяйства"
                                      required>${huntingGround.description}</textarea>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach var="huntingGround" items="${sessionScope.localizedHuntingGrounds}">
                            <c:if test="${huntingGround.languageID eq Const.russianId}">
                                <input type="text" name="district" placeholder="Админитративный район"
                                       value="${huntingGround.district}" required>
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>

                <tr>
                    <th colspan="4"><input type="hidden" name="langIDForAdding" value="${Const.englishId}">In
                        English
                    </th>
                </tr>
                <tr>
                    <td>
                        <c:forEach var="huntingGround" items="${sessionScope.localizedHuntingGrounds}">
                            <c:if test="${huntingGround.languageID eq Const.englishId}">
                                <input type="hidden" name="huntingGroundID" value="${huntingGround.id}">
                                <input type="text" name="huntingGroundName" placeholder="Название хозяйства"
                                       value="${huntingGround.name}" required>
                            </c:if>
                        </c:forEach>
                    </td>
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
                    <td>
                        <c:forEach var="huntingGround" items="${sessionScope.localizedHuntingGrounds}">
                            <c:if test="${huntingGround.languageID eq Const.englishId}">
                            <textarea rows="3" cols="60" name="description" placeholder="Описание хозяйства"
                                      required>${huntingGround.description}</textarea>
                            </c:if>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach var="huntingGround" items="${sessionScope.localizedHuntingGrounds}">
                            <c:if test="${huntingGround.languageID eq Const.englishId}">
                                <input type="text" name="district" placeholder="Админитративный район"
                                       value="${huntingGround.district}" required>
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td colspan="4">
                        <input type="submit" class="btn btn-primary btn-sm btn-block"
                               value="<fmt:message key="button.update"/>"></td>
                </tr>
            </form>
        </table>

    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
