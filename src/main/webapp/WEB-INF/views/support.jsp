<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Deal | Support</title>
    <!-- Facebook and Twitter integration -->
    <meta property="og:title" content=""/>
    <meta property="og:image" content=""/>
    <meta property="og:url" content=""/>
    <meta property="og:site_name" content=""/>
    <meta property="og:description" content=""/>
    <meta name="twitter:title" content=""/>
    <meta name="twitter:image" content=""/>
    <meta name="twitter:url" content=""/>
    <meta name="twitter:card" content=""/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/small_logo.icon"
          type="image/x-icon">
    <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/favicon.ico">
    <!-- Bootstrap  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <!-- Animate.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/animate.css">
    <!-- Icomoon Icon Fonts-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/icomoon.css">
    <!-- Semantic UI -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Semantic-UI-CSS-master/semantic.min.css">
    <!-- Awesome Icons -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
    <!-- Main sheet-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <!-- Main styles-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/support.css">
    <!--[if lt IE 9]>
    <script src="/resources/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div id="full">
    <div id="page">
        <nav class="gtco-nav" role="navigation">
            <div class="gtco-container">
                <div class="row">
                    <div class="col-sm-4 col-xs-12">
                        <div id="gtco-logo"><a href="${pageContext.request.contextPath}/redirect">Deal <em></em></a>
                        </div>
                    </div>
                    <div class="col-xs-8 text-right menu-1">
                        <ul>
                            <li><a href="#">Профиль</a></li>
                            <li><a href="#">Найти услугу</a></li>
                            <li><a href="#">Предложить услугу</a></li>
                            <li><a href="#">Поддержка</a></li>        <%--make support button cloudy--%>
                            <li class="btn-cta"><a href="#"><span>Начать</span></a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </nav>
        <header>
            <div class="head">
                <div class="row">
                    <div class="col-xs-12 text-center vertical-centering-support">
                        <h1 class="col-xs-12">Остались вопросы?</h1>
                        <h1 class="col-xs-12">Мы поможем!</h1>
                    </div>
                </div>
            </div>
        </header>
        <div class="row vertical-centering">
            <div class="col-xs-11 col-xs-push-1 col-sm-5 col-sm-push-1">
                <p class="col-xs-12 text-center">Напишите нам</p>
                <div class="form-group">
                    <form:form method="POST" modelAttribute="userFormForTechnicalSupport"
                               action="/support">
                        <spring:bind path="name">
                            <div class="row form-group ">
                                <div class="col-md-12 ${status.error ? 'has-error' : ''}">
                                    <label>Ваше Имя</label>
                                    <form:input minlenght="2" maxlength="60" path="name" name="name" type="text"
                                                class="form-control"
                                                placeholder="Иван"
                                                autofocus="true"/>
                                    <form:errors path="name"/>
                                </div>
                            </div>
                        </spring:bind>
                        <spring:bind path="email">
                            <div class="row form-group">
                                <div class="col-md-12 ${status.error ? 'has-error' : ''}">
                                    <label for="email">E-mail</label>
                                    <form:input maxlength="100" path="email" name="E-mail" type="email"
                                                class="form-control"
                                                placeholder="ivan.ivanov@gmail.com"
                                                autofocus="true"/>
                                    <form:errors path="email"/>
                                </div>
                            </div>
                        </spring:bind>
                        <spring:bind path="subject">
                            <div class="row form-group">
                                <div class="col-md-12 ${status.error ? 'has-error' : ''}">
                                    <label>Тема</label>
                                    <form:input maxlength="100" path="subject" name="theme" type="text"
                                                class="form-control"
                                                placeholder="Проблема с регистрацией"
                                                autofocus="true"/>
                                    <form:errors path="subject"/>
                                </div>
                            </div>
                        </spring:bind>
                        <spring:bind path="description">
                            <div class="row form-group">
                                <div class="col-md-12 ${status.error ? 'has-error' : ''}">
                                    <label>Описание</label>
                                    <form:textarea path="description" maxlength="3000" rows="3"/>
                                    <form:errors path="description"/>
                                </div>
                            </div>
                        </spring:bind>
                        <div class="row form-group">
                            <div class="col-md-12">
                                <input type="submit" class="btn btn-primary"
                                       value="Отправить">
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
            <div class="xs-margin-top col-xs-12 col-sm-5 col-sm-push-1">
                <p class="col-xs-10 col-xs-1 text-center">Наши контакты</p>
                <div class="contact col-xs-10 col-xs-push-2"><span><i class="fa fa-map-marker"
                                                                      aria-hidden="true"></i><span>Улица Пушкина, дом -12</span></span>
                </div>
                <div class="contact col-xs-10 col-xs-push-2"><i class="fa fa-phone" aria-hidden="true"></i><a href="#">+7-999-999-99-99-999</a>
                </div>
                <div class="contact col-xs-10 col-xs-push-2"><i class="fa fa-envelope" aria-hidden="true"></i><a
                        href="#">deal.agentservice@gmail.com</a>
                </div>
            </div>
        </div>
        <footer>
            <div id="footer" class="fh5co-border-line">
                <div class="container">
                    <div class="row">
                        <div class="col-md-8 col-md-offset-2 text-center">
                            <p>Использование сайта, в том числе подача объявлений, означает согласие с пользовательским
                                соглашением.
                                Оплачивая услуги на сайте, вы принимаете оферту <a href="#">Deal</a>. Все права
                                защищены. <br>Сделано
                                с <i class="icon-heart3 love"></i> by <a href="#" target="_blank">Deal</a><br> Создатели
                                <a
                                        href="https://vk.com/id35713161" target="_blank">Никита</a> &amp; <a
                                        href="https://vk.com/yurok_e" target="_blank">Юрий</a> &amp; <a
                                        href="https://vk.com/id153656293" target="_blank">Максим</a> &amp; <a
                                        href="https://vk.com/id352078925" target="_blank">Андрей</a></p>
                            <p class="fh5co-social-icons">
                                <a href="#"><i class="icon-twitter-with-circle"></i></a>
                                <a href="#"><i class="icon-facebook-with-circle"></i></a>
                                <a href="#"><i class="icon-instagram-with-circle"></i></a>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </footer>
    </div>
</div>
<!-- jQuery -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.min.js"></script>
<!-- jQuery Easing -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.easing.1.3.js"></script>
<!-- Bootstrap -->
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

<!--Semantic UI-->
<script src="${pageContext.request.contextPath}/resources/Semantic-UI-CSS-master/semantic.min.js"></script>

<!-- Waypoints -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.waypoints.min.js"></script>

<!-- Owl carousel -->
<script src="${pageContext.request.contextPath}/resources/js/owl.carousel.min.js"></script>

<!-- Stellar -->
<script src="${pageContext.request.contextPath}/resources/js/jquery.stellar.min.js"></script>

<!-- Main JS (Do not remove) -->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html>
