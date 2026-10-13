<%@ include file="/partials/header.jsp" %>

<h1>
    <fmt:message key="msg.user-list.title"/>
</h1>

<c:url var="newUserUrl" value="/users">
    <c:param name="action" value="new"/>
</c:url>

<a href="${newUserUrl}">
    <fmt:message key="msg.user-list.add"/>
</a>

<br><br>

<table border="1">

    <thead>
    <tr>
        <th><fmt:message key="msg.user-list.id"/></th>
        <th><fmt:message key="msg.user-list.username"/></th>
        <th><fmt:message key="msg.user-list.password"/></th>
        <th><fmt:message key="msg.user-list.active"/></th>
        <th><fmt:message key="msg.user-list.accountNonLocked"/></th>
        <th><fmt:message key="msg.user-list.lastPasswordChange"/></th>
        <th><fmt:message key="msg.user-list.passwordExpiresAt"/></th>
        <th><fmt:message key="msg.user-list.failedLoginAttempts"/></th>
        <th><fmt:message key="msg.user-list.emailVerified"/></th>
        <th><fmt:message key="msg.user-list.mustChangePassword"/></th>
        <th><fmt:message key="msg.user-list.actions"/></th>
    </tr>
    </thead>

    <tbody>

    <c:forEach var="user" items="${users}">

        <tr>
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.passwordHash}</td>
            <td>${user.active}</td>
            <td>${user.accountNonLocked}</td>
            <td>${user.lastPasswordChange}</td>
            <td>${user.passwordExpiresAt}</td>
            <td>${user.failedLoginAttempts}</td>
            <td>${user.emailVerified}</td>
            <td>${user.mustChangePassword}</td>

            <td>

                <c:url var="editUrl" value="/users">
                    <c:param name="action" value="edit"/>
                    <c:param name="id" value="${user.id}"/>
                </c:url>

                <a href="${editUrl}">
                    <fmt:message key="msg.user-list.edit"/>
                </a>

                <form action="${pageContext.request.contextPath}/users"
                      method="POST"
                      style="display:inline">

                    <input type="hidden"
                           name="action"
                           value="delete">

                    <input type="hidden"
                           name="id"
                           value="${user.id}">

                    <fmt:message key="msg.user-list.confirm" var="confirmMessage"/>

                    <button type="submit"
                            onclick="return confirm('${confirmMessage}')">
                        <fmt:message key="msg.user-list.delete"/>
                    </button>

                </form>

            </td>
        </tr>

    </c:forEach>

    </tbody>

</table>

<%@ include file="/partials/footer.jsp" %>