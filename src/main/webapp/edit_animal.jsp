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
    <title><fmt:message key="head.edit.animal"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="col-md-12">

        <jsp:include page="admin_panel_part.jsp"/>

        <h2 class="text-center text-light"><fmt:message key="head.edit.animal"/></h2>

        <table class="table table-striped table-dark text-center">
            <tr>
                <th><fmt:message key="th.animal"/></th>
            </tr>
            <form action="EditAnimal">
                <tr>
                    <th><input type="hidden" name="langIDForAdding" value="${Const.russianId}">На русском</th>
                </tr>
                <tr>
                    <td class="text-danger">
                        <c:if test="${requestScope.emptyFields != null}">
                            <small class="text-danger"><fmt:message key="error.empty.fields"/></small>
                        </c:if>
                        <c:if test="${requestScope.animalIsExist != null}">
                            <small class="text-danger"><fmt:message key="error.animal.exist"/></small>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>
                        <c:forEach var="animal" items="${sessionScope.localizedAnimals}">
                            <c:if test="${animal.languageID eq Const.russianId}">
                                <input type="hidden" name="animalID" value="${animal.id}">
                                <input type="text" name="animalName" placeholder="Название животного"
                                       value="${animal.name}" required>
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <th><input type="hidden" name="langIDForAdding" value="${Const.englishId}">In English</th>
                </tr>
                <tr>
                    <td>
                        <c:forEach var="animal" items="${sessionScope.localizedAnimals}">
                            <c:if test="${animal.languageID eq Const.englishId}">
                                <input type="hidden" name="animalID" value="${animal.id}">
                                <input type="text" name="animalName" placeholder="Hunting spieces"
                                       value="${animal.name}" required>
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>
                <tr>
                    <td><input type="submit" class="btn btn-primary btn-sm btn-block"
                               value="<fmt:message key="button.update"/>"></td>
                </tr>
            </form>
        </table>

    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
