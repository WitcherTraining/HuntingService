<%@ page contentType="text/html;charset=UTF-8" %>
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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><fmt:message key="head.edit.password"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<form action="Forward" method="get">
    <input type="hidden" name="direction" value="main.jsp">
    <input type="submit" class="btn btn btn-dark btn-lg btn-block" value="<fmt:message key="button.main"/>" required>">
</form>

<div class="container text-center text-light">
    <div class="col-md-12">
        <form action="ChangePassword" method="post" class="align-middle">
            <h2><fmt:message key="head.edit.password"/> ${sessionScope.login}</h2>
            <p><input type="text" name="oldPassword" placeholder="<fmt:message key="label.old.password"/>"
                      required></p>
            <c:if test="${requestScope.oldPasswordIsIncorrect != null}">
                <small class="text-danger"><fmt:message key="error.password.equality"/></small>
            </c:if>
            <p><input type="password" name='password' aria-describedby="pass_help"
                      placeholder="<fmt:message key="label.password"/>" maxlength="8" required></p>
            <small id="pass_help" class="form-text text-muted"><fmt:message key="alert.password"/></small>
            <c:if test="${requestScope.notCorrectPassword != null}">
                <small class="text-danger"><fmt:message key="error.password"/></small>
            </c:if>
            <p><input type="submit" class="btn btn-primary" value="<fmt:message key="button.update"/>">
        </form>
    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
