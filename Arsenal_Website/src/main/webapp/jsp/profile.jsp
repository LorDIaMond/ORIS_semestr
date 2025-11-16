<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
    <head>
        <title>Profile</title>
        <title>Arsenal Market</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/profile.css?v=${Math.random()}" type="text/css">
    </head>
    <body>
        <!-- Шапка сайта -->
        <header class="site-header">
            <!-- Навигация -->
            <nav class="nav">
                <ul class="primary-nav__list">
                    <li class="primary-nav__item">
                        <img src="${pageContext.request.contextPath}/images/events/canon.png" alt="Эмблема Арсенала">
                    </li>
                    <li class="primary-nav__item"><a class='nav-link' href="https://www.arsenal.com/">ARSENAL.COM</a></li>
                    <li class="primary-nav__item"><a class='nav-link' href="#">CLUB</a></li>
                    <li class="primary-nav__item"><a class='nav-link' href="#">MATCHES</a></li>
                    <li class="primary-nav__item">
                        <a class='nav-link' href="${pageContext.request.contextPath}/shop">SHOP</a>
                    </li>
                    <li class="primary-nav__item">
                        <c:choose>
                            <c:when test="${not empty sessionScope.userId}">
                                <a class="nav-link" href="${pageContext.request.contextPath}/profile">PROFILE</a>
                            </c:when>
                            <c:otherwise>
                                <a class="nav-link" href="${pageContext.request.contextPath}/sign">SIGN IN</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <!-- Кнопка админки видна только для админа -->
                    <c:if test="${user != null && user.isAdmin}">
                        <li class="primary-nav__item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/admin">ADMIN PANEL</a>
                        </li>
                    </c:if>
                </ul>

                <ul class="partner-nav__list">
                    <li class="partner-nav__item">
                        <img src="${pageContext.request.contextPath}/images/events/icon_adidas.png" alt="Иконка Adidas">
                    </li>
                    <li class="partner-nav__item">
                        <img src="${pageContext.request.contextPath}/images/events/icon_emirates.png" alt="Иконка Emirates">
                    </li>
                    <li class="partner-nav__item">
                        <img src="${pageContext.request.contextPath}/images/events/icon_sobha.png" alt="Иконка Sobha">
                    </li>
                    <li class="partner-nav__item">
                        <img src="${pageContext.request.contextPath}/images/events/icon_rwanda.png" alt="Иконка Visit Rwanda">
                    </li>
                </ul>
            </nav>
        </header>

        <!-- Основной контент страницы -->
        <main class="main-content">
            <div class="profile-page">

                <div class="profile-header">
                    <h1>Ваш профиль</h1>
                    <p>Здравствуйте, <strong>${user.name}</strong>!</p>
                </div>

                <!-- Основная информация -->
                <div class="profile-section">
                    <h2>Личная информация</h2>
                    <p><strong>Email:</strong> ${user.email}</p>
                </div>

                <!-- Избранное -->
                <div class="profile-section">
                    <h2>Избранное</h2>
                    <c:choose>
                        <c:when test="${not empty favoriteProducts}">
                            <div class="favorite-products">
                                <c:forEach var="product" items="${favoriteProducts}">
                                    <div class="product-card">
                                        <img src="${pageContext.request.contextPath}/images/products/${product.imageUrl}"
                                             alt="${product.name}" class="product-image">
                                        <p>${product.name}</p>
                                        <p>£${product.price}</p>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p class="empty-state">У вас пока нет избранных товаров.</p>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Заказы -->
                <div class="profile-section">
                    <h2>Мои заказы</h2>
                    <c:choose>
                        <c:when test="${not empty orders}">
                            <div class="favorite-products">
                                <c:forEach var="product" items="${orders}">
                                    <div class="product-card">
                                        <img src="${pageContext.request.contextPath}/images/products/${product.imageUrl}"
                                             alt="${product.name}" class="product-image">
                                        <p>${product.name}</p>
                                        <p>£${product.price}</p>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <p class="empty-state">У вас пока нет заказов.</p>
                        </c:otherwise>
                    </c:choose>
                </div>

                <!-- Выход -->
                <a href="${pageContext.request.contextPath}/logout" class="logout-link">Выйти из аккаунта</a>
            </div>
        </main>
    </body>
</html>
