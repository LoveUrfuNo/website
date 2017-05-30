<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Services</title>
</head>
<body>
<h1>${category}</h1>
<c:forEach var="service" items="${services}">
    <h3>
            ${service.serviceName} -> ${service.user.firstName != null ? service.user.firstName : service.user.login}
    </h3>
</c:forEach>
<a href="${pageContext.request.contextPath}/redirect" class="col-sm-12 btn btn-primary">Назад</a>
</body>
</html>
