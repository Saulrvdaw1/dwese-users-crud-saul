<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'es'}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html lang="${sessionScope.locale != null ? sessionScope.locale.language : 'es'}">

<head>
    <meta charset="UTF-8">
    <title>CRUD Usuarios</title>
</head>

<body>

<header>
    <form action="${pageContext.request.contextPath}/changeLanguage" method="get">
        <button type="submit" name="lang" value="es">Español</button>
        <button type="submit" name="lang" value="en">English</button>
    </form>

    <hr>
</header>