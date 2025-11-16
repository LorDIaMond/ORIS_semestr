
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Магазин — Arsenal Market</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/shop.css?v=${Math.random()}">
    </head>

    <script src="${pageContext.request.contextPath}/JS/filters.js"></script>
    <script src="${pageContext.request.contextPath}/JS/favorites.js"></script>

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
            <!-- Фильтр товаров -->
            <div class="filters-container">
                <!-- Фильтр товаров по категориям -->
                <div class="filter-section">
                    <h3>CATEGORY</h3>
                    <c:forEach var="category" items="${categories}">
                        <div class="filter-item">
                            <input type="checkbox" id="cat_${category.id}" value="${category.id}">
                            <label for="cat_${category.id}">${category.name}</label>
                        </div>
                    </c:forEach>
                </div>

                <!-- Фильтр товаров по цене -->
                <div class="filter-section">
                    <h3>PRICE</h3>
                    <div class="filter-item">
                        <input type="checkbox" id="price_under_20" value="under_20">
                        <label for="price_under_20">UNDER £20</label>
                    </div>
                    <div class="filter-item">
                        <input type="checkbox" id="price_20_50" value="20_50">
                        <label for="price_20_50">£20 - £50</label>
                    </div>
                    <div class="filter-item">
                        <input type="checkbox" id="price_50_100" value="50_100">
                        <label for="price_50_100">£50 - £100</label>
                    </div>
                    <div class="filter-item">
                        <input type="checkbox" id="price_100_250" value="100_250">
                        <label for="price_100_250">£100 - £250</label>
                    </div>
                    <div class="filter-item">
                        <input type="checkbox" id="price_250_500" value="250_500">
                        <label for="price_250_500">£250 - £500</label>
                    </div>
                </div>
            </div>

            <!-- Карточки товаров -->
            <div class="products-container">
                <div id="product-cards-container" class="products-section">
                    <jsp:include page="/jsp/product-cards-AJAX.jsp" />
                </div>
            </div>
        </main>

        <!-- Подвал сайта -->
    </body>
</html>
