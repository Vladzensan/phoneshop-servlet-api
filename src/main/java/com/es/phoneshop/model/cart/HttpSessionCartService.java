package com.es.phoneshop.model.cart;

import com.es.phoneshop.exceptions.OutOfStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

public class HttpSessionCartService implements CartService {
    private static final String CART_ATTRIBUTE = CartService.class + ".cart";

    private HttpSessionCartService() {
    }

    public static HttpSessionCartService getInstance() {
        return CartServiceHolder.cartService;
    }

    public Cart getCart(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute(CART_ATTRIBUTE, cart);
        }

        return cart;
    }

    public void add(Cart cart, Product product, int quantity) {
        Optional<CartItem> optionalItem = findItemByProduct(cart, product);

        CartItem cartItem;
        int totalQuantity = quantity + optionalItem
                .map(CartItem::getQuantity)
                .orElse(0);

        validateNewQuantity(product, totalQuantity);

        cartItem = optionalItem.orElse(new CartItem(product, totalQuantity));
        cartItem.setQuantity(totalQuantity);

        if (!optionalItem.isPresent()) {
            cart.getCartItems().add(cartItem);
        }

        recalculateTotals(cart);
    }

    @Override
    public void update(Cart cart, Product product, int newQuantity) {
        if (newQuantity == 0) {
            delete(cart, product);
            return;
        }

        Optional<CartItem> item = findItemByProduct(cart, product);

        validateNewQuantity(product, newQuantity);

        item.ifPresent(cartItem -> cartItem.setQuantity(newQuantity));
        recalculateTotals(cart);
    }

    @Override
    public void delete(Cart cart, Product product) {
        cart.getCartItems().removeIf(item -> item.getProduct().equals(product));
        recalculateTotals(cart);
    }

    private Optional<CartItem> findItemByProduct(Cart cart, Product product) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProduct().equals(product))
                .findAny();
    }

    private void validateNewQuantity(Product product, int newQuantity) {
        if (product.getStock() < newQuantity) {
            throw new OutOfStockException(product.getStock());
        }
    }

    private void recalculateTotals(Cart cart) {
        BigDecimal totalCost = cart.getCartItems().stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, (sum, second) -> sum.add(second));
        cart.setTotalCost(totalCost);
    }

    private static class CartServiceHolder {
        final static HttpSessionCartService cartService = new HttpSessionCartService();
    }
}