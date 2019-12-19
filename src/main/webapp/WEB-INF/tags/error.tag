<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="parameter" type="java.lang.String" required="true" %>
<%@ attribute name="errors" type="java.util.Map" required="true" %>


<c:if test="${not empty errors[parameter]}">
    <span style="color:red; white-space: pre-line">${errors[parameter]}</span>
</c:if>