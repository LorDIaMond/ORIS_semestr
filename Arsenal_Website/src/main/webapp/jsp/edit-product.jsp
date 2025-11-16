<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Редактировать товар — Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/admin-panel.css">
</head>
<body>
<div class="container">
    <h1>Редактировать товар</h1>

    <form method="post" action="${pageContext.request.contextPath}/admin/edit">
        <input type="hidden" name="id" value="${product.id}">

        <div class="form-group">
            <label>Название</label>
            <input type="text" name="name" value="${product.name}" required>
        </div>

        <div class="form-group">
            <label>Описание</label>
            <textarea name="description" rows="3">${product.description}</textarea>
        </div>

        <div class="form-group">
            <label>Цена (£)</label>
            <input type="number" step="0.01" name="price" value="${product.price}" required>
        </div>

        <div class="form-group">
            <label>Изображение</label>
            <p><img src="${pageContext.request.contextPath}/images/products/${product.imageUrl}" width="100"></p>
            <p>Имя файла: ${product.imageUrl}</p>
            <!-- Для простоты: редактирование изображения — отдельная задача -->
        </div>

        <div class="form-group">
            <label>Категория</label>
            <select name="categoryId" required>
                <c:forEach var="cat" items="${categories}">
                    <option value="${cat.id}" ${cat.id == product.categoryId ? 'selected' : ''}>
                            ${cat.name}
                    </option>
                </c:forEach>
            </select>
        </div>

        <button type="submit">Сохранить изменения</button>
        <a href="${pageContext.request.contextPath}/admin" style="margin-left: 15px;">Отмена</a>
    </form>
</div>
</body>
</html>