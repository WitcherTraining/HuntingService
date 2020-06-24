<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.language}" />
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
    <title><fmt:message key="head.users"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:useBean id="Const" class="com.epam.huntingService.util.constants.ParameterNamesConstants"/>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="col-md-12">

            <jsp:include page="admin_panel_part.jsp"/>

            <h2 class="text-center text-light"><fmt:message key="head.admins"/></h2>

            <table class="table table-striped table-dark text-center">
                <tr>
                    <th><fmt:message key="label.name"/></th>
                    <th><fmt:message key="label.surname"/></th>
                    <th><fmt:message key="label.login"/></th>
                    <th><fmt:message key="label.email"/></th>
                    <th><fmt:message key="label.phone"/></th>
                    <th><fmt:message key="th.document"/></th>
                </tr>
                <c:forEach var="user" items="${sessionScope.users}">
                    <c:if test="${user.roleID eq Const.adminRoleId}">
                        <tr>
                            <td>${user.name}</td>
                            <td>${user.surname}</td>
                            <td>${user.login}</td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>
                            <td>
                                <form action="DownloadHunterDocument">
                                    <input type="hidden" name="userID" value="${user.id}">
                                    <input type="submit" value="<fmt:message key="button.download.document"/>">
                                </form>
                            </td>
                            <td>
                                <form action="ChangeRole" method="get">
                                    <label>
                                        <select name="roleOption">
                                            <option><fmt:message key="head.hunters"/></option>
                                            <option><fmt:message key="head.admins"/></option>
                                            <option><fmt:message key="head.users"/></option>
                                        </select>
                                    </label>
                                    <input type="hidden" name="userID" value="${user.id}">
                                    <input type="submit" class="btn btn-primary btn-sm" value="<fmt:message key="button.change.role"/>">
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>

            <h5 class="text-center text-light"><fmt:message key="head.hunters"/></h5>

            <table class="table table-striped table-dark text-center">
                <tr>
                    <th><fmt:message key="label.name"/></th>
                    <th><fmt:message key="label.surname"/></th>
                    <th><fmt:message key="label.login"/></th>
                    <th><fmt:message key="label.email"/></th>
                    <th><fmt:message key="label.phone"/></th>
                    <th><fmt:message key="th.document"/></th>
                </tr>

                <c:forEach var="user" items="${sessionScope.users}">
                    <c:if test="${user.roleID eq Const.hunterRoleId}">
                        <tr>
                            <td>${user.name}</td>
                            <td>${user.surname}</td>
                            <td>${user.login}</td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>
                            <td>
                                <form action="DownloadHunterDocument">
                                    <input type="hidden" name="userID" value="${user.id}">
                                    <input type="submit" value="<fmt:message key="button.download.document"/>">
                                </form>
                            </td>
                            <td>
                                <form action="ChangeRole" method="get">
                                    <label>
                                        <select name="roleOption">
                                            <option><fmt:message key="head.hunters"/></option>
                                            <option><fmt:message key="head.admins"/></option>
                                            <option><fmt:message key="head.users"/></option>
                                        </select>
                                    </label>
                                    <input type="hidden" name="userID" value="${user.id}">
                                    <input type="submit" class="btn btn-primary btn-sm" value="<fmt:message key="button.change.role"/>">
                                </form>
                            </td>
                            <td>
                                <form action="DeleteUser" method="get">
                                    <input type="hidden" name="userID" value="${user.id}">
                                    <input type="submit" class="btn btn-primary btn-danger" value="<fmt:message key="button.delete.user"/>">
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </table>

            <h5 class="text-center text-light"><fmt:message key="head.unverified.hunters"/></h5>

            <table class="table table-striped table-dark text-center">
                <tr>
                    <th><fmt:message key="label.name"/></th>
                    <th><fmt:message key="label.surname"/></th>
                    <th><fmt:message key="label.login"/></th>
                    <th><fmt:message key="label.email"/></th>
                    <th><fmt:message key="label.phone"/></th>
                    <th><fmt:message key="th.document"/></th>
                </tr>

                <c:forEach var="user" items="${sessionScope.users}">
                    <c:if test="${user.roleID eq Const.userRoleId}">
                        <tr>
                            <td>${user.name}</td>
                            <td>${user.surname}</td>
                            <td>${user.login}</td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>
                            <td>
                                <form action="DownloadHunterDocument">
                                    <input type="hidden" name="userID" value="${user.id}">
                                    <input type="submit" value="<fmt:message key="button.download.document"/>">
                                </form>
                            </td>
                            <td>
                                <form action="ChangeRole" method="get">
                                    <label>
                                        <select name="roleOption">
                                            <option><fmt:message key="head.hunters"/></option>
                                            <option><fmt:message key="head.admins"/></option>
                                            <option><fmt:message key="head.users"/></option>
                                        </select>
                                    </label>
                                    <input type="hidden" name="userID" value="${user.id}">
                                    <input type="submit" class="btn btn-primary btn-sm" value="<fmt:message key="button.change.role"/>">
                                </form>
                            </td>
                            <td>
                                <form action="DeleteUser" method="get">
                                    <input type="hidden" name="userID" value="${user.id}">
                                    <input type="submit" class="btn btn-primary btn-danger" value="<fmt:message key="button.delete.user"/>">
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>

            </table>
        </div>
    </div>
</body>
<jsp:include page="footer.jsp"/>
</html>
