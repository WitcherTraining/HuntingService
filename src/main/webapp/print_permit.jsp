<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="language"/>
<jsp:useBean id="Const" class="com.epam.huntingService.util.constants.ParameterNamesConstants"/>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>

<body>
<br>
<div class="container-fluid">
    <div class="col-md-12">
        <p class="text-center">
        <h2><strong>ПУТЕВКА №${sessionScope.permit.id}</strong></h2>
        <br>
        <br>
        <p><strong>Тип путевки:</strong> ${sessionScope.permit.permitType}</p>
        <br>
        <p><strong>Стоимость путевки: </strong> <fmt:formatNumber value="${sessionScope.permit.totalCost}"
                                                                  pattern="###.##"/>
            <fmt:message key="label.money"/></p>
        <br>
        <p><strong>Фамилия и имя охотника: </strong> ${sessionScope.name} ${sessionScope.surname}</p>
        <br>

        <table class="table table-bordered">
            <tr>
                <th>Дата заказа путевки</th>
                <th>Наименование организации, предоставившей услуги охоты:</th>
                <th>Наименование охотничьего хозяйства, на котором производится охота:</th>
                <th>Вид животного</th>
                <th>Количество животных к охоте</th>
                <th>Начало сезона охоты</th>
                <th>Окончание сезона охоты</th>
                <th>Дата охоты (заполняется для суточной)</th>
                <th>Проверочный код</th>
            </tr>

            <tr>
                <td><fmt:formatDate value="${sessionScope.permit.orderDate}" pattern="d MMMM yyyy"/></td>
                <td>${sessionScope.permit.organization.name}</td>
                <td>${sessionScope.permit.huntingGround.name}</td>
                <td>${sessionScope.permit.animal.name}</td>
                <td>${sessionScope.permit.countOrderedAnimals}</td>
                <td>
                    <fmt:formatDate value="${sessionScope.permit.animal.animalLimitHistory.termBegin}"
                                    pattern="d MMMM yyyy"/>
                </td>
                <td>
                    <fmt:formatDate value="${sessionScope.permit.animal.animalLimitHistory.termEnd}"
                                    pattern="d MMMM yyyy"/>
                </td>
                <td><fmt:formatDate value="${sessionScope.permit.huntingDay}" pattern="d MMMM yyyy"/></td>
                <td><img src="data:image/png;base64,${sessionScope.qrBase64}" alt="..." height="100" width="100"/></td>
            </tr>
        </table>

        <table class="table table-bordered">
            <tr>
                <th>Фактически добыто</th>
                <th>Вид добытой дичи</th>
                <th>Дата добычи</th>
                <th>Количество</th>
                <th>Подпись егеря или охотника</th>
            </tr>
            <tr>
                <td height="50"></td>
                <td height="50"></td>
                <td height="50"></td>
                <td height="50"></td>
                <td height="50"></td>
            </tr>
        </table>

        <input type="button" class="d-print-none" value="<fmt:message key="button.print.permit"/>" onClick="print();"/>
    </div>
</div>
</body>
</html>
