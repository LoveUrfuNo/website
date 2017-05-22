<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Services</title>
</head>
<body>
<%--<c:choose>
    <c:when test="${category.equals('1category')}">
        <h1>1category</h1>
        <c:forEach var="service" items="${services}">
            <h3>${service.nameOfService} -> ${service.usernameOfSeller}</h3>
        </c:forEach>
    </c:when>
    <c:when test="${category.equals('2category')}">
        <h1>2category</h1>
        <c:forEach var="service" items="${services}">
            <h3>${service.nameOfService} -> ${service.usernameOfSeller}</h3>
        </c:forEach>
    </c:when>
</c:choose>--%>
<h1>${category}</h1>
<c:forEach var="service" items="${services}">
    <h3>
            ${service.nameOfService} -> ${service.user.firstName != null ? service.user.firstName : service.user.login}
    </h3>
</c:forEach>
<a href="${pageContext.request.contextPath}/redirect" class="col-sm-12 btn btn-primary">Назад</a>
</body>
</html>
