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
    <!-- Esta directiva debe ir dentro del body para que tenga efecto sobre todos los mensajes del body -->
           <li>
               <fmt:setBundle basename="messages"/>
               <form action="changeLanguage" method="get">
                   <select name="lang" onchange="this.form.submit()">
                       <option value="en" ${sessionScope.locale.language == 'en' ? 'selected' : ''}>English</option>
                       <option value="es" ${sessionScope.locale.language == 'es' ? 'selected' : ''}>Español</option>
                   </select>
               </form>
           </li>

    <hr>
</header>