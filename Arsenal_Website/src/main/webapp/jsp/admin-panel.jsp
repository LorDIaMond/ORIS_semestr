<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Arsenal Market</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin-panel.css?v=${Math.random()}" type="text/css">
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
            <div class="container">
                <h1>Админка: Управление товарами</h1>

                <!-- Форма добавления карточек товара -->
                <div class="form-section">
                    <h2>Добавить новый товар</h2>
                    <form method="post"
                          action="${pageContext.request.contextPath}/admin"
                          enctype="multipart/form-data">
                        <input type="hidden" name="action" value="create">

                        <div class="form-group">
                            <label>Название</label>
                            <input type="text" name="name" required>
                        </div>

                        <div class="form-group">
                            <label>Описание</label>
                            <textarea name="description" rows="3"></textarea>
                        </div>

                        <div class="form-group">
                            <label>Цена (£)</label>
                            <input type="number" step="0.01" name="price" required>
                        </div>

                        <div class="form-group">
                            <label>Изображение товара</label>
                            <input type="file" name="image" accept="image/*" required>
                        </div>

                        <div class="form-group">
                            <label>Категория</label>
                            <select name="categoryId" required>
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.id}">${cat.name}</option>
                                </c:forEach>
                            </select>
                        </div>

                        <button type="submit">Добавить товар</button>
                    </form>
                </div>

                <!-- Таблица товаров -->
                <div class="products-grid">
                    <c:forEach var="p" items="${products}">
                        <div class="product-card">
                            <img src="${pageContext.request.contextPath}/images/products/${p.imageUrl}" alt="${p.name}">
                            <h3>${p.name}</h3>
                            <p>£${p.price}</p>
                            <div class="admin-actions">
                                <a href="${pageContext.request.contextPath}/admin/edit?id=${p.id}" class="btn-edit">Редактировать</a>
                                <form method="post" action="${pageContext.request.contextPath}/admin" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${p.id}">
                                    <button type="submit" class="btn-delete">Удалить</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </main>
    </body>
</html>
