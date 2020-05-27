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
    <title><fmt:message key="button.register"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<form action="Forward" method="get">
    <input type="hidden" name="direction" value="main.jsp">
    <input type="submit" class="btn btn btn-dark btn-lg btn-block" value="Главная страница">
</form>

<%--<div class="container text-center text-light">--%>
<%--<div class="col-md-12">--%>
<%--<form action="RegisterUser" method="post" class="align-middle">--%>
<%--<h2><fmt:message key="button.register"/></h2>--%>
<%--<p><input type="text" name="name" placeholder="<fmt:message key="label.name"/>" maxlength="10" required></p>--%>
<%--<p><input type="text" name="surname" placeholder="<fmt:message key="label.surname"/>" maxlength="10"--%>
<%--required></p>--%>
<%--<p><input type="text" name="login" placeholder="<fmt:message key="label.login"/>" maxlength="10"--%>
<%--required></p>--%>
<%--<c:if test="${requestScope.loginIsExist != null}">--%>
<%--<small class="text-danger"><fmt:message key="error.login.exists"/></small>--%>
<%--</c:if>--%>
<%--<p><input type="text" name="email" placeholder="<fmt:message key="label.email"/>" required>--%>
<%--<c:if test="${requestScope.notCorrectEmail != null}">--%>
<%--<small class="text-danger"><fmt:message key="error.email"/></small>--%>
<%--</c:if>--%>
<%--<p><input type="text" name="phone" placeholder="<fmt:message key="label.phone"/>" maxlength="15" required>--%>
<%--<c:if test="${requestScope.notCorrectPhone != null}">--%>
<%--<small class="text-danger"><fmt:message key="error.phone"/></small>--%>
<%--</c:if>--%>
<%--<p><input type="password" name='password' aria-describedby="pass_help_reg"--%>
<%--placeholder="<fmt:message key="label.password"/>" maxlength="8" required>--%>
<%--<small id="pass_help-reg" class="form-text text-muted"><fmt:message key="alert.password"/></small>--%>
<%--<c:if test="${requestScope.notCorrectPassword != null}">--%>
<%--<small class="text-danger"><fmt:message key="error.password"/></small>--%>
<%--</c:if>--%>
<%--<p><input type="submit" name="submit" class="btn btn-primary" value="<fmt:message key="button.register"/>">--%>
<%--</form>--%>
<%--</div>--%>
<%--</div>--%>


<div class="container text-light">
    <div class="col-md-12">
        <br>
        <h2 class="text-center"><fmt:message key="head.register"/></h2>
        <br>
        <br>
        <form action="RegisterUser" method="post" id="register_form">
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <label for="validationName"><fmt:message key="label.name"/></label>
                    <input name="name" type="text" class="form-control" id="validationName"
                           placeholder="<fmt:message key="label.name"/>" maxlength="10" required>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="validationSurname"><fmt:message key="label.surname"/></label>
                    <input name="surname" type="text" class="form-control" id="validationSurname"
                           placeholder="<fmt:message key="label.surname"/>" maxlength="10" required>
                </div>
                <div class="col-md-4 mb-3">
                    <label for="validationLogin"><fmt:message key="label.login"/></label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="inputGroupPrepend2">Username</span>
                        </div>
                        <input name="login" type="text" class="form-control" id="validationLogin"
                               placeholder="<fmt:message key="label.login"/>" maxlength="10"
                               aria-describedby="inputGroupPrepend2" required>
                    </div>
                    <c:if test="${requestScope.loginIsExist != null}">
                        <small class="text-danger"><fmt:message key="error.login.exists"/></small>
                    </c:if>
                </div>
            </div>
            <div class="form-row">
                <div class="col-md-6 mb-3">
                    <label for="validationEmail"><fmt:message key="label.email"/></label>
                    <input name="email" type="text" class="form-control" id="validationEmail"
                           placeholder="<fmt:message key="label.email"/>" required>
                    <c:if test="${requestScope.notCorrectEmail != null}">
                        <small class="text-danger"><fmt:message key="error.email"/></small>
                    </c:if>
                </div>
                <div class="col-md-3 mb-3">
                    <label for="validationPhone"><fmt:message key="label.phone"/></label>
                    <input name="phone" type="text" class="form-control" id="validationPhone"
                           placeholder="<fmt:message key="label.phone"/>" maxlength="15" required>
                    <c:if test="${requestScope.notCorrectPhone != null}">
                        <small class="text-danger"><fmt:message key="error.phone"/></small>
                    </c:if>
                </div>
                <div class="col-md-3 mb-3">
                    <label for="validationPassword"><fmt:message key="label.password"/></label>
                    <input name="password" type="password" class="form-control" id="validationPassword"
                           placeholder="<fmt:message key="label.password"/>" maxlength="8" required>
                    <c:if test="${requestScope.notCorrectPassword != null}">
                        <small class="text-danger"><fmt:message key="error.password"/></small>
                    </c:if>
                </div>
                <small id="pass_help-reg" class="form-text text-danger text-right"><fmt:message key="alert.password"/></small>
            </div>
            <button name="submit" class="btn btn-primary text-right" type="submit" form="register_form"><fmt:message
                    key="button.register"/></button>
        </form>
    </div>
</div>

</body>
<jsp:include page="footer.jsp"/>
</html>
