<%--
  <pre class="product-description">${product.description}</pre>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${product.name}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/product.css?v=${Math.random()}">
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

    <main class="main-content">
        <div class="product-detail">
            <img src="${pageContext.request.contextPath}/images/products/${product.imageUrl}"
                 alt="${product.name}" class="detail-image">

            <div class="detail-info">
                <h1>${product.name}</h1>
                <p class="detail-price">£${product.price}</p>
                <pre class="detail-description">${product.description}</pre>
            </div>
        </div>
    </main>

</body>
</html>
