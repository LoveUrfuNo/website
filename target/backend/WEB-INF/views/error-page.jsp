<%--
  Created by IntelliJ IDEA.
  User: Никита
  Date: 23.04.2017
  Time: 11:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Oops!</title>
    <!-- Bootstrap  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <!-- Awesome Icons -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
    <!-- Main styles-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/error-page.css">
</head>
<body>
<div class="row centering">
<h1 class="col-sm-12 text-center">404</h1>
<h2 class="col-sm-12 text-center"><i class="fa fa-exclamation-circle fa-lg" aria-hidden="true"></i>  Упс! Запрашиваемая страница не найдена!</h2>
    <h3 class="col-sm-12 text-center"> Вы можете <a href="${pageContext.request.contextPath}/redirect">вернуться</a> в любое время!</h3>
</div>
<!-- jQuery -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<!-- jQuery Easing -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.easing.1.3.js"></script>
<!-- Bootstrap -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
<!-- Main JS (Do not remove) -->
<script src="${pageContext.request.contextPath}/resources/js/error-page.js"></script>
</body>
</html>
