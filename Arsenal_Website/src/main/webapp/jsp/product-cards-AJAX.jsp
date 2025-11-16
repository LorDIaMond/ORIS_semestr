
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="product" items="${products}">
    <div class="product-card" data-product-id="${product.id}">

    <!-- Контейнер для карточки -->
        <div class="product-content">
            <a href="${pageContext.request.contextPath}/product?id=${product.id}" class="product-link">
                <img src="${pageContext.request.contextPath}/images/products/${product.imageUrl}"
                     alt="${product.name}"
                     class="product-image">
                <h3 class="product-name">${product.name}</h3>
                <p class="product-price">£${product.price}</p>
            </a>

            <!-- Кнопка обавления в избранные -->
            <c:if test="${not empty sessionScope.userId}">
                <button class="favorite-btn"
                        data-favorite="${isFavorite[product.id] ? 'true' : 'false'}"
                        title="Add to favorites">
                        ${isFavorite[product.id] ? '❤️' : '🤍'}
                </button>
            </c:if>

            <!-- Кнопка добавления в корзину -->
            <c:if test="${not empty sessionScope.userId}">
                <button class="cart-btn"
                        data-in-cart="${cart[product.id] ? 'true' : 'false'}"
                        title="Add to cart">
                        ${cart[product.id] ? '🛒 In Cart' : '➕ Add to Cart'}
                </button>
            </c:if>
        </div>
    </div>
</c:forEach>

