<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Результаты</title>
</head>
<body>
<c:if test="${did_you_meant_it != null}">
    <span>Возможно, вы имели в виду: </span>
    <b>
        <i>
            <a href="#">${did_you_meant_it}</a>
        </i>
    </b>
    <br>
</c:if>
<c:if test="${results_of_the_request_are_shown != null}">
    <span>Показаны результаты по запросу </span>
    <a href="#">
        <b>
            <i>${results_of_the_request_are_shown}</i>
        </b>
    </a>
    <br>
    <span>Искать вместо этого </span>
    <a href="#">${search_instead_this}</a>
</c:if>
<c:forEach var="service" items="${search_results}">
    <h2>${service.user.firstName != null ? service.user.firstName : service.user.login}
        -> ${service.nameOfService} всего за ${service.serviceCost}${service.currency}</h2>
</c:forEach>
<a href="${pageContext.request.contextPath}/redirect" class="col-sm-12 btn btn-primary">Назад</a>
</body>
</html>
