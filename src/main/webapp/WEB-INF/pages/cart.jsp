<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">

    <form id="form" method="post" action="<c:url value="/cart"/>">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="price">Price</td>
                <td>Quantity</td>
                <td>Actions</td>
            </tr>
            </thead>
            <c:forEach var="item" items="${cart.cartItems}">
                <tr>
                    <td>
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                    </td>
                    <td><a href="<c:url value="/products/${item.product.id}"/>">${item.product.description}</a></td>
                    <td class="price">
                        <fmt:formatNumber value="${item.product.price}" type="currency"
                                          currencySymbol="${item.product.currency.symbol}"/>
                    </td>
                    <td><input value="${item.quantity}" name="quantity" type="number" min="1">
                        <input type="hidden" name="productId" value="${item.product.id}">
                    </td>
                    <td>
                        <button form="delete" name="productId" value="${item.product.id}">Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <c:if test="${not empty error}">
            <span style="color:red; white-space: pre-line">${error}</span>
        </c:if>
        <c:if test="${param.success}">
            <span style="color:green">Updated successfully</span>
        </c:if>
    </form>

    <form id="delete" method="post" action="<c:url value="/cart/deleteCartItem"/>"></form>

    <input type="submit" value="update" form="form"/>
    <p>
    <c:if test="${not empty cart.cartItems}">
        <form method="get" action="<c:url value="/checkout"/>">
            <button>Checkout</button>
        </form>
    </c:if>
    </p>

</tags:master>