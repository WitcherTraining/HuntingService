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
    <title><fmt:message key="button.search"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>

<body>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="col-md-12 text-light">
        <div class="row">
            <h2><fmt:message key="label.search"/> ${sessionScope.searchSequence}:</h2>
            <h4>
                <c:if test="${requestScope.foundNothing != null}">
                    <div class="text-danger"><fmt:message key="error.found.nothing"/></div>
                </c:if>
                <c:if test="${requestScope.emptySearch != null}">
                    <div class="text-danger"><fmt:message key="error.search"/></div>
                </c:if>
            </h4>
        </div>
        <c:if test="${sessionScope.huntingGroundID != null}">
            <p><strong><fmt:message key="th.hunting.ground"/></strong></p>
            <br>
            <form id="searching_hg" action="ShowHuntingGround" method="get">
                <input type="hidden" name="huntingGroundID" value="${sessionScope.huntingGroundID}">
                <button type="submit" form="searching_hg"
                        class="btn btn-link">${sessionScope.huntingGroundName}</button>
            </form>
        </c:if>
        <c:if test="${sessionScope.organizationID != null}">
            <p><strong><fmt:message key="th.organization"/></strong></p>
            <br>
            <form id="searching_org" action="ShowOrganization" method="get">
                <input type="hidden" name="organizationID" value="${sessionScope.organizationID}">
                <button type="submit" form="searching_org" class="btn btn-link">${sessionScope.orgName}</button>
            </form>
        </c:if>
        <c:if test="${sessionScope.huntingGroundsWithThisAnimal != null}">
            <div class="row">
                <p><strong><fmt:message key="label.search.hunting.grounds.by.animal"/></strong></p>
                <div class="row">
                    <c:forEach var="huntingGround" items="${sessionScope.huntingGroundsWithThisAnimal}">
                        <div class="col-md-6">
                            <form action="ShowHuntingGround" method="get">
                                <input type="hidden" name="huntingGroundID" value="${huntingGround.id}">
                                <input type="submit" class="btn btn-link" value="${huntingGround.name}">
                            </form>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        <c:if test="${sessionScope.huntingGroundsByDistrict != null}">
            <div class="row">
                <p><strong><fmt:message key="label.search.hunting.grounds.by.district"/></strong></p>
                <br>
                <c:forEach var="huntingGround" items="${sessionScope.huntingGroundsByDistrict}">
                    <div class="col-md-6">
                        <form action="ShowHuntingGround" method="get">
                            <input type="hidden" name="huntingGroundID" value="${huntingGround.id}">
                            <input type="submit" class="btn btn-link" value="${huntingGround.name}">
                        </form>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
