<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="link" class="java.lang.String" scope="session"/>

<tags:master pageTitle="Welcome">
    <h1 class="text">Welcome to our website!</h1>
    <form method="get" action="${link}">
        <button type="submit">Proceed</button>
    </form>
</tags:master>