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
    <title><fmt:message key="button.cabinet"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<jsp:useBean id="Const" class="com.epam.huntingService.util.ParameterNamesConstants"/>
<body>
<jsp:include page="header.jsp"/>
<br>
<div class="container">
    <div class="col-md-12">
        <form class="text-right" action="ChangeLanguage" method="get">
            <label>
                <select name="languageToChange">
                    <option value="ru_RU">Русский</option>
                    <option value="en_US">English</option>
                </select>
            </label>
            <input type="hidden" name="direction" value="cabinet.jsp">
            <input type="submit" class="btn btn-primary btn-sm" value="<fmt:message key="button.change.language"/>">
        </form>
        <form class="text-right" action="Forward" method="get">
            <input type="hidden" name="direction" value="edit_password.jsp">
            <input type="submit" class="btn btn-primary btn-sm" value="<fmt:message key="button.change.password"/>">
        </form>
        <c:if test="${requestScope.successfullyUpdatedUser != null}">
            <small class="text-success"><fmt:message key="alert.update.profile"/></small>
        </c:if>
        <form class="text-right" action="Forward" method="get">
            <input type="hidden" name="direction" value="edit_profile.jsp">
            <input type="submit" class="btn btn-primary btn-sm" value="<fmt:message key="head.edit.profile"/>">
        </form>

        <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
            <jsp:include page="admin_panel_part.jsp"/>
        </c:if>

        <c:if test="${sessionScope.roleID eq Const.hunterRoleId}">
            <h2 class="text-center text-light"><fmt:message key="head.daily.permits"/></h2>

            <table class="table table-striped table-dark text-center">
                <tr>
                    <th><fmt:message key="th.order.date"/></th>
                    <th><fmt:message key="th.organization"/></th>
                    <th><fmt:message key="th.hunting.ground"/></th>
                    <th><fmt:message key="th.animal"/></th>
                    <th><fmt:message key="th.animals.count"/></th>
                    <th><fmt:message key="th.term.begin"/></th>
                    <th><fmt:message key="th.term.end"/></th>
                    <th><fmt:message key="th.total.cost"/></th>
                    <th><fmt:message key="th.hunting.date"/></th>
                </tr>

                <c:forEach var="permit" items="${sessionScope.permits}">
                    <c:if test="${permit.permitType eq 'Суточная'}">
                        <tr>
                            <td><fmt:formatDate value="${permit.orderDate}" pattern="d MMMM yyyy"/></td>
                            <td>${permit.organization.name}</td>
                            <td>${permit.huntingGround.name}</td>
                            <td>${permit.animal.name}</td>
                            <td>${permit.countOrderedAnimals}</td>
                            <td>
                                <fmt:formatDate value="${permit.animal.animalLimitHistory.termBegin}"
                                                pattern="d MMMM yyyy"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${permit.animal.animalLimitHistory.termEnd}"
                                                pattern="d MMMM yyyy"/>
                            </td>
                            <td>
                                <fmt:formatNumber value="${permit.totalCost}" pattern="###.##"/>
                                <fmt:message key="label.money"/>
                            </td>
                            <td><fmt:formatDate value="${permit.huntingDay}" pattern="d MMMM yyyy"/></td>
                            <td>
                                <form action="PreparePermitToPrint" method="get">
                                    <input type="hidden" name="permitID" value="${permit.id}">
                                    <input type="submit" class="btn btn-primary btn-sm"
                                           value="<fmt:message key="button.print.permit"/>">
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>

            <h2 class="text-center text-light"><fmt:message key="head.season.permits"/></h2>

            <table class="table table-striped table-dark text-center">
                <tr>
                    <th><fmt:message key="th.order.date"/></th>
                    <th><fmt:message key="th.organization"/></th>
                    <th><fmt:message key="th.hunting.ground"/></th>
                    <th><fmt:message key="th.animal"/></th>
                    <th><fmt:message key="th.animals.count"/></th>
                    <th><fmt:message key="th.term.begin"/></th>
                    <th><fmt:message key="th.term.end"/></th>
                    <th><fmt:message key="th.total.cost"/></th>
                </tr>

                <c:forEach var="permit" items="${sessionScope.permits}">
                    <c:if test="${permit.permitType eq 'Сезонная'}">
                        <tr>
                            <td><fmt:formatDate value="${permit.orderDate}" pattern="d MMMM yyyy"/></td>
                            <td>${permit.organization.name}</td>
                            <td>${permit.huntingGround.name}</td>
                            <td>${permit.animal.name}</td>
                            <td>${permit.countOrderedAnimals}</td>
                            <td>
                                <fmt:formatDate value="${permit.animal.animalLimitHistory.termBegin}"
                                                pattern="d MMMM yyyy"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${permit.animal.animalLimitHistory.termEnd}"
                                                pattern="d MMMM yyyy"/>
                            </td>
                            <td>
                                <fmt:formatNumber value="${permit.totalCost}" pattern="###.##"/>
                                <fmt:message key="label.money"/>
                            </td>
                            <td>
                                <form action="PreparePermitToPrint" method="get">
                                    <input type="hidden" name="permitID" value="${permit.id}">
                                    <input type="submit" class="btn btn-primary btn-sm"
                                           value="<fmt:message key="button.print.permit"/>">
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>
            <br>
            <div class="text-center text-light">
                <h4 class="text-danger">
                    <c:if test="${requestScope.docUploaded != null}">
                        <small class="text-success"><fmt:message key="alert.upload"/></small>
                    </c:if>
                    <c:if test="${requestScope.incorrectFile != null}">
                        <small class="text-success"><fmt:message key="error.file"/></small>
                    </c:if>
                </h4>
                <h4><fmt:message key="alert.document"/></h4>
                <form action="UploadHunterDocument" method="post" enctype="multipart/form-data">
                    <input type="file" name="document">
                    <input type="submit" class="btn btn-primary btn-sm"
                           value="<fmt:message key="button.upload.document"/>">
                </form>
            </div>
        </c:if>

        <c:if test="${sessionScope.roleID eq Const.userRoleId}">
            <div class="text-center text-light">

                <c:if test="${requestScope.docUploaded != null}">
                    <small class="text-success"><fmt:message key="alert.upload"/></small>
                </c:if>
                <c:if test="${requestScope.incorrectFile != null}">
                    <small class="text-danger"><fmt:message key="error.file"/></small>
                </c:if>

                <h4 class="alert">
                    <fmt:message key="alert.document.primary"/>
                </h4>
                <form action="UploadHunterDocument" method="post" enctype="multipart/form-data">
                    <input type="file" name="document">
                    <input type="submit" class="btn btn-primary btn-sm"
                           value="<fmt:message key="button.upload.document"/>">
                </form>
            </div>
        </c:if>
    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
