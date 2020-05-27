<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>

<jsp:useBean id="year" class="com.epam.huntingService.util.DateConverter"/>
<jsp:useBean id="Const" class="com.epam.huntingService.util.ParameterNamesConstants"/>

<a href="main.jsp" class="btn btn-dark btn-lg btn-block active">
    <span class="badge"><img src="img/hunting.jpg" class="rounded-circle" alt="..." width="50" height="50"/></span>
    <fmt:message key="button.main"/>
</a>
<nav class="navbar sticky-top navbar-dark bg-dark">
    <form action="Search" name="search" class="form-inline">
        <label>
            <select class="custom-select-sm" name="searchOption">
                <option selected><fmt:message key="th.animal"/></option>
                <option><fmt:message key="th.organization"/></option>
                <option><fmt:message key="th.hunting.ground"/></option>
                <option><fmt:message key="th.district"/></option>
            </select>
        </label>
        <input type="search" class="form-control-sm" size="170" aria-label="search" name="searchSequence"
               placeholder="<fmt:message key="label.search"/>"/>
        <input type="hidden" name="direction" value="search_results.jsp">
        <input type="submit" class="btn btn-outline-primary btn-sm" value="<fmt:message key="button.search"/>"/>
    </form>


    <div class="mx-auto text-right" style="width: 95%;">
        <c:if test="${sessionScope.roleID eq null}">
        <ul class="list-inline">
            <li class="list-inline-item">
                <form id="login" action="Login" method="post">
                    <input type="text" name="login" placeholder="<fmt:message key="label.login"/>">
                    <input type="password" name='password' placeholder="<fmt:message key="label.password"/>"
                           required>
                    <input type="hidden" name="direction" value="main.jsp">
                    <button type="submit" form="login" class="btn btn-link text-light"><fmt:message key="button.login"/>
                        <span class="badge"><img src="img/boots.png" alt="..." width="30" height="30"/></span>
                    </button>
                </form>
            </li>
            <li class="list-inline-item">
                <form id="forwardReg" action="Forward" method="get">
                    <input type="hidden" name="direction" value="registration.jsp">
                    <button type="submit" form="forwardReg" class="btn btn-link text-light">
                        <fmt:message key="button.register"/>
                        <span class="badge"><img src="img/knife.png" alt="..." width="30" height="30"/></span>
                    </button>
                </form>
            </li>
            <li>
                <c:if test="${requestScope.errorLogin != null}">
                    <h6 class="text-danger"><fmt:message key="error.login"/></h6>
                </c:if>
            </li>
        </ul>
    </div>
    </c:if>

    <c:if test="${sessionScope.roleID ne null}">
        <div class="text-light text-right">
            <ul class="list-inline">
                <li class="list-inline-item">
                    <form id="cabinet" action="ShowOrderedPermits" method="get">
                        <button type="submit" form="cabinet" class="btn btn-primary">${sessionScope.role} : ${sessionScope.login}
                            <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
                                <span class="badge"><img src="img/horn.png" alt="..." width="30" height="30"/></span>
                            </c:if>
                            <c:if test="${sessionScope.roleID eq Const.hunterRoleId}">
                                <span class="badge"><img src="img/hunter.png" alt="..." width="30" height="30"/></span>
                            </c:if>
                            <c:if test="${sessionScope.roleID eq Const.userRoleId}">
                                <span class="badge"><img src="img/jacket.png" alt="..." width="30" height="30"/></span>
                            </c:if>
                        </button>
                    </form>
                </li>
                <li class="list-inline-item">
                    <form id="logout" action="LogOutService" method="get">
                        <button type="submit" form="logout" class="btn btn-primary"><fmt:message
                                key="button.logout"/>
                            <span class="badge"><img src="img/boots.png" alt="..." width="30" height="30"/></span>
                        </button>
                    </form>
                </li>
            </ul>
        </div>
    </c:if>
</nav>
<nav class="navbar sticky-top navbar-dark bg-dark">
    <div class="text-light">
        <ul class="list-inline">
            <li class="list-inline-item">
                <form id="limit" action="PrepareLimitInfo" method="get">
                    <input type="hidden" name="chosenYear" value="${year.getCurrentYear()}">
                    <button type="submit" form="limit" class="btn btn-link text-light"><fmt:message key="th.limit"/>
                        <span class="badge"><img src="img/deer.png" alt="..." width="50" height="50"/></span>
                    </button>
                </form>
            </li>
            <li class="list-inline-item">
                <form id="hg" action="ShowAllHuntingGrounds" method="get">
                    <button type="submit" form="hg" class="btn btn-link text-light"><fmt:message
                            key="button.hunting.grounds"/>
                        <span class="badge"><img src="img/dog.png" alt="..." width="50" height="50"/></span>
                    </button>
                </form>
            </li>
            <li class="list-inline-item">
                <form id="org" action="ShowAllOrganizations" method="get">
                    <button type="submit" form="org" class="btn btn-link text-light"><fmt:message
                            key="button.organizations"/>
                        <span class="badge"><img src="img/compass.png" alt="..." width="50" height="50"/></span>
                    </button>
                </form>
            </li>
            <li class="list-inline-item">
                <form id="animals" action="ShowAllAnimals" method="get">
                    <button type="submit" form="animals" class="btn btn-link text-light"><fmt:message
                            key="button.animals"/>
                        <span class="badge"><img src="img/duck.png" alt="..." width="50" height="50"/></span>
                    </button>
                </form>
            </li>
            <li class="list-inline-item">
                <form class="text-right" id="forwardCart" action="Forward" method="get">
                    <input type="hidden" name="direction" value="cart.jsp">
                    <button type="submit" form="forwardCart" class="btn btn-link text-light"><fmt:message
                            key="button.cart"/>
                        <span class="badge"><img src="img/backpack.png" alt="..." width="50" height="50"/>
                            <span class="badge badge-light">${sessionScope.cartWithItems}</span></span></button>
                </form>
            </li>
            <li class="list-inline-item">
                <form id="cabinetNavig" action="ShowOrderedPermits" method="get">
                    <button type="submit" form="cabinetNavig" class="btn btn-link text-light"><fmt:message
                            key="button.cabinet"/>
                        <c:if test="${sessionScope.roleID eq Const.adminRoleId}">
                            <span class="badge"><img src="img/horn.png" alt="..." width="50" height="50"/></span>
                        </c:if>
                        <c:if test="${sessionScope.roleID eq Const.hunterRoleId}">
                            <span class="badge"><img src="img/hunter.png" alt="..." width="50" height="50"/></span>
                        </c:if>
                        <c:if test="${sessionScope.roleID eq Const.userRoleId}">
                            <span class="badge"><img src="img/jacket.png" alt="..." width="50" height="50"/></span>
                        </c:if>
                        <c:if test="${sessionScope.roleID eq null}">
                            <span class="badge"><img src="img/brick1.png" alt="..." width="50" height="50"/></span>
                        </c:if>
                    </button>
                </form>
            </li>
        </ul>
    </div>
</nav>
<br>