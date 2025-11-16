<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Arsenal Market</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/register.css?v=${Math.random()}" type="text/css">
    </head>
    <body>
    <div class="sign-in-window">
        <h1>Create Account</h1>
        <form method="post" action="${pageContext.request.contextPath}/register">
            <div class="form-group">
                <label for="name">name</label>
                <input type="text" id="name" name="name" value="${param.name}" required>
                <c:if test="${not empty errors.name}">
                    <span class="error">${errors.name}</span>
                </c:if>
            </div>
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
            <button type="submit">Register</button>
        </form>
        <p>Already have an account? <a href="${pageContext.request.contextPath}/sign">Sign In</a></p>
    </div>
    </body>
</html>
