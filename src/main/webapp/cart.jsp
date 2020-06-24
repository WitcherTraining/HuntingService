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
    <title><fmt:message key="head.cart"/></title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<jsp:useBean id="Const" class="com.epam.huntingService.util.constants.ParameterNamesConstants"/>
<body>
<jsp:include page="header.jsp"/>
<div class="container text-center text-light">
    <div class="col-md-12">

        <h2 class="text-center text-light"><fmt:message key="head.cart"/></h2>
        <br>
        <h3 class="text-center text-light"><fmt:message key="head.daily.permits"/></h3>

        <table class="table table-striped table-dark text-center">
            <tr>
                <th><fmt:message key="th.organization"/></th>
                <th><fmt:message key="th.hunting.ground"/></th>
                <th><fmt:message key="th.animal"/></th>
                <th><fmt:message key="th.permit.type"/></th>
                <th><fmt:message key="th.term.begin"/></th>
                <th><fmt:message key="th.term.end"/></th>
                <th><fmt:message key="th.daily.order.instruction"/></th>
                <th><fmt:message key="th.remove.from.cart"/></th>
            </tr>
            <tr>
                <th colspan="8" class="text-danger">
                    <c:if test="${requestScope.emptyFields != null}">
                        <small class="text-danger"><fmt:message key="error.empty.fields"/></small>
                    </c:if>
                    <c:if test="${requestScope.wrongHuntingDate != null}">
                        <small class="text-danger"><fmt:message key="error.hunting.date"/></small>
                    </c:if>
                    <c:if test="${requestScope.wrongOrderedAnimalsCount != null}">
                        <small class="text-danger"><fmt:message key="error.ordered.animals"/></small>
                    </c:if>
                </th>
            </tr>

            <c:forEach var="cartItems" items="${sessionScope.cartItems}">
                <c:if test="${cartItems.permitType eq 'Суточная'}">
                    <tr>
                        <th>${cartItems.organizationName}</th>
                        <td>${cartItems.huntingGroundName}</td>
                        <td>${cartItems.animalName}</td>
                        <td>${cartItems.permitType}</td>
                        <td><fmt:formatDate value="${cartItems.animalTermBeginUDate}" pattern="d MMMM yyyy"/></td>
                        <td><fmt:formatDate value="${cartItems.animalTermEndUDate}" pattern="d MMMM yyyy"/></td>
                        <td>
                            <c:if test="${sessionScope.roleID eq Const.hunterRoleId}">
                                <form action="OrderDailyPermit" method="get">
                                    <label>
                                        <input type="number" min="1" max="5" value="1" name="countAnimalsForHunt"
                                               class="text-input">
                                    </label>
                                    <label>
                                        <input type="date" name="huntingDay" required value="${sessionScope.today}"
                                               max="${cartItems.animalTermEndUDate}" min="${sessionScope.today}" class="calendar-grid">
                                    </label>
                                    <input type="hidden" name="ItemID" value="${cartItems.ID}">
                                    <input type="submit" class="btn btn-primary btn-sm"
                                           value="<fmt:message key="button.order"/>">
                                </form>
                            </c:if>
                            <c:if test="${sessionScope.roleID eq Const.userRoleId}">
                                <form action="Forward" method="get">
                                    <input type="hidden" name="direction" value="cabinet.jsp">
                                    <input type="submit" class="btn btn-primary btn-sm"
                                           value="<fmt:message key="button.cabinet"/>">
                                </form>
                            </c:if>
                            <c:if test="${sessionScope.roleID eq null}">
                                <form action="Forward" method="get">
                                    <input type="hidden" name="direction" value="registration.jsp">
                                    <input type="submit" class="btn btn-primary btn-sm"
                                           value="<fmt:message key="button.register"/>">
                                </form>
                            </c:if>
                        </td>
                        <td>
                            <form action="RemoveFromCart" method="get">
                                <input type="hidden" name="ItemID" value="${cartItems.ID}">
                                <input type="submit" class="btn btn-primary btn-sm" value="<fmt:message key="button.remove"/>">
                            </form>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>

        <h3><fmt:message key="head.season.permits"/></h3>

        <table class="table table-striped table-dark text-center">
            <tr>
                <th><fmt:message key="th.organization"/></th>
                <th><fmt:message key="th.hunting.ground"/></th>
                <th><fmt:message key="th.animal"/></th>
                <th><fmt:message key="th.permit.type"/></th>
                <th><fmt:message key="th.term.begin"/></th>
                <th><fmt:message key="th.term.end"/></th>
                <th><fmt:message key="th.season.order.instruction"/></th>
                <th><fmt:message key="th.remove.from.cart"/></th>
            </tr>
            <tr>
                <th colspan="8" class="text-danger">
                    <c:if test="${requestScope.emptyFields != null}">
                        <small class="text-danger"><fmt:message key="error.empty.fields"/></small>
                    </c:if>
                    <c:if test="${requestScope.wrongHuntingDate != null}">
                        <small class="text-danger"><fmt:message key="error.hunting.date"/></small>
                    </c:if>
                    <c:if test="${requestScope.wrongOrderedAnimalsCount != null}">
                        <small class="text-danger"><fmt:message key="error.ordered.animals"/></small>
                    </c:if>
                </th>
            </tr>

            <c:forEach var="cartItem" items="${sessionScope.cartItems}">
                <c:if test="${cartItem.permitType eq 'Сезонная'}">
                    <tr>
                        <th>${cartItem.organizationName}</th>
                        <td>${cartItem.huntingGroundName}</td>
                        <td>${cartItem.animalName}</td>
                        <td>${cartItem.permitType}</td>
                        <td><fmt:formatDate value="${cartItem.animalTermBeginUDate}" pattern="d MMMM yyyy"/></td>
                        <td><fmt:formatDate value="${cartItem.animalTermEndUDate}" pattern="d MMMM yyyy"/></td>
                        <td>
                            <c:if test="${sessionScope.roleID eq Const.hunterRoleId}">
                                <form action="OrderSeasonPermit" method="get">
                                    <input type="hidden" name="ItemID" value="${cartItem.ID}">
                                    <input type="hidden" name="huntingGroundQuota" value="${cartItem.animalQuota}">
                                    <label>
                                        <input type="number" min="1" max="${cartItem.animalQuota}" value="1"
                                               name="countAnimalsForHunt" required class="text-input">
                                    </label>
                                    <input type="submit" class="btn btn-primary btn-sm"
                                           value="<fmt:message key="button.order"/>">
                                </form>
                            </c:if>
                            <c:if test="${sessionScope.roleID eq Const.userRoleId}">
                                <form action="Forward" method="get">
                                    <input type="hidden" name="direction" value="cabinet.jsp">
                                    <input type="submit" class="btn btn-primary btn-sm"
                                           value="<fmt:message key="button.cabinet"/>">
                                </form>
                            </c:if>
                            <c:if test="${sessionScope.roleID eq null}">
                                <form action="Forward" method="get">
                                    <input type="hidden" name="direction" value="registration.jsp">
                                    <input type="submit" class="btn btn-primary btn-sm"
                                           value="<fmt:message key="button.register"/>">
                                </form>
                            </c:if>
                        </td>
                        <td>
                            <form action="RemoveFromCart" method="get">
                                <input type="hidden" name="ItemID" value="${cartItem.ID}">
                                <input type="submit" class="btn btn-primary btn-sm" value="<fmt:message key="button.remove"/>">
                            </form>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>

        <h6>
            *<fmt:message key="alert.order"/>
            <br>
            <a href="http://adilet.zan.kz/rus/docs/V1500011091"><fmt:message key="url.hunting.rules"/></a>
            <br>
            <a href="http://adilet.zan.kz/rus/docs/K1700000120#z582"><fmt:message key="url.fee.rates"/></a>
        </h6>
    </div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>
