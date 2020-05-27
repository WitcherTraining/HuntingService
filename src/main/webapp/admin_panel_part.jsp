<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<jsp:useBean id="year" class="com.epam.huntingService.util.DateConverter"/>

<br>
<div class="row">
    <div class="col-md-6">
        <form action="ShowAllUsersByCategories" id="user-name-label" method="get">
            <button type="submit" form="user-name-label" class="btn btn-primary btn-lg btn-block"><fmt:message
                    key="button.users.category"/>
                <span class="badge"><img src="img/hunter.png" alt="..." width="50" height="50"/></span>
            </button>
        </form>
    </div>
    <div class="col-md-6">
        <form action="ShowAllPermits" id="permits" method="get">
            <button type="submit" form="permits" class="btn btn-primary btn-lg btn-block">
                <fmt:message key="button.permits"/>
                <span class="badge"><img src="img/backpack.png" alt="..." width="50" height="50"/></span>
            </button>
        </form>
    </div>
</div>
<div class="row">
    <div class="col-md-6">
        <form action="ShowAnimalsLimit" id="limit_history" method="get">
            <label>
                <input type="number" name="chosenYear" required value="${year.getCurrentYear()}" min="${sessionScope.firstYearLimit}"
                       max="${year.getCurrentYear()}">
            </label>
            <button type="submit" form="limit_history" class="btn btn-primary btn-lg btn-block"><fmt:message
                    key="button.limit"/>
                <span class="badge"><img src="img/deer.png" alt="..." width="50" height="50"/></span>
            </button>
        </form>
        <c:if test="${requestScope.wrongYear != null}">
            <small class="text-danger"><fmt:message key="error.year"/></small>
        </c:if>
    </div>
</div>
<br>