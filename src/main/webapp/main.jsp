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
    <title><fmt:message key="button.main"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<jsp:include page="header.jsp"/>
<body>

<br>
<h4 class="text-center text-light font-italic"><strong><fmt:message key="head.map"/></strong></h4>
<div class="mx-auto text-center">
    <a href="hgKarMin.jpg" download="img/hgKarMin.jpg">
        <img src="img/hgKarMin.jpg" class="rounded img-thumbnail" alt="..."/>
    </a>
</div>
<br>
</body>
<jsp:include page="footer.jsp"/>
</html>
