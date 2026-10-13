<%@ include file="/partials/header.jsp" %>

<h1>
    <fmt:message key="${user == null ? 'msg.user-form.add' : 'msg.user-form.edit'}"/>
</h1>

<c:if test="${not empty error}">
    <p>
        ${error}
    </p>
</c:if>

<form action="${pageContext.request.contextPath}/users" method="POST">

    <input type="hidden"
           name="action"
           value="${user == null ? 'insert' : 'update'}">

    <c:if test="${user != null}">
        <input type="hidden"
               name="id"
               value="${user.id}">
    </c:if>

    <label for="username">
        <fmt:message key="msg.user-form.username"/>:
    </label>

    <input type="text"
           name="username"
           id="username"
           maxlength="40"
           required
           value="${user != null ? user.username : ''}">

    <br><br>

    <label for="passwordHash">
        <fmt:message key="msg.user-form.password"/>:
    </label>

    <input type="text"
           name="passwordHash"
           id="passwordHash"
           maxlength="500"
           required
           value="${user != null ? user.passwordHash : ''}">

    <br><br>

    <label for="active">
        <fmt:message key="msg.user-form.active"/>:
    </label>

    <input type="checkbox"
           name="active"
           id="active"
           value="true"
           <c:if test="${user != null && user.active}">checked</c:if>>

    <br><br>

    <label for="accountNonLocked">
        <fmt:message key="msg.user-form.accountNonLocked"/>:
    </label>

    <input type="checkbox"
           name="accountNonLocked"
           id="accountNonLocked"
           value="true"
           <c:if test="${user != null && user.accountNonLocked}">checked</c:if>>

    <br><br>

    <label for="lastPasswordChange">
        <fmt:message key="msg.user-form.lastPasswordChange"/>:
    </label>

    <input type="datetime-local"
           name="lastPasswordChange"
           id="lastPasswordChange"
           required
           value="${user != null ? user.lastPasswordChange : ''}">

    <br><br>

    <label for="failedLoginAttempts">
        <fmt:message key="msg.user-form.failedLoginAttempts"/>:
    </label>

    <input type="number"
           name="failedLoginAttempts"
           id="failedLoginAttempts"
           min="0"
           required
           value="${user != null ? user.failedLoginAttempts : 0}">

    <br><br>

    <label for="emailVerified">
        <fmt:message key="msg.user-form.emailVerified"/>:
    </label>

    <input type="checkbox"
           name="emailVerified"
           id="emailVerified"
           value="true"
           <c:if test="${user != null && user.emailVerified}">checked</c:if>>

    <br><br>

    <label for="mustChangePassword">
        <fmt:message key="msg.user-form.mustChangePassword"/>:
    </label>

    <input type="checkbox"
           name="mustChangePassword"
           id="mustChangePassword"
           value="true"
           <c:if test="${user != null && user.mustChangePassword}">checked</c:if>>

    <br><br>

    <fmt:message
            key="${user == null ? 'msg.user-form.create' : 'msg.user-form.update'}"
            var="buttonText"/>

    <input type="submit"
           value="${buttonText}">

</form>

<br>

<a href="${pageContext.request.contextPath}/users">
    <fmt:message key="msg.user-form.returnback"/>
</a>

<%@ include file="/partials/footer.jsp" %>