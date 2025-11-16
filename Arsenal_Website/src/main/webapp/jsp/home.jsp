<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Arsenal Market</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/home.css?v=${Math.random()}" type="text/css">
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
            <div class="content-wrapper">
                <!-- Новости -->
                <div class="news-section">
                    <c:if test="${news != null}">
                        <h2 class="news-title">${news.title}</h2>
                        <a href="${news.externalUrl}" target="_blank" class="news-card-link">
                            <div class="news-card">
                                <img src="${pageContext.request.contextPath}/${news.imageUrl}"
                                     alt="${news.title}"
                                     class="news-image">
                                <div class="news-overlay">
                                    <p class="news-teaser">
                                        <c:choose>
                                            <c:when test="${news.content != null && fn:length(news.content) > 120}">
                                                ${fn:substring(news.content, 0, 120)}...
                                            </c:when>
                                            <c:otherwise>
                                                ${news.content}...
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </div>
                            </div>
                        </a>
                    </c:if>
                </div>

                <!-- Превью матчей -->
                <div class="match-section">
                    <c:if test="${match != null && match.matchDate != null}">
                        <c:set var="matchDate" value="${match.matchDate}" />
                        <div class="match-info">
                            <div class="match-date-competition">
                                <p class="date"><fmt:formatDate value="${matchDate}" pattern="EEE MMM dd" /></p>
                            </div>
                            <div class="match-time-stadium">
                                <p>KICKOFF - <fmt:formatDate value="${matchDate}" pattern="HH:mm" /></p>
                                <p class="stadium">${match.stadium}</p>
                            </div>
                        </div>

                        <div class="teams-vs">
                            <img src="${pageContext.request.contextPath}/${match.homeTeamLogoUrl}" alt="${match.homeTeam}" class="team-logo">
                            <c:choose>
                                <c:when test="${match.score != null}">
                                    <div class="match-score">${match.score}</div>
                                </c:when>
                                <c:otherwise>
                                    <span class="vs">V</span>
                                </c:otherwise>
                            </c:choose>
                            <img src="${pageContext.request.contextPath}/${match.awayTeamLogoUrl}" alt="${match.awayTeam}" class="team-logo">
                        </div>

                        <div class="match-footer">
                            <img src="${pageContext.request.contextPath}/images/events/icon_sky-sports.png" alt="Sky Sports" class="sky-logo">
                            <a href="https://www.arsenal.com/fixture/arsenal/2025-Oct-26/crystal-palace#!match-news" class="btn btn-secondary">NEWS & VIDEO</a>
                            <a href="  https://www.arsenal.com/tickets  " target="_blank" class="btn btn-primary">TICKET INFO</a>
                        </div>
                    </c:if>
                </div>
            </div>
        </main>

        <!-- Подвал сайта -->
    </body>
</html>
