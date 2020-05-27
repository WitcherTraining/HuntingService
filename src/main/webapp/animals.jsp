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
    <title><fmt:message key="th.animal"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:useBean id="Const" class="com.epam.huntingService.util.ParameterNamesConstants"/>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="col-md-12">

        <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
            <jsp:include page="admin_panel_part.jsp"/>
        </c:if>

        <h2 class="text-center text-light"><fmt:message key="head.spieces"/></h2>

        <table class="table table-striped table-dark text-center">
            <tr>
                <th><fmt:message key="th.animal.id"/></th>
                <th><fmt:message key="th.animal"/></th>
            </tr>

            <c:forEach var="animal" items="${sessionScope.animals}">
                <tr>
                    <td>${animal.id}</td>
                    <td>
                        <form action="Search" name="search" class="text-center">
                            <input type="hidden" name="searchSequence" value="${animal.name}"/>
                            <input type="hidden" name="direction" value="search_results.jsp">
                            <input type="submit" class="btn btn-sm btn-link" value="${animal.name}"/>
                        </form>
                    </td>
                    <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
                        <td>
                            <form action="PrepareAnimalEditing">
                                <input type="hidden" name="animalID" value="${animal.id}">
                                <input type="submit" class="btn btn-primary btn-sm btn-block"
                                       value="<fmt:message key="button.edit"/>">
                            </form>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>

            <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
                <form action="AddAnimal">
                    <tr>
                        <td colspan="3" class="text-danger">
                            <c:if test="${requestScope.animalIsExist != null}">
                                <small class="text-danger"><fmt:message key="error.animal.exist"/></small>
                            </c:if>
                            <c:if test="${requestScope.emptyFields != null}">
                                <small class="text-danger"><fmt:message key="error.empty.fields"/></small>
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td><input type="hidden" name="langIDForAdding" value="${Const.russianId}">На русском</td>
                        <td colspan="2" width="60"><input type="text" name="animalName"
                                                          placeholder="Название охотничьего вида" required></td>
                    </tr>
                    <tr>
                        <td><input type="hidden" name="langIDForAdding" value="${Const.englishId}">In English</td>
                        <td colspan="2" width="60"><input type="text" name="animalName" placeholder="Hunting spieces"
                                                          required></td>
                    </tr>
                    <tr>
                        <td colspan="3"><input type="submit" class="btn btn-primary btn-sm btn-block"
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
