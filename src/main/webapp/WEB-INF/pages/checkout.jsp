<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Checkout">

    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="price">Price</td>
            <td>Quantity</td>
        </tr>
        </thead>
        <c:forEach var="item" items="${order.cartItems}">
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
                <td>${item.quantity}</td>

            </tr>
        </c:forEach>
        <tr>
            <td colspan="2">Subtotal</td>
            <td class="price">${order.subTotal}</td>
        </tr>
        <tr>
            <td colspan="2">Delivery cost</td>
            <td class="price">${order.deliveryCost}</td>
        </tr>
        <tr>
            <td colspan="2">Total cost</td>
            <td class="price">${order.totalCost}</td>
        </tr>
    </table>

    <form method="post" action="<c:url value="/checkout"/>">
        <p>
        <table>
            <tbody>
            <tr>
                <td colspan="2">Surname</td>
                <td><input name="Surname"> <tags:error parameter="Surname" errors="${errors}"/></td>
            </tr>
            <tr>
                <td colspan="2">Name</td>
                <td><input name="Name"> <tags:error parameter="Name" errors="${errors}"/></td>
            </tr>
            <tr>
                <td colspan="2">Phone number</td>
                <td><input name="Phone"> <tags:error parameter="Phone" errors="${errors}"/></td>
            </tr>
            <tr>
                <td colspan="2">Delivery address</td>
                <td><input name="Address"> <tags:error parameter="Address" errors="${errors}"/></td>
            </tr>
            <tr>
                <td colspan="2">Delivery date</td>
                <td><input name="Date"> <tags:error parameter="Date" errors="${errors}"/></td>
            </tr>
            <tr>
                <td colspan="2">Payment method</td>
                <td><select name="Payment">
                    <option value="Cash">Cash</option>
                    <option value="CreditCard">CreditCard</option>
                </select>
                </td>
            </tr>

            </tbody>
        </table>
        </p>

        <p>
            <button type="submit">Order</button>
        </p>
    </form>

</tags:master>