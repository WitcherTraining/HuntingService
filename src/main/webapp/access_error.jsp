<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>

<html>
<style>
    body {
        background-size: cover;
        background: url(img/darkTree.jpg) no-repeat fixed center;
    }
</style>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="head.access.error"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <div class="text-danger">
        <h1><fmt:message key="head.access.not.possible"/></h1>
        <form action="Forward" method="get">
            <input type="hidden" name="direction" value="main.jsp">
            <input type="submit" class="btn btn-link" value="<fmt:message key="button.to.main"/>">
        </form>
    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>