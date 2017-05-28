<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="java.io.OutputStream" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]> <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]> <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"> <!--<![endif]-->
<head>
    <title>Deal</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
    <!-- Animate.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/animate.css">
    <!-- Icomoon Icon Fonts-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/icomoon.css">
    <!-- Bootstrap  -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <!-- Owl Carousel -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/owl.theme.default.min.css">
    <!-- Jquery UI -->
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/resources/jquery-ui-1.12.1.custom/jquery-ui.min.cssF">
    <!-- Semantic UI -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Semantic-UI-CSS-master/semantic.min.css">
    <!-- Main styles-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
    <!-- Awesome Icons -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/font-awesome.min.css">
    <!-- Modernizr JS -->
    <script src="${pageContext.request.contextPath}/resources/js/modernizr.min.js"></script>
    <!-- FOR IE9 below -->
    <!--[if lt IE 9]>
    <script src="/resources/js/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div id="full">
    <div id="page">
        <span class="backgroundSlider"></span>
        <div class="page-inner">
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
                                <li><a href="${pageContext.request.contextPath}/add_service">Предложить услугу</a></li>
                                <li><a href="${pageContext.request.contextPath}/support">Поддержка</a></li>
                                <li class="btn-cta"><a href="#"><span>Начать</span></a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </nav>
            <header id="gtco-header" class="gtco-cover" role="banner" style="">
                <div class="overlay"></div>
                <div class="gtco-container">
                    <div class="row">
                        <div class="col-md-12 col-md-offset-0 text-left">
                            <div class="row row-mt-15em">
                                <div class="col-md-7 mt-text animate-box" data-animate-effect="fadeInUp">
                                    <div class="ui action input">
                                        <form:form name="search" method="POST" modelAttribute="searchRequest"
                                                   action="${pageContext.request.contextPath}/search_services">
                                            <spring:bind path="searchLine">
                                                <div class="form-group ${status.error ? 'has-error' : ''}">
                                                    <form:input id="tags" path="searchLine"
                                                                type="text"
                                                                placeholder="Поиск услуг"/>
                                                    <form:errors path="searchLine"/>
                                                </div>
                                            </spring:bind>
                                            <div id="asd0">

                                            </div>
                                            <div id="asd1">

                                            </div>
                                            <div id="asd2">

                                            </div>
                                            <div id="asd3">

                                            </div>
                                            <div id="asd4">

                                            </div>
                                            <input type="submit" class="ui green button" value="Найти">
                                        </form:form>
                                    </div>
                                    <span class="intro-text-small">Добро пожаловать на "НАЗВАНИЕ"</span>
                                    <h1>Покупай и продавай вместе с нами</h1>
                                </div>
                                <div class="col-md-5 animate-box" data-animate-effect="fadeInRight">
                                    <!-- До регистрации -->
                                    <div class="form-wrap">
                                        <div class="tab">
                                            <ul class="tab-menu">
                                                <li class="active gtco-first"><a data-tab="signup">Регистрация</a>
                                                </li>
                                                <li class="gtco-second"><a href="#" data-tab="login">Вход</a></li>
                                            </ul>
                                            <div class="tab-content">
                                                <div class="tab-content-inner active" data-content="signup">
                                                    <form:form method="POST" modelAttribute="userForm"
                                                               action="/registration"
                                                               class="form-signin">
                                                        <div class="row form-group">
                                                            <div class="col-md-12">
                                                                <spring:bind path="username">
                                                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                                                        <label for="username">E-mail</label>
                                                                        <form:input type="email" path="username"
                                                                                    class="form-control"
                                                                                    placeholder="ivanov@email.com"/>
                                                                        <form:errors path="username"/>
                                                                    </div>
                                                                </spring:bind>
                                                            </div>
                                                        </div>
                                                        <div class="row form-group">
                                                            <div class="col-md-12">
                                                                <spring:bind path="login">
                                                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                                                        <label for="login">Логин</label>
                                                                        <form:input type="text" path="login"
                                                                                    class="form-control"
                                                                                    placeholder="PussySlayer"/>
                                                                        <form:errors path="login"/>
                                                                    </div>
                                                                </spring:bind>
                                                            </div>
                                                        </div>
                                                        <div class="row form-group">
                                                            <div class="col-md-12">
                                                                <spring:bind path="password">
                                                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                                                        <label for="password">Пароль</label>
                                                                        <form:input type="password" path="password"
                                                                                    class="form-control"
                                                                                    placeholder="Пароль"/>
                                                                        <form:errors path="password"/>
                                                                    </div>
                                                                </spring:bind>
                                                            </div>
                                                        </div>
                                                        <div class="row form-group">
                                                            <div class="col-md-12">
                                                                <spring:bind path="confirmPassword">
                                                                    <div class="form-group ${status.error ? 'has-error' : ''}">
                                                                        <label for="confirmPassword">Повторите
                                                                            пароль</label>
                                                                        <form:input type="password"
                                                                                    path="confirmPassword"
                                                                                    class="form-control"
                                                                                    placeholder="Подтвердите пароль"/>
                                                                        <form:errors path="confirmPassword"/>
                                                                    </div>
                                                                </spring:bind>
                                                            </div>
                                                        </div>

                                                        <div class="row form-group">
                                                            <div class="col-md-12">
                                                                <input type="submit" class="btn btn-primary"
                                                                       value="Зарегистрироваться">
                                                            </div>
                                                        </div>
                                                    </form:form>
                                                </div>
                                                <div class="tab-content-inner" data-content="login">
                                                    <form method="POST" action="${contextPath}/login"
                                                          class="form-signin">
                                                        <div class="form-group ${error != null ? 'has-error' : ''}">
                                                            <div class="row form-group">
                                                                <div class="col-md-12">
                                                                    <label for="username">Логин или e-mail</label>
                                                                    <span class="errors">${message}</span>
                                                                    <input name="username" type="text"
                                                                           class="form-control"
                                                                           placeholder="Имя пользователя"
                                                                           autofocus="true"/>
                                                                </div>
                                                            </div>
                                                            <div class="row form-group">
                                                                <div class="col-md-12">
                                                                    <label for="password">Пароль</label>
                                                                    <input name="password" type="password"
                                                                           class="form-control" placeholder="Пароль"/>
                                                                    <span class="errors">${error}</span>
                                                                </div>
                                                            </div>
                                                            <div class="checkbox">
                                                                <label>
                                                                    <input type="checkbox" name="remember-me-parameter">
                                                                    Запомнить меня
                                                                </label>
                                                            </div>
                                                            <input type="hidden" name="${_csrf.parameterName}"
                                                                   value="${_csrf.token}"/>
                                                            <div class="row form-group">
                                                                <div class="col-md-12">
                                                                    <input type="submit" class="btn btn-primary"
                                                                           value="Войти">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- После Регистрации или входа -->
                                    <div class="form-wrap2">
                                        <c:set var="registration" value="registration"/>
                                        <c:set var="login" value="login"/>
                                        <c:choose>
                                            <c:when test="${status == registration}">
                                                <div class="profile">
                                                    <div class="is-not-activated">
                                                        <div class="row">
                                                            <div class="col-md-3 vertical-centering">
                                                                <div class="navigation">
                                                                    <a class="col-md-12" href="#"><i
                                                                            class="mail disabled circular icon"></i></a>
                                                                    <a class="col-md-12" href="#"><i
                                                                            class="mail disabled circular icon"></i></a>
                                                                    <a class="col-md-12" href="#"><i
                                                                            class="mail disabled circular icon"></i></a>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <img class="profile-icon"
                                                                     src="${pageContext.request.contextPath}/resources/images/unknownAvatar.png">
                                                            </div>
                                                            <div class="col-md-3 vertical-centering">
                                                                <div class="navigation">
                                                                    <a class="col-md-12" href="#"><i
                                                                            class="mail disabled circular icon"></i></a>
                                                                    <a class="col-md-12" href="#"><i
                                                                            class="mail disabled circular icon"></i></a>
                                                                    <a class="col-md-12" href="#"><i
                                                                            class="mail disabled circular icon"></i></a>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="content text-center">
                                                                    <a class="header text-center">${name}</a>
                                                                    <div class="col-md-12">
                                                                        <form action="http://${email_url}">
                                                                            <button type="submit" class="btn btn-info">
                                                                                Активируйте аккаунт
                                                                            </button>
                                                                        </form>
                                                                    </div>
                                                                    <div class="description">Lorem mi sae na kruz
                                                                        akellam
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="ui white divider"></div>
                                                        <div class="row">
                                                            <div class="col-md-4 text-center">
                                                                <a href="#"
                                                                   class="ui inverted disabled pink button">Pink</a>
                                                            </div>
                                                            <div class="col-md-4 text-center">
                                                                <a href="#"
                                                                   class="ui inverted disabled pink button">Pink</a>
                                                            </div>
                                                            <div class="col-md-4 text-center">
                                                                <a href="#"
                                                                   class="ui inverted disabled pink button">Pink</a>
                                                            </div>
                                                        </div>
                                                        <div class="ui white divider"></div>
                                                        <div class="logout-form">
                                                            <form id="logoutForm" method="POST"
                                                                  action="${contextPath}/logout">
                                                                <input type="hidden" name="${_csrf.parameterName}"
                                                                       value="${_csrf.token}"/>
                                                            </form>
                                                            <div class="row form-group">
                                                                <div class="col-md-12 text-center">
                                                                    <input type="submit" class="btn btn-primary"
                                                                           onclick="document.forms['logoutForm'].submit()"
                                                                           value="Выйти">
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:when>
                                            <c:when test="${status == login}">
                                                <div class="profile">
                                                    <div class="is-activated">
                                                        <div class="row">
                                                            <div class="col-md-3 vertical-centering">
                                                                <div class="navigation">
                                                                    <a class="col-md-12" href="#"><i
                                                                            class="mail circular icon"></i></a>
                                                                    <a class="col-md-12" href="#"><i
                                                                            class="mail circular icon"></i></a>
                                                                    <a class="col-md-12" href="#"><i
                                                                            class="mail circular icon"></i></a>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <c:if test="${currentUser.avatar != null}">
                                                                    <img class="profile-icon"
                                                                         src="${currentUser.avatar}">
                                                                </c:if>
                                                                <c:if test="${currentUser.avatar == null}">
                                                                    <img class="profile-icon"
                                                                         src="${pageContext.request.contextPath}/resources/images/unknownAvatar.png">
                                                                </c:if>
                                                            </div>
                                                            <div class=" col-md-3 vertical-centering">
                                                                <div class="navigation">
                                                                    <a class="col-md-12"
                                                                       href="${pageContext.request.contextPath}/show_your_services"><i
                                                                            class="shop circular icon"></i></a>
                                                                    <a class="col-md-12"
                                                                       href="${pageContext.request.contextPath}/add_service"><i
                                                                            class="add circular icon"></i></a>
                                                                    <a class="col-md-12" href="#"><i
                                                                            class="mail circular icon"></i></a>
                                                                </div>
                                                            </div>
                                                            <c:if test="${currentUser.avatar == null}">
                                                                <form:form method="POST"
                                                                           action="uploadFile/loadAvatar?${_csrf.parameterName}=${_csrf.token}"
                                                                           enctype="multipart/form-data">
                                                                    <div class="col-md-12 text-center">
                                                                        <input id="upload" accept="image/*" class="hide"
                                                                               type="file"
                                                                               name="file">
                                                                        <label class="btn btn-info"
                                                                               for="upload">Загрузить</label>
                                                                    </div>
                                                                    <div class="col-md-12 text-center">
                                                                        <input class="btn btn-warning" type="submit"
                                                                               value="Upload">
                                                                    </div>
                                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                                           value="${_csrf.token}"/>
                                                                </form:form>
                                                            </c:if>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="content text-center">
                                                                    <a class="header text-center">${name}</a>
                                                                    <div class="description">
                                                                        Lorem mi sae na kruz akellam
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="ui white divider"></div>
                                                        <div class="row">
                                                            <div class="col-md-6 text-center">
                                                                <a href="#"
                                                                   class="ui inverted pink button">Pink</a>
                                                            </div>
                                                            <div class="col-md-6 text-center">
                                                                <a href="${pageContext.request.contextPath}/options"
                                                                   class="ui inverted pink button">Настройки</a>
                                                            </div>
                                                        </div>
                                                        <div class="ui white divider"></div>
                                                        <form id="logoutForm" method="POST"
                                                              action="${contextPath}/logout">
                                                            <input type="hidden" name="${_csrf.parameterName}"
                                                                   value="${_csrf.token}"/>
                                                        </form>
                                                        <div class="row form-group">
                                                            <div class="col-md-12 text-center">
                                                                <input type="submit" class="btn btn-primary"
                                                                       onclick="document.forms['logoutForm'].submit()"
                                                                       value="Выйти">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </header>
        </div>
    </div>

    <div id="fh5co-intro-section">
        <div class="container">
            <div class="row">
                <div class="col-md-12 text-center">
                    <h2>Мы упрощаем сделки</h2>
                    <p>Попробуй сам <a href="${pageContext.request.contextPath}/add_service">предложить услугу</a></p>
                </div>
            </div>
        </div>
    </div><!-- end fh5co-intro-section -->

    <div class="owl-carousel owl-carousel1 owl-carousel-fullwidth fh5co-light-arrow animate-box"
         data-animate-effect="fadeIn">
        <div class="item">
            <div class="item">
                <img src="http://great-usa.ru/wp-content/uploads/2016/12/piano-1406526_1920.jpg" alt="image">
                <a href="${pageContext.request.contextPath}/show_all_services/1category"
                   class="pop-up-overlay text-center">
                    <div class="desc">
                        <h3>Категория 1</h3>
                        <span>Переезд</span>
                    </div>
                </a>
            </div>
            <div class="item">
                <img src="http://great-usa.ru/wp-content/uploads/2016/12/piano-1406526_1920.jpg" alt="image">
                <a href="${pageContext.request.contextPath}/show_all_services/2category"
                   class="pop-up-overlay text-center">
                    <div class="desc">
                        <h3>Категория 2</h3>
                        <span>Переезд</span>
                    </div>
                </a>
            </div>
        </div>
        <div class="item">
            <div class="item">
                <img src="http://great-usa.ru/wp-content/uploads/2016/12/piano-1406526_1920.jpg" alt="image">
                <a href="${pageContext.request.contextPath}/show_all_services/3category"
                   class="pop-up-overlay text-center">
                    <div class="desc">
                        <h3>Категория 3</h3>
                        <span>Переезд</span>
                    </div>
                </a>
            </div>
            <div class="item">
                <img src="http://great-usa.ru/wp-content/uploads/2016/12/piano-1406526_1920.jpg" alt="image">
                <a href="#" class="pop-up-overlay text-center">
                    <div class="desc">
                        <h3>Перевозки</h3>
                        <span>Переезд</span>
                    </div>
                </a>
            </div>
        </div>
        <div class="item">
            <div class="item">
                <img src="http://svadbavm.ru/upload/iblock/62f/couple.jpg" alt="image">
                <a href="#" class="pop-up-overlay text-center">
                    <div class="desc">
                        <h3>Перевозки</h3>
                        <span>Переезд</span>
                    </div>
                </a>
            </div>
            <div class="item">
                <img src="http://great-usa.ru/wp-content/uploads/2016/12/piano-1406526_1920.jpg" alt="image">
                <a href="#" class="pop-up-overlay text-center">
                    <div class="desc">
                        <h3>Перевозки</h3>
                        <span>Переезд</span>
                    </div>
                </a>
            </div>
        </div>
        <div class="item">
            <div class="item">
                <img src="http://great-usa.ru/wp-content/uploads/2016/12/piano-1406526_1920.jpg" alt="image">
                <a href="#" class="pop-up-overlay text-center">
                    <div class="desc">
                        <h3>Перевозки</h3>
                        <span>Переезд</span>
                    </div>
                </a>
            </div>
            <div class="item">
                <img src="http://great-usa.ru/wp-content/uploads/2016/12/piano-1406526_1920.jpg" alt="image">
                <a href="#" class="pop-up-overlay text-center">
                    <div class="desc">
                        <h3>Перевозки</h3>
                        <span>Переезд</span>
                    </div>
                </a>
            </div>
        </div>
    </div>

    <div id="fh5co-common-section">
        <div class="container">
            <div class="heading-section text-center">
                <h2>Кем мы являемся</h2>
            </div>
            <div class="row">
                <div class="col-md-10 col-md-offset-1">
                    <div class="col-md-6 col-sm-6 services-num services-num-text-right">
                        <span class="number-holder"></span>
                        <div class="desc">
                            <h3>Школьники делают сайт</h3>
                            <p>Собралась команда и начала создавать что-то</p>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-6 services-num">
                        <span class="number-holder"></span>
                        <div class="desc">
                            <h3>С нами сотрудничают такие компании как</h3>
                            <p>кто-то, что-то</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div><!-- end fh5co-common-section -->

    <div class="fh5co-parallax"
         style="background-image: url(http://www.publicdomainpictures.net/pictures/180000/velka/hand-with-thumb-up.jpg);"
         data-stellar-background-ratio="0.5">
        <div class="overlay"></div>
        <div class="container">
            <div class="row">
                <div class="col-md-8 col-md-offset-2 col-sm-12 col-sm-offset-0 col-xs-12 col-xs-offset-0 text-center fh5co-table">
                    <div class="fh5co-intro fh5co-table-cell">
                        <h1 class="text-center">Совершать сделки легко</h1>
                        <p>Сделано с любовью <a href="#">Deal</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div><!-- end: fh5co-parallax -->

    <div id="fh5co-services-section">
        <div class="container">
            <div class="heading-section text-center">
                <h2>Удобное мобильное приложение</h2>
            </div>
            <div class="row">
                <div class="col-md-4 col-sm-6">
                    <div class="fh5co-services-right">
                        <div class="fh5co-table2 text-center">
                            <div class="fh5co-table-cell2">
                                <i class="icon-heart3"></i>
                            </div>
                        </div>
                        <div class="holder-section">
                            <h3>Массаж</h3>
                            <p>Каждый хочет массаж </p>
                        </div>
                    </div>
                    <div class="fh5co-services-right">
                        <div class="fh5co-table2 fh5co-table2-color-2 text-center">
                            <div class="fh5co-table-cell2">
                                <i class="icon-laptop"></i>
                            </div>
                        </div>
                        <div class="holder-section">
                            <h3>Программирование</h3>
                            <p>Найди разработчика себе по душе </p>
                        </div>
                    </div>
                    <div class="fh5co-services-right">
                        <div class="fh5co-table2 fh5co-table2-color-3 text-center">
                            <div class="fh5co-table-cell2">
                                <i class="icon-video"></i>
                            </div>
                        </div>
                        <div class="holder-section">
                            <h3>Редактирование видео</h3>
                            <p>Нужен свадебный фильм? Просто попроси! </p>
                        </div>
                    </div>
                </div>

                <div class="col-md-4 text-center">
                    <div class="fhco-hero2">
                        <img class="img-responsive"
                             src="${pageContext.request.contextPath}/resources/images/iphone6.png"
                             alt="iphone6">
                    </div>
                </div>

                <div class="col-md-4 col-sm-6">
                    <div class="fh5co-services">
                        <div class="fh5co-table2 fh5co-table2-color-4 text-center">
                            <div class="fh5co-table-cell2">
                                <i class="icon-mobile"></i>
                            </div>
                        </div>
                        <div class="holder-section">
                            <h3>Починим электронику</h3>
                            <p>Сломанный телефон - не проблема </p>
                        </div>
                    </div>
                    <div class="fh5co-services">
                        <div class="fh5co-table2 fh5co-table2-color-5 text-center">
                            <div class="fh5co-table-cell2">
                                <i class="icon-gears"></i>
                            </div>
                        </div>
                        <div class="holder-section">
                            <h3>Сантехника</h3>
                            <p>Механика вызывали? </p>
                        </div>
                    </div>
                    <div class="fh5co-services">
                        <div class="fh5co-table2 fh5co-table2-color-6 text-center">
                            <div class="fh5co-table-cell2">
                                <i class="icon-piechart"></i>
                            </div>
                        </div>
                        <div class="holder-section">
                            <h3>Домашнее задание</h3>
                            <p>Не можешь сделать дз? А первокурсник сделает его за сотку </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div><!-- end: fh5co-services-section -->

    <div id="fh5co-featured-work-section">
        <div class="container-fluid">
            <div class="heading-section text-center">
                <h2>Большое количество сфер услуг</h2>
            </div>
            <div class="owl-carousel owl-carousel2">
                <div class="item">
                    <!-- <a href="#" class="image-popup"> -->
                    <img src="https://render.fineartamerica.com/images/images-profile-flow/350/images/artworkimages/medium/1/swamp-thing-teshia-art.jpg"
                         alt="image">
                    <a href="#" class="pop-up-overlay text-center">
                        <div class="desc">
                            <h3>Искусство</h3>
                            <span>Живопись</span>
                        </div>
                    </a>
                </div>
                <div class="item">
                    <img src="http://weblada.ru/uploads/posts/2011-01/1295599609_455.jpg" alt="image">
                    <a href="#" class="pop-up-overlay pop-up-overlay-color-2 text-center">
                        <div class="desc">
                            <h3>Перевозки</h3>
                            <span>Переезд</span>
                        </div>
                    </a>
                </div>
                <div class="item">
                    <!-- <a href="#" class="image-popup"> -->
                    <img src="http://www.na-obzor.ru/image_galery/82/2516.jpg" alt="image">
                    <!-- </a> -->
                    <a href="#" class="pop-up-overlay pop-up-overlay-color-3 text-center">
                        <div class="desc">
                            <h3>Мобильные приложения</h3>
                            <span>Андройд</span>
                        </div>
                    </a>
                </div>
                <div class="item">
                    <!-- <a href="#" class="image-popup"> -->
                    <img src="${pageContext.request.contextPath}/resources/images/portfolio_pic4.jpg" alt="image">
                    <!-- </a> -->
                    <a href="#" class="pop-up-overlay pop-up-overlay-color-4 text-center">
                        <div class="desc">
                            <h3>Camera Lens</h3>
                            <span>Illustration</span>
                        </div>
                    </a>
                </div>
                <div class="item">
                    <!-- <a href="#" class="image-popup"> -->
                    <img src="images/portfolio_pic5.jpg" alt="image">
                    <!-- </a> -->
                    <a href="#" class="pop-up-overlay text-center">
                        <div class="desc">
                            <h3>Card</h3>
                            <span>Card</span>
                        </div>
                    </a>
                </div>
                <div class="item">
                    <!-- <a href="#" class="image-popup"> -->
                    <img src="images/portfolio_pic6.jpg" alt="image">
                    <!-- </a> -->
                    <a href="#" class="pop-up-overlay pop-up-overlay-color-2 text-center">
                        <div class="desc">
                            <h3>Shoes</h3>
                            <span>Brand</span>
                        </div>
                    </a>
                </div>
                <div class="item">
                    <!-- <a href="#" class="image-popup"> -->
                    <img src="images/portfolio_pic7.jpg" alt="image">
                    <!-- </a> -->
                    <a href="#" class="pop-up-overlay text-center">
                        <div class="desc">
                            <h3>Magazine</h3>
                            <span>Web</span>
                        </div>
                    </a>
                </div>
                <div class="item">
                    <!-- <a href="#" class="image-popup"> -->
                    <img src="images/portfolio_pic8.jpg" alt="image">
                    <!-- </a> -->
                    <a href="#" class="pop-up-overlay pop-up-overlay-color-3 text-center">
                        <div class="desc">
                            <h3>VCard</h3>
                            <span>Card</span>
                        </div>
                    </a>
                </div>
                <div class="item">
                    <!-- <a href="#" class="image-popup"> -->
                    <img src="images/portfolio_pic9.jpg" alt="image">
                    <!-- </a> -->
                    <a href="#" class="pop-up-overlay pop-up-overlay-color-4 text-center">
                        <div class="desc">
                            <h3>Paper</h3>
                            <span>Illustration</span>
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </div><!-- end fh5co-featured-work-section -->

    <div id="fh5co-blog-section">
        <div class="container">
            <div class="heading-section text-center">
                <h2>Недавние обновления</h2>
            </div>
            <div class="row">
                <div class="col-md-4 blog-section">
                    <span>22 <small>Марта 2017</small></span>
                    <h3><a href="#">Начали разрабатывать что-то</a></h3>
                    <p>Что-то сделали, работает хорошо, но не очень, скоро релиз</p>
                    <a class="btn btn-primary" href="#">Подробнее</a>
                </div>
                <div class="col-md-4 blog-section">
                    <span>13 <small>Май 2017</small></span>
                    <h3><a href="#">Начали разрабатывать что-то</a></h3>
                    <p>FЧто-то сделали, работает хорошо, но не очень, скоро релиз</p>
                    <a class="btn btn-primary" href="#">Подробнее</a>
                </div>
                <div class="col-md-4 blog-section">
                    <span>01 <small>Апрель 2017</small></span>
                    <h3><a href="#">Начали разрабатывать что-то</a></h3>
                    <p>Что-то сделали, работает хорошо, но не очень, скоро релиз</p>
                    <a class="btn btn-primary" href="#">Подробнее</a>
                </div>
            </div>
        </div>
    </div><!-- end: fh5co-blog-section -->

    <div class="fh5co-parallax"
         style="background-image: url(https://cdn2.professor-falken.com/wp-content/uploads/2017/01/hombre-cuello-camisa-corbata-barba-mano-Fondos-de-Pantalla-HD-professor-falken.com_.jpg);"
         data-stellar-background-ratio="0.5">
        <div class="overlay"></div>
        <div class="container">
            <div class="row">
                <div class="col-md-8 col-md-offset-2 col-sm-12 col-sm-offset-0 col-xs-12 col-xs-offset-0 text-center fh5co-table">
                    <div class="fh5co-intro fh5co-table-cell">
                        <h1 class="text-center">Стань ближе к успеху</h1>
                        <p>С компанией <a href="#">Deal</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div><!-- end: fh5co-parallax -->

    <footer>
        <div id="footer" class="fh5co-border-line">
            <div class="container">
                <div class="row">
                    <div class="col-md-8 col-md-offset-2 text-center">
                        <p>Использование сайта, в том числе подача объявлений, означает согласие с пользовательским
                            соглашением.
                            Оплачивая услуги на сайте, вы принимаете оферту <a href="#">Deal</a>. Все права защищены.
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
<!-- JQuery UI -->
<script src="${pageContext.request.contextPath}/resources/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<!-- Utilities -->
<script src="${pageContext.request.contextPath}/resources/js/utilities.js"></script>
<!-- Main JS (Do not remove) -->
<script src="${pageContext.request.contextPath}/resources/js/main.js" charset="utf-8"></script>
</body>
</html>
