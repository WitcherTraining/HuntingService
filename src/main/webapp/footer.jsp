<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>

<br>
<br>
<br>
<footer>
    <div class="modal-footer bg-dark text-center text-light text-secondary fixed-bottom">
        <img src="img/binocular.png" alt="..." width="30" height="30"/>
        <div class="font-italic"><fmt:message key="head.footer"/></div>
    </div>
</footer>