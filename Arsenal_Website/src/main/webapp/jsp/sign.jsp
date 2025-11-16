<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Arsenal Market</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/sign.css?v=${Math.random()}" type="text/css">
    </head>
    <body>
    <div class="sign-in-window">
        <h1>Sign In</h1>
        <form method="post" action="${pageContext.request.contextPath}/sign">
            <div class="form-group">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="${param.email}" required>
                <c:if test="${not empty errors.email}">
                    <span class="error">${errors.email}</span>
                </c:if>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" required>
                <c:if test="${not empty errors.password}">
                    <span class="error">${errors.password}</span>
                </c:if>
            </div>
            <button type="submit">Sign In</button>
        </form>
        <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Register</a></p>
    </div>
    </body>
</html>
